<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Edit Chip</title>
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
		$("#menuImageAlignment").addClass("active");
	});
</script>

</head>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<c:import url="header.jsp"></c:import>
	<div class="container">
		<div class="page-header">
			<h1>
				Edit image alignment <small>${imagealignment.name}</small>
			</h1>
		</div>

		<c:if test="${not empty errors}">
			<div class="alert alert-error">Your input is not valid.</div>
		</c:if>

		<div>
			<form:form method="POST" commandName="imagealignment"
				action="${contextPath}/imagealignment/submitedit" class="form-horizontal">

				<spring:bind path="imagealignment.name">
					<div class="control-group ${status.error ? 'error' : ''}">
						<label class="control-label" for="inputName">Name</label>
						<div class="controls">
							<form:input type="text" id="inputName" placeholder="Name"
								path="imagealignment.name" />
							<span class='help-inline'>${status.errorMessage}</span>
						</div>
					</div>
				</spring:bind>


				<spring:bind path="imagealignment.chip_id">
					<div class="control-group  ${status.error ? 'error' : ''}">
						<label class="control-label" for="inputChip">Chip</label>
						<div class="controls">
							<form:select id="inputChip" path="imagealignment.chip_id">
								<form:options items="${chipChoices}"></form:options>
							</form:select>
							<span class='help-inline'>${status.errorMessage}</span>
						</div>
					</div>
				</spring:bind>

				<spring:bind path="imagealignment.figure_red">
					<div class="control-group  ${status.error ? 'error' : ''}">
						<label class="control-label" for="imageRed">Image red</label>
						<div class="controls">
							<form:select id="imageRed" path="imagealignment.figure_red">
								<form:options items="${imageChoices}"></form:options>
							</form:select>
							<span class='help-inline'>${status.errorMessage}</span>
						</div>
					</div>
				</spring:bind>


				<spring:bind path="imagealignment.figure_blue">
					<div class="control-group  ${status.error ? 'error' : ''}">
						<label class="control-label" for="imageBlue">Image blue</label>
						<div class="controls">
							<form:select id="imageBlue" path="imagealignment.figure_blue">
								<form:options items="${imageChoices}"></form:options>
							</form:select>
							<span class='help-inline'>${status.errorMessage}</span>
						</div>
					</div>
				</spring:bind>

				<spring:bind path="imagealignment.alignment_field1">
					<div class="control-group  ${status.error ? 'error' : ''}">
						<label class="control-label" for="alignment">Alignment
							matrix</label>
						<div class="controls controls-row">
							<form:input class="span1" type="text" id="alignmentField1"
								path="imagealignment.alignment_field1" />
							<form:input class="span1" type="text" id="alignmentField2"
								path="imagealignment.alignment_field2" />
							<form:input class="span1" type="text" id="alignmentField3"
								path="imagealignment.alignment_field3" />
						</div>
						<div class="controls controls-row">
							<form:input class="span1" type="text" id="alignmentField4"
								path="imagealignment.alignment_field4" />
							<form:input class="span1" type="text" id="alignmentField5"
								path="imagealignment.alignment_field5" />
							<form:input class="span1" type="text" id="alignmentField6"
								path="imagealignment.alignment_field6" />
						</div>
						<div class="controls controls-row">
							<form:input class="span1" type="text" id="alignmentField7"
								path="imagealignment.alignment_field7" />
							<form:input class="span1" type="text" id="alignmentField8"
								path="imagealignment.alignment_field8" />
							<form:input class="span1" type="text" id="alignmentField9"
								path="imagealignment.alignment_field9" />
						</div>

					</div>

				</spring:bind>


				<div class="control-group">
					<button type="submit" class="btn">Save</button>
				</div>

				<form:input type="hidden" path="id" />
			</form:form>
		</div>
		<div>
			<a href="<c:url value="imagealignment"/>${imagealignment.id}">Cancel</a>
		</div>

	</div>




</body>
</html>