<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Edit pipelineexperiment</title>
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
		$("#menuTask").addClass("active");
	});
</script>

</head>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />

	<c:import url="header.jsp"></c:import>


	<div class="container">

		<div class="page-header">
			<h1>
				Edit pipeline experiment <small>${pipelineexperiment.name}</small>
			</h1>
		</div>


		<c:if test="${not empty errors}">
			<div class="alert alert-error">Your input is not valid.</div>
		</c:if>

		<div>
			<form:form method="POST" commandName="pipelineexperiment"
				action="${contextPath}/pipelineexperiment/submitedit" class="form-horizontal">


				<spring:bind path="name">
					<div class="control-group ${status.error ? 'error' : ''}">
						<label class="control-label" for="inputName">Name</label>
						<div class="controls">
							<form:input type="text" id="inputName" placeholder="Name" path="name" />
							<span class='help-inline'>${status.errorMessage}</span>
						</div>
					</div>
				</spring:bind>


				<spring:bind path="account_id">
				    <div class="control-group">
		                <label class="control-label" for="inputAccountId">Account</label>
		                <div class="controls">
		                <form:select id="inputAccountId" path="account_id" multiple="false">
                 		  <form:options items="${accountChoices}"></form:options>
                		</form:select>
                	 </div>
                	</div>
               	</spring:bind>

				<div class="control-group">
					<button type="submit" class="btn">Save</button>
				</div>

				<form:input type="hidden" path="id" />
				<form:input type="hidden" path="emr_jobflow_id" />
                                <form:input type="hidden" path="created_at" />
				<form:input type="hidden" path="last_modified" />
				
				
			</form:form>
		</div>
		<div>
			<a href="<c:url value="/pipelineexperiment/"/>${pipelineexperiment.id}">Cancel</a>
		</div>

	</div>

</body>
</html>
