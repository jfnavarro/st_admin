/*
 *Copyright © 2012 Spatial Transcriptomics AB
 *Read LICENSE for more information about licensing terms
 *Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
 * 
 */
package com.spatialtranscriptomics.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.spatialtranscriptomics.form.PipelineExperimentForm;
import com.spatialtranscriptomics.service.EMRService;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduceClient;
import com.amazonaws.services.elasticmapreduce.model.BootstrapActionConfig;
import com.amazonaws.services.elasticmapreduce.model.DescribeJobFlowsRequest;
import com.amazonaws.services.elasticmapreduce.model.DescribeJobFlowsResult;
import com.amazonaws.services.elasticmapreduce.model.HadoopJarStepConfig;
import com.amazonaws.services.elasticmapreduce.model.JobFlowDetail;
import com.amazonaws.services.elasticmapreduce.model.JobFlowInstancesConfig;
import com.amazonaws.services.elasticmapreduce.model.RunJobFlowRequest;
import com.amazonaws.services.elasticmapreduce.model.RunJobFlowResult;
import com.amazonaws.services.elasticmapreduce.model.ScriptBootstrapActionConfig;
import com.amazonaws.services.elasticmapreduce.model.StepConfig;
import com.amazonaws.services.elasticmapreduce.model.TerminateJobFlowsRequest;
import com.amazonaws.services.elasticmapreduce.util.StreamingStep;

/**
 * This class implements the logic to start/stop/monitor Amazon Elastic
 * MapReduce (EMR) JobFlows. The connection to the EMR is handled in a
 * AmazonElasticMapReduceClient object, which is configured in
 * mvc-dispatcher-servlet.xml
 */
//TODO update set up and congiruation of EMR with new hadoop version
//TODO might be possible to add new features for retrieving job status etc...
@Service
public class EMRServiceImpl implements EMRService {

    private static final Logger logger = Logger.getLogger(EMRServiceImpl.class);

    @Autowired
    AmazonElasticMapReduceClient emrClient;

    private @Value("${s3.pipelinebucket}")
    String pipelineBucket;

    private @Value("${s3.inputpath}")
    String inputPath;

    private @Value("${s3.experimentspath}")
    String experimentsPath;

    private @Value("${s3.genomespath}")
    String genomesPath;

    private @Value("${s3.annotationspath}")
    String annotationsPath;

    private @Value("${s3.idspath}")
    String idfilesPath;

    private @Value("${s3.contaminantpath}")
    String contaminantPath;

    private @Value("${s3.genomesname}")
    String genomesName;

    private @Value("${s3.contaminantname}")
    String rnaGenomesName;

    private @Value("${s3.bootstrapperpath}")
    String bootstrapperPath;

    private @Value("${s3.mergerpath}")
    String mergerPath;

    private @Value("${s3.mapperpath}")
    String mapperPath;

    private @Value("${s3.reducerpath}")
    String reducerPath;

