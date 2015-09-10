<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Experiment details</title>
<link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet" media="screen">

<!-- Boostrap and JQuery libraries, for the logout button and other JS features -->
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>

<!-- Script to set the highlight the active menu in the header -->
<script>
	$(document).ready(function(event) {
		$("#menuPipelineExperiment").addClass("active");
	});
</script>

</head>
<body>

	<c:import url="header.jsp"></c:import>

	<div class="container">
		<div class="page-header">
			<h1>
				Pipeline experiment <small>${pipelineexperiment.name}</small>
			</h1>
		</div>
		<div>
			<a href="<c:url value="/pipelineexperiment/"/>">Back</a> | <a
				href="<c:url value="/pipelineexperiment/"/>${pipelineexperiment.id}/edit">Edit pipeline experiment</a>
		</div>

		<div class="row">
			<div class="span2 pull-right">
				<a href="javascript:history.go(0)" class="btn">Refresh</a>
			</div>
		</div>

		<c:if test="${not empty msg}">
			<div class="alert alert-success">
				<strong>Success! </strong>${msg}
			</div>
		</c:if>

		<dl class="dl-horizontal">
			<dt>Account</dt>
			<dd>${accountName}&nbsp;</dd>
                        <dt>Mapping tool</dt>
			<dd>${mapping_tool}&nbsp;</dd>
                        <dt>Mapping genome</dt>
			<dd>${mapping_genome}&nbsp;</dd>
                        <dt>Annotation tool</dt>
			<dd>${annotation_tool}&nbsp;</dd>
                        <dt>Annotation genome</dt>
			<dd>${annotation_genome}&nbsp;</dd>
                        <dt>Chip</dt>
			<dd>${chip_id}&nbsp;</dd>
                        <dt>Input folder</dt>
			<dd>${input_files_folder}&nbsp;</dd>
			<dt>Job ID</dt>
			<dd>${pipelineexperiment.emr_jobflow_id}&nbsp;</dd>
                        <dt>Job state</dt>
			<dd>${pipelineexperiment.emr_state}&nbsp;</dd>
			<dt>Job created</dt>
			<dd>${pipelineexperiment.emr_creation_date_time}&nbsp;</dd>
			<dt>Job ended</dt>
			<dd>${pipelineexperiment.emr_end_date_time}&nbsp;</dd>
			<dt>Job last message</dt>
			<dd>${pipelineexperiment.emr_last_state_change_reason}&nbsp;</dd>
                        <dt>Created</dt>
			<dd>${pipelineexperiment.created_at.toDate()}&nbsp;</dd>
			<dt>Last modified</dt>
			<dd>${pipelineexperiment.last_modified.toDate()}&nbsp;</dd>
		</dl>
		
		<c:if test="${pipelineexperiment.emr_state == 'COMPLETED'}">
			<dl class="dl-horizontal">
				<dt>Experiment output results</dt>
				<dd>
					<a href="<c:url value="/pipelineexperiment/"/>${pipelineexperiment.id}/output?format=json">Download features in JSON</a> <br/> 
                                        <a href="<c:url value="/pipelineexperiment/"/>${pipelineexperiment.id}/output?format=csv">Download features in CSV</a> <br/>
                                        <a href="<c:url value="/pipelineexperiment/"/>${pipelineexperiment.id}/qafile">Download QA stats in JSON</a>
				</dd>
                                
                                <!-- TODO add a button to be able to create a dataset from here -->
			</dl>
		</c:if>
	</div>

</body>
</html>