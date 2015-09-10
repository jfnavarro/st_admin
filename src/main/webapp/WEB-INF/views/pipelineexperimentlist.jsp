<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Pipeline experiments</title>
<link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet"
	media="screen">

<!-- Boostrap and JQuery libraries, for the logout button and other JS features -->
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>

<!-- Script to set the highlight the active menu in the header -->
<script>
	$(document).ready(function(event) {
		$("#menuPipelineExperiment").addClass("active");
	});
</script>

<!-- Script for Delete dialog -->
<script type="text/javascript" charset="utf-8" src="<c:url value="/js/delete-dialog.js"/>"></script>

</head>
<body>

	<c:import url="header.jsp"></c:import>

	<div class="container">
		<div class="page-header">
			<h1>Pipeline experiments</h1>
		</div>

		<c:if test="${not empty msg}">
			<div class="alert alert-success">
				<strong>Success! </strong>${msg}
			</div>
		</c:if>


		<div>
			<a href="<c:url value="/pipelineexperiment/create/"/>">Create pipeline experiment</a>
		</div>

		<table class="table">
			<thead>
				<tr>
					<th>Name</th>
					<th>Account</th>
                                        <th>Status</th>
                                        <th>Created</th>
                                        <th>Last modified</th>
				</tr>

			</thead>
			<tbody>
				<c:forEach var="exp" items="${pipelineexperimentList}">
					<tr>
						<td><a href="<c:url value="/pipelineexperiment/"/>${exp.id}">${exp.name}</a></td>
						<td>${accountChoices[exp.account_id]}</td>
                                                <td>${exp.emr_state}</td>
                                                <td><small><fmt:formatDate value="${exp.created_at.toDate()}" pattern="yyyy-MM-dd HH:mm:ss" /></small></td>
                                                <td><small><fmt:formatDate value="${exp.last_modified.toDate()}" pattern="yyyy-MM-dd HH:mm:ss" /></small></td>
						<td><a href="#deleteModal" data-toggle="modal"
                                                       data-id="<c:url value="/pipelineexperiment/"/>${exp.id}/delete"
                                                       class="open-DeleteDialog btn btn-danger btn-small">Delete</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

	</div>

	<div id="deleteModal" class="modal hide fade" tabindex="-1">
		<div class="modal-header">
			<h3 id="deleteModalLabel">Delete pipeline experiment</h3>
		</div>
		<div class="modal-body">
			<div>Are you sure you want to delete the pipeline experiment?<br/>
				This will stop the job run, delete the associated pipeline experiment statistics,<br/>
				and remove the associated files on Amazon.</div>
		</div>
		<div class="modal-footer">
			<div id="deleteBtn"></div>
		</div>
	</div>

</body>
</html>