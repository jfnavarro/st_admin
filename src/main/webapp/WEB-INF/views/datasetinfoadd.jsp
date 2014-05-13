<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
	
<head>
<title>Dataset access for accounts</title>

<link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet" media="screen">

<!-- Boostrap and JQuery libraries, for the logout button and other JS features -->
<script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script
	src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>

<!-- Script to set the highlight the active menu in the header -->
<script>
	$(document).ready(function(event) {
		$("#menuDatasetInfo").addClass("active");
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
			<h1>Create dataset access for account</h1>
		</div>

		<c:if test="${not empty errors}">
			<div class="alert alert-error">
				<strong>Error: </strong>Your input is not valid. Please check the values in the form below.
			</div>
			<c:forEach var="err" items="${errors}" varStatus="idx">
				<div class="alert alert-error">${err.defaultMessage}</div>
			</c:forEach>
		</c:if>

		

		<div>
			<form:form method="POST" commandName="datasetinfo"
				action="${contextPath}/datasetinfo/submitadd" class="form-horizontal">

				<spring:bind path="account_id">				
					<div class="control-group">
	                <label class="control-label" for="inputAccounts">Accounts</label>
	                <div class="controls">
		                <form:select id="inputAccounts" path="" multiple="false">
		                    <form:options items="${accountChoices}"></form:options>
		                 </form:select>
		                 </div>
	                 </div>
                 </spring:bind>

				<spring:bind path="dataset_id">				
					<div class="control-group">
	                <label class="control-label" for="inputDatasets">Datasets</label>
	                <div class="controls">
		                <form:select id="inputDatasets" path=""  multiple="false">
		                    <form:options items="${datasetChoices}"></form:options>
		                 </form:select>
		                 </div>
	                 </div>
                 </spring:bind>
				
				<spring:bind path="comment">
					<div class="control-group  ${status.error ? 'error' : ''}">
						<label class="control-label" for="inputComment">Comments</label>
						<div class="controls">
							<form:input type="text" id="inputComment" placeholder="Comment" path="" />
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
			<a href="<c:url value="/datasetinfo/"/>${datasetinfo.id}">Cancel</a>
		</div>

	</div>

</body>
</html>
