<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Experiment Details</title>
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
		$("#menuExperiment").addClass("active");
	});
</script>

</head>
<body>

	<c:import url="header.jsp"></c:import>

	<div class="container">
		<div class="page-header">
			<h1>
				Experiment <small>${experiment.name}</small>
			</h1>
		</div>
		<div>
			<a href="<c:url value="/experiment/"/>">Back</a>
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

			<!--	<dt>EMR Job ID</dt>
			<dd>${jobflow.jobFlowId}&nbsp;</dd> -->
			<dt>Status</dt>
			<dd>${jobflow.executionStatusDetail.state}&nbsp;</dd>
			<dt>Created</dt>
			<dd>${jobflow.executionStatusDetail.creationDateTime}&nbsp;</dd>
			<dt>Ended</dt>
			<dd>${jobflow.executionStatusDetail.endDateTime}&nbsp;</dd>
			<dt>Last Message</dt>
			<dd>${jobflow.executionStatusDetail.lastStateChangeReason}&nbsp;</dd>
		</dl>
		<c:if test="${jobflow.executionStatusDetail.state == 'COMPLETED'}">
			<dl class="dl-horizontal">
				<dt>Output</dt>
				<dd>
					<a
						href="<c:url value="/experiment/"/>${experiment.id}/output?format=json">Download
						JSON</a> <br /> <a
						href="<c:url value="/experiment/"/>${experiment.id}/output?format=csv">Download
						CSV</a>
				</dd>
			</dl>
		</c:if>


	</div>

	<div id="deleteModal" class="modal hide fade" tabindex="-1">
		<div class="modal-header">
			<h3 id="deleteModalLabel">Delete experiment</h3>
		</div>
		<div class="modal-body">
			<div>Are you sure you want to delete the experiment? This will
				stop the EMR job and delete all data.</div>
		</div>
		<div class="modal-footer">
			<div id="deleteBtn"></div>
		</div>
	</div>
</body>
</html>