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
		$("#menuChip").addClass("active");
	});
</script>

</head>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />

	<c:import url="header.jsp"></c:import>


	<div class="container">

		<div class="page-header">
			<h1>
				Edit Chip <small>${chip.name}</small>
			</h1>
		</div>


		<c:if test="${not empty errors}">
			<div class="alert alert-error">Your input is not valid.</div>
		</c:if>

		<div>
			<form:form method="POST" commandName="chip"
				action="${contextPath}/chip/submitedit" class="form-horizontal">


				<spring:bind path="name">
					<div class="control-group ${status.error ? 'error' : ''}">
						<label class="control-label" for="inputName">Name</label>
						<div class="controls">
							<form:input type="text" id="inputName" placeholder="Name"
								path="name" />
							<span class='help-inline'>${status.errorMessage}</span>
						</div>
					</div>
				</spring:bind>

				<spring:bind path="barcodes">
					<div class="control-group ${status.error ? 'error' : ''}">
						<label class="control-label" for="inputBarcodes">Barcodes</label>
						<div class="controls">
							<form:input type="text" id="inputBarcodes" placeholder="Barcodes"
								path="barcodes" />
							<span class='help-inline'>${status.errorMessage}</span>
						</div>
					</div>
				</spring:bind>


				<spring:bind path="x1">
					<div class="control-group ${status.error ? 'error' : ''}">
						<label class="control-label" for="inputX1">X1</label>
						<div class="controls">
							<form:input type="text" id="inputX1" placeholder="X1" path="x1" />
							<span class='help-inline'>${status.errorMessage}</span>
						</div>
					</div>
				</spring:bind>

				<spring:bind path="x1_border">
					<div class="control-group ${status.error ? 'error' : ''}">
						<label class="control-label" for="inputX1Border">X1 Border</label>
						<div class="controls">
							<form:input type="text" id="inputX1Border"
								placeholder="X1 border" path="x1_border" />
							<span class='help-inline'>${status.errorMessage}</span>
						</div>
					</div>
				</spring:bind>

				<spring:bind path="x1_total">
					<div class="control-group ${status.error ? 'error' : ''}">
						<label class="control-label" for="inputX1Total">X1 Total</label>
						<div class="controls">
							<form:input type="text" id="inputX1Total" placeholder="X1 total"
								path="x1_total" />
							<span class='help-inline'>${status.errorMessage}</span>
						</div>
					</div>
				</spring:bind>

				<spring:bind path="x2">
					<div class="control-group ${status.error ? 'error' : ''}">
						<label class="control-label" for="inputX2">X2</label>
						<div class="controls">
							<form:input type="text" id="inputX2" placeholder="X2" path="x2" />
							<span class='help-inline'>${status.errorMessage}</span>
						</div>
					</div>
				</spring:bind>

				<spring:bind path="x2_border">
					<div class="control-group ${status.error ? 'error' : ''}">
						<label class="control-label" for="inputX2Border">X2 Border</label>
						<div class="controls">
							<form:input type="text" id="inputX2Border"
								placeholder="X2 border" path="x2_border" />
							<span class='help-inline'>${status.errorMessage}</span>
						</div>
					</div>
				</spring:bind>

				<spring:bind path="x2_total">
					<div class="control-group ${status.error ? 'error' : ''}">
						<label class="control-label" for="inputX2Total">X2 Total</label>
						<div class="controls">
							<form:input type="text" id="inputX2Total" placeholder="X2 total"
								path="x2_total" />
							<span class='help-inline'>${status.errorMessage}</span>
						</div>
					</div>
				</spring:bind>

				<!-- Y -->

				<spring:bind path="y1">
					<div class="control-group ${status.error ? 'error' : ''}">
						<label class="control-label" for="inputY1">Y1</label>
						<div class="controls">
							<form:input type="text" id="inputY1" placeholder="Y1" path="y1" />
							<span class='help-inline'>${status.errorMessage}</span>
						</div>
					</div>
				</spring:bind>

				<spring:bind path="y1_border">
					<div class="control-group ${status.error ? 'error' : ''}">
						<label class="control-label" for="inputY1Border">Y1 Border</label>
						<div class="controls">
							<form:input type="text" id="inputY1Border"
								placeholder="Y1 border" path="y1_border" />
							<span class='help-inline'>${status.errorMessage}</span>
						</div>
					</div>
				</spring:bind>

				<spring:bind path="y1_total">
					<div class="control-group ${status.error ? 'error' : ''}">
						<label class="control-label" for="inputY1Total">Y1 Total</label>
						<div class="controls">
							<form:input type="text" id="inputY1Total" placeholder="Y1 total"
								path="y1_total" />
							<span class='help-inline'>${status.errorMessage}</span>
						</div>
					</div>
				</spring:bind>

				<spring:bind path="y2">
					<div class="control-group ${status.error ? 'error' : ''}">
						<label class="control-label" for="inputY2">Y2</label>
						<div class="controls">
							<form:input type="text" id="inputY2" placeholder="Y2" path="y2" />
							<span class='help-inline'>${status.errorMessage}</span>
						</div>
					</div>
				</spring:bind>

				<spring:bind path="y2_border">
					<div class="control-group ${status.error ? 'error' : ''}">
						<label class="control-label" for="inputY2Border">Y2 Border</label>
						<div class="controls">
							<form:input type="text" id="inputY2Border"
								placeholder="Y2 border" path="y2_border" />
							<span class='help-inline'>${status.errorMessage}</span>
						</div>
					</div>
				</spring:bind>

				<spring:bind path="y2_total">
					<div class="control-group ${status.error ? 'error' : ''}">
						<label class="control-label" for="inputY2Total">Y2 Total</label>
						<div class="controls">
							<form:input type="text" id="inputY2Total" placeholder="Y2 total"
								path="y2_total" />
							<span class='help-inline'>${status.errorMessage}</span>
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
			<a href="<c:url value="/chip/"/>${chip.id}">Cancel</a>
		</div>

	</div>




</body>
</html>
