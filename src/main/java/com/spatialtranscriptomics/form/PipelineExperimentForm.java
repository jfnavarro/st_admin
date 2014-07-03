/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.form;

import com.spatialtranscriptomics.component.StaticContextAccessor;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

/**
 * This class implements the model for the "create pipeline experiment form". 
 * Does validation using Hibernate validator constraints.
 * 
 * */

public class PipelineExperimentForm {
	
	@NotBlank
	private String experimentName;
	
	@NotBlank
	private String numNodes;
	
	@NotBlank
	private String accountId;
	
	@NotBlank
	private String nodeTypeMaster;
	
	@NotBlank
	private String nodeTypeSlave;
	
	@NotBlank
	private String folder;
	
	@NotBlank
	private String idFile;
	
	@NotBlank
	private String referenceAnnotation;
	
	@NotBlank
	private String referenceGenome;
	
	private String bowtieFile;

	private String htseqAnnotationMode;
	
	private int allowMissed;
	
	private int kMerLength;
	
	private int numBasesTrimFw;
	
	private int numBasesTrimRev;
	
	private int minSeqLength;
	
	private int barcodeLength;
	
	private boolean phred64Quality;
	
	private boolean htSeqDisregard;
	
	private int barcodeStartPos;
	
	private int idPosError;
	
//	private int bowtieThreads;
	
	private int minQualityTrim;
	
	private boolean discardFw;
	
	private boolean discardRev;
	
	private boolean inclNonDiscordant;
	
	private String comments;
	
	@Range(min = 1000, max = 1000000)
	private int chunks;

        public PipelineExperimentForm() {
            this.accountId = StaticContextAccessor.getCurrentUser().getId();
        }
        
	/**
         * Returns the experiment name.
         * @return experiment name.
         */
	public String getExperimentName() {
		return experimentName;
	}

        /**
         * Sets the experiment name.
         * @param experimentName experiment name.
         */
	public void setExperimentName(String experimentName) {
		this.experimentName = experimentName;
	}
	
        /**
         * Returns the number of nodes.
         * @return the number of nodes.
         */
	public String getNumNodes() {
		return numNodes;
	}

        /**
         * Sets the number of nodes.
         * @param numNodes number of nodes.
         */
	public void setNumNodes(String numNodes) {
		this.numNodes = numNodes;
	}
	
        /**
         * Returns the account ID.
         * @return account ID.
         */
	public String getAccountId() {
		return accountId;
	}

        /**
         * Sets the account ID.
         * @param accountId account ID.
         */
	public void setAccountId(String accountId) {
            this.accountId = accountId;
	}

        /**
         * Returns the node type master.
         * @return node type master.
         */
	public String getNodeTypeMaster() {
		return nodeTypeMaster;
	}

        /**
         * Sets the node type master.
         * @param nodeTypeMaster node type master.
         */
	public void setNodeTypeMaster(String nodeTypeMaster) {
		this.nodeTypeMaster = nodeTypeMaster;
	}

        /**
         * Returns the node type slave.
         * @return node type slave.
         */
	public String getNodeTypeSlave() {
		return nodeTypeSlave;
	}

        /**
         * Sets the node type slave.
         * @param nodeTypeSlave node type slave.
         */
	public void setNodeTypeSlave(String nodeTypeSlave) {
		this.nodeTypeSlave = nodeTypeSlave;
	}

        /**
         * Returns the folder.
         * @return folder.
         */
	public String getFolder() {
		return folder;
	}

        /**
         * Sets the folder.
         * @param folder the folder.
         */
	public void setFolder(String folder) {
		this.folder = folder;
	}

        /**
         * Returns the ID file.
         * @return the ID file.
         */
	public String getIdFile() {
		return idFile;
	}

        /**
         * Sets the ID file.
         * @param idFile ID file.
         */
	public void setIdFile(String idFile) {
		this.idFile = idFile;
	}

        /**
         * Returns the ref annotation.
         * @return ref annotation.
         */
	public String getReferenceAnnotation() {
		return referenceAnnotation;
	}

