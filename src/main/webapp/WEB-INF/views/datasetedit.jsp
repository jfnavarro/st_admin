<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Edit Dataset</title>
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
			<h1>
				Edit Dataset <small>${datasetform.dataset.name}</small>
			</h1>
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

		<!--<c:forEach var="err" items="${errors}" varStatus="idx">
			<div class="alert alert-error">${err.defaultMessage}</div>
		</c:forEach> -->

		<div class="row">
			<form:form method="POST" commandName="datasetform"
				action="${contextPath}/dataset/submitedit" class="form-horizontal"
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


						


						<legend>
							Features <small>(Leave both empty to keep the current
								features)</small>
						</legend>

						<!-- spring:bind path="experimentId">
							<div class="control-group  ${status.error ? 'error' : ''}">
								<label class="control-label" for="experiment">Experiment
									Output </label>
								<div class="controls">
									<form:select id="experimentId" path="experimentId">
										<option></option>
										<form:options items="${experimentChoices}"></form:options>
									</form:select>
									<span class='help-inline'>${status.errorMessage}</span>
								</div>
							</div>
						</spring:bind -->

						<div class="pull-center text-center">
							<strong>-- or --</strong>
						</div>

						<!-- <spring:bind path="featureFile">
							<div class="control-group  ${status.error ? 'error' : ''}">
								<div class="control-group">
									<label class="control-label" for="featureFile">Feature
										File </label>
									<div class="controls">
										<form:input type="file" id="featureFile"
											placeholder="Feature file" path="featureFile" />
										<span class="help-inline"></span> <span class='help-inline'>${status.errorMessage}</span>
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
					<button type="submit" class="btn">Save</button>
				</div>

				<form:input type="hidden" path="dataset.id" />



			</form:form>
		</div>
		<div>
			<a href="<c:url value="/dataset/"/>${datasetform.dataset.id}">Cancel</a>
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