    // See:
    // http://stackoverflow.com/questions/9283929/how-to-specify-mapred-configurations-java-options-with-custom-jar-in-cli-using
    @Override
    public String startJobFlow(PipelineExperimentForm j, String experimentId) {

        logger.info("Amazon EMR: About to start job for experiment " + experimentId);
        
        // set args for Pipeline mapper script
        List<String> mapperArgs = new ArrayList<String>();
        //name in this case is irrelevant as there is a run for each node
        mapperArgs.add(" --expName " + experimentId);
        mapperArgs.add(" --ids " + j.getIdFile());
        mapperArgs.add(" --ref-annotation " + j.getReferenceAnnotation());
        //the selected genome will be copied in the running instance as genome/genome
        mapperArgs.add(" --ref-map genome/genome");
        mapperArgs.add(" --allowed-missed " + j.getAllowMissed());
        mapperArgs.add(" --allowed-kimer " + j.getkMerLength());
        mapperArgs.add(" --min-length-qual-trimming " + j.getMinSeqLength());
        mapperArgs.add(" --mapping-fw-trimming " + j.getNumBasesTrimFw());
        mapperArgs.add(" --mapping-rv-trimming " + j.getNumBasesTrimRev());
        mapperArgs.add(" --length-id " + j.getBarcodeLength());
        mapperArgs.add(" --htseq-mode " + j.getHtseqAnnotationMode());
        mapperArgs.add(" --start-id " + j.getBarcodeStartPos());
        mapperArgs.add(" --error-id " + j.getIdPosError());
        mapperArgs.add(" --bowtie-threads " + "8");
        mapperArgs.add(" --min-quality-trimming " + j.getMinQualityTrim());
        //activating this for now
        mapperArgs.add(" --verbose ");
        //TODO to get temp files, qa parameters and logs
        //we could use these option to output directly to S3
        //they would have to be moved after reducing and merging
        //same for the logs
        //mapperArgs.add(" --output-folder ");
        //mapperArgs.add(" --temp-folder ");
        //mapperArgs.add(" --keep-discarded-files ");
        //mapperArgs.add(" --no-clean-up ");
        //mapperArgs.add(" --log-file " + experimentId + "_LOG.txt");
        
        if (!j.getContaminantGenome().isEmpty()) {
            //rnagnome is the local path in the running node as the
            //selected contaminang genome will be copied there with that name
            mapperArgs.add(" --contaminant-bowtie2-index " + "rnagenome/rnagenome");
        }
        
        if (j.isPhred64Quality()) {
            mapperArgs.add(" --qual-64 ");
        }
        
        if (j.isHtSeqDisregard()) {
            mapperArgs.add(" --htseq-no-ambiguous ");
        }
        
        if (j.isDiscardFw()) {
            mapperArgs.add(" --discard-fw ");
        }
        
        if (j.isDiscardRev()) {
            mapperArgs.add(" --discard-rv ");
        }
        
        if (j.isInclNonDiscordant()) {
            mapperArgs.add(" --bowtie2-discordant ");
        }

        if (j.getIncMolBarcodes()) {
            mapperArgs.add(" --molecular-barcodes ");
            mapperArgs.add(" --mc-allowed-missmatches " + j.getMolBarcodesMissMatches());
            mapperArgs.add(" --mc-start-position " + j.getMolBarcodesStart());
            mapperArgs.add(" --mc-end-position " + j.getMolBarcodesEnd());
            mapperArgs.add(" --min-cluster-size " + j.getMolBarcodesMinClusterSize());
        }
        
        // concatenate all args to a single String
        String mapperArgsString = "";
        for (String s : mapperArgs) {
            mapperArgsString += s;
        }

	// Set args for MapReduce Jobflow config
        // bootstrap script path
        String bootstrapScript = "s3n://" + pipelineBucket + "/" + bootstrapperPath;

        // log dir path for EMR logs
        String logDir = "s3n://" + pipelineBucket + "/" + experimentsPath
                + experimentId + "/log/";

        // input dir of pre-processing step for the reads to merge them into one
        String mergeInputDir = "s3n://" + pipelineBucket + "/" + inputPath
                + j.getFolder() + "/";

        // output dir of merge step where the merged reads will be placed
        String mergeOutputDir = "s3n://" + pipelineBucket + "/"
                + experimentsPath + experimentId + "/merged/";

        // .jar file for merge step tool
        String mergeJar = "s3n://" + pipelineBucket + "/" + mergerPath;

        // input dir of pipeline step
        String pipelineInputDir = mergeOutputDir;

        // output dir of pipeline step
        String pipelineOutputDir = "s3n://" + pipelineBucket + "/"
                + experimentsPath + experimentId + "/output/";

	// pipeline mapper script path (the mapper script is copied to /home dir of all
        // EMR nodes at bootstrap, so we give just a file name here)
        String pipelineMapper = mapperPath + mapperArgsString;

        // pipeline reducer script path (similar to mapper)
        String pipelineReducer = reducerPath;

        // hadoop arg to copy "genome" config files to all EMR nodes
        String cacheGenome = "s3n://" + pipelineBucket + "/" + genomesPath
                + j.getReferenceGenome() + "/" + genomesName;

	// hadoop arg to copy "rnagenome" (Bowtie2 indexes) config files to all
        // EMR nodes, if selected by user
        String cacheRNAGenome = "";
        if (!j.getContaminantGenome().isEmpty()) {
            cacheRNAGenome = "s3n://" + pipelineBucket + "/" + contaminantPath
                    + j.getContaminantGenome() + "/" + rnaGenomesName;
        }

        // hadoop arg to copy "annotation" config file to all EMR nodes
        String cacheAnnotation = "s3n://" + pipelineBucket + "/"
                + annotationsPath + j.getReferenceAnnotation() + "#"
                + j.getReferenceAnnotation();

        // hadoop arg to copy "ID" config file to all EMR nodes
        String cacheIds = "s3n://" + pipelineBucket + "/" + idfilesPath
                + j.getIdFile() + "#" + j.getIdFile();

        // EMR node types and number of nodes
        //TODO we should optimize this variables
        String nodeTypeMaster = "t2.medium";
        String nodeTypeSlave = "r3.xlarge";
        int numNodes = Integer.parseInt("10");

        logger.info("Amazon EMR Merge In: " + mergeInputDir);
        logger.info("Amazon EMR Merge Out: " + mergeOutputDir);
        logger.info("Amazon EMR Pipeline In: " + pipelineInputDir);
        logger.info("Amazon EMR Pipeline out: " + pipelineOutputDir);
        logger.info("Amazon EMR Mapper: " + pipelineMapper);
        logger.info("Amazon EMR Reducer: " + pipelineReducer);
        logger.info("Amazon EMR Logs: " + logDir);
        logger.info("Amazon EMR bootstrap: " + bootstrapScript);
        logger.info("Amazon EMR cachegenome: " + cacheGenome);
        logger.info("Amazon EMR cache rna genome: " + cacheRNAGenome);
        logger.info("Amazon EMR cacheAnnotation: " + cacheAnnotation);
        logger.info("Amazon EMR cacheIds: " + cacheIds);

	// Setup "Merge Inputdata" step
        HadoopJarStepConfig mergeJarConfig = new HadoopJarStepConfig().withJar(
                mergeJar).withArgs("-if", "fastq", mergeInputDir, mergeOutputDir);
        StepConfig mergeStepConfig = new StepConfig("Merge Input Data Step", mergeJarConfig);

	// Setup "Pipeline" step
        // set streaming args
        List<String> streamingArgs = new ArrayList<String>();
        streamingArgs.add("-cacheArchive");
        streamingArgs.add(cacheGenome);
        streamingArgs.add("-cacheFile");
        streamingArgs.add(cacheAnnotation);
        streamingArgs.add("-cacheFile");
        streamingArgs.add(cacheIds);
        streamingArgs.add("-input");
        streamingArgs.add(pipelineInputDir);
        streamingArgs.add("-output");
        streamingArgs.add(pipelineOutputDir);
        streamingArgs.add("-mapper");
        streamingArgs.add(pipelineMapper);
        streamingArgs.add("-reducer");
        streamingArgs.add(pipelineReducer);

        // cache rna genome only if selected by user
        if (!j.getContaminantGenome().isEmpty()) {
            streamingArgs.add("-cacheArchive");
            streamingArgs.add(cacheRNAGenome);
        }

        // setup EMR pipeline step
        HadoopJarStepConfig pipelineJarConfig = new StreamingStep().toHadoopJarStepConfig();
        pipelineJarConfig.withArgs(streamingArgs);
        StepConfig pipelineStepConfig = new StepConfig().withName(
                "Run Pipeline Step").withHadoopJarStep(pipelineJarConfig);

	// Setup Bootstrap for EMR nodes
        ScriptBootstrapActionConfig sba = new ScriptBootstrapActionConfig()
                .withPath(bootstrapScript);
        BootstrapActionConfig bootstrapConfig = new BootstrapActionConfig()
                .withName("Bootstrap").withScriptBootstrapAction(sba);

        // EMR nodes configuration
        JobFlowInstancesConfig instanceConfig = new JobFlowInstancesConfig()
                .withInstanceCount(numNodes)
                .withMasterInstanceType(nodeTypeMaster)
                .withSlaveInstanceType(nodeTypeSlave)
                .withHadoopVersion("2.4.0");

        // Setup whole pipeline EMR run
        RunJobFlowRequest req = new RunJobFlowRequest()
                .withName("Pipeline JobFlow").withInstances(instanceConfig)
                .withSteps(mergeStepConfig, pipelineStepConfig)
                .withLogUri(logDir).withAmiVersion("2.4.1")
                .withBootstrapActions(bootstrapConfig);

        // Important to set region 
        emrClient.setRegion(Region.getRegion(Regions.EU_WEST_1));

	// start MapReduce JobFlow
        logger.info("Amazon EMR: Applying to run jub flow.");
        RunJobFlowResult jobFlowResult = emrClient.runJobFlow(req);
        logger.info("Amazon EMR: Got job flow result ID " + jobFlowResult.getJobFlowId());
        return jobFlowResult.getJobFlowId();
    }