        /**
         * Sets the ref annotation
         * @param referenceAnnotation ref annotation. 
         */
	public void setReferenceAnnotation(String referenceAnnotation) {
		this.referenceAnnotation = referenceAnnotation;
	}

        /**
         * Returns the ref genome.
         * @return ref genome.
         */
	public String getReferenceGenome() {
		return referenceGenome;
	}

        /**
         * Sets the ref genome.
         * @param referenceGenome ref genome.
         */
	public void setReferenceGenome(String referenceGenome) {
		this.referenceGenome = referenceGenome;
	}
	
        /**
         * Returns the Bowtie file.
         * @return Bowtie file.
         */
	public String getBowtieFile() {
		return bowtieFile;
	}

        /**
         * Sets the Bowtie file.
         * @param bowtieFile Bowtie file.
         */
	public void setBowtieFile(String bowtieFile) {
		this.bowtieFile = bowtieFile;
	}

	public String getHtseqAnnotationMode() {
		return htseqAnnotationMode;
	}

	public void setHtseqAnnotationMode(String htseqAnnotationMode) {
		this.htseqAnnotationMode = htseqAnnotationMode;
	}
	
	public int getAllowMissed() {
		return allowMissed;
	}

	public void setAllowMissed(int allowMissed) {
		this.allowMissed = allowMissed;
	}

	public int getkMerLength() {
		return kMerLength;
	}

	public void setkMerLength(int kMerLength) {
		this.kMerLength = kMerLength;
	}

	public int getNumBasesTrimFw() {
		return numBasesTrimFw;
	}

	public void setNumBasesTrimFw(int numBasesTrimFw) {
		this.numBasesTrimFw = numBasesTrimFw;
	}
	
	public int getNumBasesTrimRev() {
		return numBasesTrimRev;
	}

	public void setNumBasesTrimRev(int numBasesTrimRev) {
		this.numBasesTrimRev = numBasesTrimRev;
	}
	

	public int getMinSeqLength() {
		return minSeqLength;
	}

	public void setMinSeqLength(int minSeqLength) {
		this.minSeqLength = minSeqLength;
	}

	public int getBarcodeLength() {
		return barcodeLength;
	}

	public void setBarcodeLength(int barcodeLength) {
		this.barcodeLength = barcodeLength;
	}

	public boolean isPhred64Quality() {
		return phred64Quality;
	}

	public void setPhred64Quality(boolean phred64Quality) {
		this.phred64Quality = phred64Quality;
	}

	public boolean isHtSeqDisregard() {
		return htSeqDisregard;
	}

	public void setHtSeqDisregard(boolean htSeqDisregard) {
		this.htSeqDisregard = htSeqDisregard;
	}

	public int getBarcodeStartPos() {
		return barcodeStartPos;
	}

	public void setBarcodeStartPos(int barcodeStartPos) {
		this.barcodeStartPos = barcodeStartPos;
	}

	public int getIdPosError() {
		return idPosError;
	}

	public void setIdPosError(int idPosError) {
		this.idPosError = idPosError;
	}

//	public int getBowtieThreads() {
//		return bowtieThreads;
//	}
//
//	public void setBowtieThreads(int bowtieThreads) {
//		this.bowtieThreads = bowtieThreads;
//	}

	public int getMinQualityTrim() {
		return minQualityTrim;
	}

	public void setMinQualityTrim(int minQualityTrim) {
		this.minQualityTrim = minQualityTrim;
	}

	public boolean isDiscardFw() {
		return discardFw;
	}

	public void setDiscardFw(boolean discardFw) {
		this.discardFw = discardFw;
	}

	public boolean isDiscardRev() {
		return discardRev;
	}

	public void setDiscardRev(boolean discardRev) {
		this.discardRev = discardRev;
	}

	public boolean isInclNonDiscordant() {
		return inclNonDiscordant;
	}

	public void setInclNonDiscordant(boolean inclNonDiscordant) {
		this.inclNonDiscordant = inclNonDiscordant;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public int getChunks() {
		return chunks;
	}

	public void setChunks(int chunks) {
		this.chunks = chunks;
	}

	

}
