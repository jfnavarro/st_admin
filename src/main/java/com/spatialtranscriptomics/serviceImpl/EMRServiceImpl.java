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
 * This class implements the logic to start/stop/monitor Amazon Elastic MapReduce (EMR) JobFlows.
 * The connection to the EMR is handled in a AmazonElasticMapReduceClient object, which is configured in mvc-dispatcher-servlet.xml
 */

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

	private @Value("${s3.idfilespath}")
	String idfilesPath;

	private @Value("${s3.bowtiepath}")
	String bowtiePath;

	private @Value("${s3.genomesname}")
	String genomesName;

	private @Value("${s3.rnagenomesname}")
	String rnaGenomesName;

	private @Value("${s3.bootstrapperpath}")
	String bootstrapperPath;

	private @Value("${s3.mergerpath}")
	String mergerPath;

	private @Value("${s3.mapperpath}")
	String mapperPath;

	private @Value("${s3.reducerpath}")
	String reducerPath;

	// see
	// http://stackoverflow.com/questions/9283929/how-to-specify-mapred-configurations-java-options-with-custom-jar-in-cli-using
        @Override
	public String startJobFlow(PipelineExperimentForm j, String experimentId) {

		// set args for Pipeline mapper script
		List<String> mapperArgs = new ArrayList<String>();
		mapperArgs.add(" --expName " + "pms");
		mapperArgs.add(" --ids " + j.getIdFile());
		mapperArgs.add(" --ref-annotation " + j.getReferenceAnnotation());
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
		mapperArgs.add(" --bowtie-threads " + "8");//j.getBowtieThreads());
		mapperArgs.add(" --min-quality-trimming " + j.getMinQualityTrim());
		mapperArgs.add(" --chunks " + j.getChunks());

		if (!j.getBowtieFile().isEmpty()) {
			mapperArgs.add(" --contaminant-bowtie2-index "
					+ "rnagenome/rnagenome");
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

		// concatenate all args to a single String
		String mapperArgsString = "";
		for (String s : mapperArgs) {
			mapperArgsString += s;
		}

		// Set args for MapReduce Jobflow config

		// bootstrap script path
		String bootstrapScript = "s3n://" + pipelineBucket + "/"
				+ bootstrapperPath;

		// log dir path 
		String logDir = "s3n://" + pipelineBucket + "/" + experimentsPath
				+ experimentId + "/log/";

		// input dir of merge step
		String mergeInputDir = "s3n://" + pipelineBucket + "/" + inputPath
				+ j.getFolder() + "/";

		// output dir of merge step
		String mergeOutputDir = "s3n://" + pipelineBucket + "/"
				+ experimentsPath + experimentId + "/merged/";

		// .jar file for merge step
		String mergeJar = "s3n://" + pipelineBucket + "/" + mergerPath;

		// input dir of pipeline step
		String pipelineInputDir = mergeOutputDir;

		// output dir of pipeline step
		String pipelineOutputDir = "s3n://" + pipelineBucket + "/"
				+ experimentsPath + experimentId + "/output/";

		// mapper script path (the mapper script is copied to /home dir of all
		// EMR nodes at bootstrap, so we give just a file name here)
		String pipelineMapper = mapperPath + mapperArgsString;

		// reducer script path (similar to mapper)
		String pipelineReducer = reducerPath;

		// hadoop arg to copy "genome" config files to all EMR nodes
		String cacheGenome = "s3n://" + pipelineBucket + "/" + genomesPath
				+ j.getReferenceGenome() + "/" + genomesName;

		// hadoop arg to copy "rnagenome" (Bowtie2 indexes) config files to all
		// EMR nodes, if selected by user
		String cacheRNAGenome = "";
		if (!j.getBowtieFile().isEmpty()) {
			cacheRNAGenome = "s3n://" + pipelineBucket + "/" + bowtiePath
					+ j.getBowtieFile() + "/" + rnaGenomesName;
		}

		// hadoop arg to copy "annotation" config file to all EMR nodes
		String cacheAnnotation = "s3n://" + pipelineBucket + "/"
				+ annotationsPath + j.getReferenceAnnotation() + "#"
				+ j.getReferenceAnnotation();

		// hadoop arg to copy "ID" config file to all EMR nodes
		String cacheIds = "s3n://" + pipelineBucket + "/" + idfilesPath
				+ j.getIdFile() + "#" + j.getIdFile();

		// EMR node types and number of nodes
		String nodeTypeMaster = j.getNodeTypeMaster();
		String nodeTypeSlave = j.getNodeTypeSlave();
		int numNodes = Integer.parseInt(j.getNumNodes());

		logger.error("Merge In: " + mergeInputDir);
		logger.error("Merge Out: " + mergeOutputDir);
		logger.error("Pipeline In: " + pipelineInputDir);
		logger.error("Pipeline out: " + pipelineOutputDir);
		logger.error("Mapper: " + pipelineMapper);
		logger.error("Reducer: " + pipelineReducer);
		logger.error("Logs: " + logDir);
		logger.error("bootstrap: " + bootstrapScript);
		logger.error("cachegenome: " + cacheGenome);
		logger.error("cache rna genome: " + cacheRNAGenome);

		logger.error("cacheAnnotation: " + cacheAnnotation);
		logger.error("cacheIds: " + cacheIds);

		// Setup "Merge Inputdata" step

		HadoopJarStepConfig mergeJarConfig = new HadoopJarStepConfig().withJar(
				mergeJar).withArgs("-if", "fastq", mergeInputDir,
				mergeOutputDir);

		StepConfig mergeStepConfig = new StepConfig("Merge Input Data Step",
				mergeJarConfig);

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
		if (!j.getBowtieFile().isEmpty()) {
			streamingArgs.add("-cacheArchive");
			streamingArgs.add(cacheRNAGenome);
		}

		HadoopJarStepConfig pipelineJarConfig = new StreamingStep()
				.toHadoopJarStepConfig();
		pipelineJarConfig.withArgs(streamingArgs);

		StepConfig pipelineStepConfig = new StepConfig().withName(
				"Run Pipeline Step").withHadoopJarStep(pipelineJarConfig);

		// Setup MapReduce JobFlow

		ScriptBootstrapActionConfig sba = new ScriptBootstrapActionConfig()
				.withPath(bootstrapScript);

		BootstrapActionConfig bootstrapConfig = new BootstrapActionConfig()
				.withName("Bootstrap").withScriptBootstrapAction(sba);

		JobFlowInstancesConfig instanceConfig = new JobFlowInstancesConfig()
				.withInstanceCount(numNodes)
				.withMasterInstanceType(nodeTypeMaster)
				.withSlaveInstanceType(nodeTypeSlave)
				.withHadoopVersion("1.0.3");

		RunJobFlowRequest req = new RunJobFlowRequest()
				.withName("Pipeline JobFlow").withInstances(instanceConfig)
				.withSteps(mergeStepConfig, pipelineStepConfig)
				.withLogUri(logDir).withAmiVersion("2.4.1")
				.withBootstrapActions(bootstrapConfig);

		emrClient.setRegion(Region.getRegion(Regions.EU_WEST_1));

		// start MapReduce JobFlow

		RunJobFlowResult jobFlowResult = emrClient.runJobFlow(req);
		return jobFlowResult.getJobFlowId();

	}

	@Override
	public void stopJobFlow(String jobFlowId) {
		if (jobFlowId == null) {
			return;
		}
		List<String> idAsList = new ArrayList<String>();
		idAsList.add(jobFlowId);
		TerminateJobFlowsRequest req = new TerminateJobFlowsRequest(idAsList);

		emrClient.setRegion(Region.getRegion(Regions.EU_WEST_1));
		emrClient.terminateJobFlows(req);
	}

	@Override
	public JobFlowDetail findJobFlow(String jobFlowId) {
		//System.out.println("EMR name: " + emrClient.getServiceName());
		//System.out.println("EMR tostring: " + emrClient.toString());
		
		//System.out.println("EMR describe cluster: " + emrClient.describeCluster().toString());
		if (jobFlowId == null) {
			return null;
		}
		List<String> idAsList = new ArrayList<String>();
		idAsList.add(jobFlowId);
		//System.out.println("Getting...");
		DescribeJobFlowsRequest req = new DescribeJobFlowsRequest(idAsList);
		//System.out.println("Got: " + req.toString());
		
		emrClient.setRegion(Region.getRegion(Regions.EU_WEST_1));
		//System.out.println("Set region...");
		
		DescribeJobFlowsResult res = emrClient.describeJobFlows(req);
		List<JobFlowDetail> jobflows = res.getJobFlows();
		//System.out.println("Got results" + res.toString());
		//System.out.println("Got jobflows of size: " + jobflows.size());
		
		if (jobflows.size() < 1) {
			return null;
		}
		return jobflows.get(0);
	}

}
