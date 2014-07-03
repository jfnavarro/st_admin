<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Create pipeline experiment</title>

<link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet"
	media="screen">

<!-- Boostrap and JQuery libraries, for the logout button and other JS features -->
<script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script
	src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>

<!-- Script to set the highlight the active menu in the header -->
<script>
	$(document).ready(function(event) {
		$("#menuPipelineExperiment").addClass("active");
	});
</script>
	
</head>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />

	<c:import url="header.jsp"></c:import>

	<div class="container">

		<div class="page-header">
			<h1>Create pipeline experiment</h1>
		</div>


		<c:if test="${not empty errors}">
			<div class="alert alert-error">
				<strong>Error: </strong>Your input is not valid. Please check the values in the form below.
			</div>
		</c:if>


		<div>
			<form:form method="POST" commandName="pipelineexperimentform"
				action="${contextPath}/pipelineexperiment/submitcreate"
				class="form-horizontal">


				<div class="row">
					<div class="span6">
						<legend>EMR parameters</legend>

						<!-- TODO VALIDATION -->

						<div class="control-group">
							<label class="control-label" for="experimentName">Pipeline experiment name</label>
							<div class="controls">
								<form:input type="text" id="experimentName"
									placeholder="Choose a name" path="experimentName" />
							</div>
						</div>


						<div class="control-group">
							<label class="control-label" for="numNodes">Number of instances</label>
							<div class="controls">
								<form:select id="numNodes" path="numNodes">
									<option></option>
									<form:options items="${numNodesChoices}" />
								</form:select>
							</div>
						</div>
						
					</div>

					<!-- end span -->


					<div class="span6">
						<legend>Pipeline parameters</legend>
						<div class="control-group">
							<label class="control-label" for="folder">Input data folder</label>
							<div class="controls">
								<form:select id="folder" path="folder">
									<option></option>
									<form:options items="${folderChoices}" />
								</form:select>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="idFile">Chip (IDs) file</label>
							<div class="controls">
								<form:select id="idFile" path="idFile">
									<option></option>
									<form:options items="${idFileChoices}" />
								</form:select>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="referenceAnnotation">Reference
								annotation file</label>
							<div class="controls">
								<form:select id="referenceAnnotation" path="referenceAnnotation">
									<option></option>
									<form:options items="${refAnnotationChoices}" />
								</form:select>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="referenceGenome">Reference genome bowtie2 index</label>
							<div class="controls">
								<form:select id="referenceGenome" path="referenceGenome">
									<option></option>
									<form:options items="${refGenomeChoices}" />
								</form:select>
							</div>
						</div>

						<!-- Toggle Optional button -->
						<div class="control-group pull-right">
							<button class="btn btn-link" type="button" class="btn btn-danger"
								data-toggle="collapse" data-target="#optional">Optional parameters</button>
						</div>

					</div>

				</div>
				<div id="optional" class="collapse out">

					<legend>Optional parameters</legend>
					<div class="row">
						<div class="span6">

							<div class="control-group">
							<label class="control-label" for="nodeTypeMaster">Master instance type</label>
							<div class="controls">
								<form:select id="nodeTypeMaster" path="nodeTypeMaster">
										<form:option selected="m1.large" label="M1Large (default)"
											value="m1.large"></form:option>
										<form:options items="${nodeTypeChoices}" />
									</form:select>
								</div>
							</div>
	
							<div class="control-group">
								<label class="control-label" for="nodeTypeSlave">Slave instance type</label>
								<div class="controls">
									<form:select id="nodeTypeSlave" path="nodeTypeSlave">
										<form:option selected="m1.xlarge" label="M1XLarge (default)"
											value="m1.xlarge"></form:option>
										<form:options items="${nodeTypeChoices}" />
									</form:select>
								</div>
							</div>

							<div class="control-group">
								<label class="control-label" for="allowMissed">Number of allowed mismatches for barcode mapping</label>
								<div class="controls">
									<form:input type="text" id="allowMissed" value="3"
										path="allowMissed" />
								</div>
							</div>

							<div class="control-group">
								<label class="control-label" for="kMerLength">Kmer for barcode mapping</label>
								<div class="controls">
									<form:input type="text" id="kMerLength" value="6"
										path="kMerLength" />
								</div>
							</div>

							<div class="control-group">
								<label class="control-label" for="numBasesTrimFw">Number of bases to trim (forward)</label>
								<div class="controls">
									<form:input type="text" id="numBasesTrimFw" value="42"
										path="numBasesTrimFw" />
								</div>
							</div>

							<div class="control-group">
								<label class="control-label" for="numBasesTrimRev">Number of bases to trim (reverse)</label>
								<div class="controls">
									<form:input type="text" id="numBasesTrimRev" value="5"
										path="numBasesTrimRev" />
								</div>
							</div>

							<div class="control-group">
								<label class="control-label" for="minSeqLength">Minimal sequence length after trimming</label>
								<div class="controls">
									<form:input type="text" id="minSeqLength" value="28"
										path="minSeqLength" />
								</div>
							</div>

							<div class="control-group">
								<label class="control-label" for="BarcodeLength">Barcode length</label>
								<div class="controls">
									<form:input type="text" id="BarcodeLength" value="18"
										path="BarcodeLength" />
								</div>
							</div>

							<div class="control-group">
								<label class="control-label" for="phred64Quality">Use phred-64 quality system (reads)</label>
								<div class="controls">
									<form:checkbox id="phred64Quality" path="phred64Quality" />
								</div>
							</div>

							<!--  Alternative Layout for checkboxes:
				<div class="control-group">
					<label class="checkbox"> <form:checkbox path="phred64Quality" />Phred-64 quality</label>
				</div>-->


							
						</div>

						<div class="span6">
							<!-- Second column of optional parameters -->

							<div class="control-group">
								<label class="control-label" for="bowtieFile">Contaminant genome bowtie2 index</label>
								<div class="controls">
									<form:select id="bowtieFile" path="bowtieFile">
										<form:option selected="none" label="none" value=""></form:option>
										<form:options items="${bowtieFileChoices}"></form:options>
									</form:select>
								</div>
							</div>

							<div class="control-group">
								<label class="control-label" for="htseqAnnotationMode">HTSeq type of annotation mode</label>
								<div class="controls">
									<form:select id="htseqAnnotationMode"
										path="htseqAnnotationMode">
										<form:option selected="intersection-nonempty"
											label="intersection-nonempty" value="intersection-nonempty"></form:option>
										<form:options items="${htseqAnnotationChoices}" />
									</form:select>
								</div>
							</div>

							<div class="control-group">
								<label class="control-label" for="BarcodeStartPos">Barcode start position in the sequence</label>
								<div class="controls">
									<form:input type="text" id="BarcodeStartPos" value="0"
										path="BarcodeStartPos" />
								</div>
							</div>

							<div class="control-group">
								<label class="control-label" for="idPosError">Barcode positional error</label>
								<div class="controls">
									<form:input type="text" id="idPosError" value="0"
										path="idPosError" />
								</div>
							</div>

							<div class="control-group">
								<label class="control-label" for="minQualityTrim">Minimum trimming quality</label>
								<div class="controls">
									<form:input type="text" id="minQualityTrim" value="20"
										path="minQualityTrim" />
								</div>
							</div>

							<div class="control-group">
								<label class="control-label" for="htSeqDisregard">HTSeq discard reads that annotates to ambigous genes</label>
								<div class="controls">
									<form:checkbox id="htSeqDisregard" path="htSeqDisregard" />
								</div>
							</div>

							<div class="control-group">
								<label class="control-label" for="discardFw">Discard forward reads that map uniquely</label>
								<div class="controls">
									<form:checkbox id="discardFw" path="discardFw" />
								</div>
							</div>

							<div class="control-group">
								<label class="control-label" for="discardRev">Discard reverse reads that map uniquely</label>
								<div class="controls">
									<form:checkbox id="discardRev" path="discardRev" />
								</div>
							</div>

							<div class="control-group">
								<label class="control-label" for="inclNonDiscordant">Discard non-discordant alignments when mapping</label>
								<div class="controls">
									<form:checkbox id="inclNonDiscordant" path="inclNonDiscordant" />
								</div>
							</div>

							<div class="control-group">
								<label class="control-label" for="chunks">Chunks size</label>
								<div class="controls">
									<form:input type="text" id="chunks" value="10000" path="chunks" />
								</div>
							</div>

						</div>
					</div>

					<!-- end optional div -->
					<legend></legend>
				</div>

				<div class="control-group">

					<button type="submit" class="btn btn-primary">Start pipeline</button>
				</div>

			</form:form>
		</div>

	</div>


</body>
</html>