    @Override
    public void stopJobFlow(String jobFlowId) {
        
        if (jobFlowId == null) {
            return;
        }
        
        logger.info("Amazon EMR: About to stop job flow " + jobFlowId);
        List<String> idAsList = new ArrayList<String>();
        idAsList.add(jobFlowId);
        TerminateJobFlowsRequest req = new TerminateJobFlowsRequest(idAsList);
        emrClient.setRegion(Region.getRegion(Regions.EU_WEST_1));
        emrClient.terminateJobFlows(req);
    }

    // NOTE: Currently, Amazon will only return job details of jobs not older than
    // a couple of months.
    @Override
    public JobFlowDetail findJobFlow(String jobFlowId) {
	
        if (jobFlowId == null) {
            return null;
        }
        
        logger.info("Amazon EMR: About to find details of job flow " + jobFlowId);
        List<String> idAsList = new ArrayList<String>();
        idAsList.add(jobFlowId);
        DescribeJobFlowsRequest req = new DescribeJobFlowsRequest(idAsList);
        emrClient.setRegion(Region.getRegion(Regions.EU_WEST_1));
        DescribeJobFlowsResult res = emrClient.describeJobFlows(req);
        List<JobFlowDetail> jobflows = res.getJobFlows();

        if (jobflows.size() < 1) {
            logger.info("Amazon EMR: Could not find details of job flow " 
                    + jobFlowId + ". Is job too old?");
            return null;
        }
        
        logger.info("Amazon EMR: Found details of job flow " + jobFlowId);
        return jobflows.get(0);
    }

}
