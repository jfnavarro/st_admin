<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
	
<head>
<title>Create task</title>

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

<!--  Scripts for Multiselect -->	
<script type="text/javascript"
	src="<c:url value="/js/bootstrap-multiselect.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/js/st-multiselect-button.js"/>"></script>


</head>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />

	<c:import url="header.jsp"></c:import>

	<div class="container">



		<div class="page-header">
			<h1>Create task</h1>
		</div>


		<c:if test="${not empty errors}">
			<div class="alert alert-error">
				<strong>Error: </strong>Your input is not valid. Please check the
				values in the form below.
			</div>
		</c:if>

		<!--<c:forEach var="err" items="${errors}" varStatus="idx">
			<div class="alert alert-error">${err.defaultMessage}</div>
		</c:forEach> -->

		<div>
			<form:form method="POST" commandName="task"
				action="${contextPath}/task/submitadd" class="form-horizontal">

				
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

				
				<spring:bind path="status">
					<div class="control-group ${status.error ? 'error' : ''}">
						<label class="control-label" for="inputStatus">Status</label>
						<div class="controls">
							<form:input type="text" id="status" placeholder="Status" path="status" />
							<span class='help-inline'>${status.errorMessage}</span>
						</div>
					</div>
				</spring:bind>

				<spring:bind path="start">
					<div class="control-group ${status.error ? 'error' : ''}">
						<label class="control-label" for="inputStart">Start</label>
						<div class="controls">
							<form:input type="text" id="inputStart"
								placeholder="Start" path="start" />
							<span class='help-inline'>${status.errorMessage}</span>
						</div>
					</div>
				</spring:bind>

				<spring:bind path="end">
					<div class="control-group ${status.error ? 'error' : ''}">
						<label class="control-label" for="inputEnd">End</label>
						<div class="controls">
							<form:input type="text" id="inputEnd" placeholder="End"
								path="end" />
							<span class='help-inline'>${status.errorMessage}</span>
						</div>
					</div>
				</spring:bind>

				<spring:bind path="selection_ids">
			    <div class="control-group">
	                <label class="control-label" for="inputSelections">Selections</label>
	                <div class="controls">
	                <form:select id="inputSelections" path="selection_ids" class="multiselect" multiple="multiple">
	                    <form:options items="${selectionChoices}"></form:options>
	                 </form:select>
	                 </div>
                 </div>
                 </spring:bind>
                 
                 <spring:bind path="parameters">
					<div class="control-group ${status.error ? 'error' : ''}">
						<label class="control-label" for="inputParameters">Parameters</label>
						<div class="controls">
							<form:input type="text" id="inputParameters"
								placeholder="Parameters" path="parameters" />
							<span class='help-inline'>${status.errorMessage}</span>
						</div>
					</div>
				</spring:bind>

				<spring:bind path="result_file">
					<div class="control-group ${status.error ? 'error' : ''}">
						<label class="control-label" for="inputResultFile">Result file</label>
						<div class="controls">
							<form:input type="text" id="inputResultFile" placeholder="Result file"
								path="result_file" />
							<span class='help-inline'>${status.errorMessage}</span>
						</div>
					</div>
				</spring:bind>

				<div>
					<button type="submit" class="btn">Create</button>
				</div>
			</form:form>
		</div>
		<div>
			<a href="<c:url value="/task/"/>">Cancel</a>
		</div>

	</div>

</body>
</html>