<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Experiment details</title>
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
			<dd>${account.username}&nbsp;</dd>
			<dt>EMR job ID</dt>
			<dd>${pipelineexperiment.emr_jobflow_id}&nbsp;</dd>
			<dt>EMR job created</dt>
			<dd>${jobflow.executionStatusDetail.creationDateTime}&nbsp;</dd>
			<dt>EMR job ended</dt>
			<dd>${jobflow.executionStatusDetail.endDateTime}&nbsp;</dd>
			<dt>EMR job last message</dt>
			<dd>${jobflow.executionStatusDetail.lastStateChangeReason}&nbsp;</dd>
		</dl>
		
		<c:if test="${not empty stats}">
		<dl class="dl-horizontal">
		  <dt>Statistics</dt>
			<dd>
				<a href="<c:url value="/pipelinestats/"/>${stats.id}">Show</a>
			</dd>
		</dl>
		</c:if>
		
		<c:if test="${jobflow.executionStatusDetail.state == 'COMPLETED'}">
			<dl class="dl-horizontal">
				<dt>EMR job output</dt>
				<dd>
					<a
						href="<c:url value="/pipelineexperiment/"/>${pipelineexperiment.id}/output?format=json">Download JSON</a> <br /> <a
						href="<c:url value="/pipelineexperiment/"/>${pipelineexperiment.id}/output?format=csv">Download CSV</a>
				</dd>
			</dl>
		</c:if>
	</div>

	<div id="deleteModal" class="modal hide fade" tabindex="-1">
		<div class="modal-header">
			<h3 id="deleteModalLabel">Delete pipeline experiment</h3>
		</div>
		<div class="modal-body">
			<div>Are you sure you want to delete the pipeline experiment? This will
				stop the EMR job and delete all data.</div>
		</div>
		<div class="modal-footer">
			<div id="deleteBtn"></div>
		</div>
	</div>
</body>
</html>