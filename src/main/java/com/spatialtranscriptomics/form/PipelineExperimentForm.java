/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.form;

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

	
	public String getExperimentName() {
		return experimentName;
	}

	public void setExperimentName(String experimentName) {
		this.experimentName = experimentName;
	}
	
	public String getNumNodes() {
		return numNodes;
	}

	public void setNumNodes(String numNodes) {
		this.numNodes = numNodes;
	}
	
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getNodeTypeMaster() {
		return nodeTypeMaster;
	}

	public void setNodeTypeMaster(String nodeTypeMaster) {
		this.nodeTypeMaster = nodeTypeMaster;
	}

	public String getNodeTypeSlave() {
		return nodeTypeSlave;
	}

	public void setNodeTypeSlave(String nodeTypeSlave) {
		this.nodeTypeSlave = nodeTypeSlave;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public String getIdFile() {
		return idFile;
	}

	public void setIdFile(String idFile) {
		this.idFile = idFile;
	}

	public String getReferenceAnnotation() {
		return referenceAnnotation;
	}

	public void setReferenceAnnotation(String referenceAnnotation) {
		this.referenceAnnotation = referenceAnnotation;
	}

	public String getReferenceGenome() {
		return referenceGenome;
	}

	public void setReferenceGenome(String referenceGenome) {
		this.referenceGenome = referenceGenome;
	}
	
	public String getBowtieFile() {
		return bowtieFile;
	}

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
