<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Create Dataset</title>
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
		$("#menuDataset").addClass("active");
	});
</script>

<!-- Script to style file upload -->
<script type="text/javascript"
	src="<c:url value="/js/bootstrap-filestyle.min.js"/>"></script>


</head>
<body>
	<c:import url="header.jsp"></c:import>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />

	<div class="container">


		<div class="page-header">
			<h1>Create Dataset</h1>
		</div>


		<c:if test="${not empty errors}">
			<div class="alert alert-error">
				<strong>Error: </strong>Your input is not valid. Please check the
				values in the form below.
			</div>
		</c:if>

		<c:if test="${not empty featureerror}">
			<div class="alert alert-error">
				<strong>Error: </strong>${featureerror}
			</div>
		</c:if>

		<div>

			<div class="row">
				<form:form method="POST" commandName="datasetform"
					action="${contextPath}/dataset/submitadd" class="form-horizontal"
					enctype="multipart/form-data">


					<fieldset>

						<div class="span6">
							<legend>General</legend>

							<spring:bind path="dataset.name">
								<div class="control-group  ${status.error ? 'error' : ''}">
									<label class="control-label" for="inputName">Name</label>
									<div class="controls">
										<form:input type="text" id="inputName" placeholder="Name"
											path="dataset.name" />
										<span class='help-inline'>${status.errorMessage}</span>
									</div>
								</div>
							</spring:bind>

							<spring:bind path="dataset.chipid">
								<div class="control-group  ${status.error ? 'error' : ''}">
									<label class="control-label" for="inputChip">Chip</label>
									<div class="controls">
										<form:select id="inputChip" path="dataset.chipid">
											<option></option>
											<form:options items="${chipChoices}"></form:options>
										</form:select>
										<span class='help-inline'>${status.errorMessage}</span>
									</div>
								</div>
							</spring:bind>


							<spring:bind path="dataset.figure_red">
								<div class="control-group  ${status.error ? 'error' : ''}">
									<label class="control-label" for="imageRed">Image Red</label>
									<div class="controls">
										<form:select id="imageRed" path="dataset.figure_red">
											<option></option>
											<form:options items="${imageChoices}"></form:options>
										</form:select>
										<span class='help-inline'>${status.errorMessage}</span>
									</div>
								</div>
							</spring:bind>


							<spring:bind path="dataset.figure_blue">
								<div class="control-group  ${status.error ? 'error' : ''}">
									<label class="control-label" for="imageBlue">Image Blue</label>
									<div class="controls">
										<form:select id="imageBlue" path="dataset.figure_blue">
											<option></option>
											<form:options items="${imageChoices}"></form:options>
										</form:select>
										<span class='help-inline'>${status.errorMessage}</span>
									</div>
								</div>
							</spring:bind>

							<spring:bind path="dataset.alignment_field1">
								<div class="control-group  ${status.error ? 'error' : ''}">
									<label class="control-label" for="alignment">Alignment
										Matrix</label>
									<div class="controls controls-row">
										<form:input class="span1" type="text" id="alignmentField1"
											path="dataset.alignment_field1" />
										<form:input class="span1" type="text" id="alignmentField2"
											path="dataset.alignment_field2" />
										<form:input class="span1" type="text" id="alignmentField3"
											path="dataset.alignment_field3" />
									</div>
									<div class="controls controls-row">
										<form:input class="span1" type="text" id="alignmentField4"
											path="dataset.alignment_field4" />
										<form:input class="span1" type="text" id="alignmentField5"
											path="dataset.alignment_field5" />
										<form:input class="span1" type="text" id="alignmentField6"
											path="dataset.alignment_field6" />
									</div>
									<div class="controls controls-row">
										<form:input class="span1" type="text" id="alignmentField7"
											path="dataset.alignment_field7" />
										<form:input class="span1" type="text" id="alignmentField8"
											path="dataset.alignment_field8" />
										<form:input class="span1" type="text" id="alignmentField9"
											path="dataset.alignment_field9" />
									</div>

								</div>

							</spring:bind>

							<!--  start features -->

							<legend>
								Features <small>(choose either experiment output or
									feature file)</small>
							</legend>

							<spring:bind path="experimentId">
								<div class="control-group  ${status.error ? 'error' : ''}">
									<label class="control-label" for="experiment">Experiment
										Output</label>
									<div class="controls">
										<form:select id="experimentId" path="experimentId">
											<option></option>
											<form:options items="${experimentChoices}"></form:options>
										</form:select>
										<span class='help-inline'>${status.errorMessage}</span>
									</div>
								</div>
							</spring:bind>

							<div class="pull-center text-center">
								<strong>-- or --</strong>
							</div>

							<!-- 		<spring:bind path="featureFile">
								<div class="control-group  ${status.error ? 'error' : ''}">
									<div class="control-group">
										<label class="control-label" for="featureFile">Feature
											File</label>
										<div class="controls">
											<form:input type="file" id="featureFile"
												placeholder="Feature file" path="featureFile" />
											<span class='help-inline'>${status.errorMessage}</span>
										</div>
									</div>
								</div>
							</spring:bind> -->

							<spring:bind path="featureFile">
								<div class="control-group  ${status.error ? 'error' : ''}">
									<label class="control-label" for="featureFile">Feature
										File</label>
									<div class="control-group">

										<div class="controls">
											<form:input type="file" id="featureFile"
												placeholder="Feature file" path="featureFile"
												class="filestyle" />
											<span class='help-inline'>${status.errorMessage}</span>
										</div>
									</div>
								</div>
							</spring:bind>





							<!-- end features -->



						</div>






						<div class="span4">
							<legend>Stats</legend>

							<spring:bind path="dataset.stat_barcodes">
								<div class="control-group  ${status.error ? 'error' : ''}">
									<label class="control-label" for="statBarcodes">Stat
										Barcodes</label>
									<div class="controls">
										<form:input type="text" id="statBarcodes"
											placeholder="Barcodes" path="dataset.stat_barcodes" />
										<span class='help-inline'>${status.errorMessage}</span>
									</div>
								</div>
							</spring:bind>

							<spring:bind path="dataset.stat_genes">
								<div class="control-group  ${status.error ? 'error' : ''}">
									<label class="control-label" for="statGenes">Stat Genes</label>
									<div class="controls">
										<form:input type="text" id="statGenes" placeholder="Genes"
											path="dataset.stat_genes" />
										<span class='help-inline'>${status.errorMessage}</span>
									</div>
								</div>
							</spring:bind>

							<spring:bind path="dataset.stat_unique_barcodes">
								<div class="control-group  ${status.error ? 'error' : ''}">

									<label class="control-label" for="statUniqueBarcodes">Stat
										Unique Barcodes</label>
									<div class="controls">
										<form:input type="text" id="statUniqueBarcodes"
											placeholder="Unique barcodes"
											path="dataset.stat_unique_barcodes" />
										<span class='help-inline'>${status.errorMessage}</span>
									</div>
								</div>
							</spring:bind>

							<spring:bind path="dataset.stat_unique_genes">
								<div class="control-group  ${status.error ? 'error' : ''}">
									<label class="control-label" for="statUniqueGenes">Stat
										Unique Genes</label>
									<div class="controls">
										<form:input type="text" id="statUniqueGenes"
											placeholder="Unique genes" path="dataset.stat_unique_genes" />
										<span class='help-inline'>${status.errorMessage}</span>
									</div>
								</div>
							</spring:bind>


							<spring:bind path="dataset.stat_tissue">
								<div class="control-group  ${status.error ? 'error' : ''}">
									<label class="control-label" for="statTissue">Stat
										Tissue</label>
									<div class="controls">
										<form:input type="text" id="statTissue" placeholder="Tissue"
											path="dataset.stat_tissue" />
										<span class='help-inline'>${status.errorMessage}</span>
									</div>
								</div>
							</spring:bind>

							<spring:bind path="dataset.stat_specie">
								<div class="control-group  ${status.error ? 'error' : ''}">
									<label class="control-label" for="statSpecie">Stat
										Specie</label>
									<div class="controls">
										<form:input type="text" id="statSpecie" placeholder="Specie"
											path="dataset.stat_specie" />
										<span class='help-inline'>${status.errorMessage}</span>
									</div>
								</div>
							</spring:bind>

							<spring:bind path="dataset.stat_comments">
								<div class="control-group  ${status.error ? 'error' : ''}">
									<label class="control-label" for="statComments">Stat
										Comments</label>
									<div class="controls">
										<form:textarea rows="5" id="statComments"
											placeholder="Free comments" path="dataset.stat_comments"></form:textarea>
										<span class='help-inline'>${status.errorMessage}</span>
									</div>
								</div>
							</spring:bind>

						</div>










					</fieldset>

					<div class="control-group">
						<button type="submit" class="btn">Create</button>
					</div>

				</form:form>
			</div>

		</div>

		<div>
			<a href="<c:url value="/dataset/"/>">Cancel</a>
		</div>

	</div>


	<!-- Load File upload style -->
	<script>
		$(":file").filestyle({
			buttonText : "Choose .json file",
			classInput : "input-small"
		});
	</script>


</body>
</html>
