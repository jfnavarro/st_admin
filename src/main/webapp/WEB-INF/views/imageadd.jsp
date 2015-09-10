<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Import Image</title>
<link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet" media="screen">

<!-- Boostrap and JQuery libraries, for the logout button and other JS features -->
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>

<!-- Script to set the highlight the active menu in the header -->
<script>
	$(document).ready(function(event) {
		$("#menuImage").addClass("active");
	});
</script>

<!-- Script to style file upload -->
<script type="text/javascript" src="<c:url value="/js/bootstrap-filestyle.min.js"/>"></script>

</head>
<body>

	<c:set var="contextPath" value="${pageContext.request.contextPath}" />

	<c:import url="header.jsp"></c:import>

	<div class="container">


		<div class="page-header">
			<h1>Import Image</h1>
		</div>

		<c:if test="${not empty errors}">
			<div class="alert alert-error">
				<strong>Error: </strong>Your input is not valid. Please check the
				values in the form below.
			</div>
		</c:if>

		<div>
			<form:form method="POST" commandName="imageform"
				action="${contextPath}/image/compressed/submitadd" class="form-horizontal"
				enctype="multipart/form-data">

				<spring:bind path="fileName">
					<div class="control-group  ${status.error ? 'error' : ''}">
					<label class="control-label">Image File</label>
						<div class="controls">
							<form:input id="image" path="imageFile" type="file"
								class="filestyle"></form:input>
							<span class='help-inline'>${status.errorMessage}</span>
						</div>
					</div>
				</spring:bind>

				<div class="control-group">
					<button type="submit" class="btn">Import</button>
				</div>

			</form:form>
		</div>
		<div>
			<a href="<c:url value="/image/"/>">Cancel</a>
		</div>

	</div>

	<!-- Load File upload style -->
	<script>
		$(":file").filestyle({
			buttonText : "Choose .jpg file"
		});
	</script>

</body>
</html>
