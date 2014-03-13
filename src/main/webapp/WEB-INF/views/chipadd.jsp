<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Create Chip</title>
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
			<h1>Create chip</h1>
		</div>



		<div>
			<form:form method="POST" commandName="chip"
				action="${contextPath}/chip/submitadd" class="form-horizontal">
				<div class="control-group">
					<label class="control-label" for="inputName">Name</label>
					<div class="controls">
						<form:input type="text" id="inputName" placeholder="Name"
							path="name" />
					</div>
				</div>

				<div class="control-group">
					<label class="control-label" for="inputBarcodes">Barcodes</label>
					<div class="controls">
						<form:input type="text" id="inputbarcodes" placeholder="Barcodes"
							path="barcodes" />
					</div>
				</div>

				<div class="control-group">
					<label class="control-label" for="inputX1">X1</label>
					<div class="controls">
						<form:input type="text" id="inputX1" placeholder="X1" path="x1" />
					</div>
				</div>


				<div class="control-group">
					<label class="control-label" for="inputX1Border">X1 Border</label>
					<div class="controls">
						<form:input type="text" id="inputX1Border" placeholder="X1 border"
							path="x1_border" />
					</div>
				</div>

				<div class="control-group">
					<label class="control-label" for="inputX1Total">X1 Total</label>
					<div class="controls">
						<form:input type="text" id="inputX1Total" placeholder="X1 total"
							path="x1_total" />
					</div>
				</div>


				<div class="control-group">
					<label class="control-label" for="inputX2">X2</label>
					<div class="controls">
						<form:input type="text" id="inputX2" placeholder="X2" path="x2" />
					</div>
				</div>


				<div class="control-group">
					<label class="control-label" for="inputX2Border">X2 Border</label>
					<div class="controls">
						<form:input type="text" id="inputX2Border" placeholder="X2 border"
							path="x2_border" />
					</div>
				</div>

				<div class="control-group">
					<label class="control-label" for="inputX1Total">X2 Total</label>
					<div class="controls">
						<form:input type="text" id="inputX2Total" placeholder="X2 total"
							path="x2_total" />
					</div>
				</div>

				<!-- Y -->

				<div class="control-group">
					<label class="control-label" for="inputY1">Y1</label>
					<div class="controls">
						<form:input type="text" id="inputY1" placeholder="Y1" path="y1" />
					</div>
				</div>


				<div class="control-group">
					<label class="control-label" for="inputY1Border">Y1 Border</label>
					<div class="controls">
						<form:input type="text" id="inputY1Border" placeholder="Y1 border"
							path="y1_border" />
					</div>
				</div>

				<div class="control-group">
					<label class="control-label" for="inputY1Total">Y1 Total</label>
					<div class="controls">
						<form:input type="text" id="inputY1Total" placeholder="Y1 total"
							path="y1_total" />
					</div>
				</div>


				<div class="control-group">
					<label class="control-label" for="inputX2">X2</label>
					<div class="controls">
						<form:input type="text" id="inputX2" placeholder="X2" path="x2" />
					</div>
				</div>


				<div class="control-group">
					<label class="control-label" for="inputX2Border">X2 Border</label>
					<div class="controls">
						<form:input type="text" id="inputX2Border" placeholder="X2 border"
							path="x2_border" />
					</div>
				</div>

				<div class="control-group">
					<label class="control-label" for="inputY1Total">X2 Total</label>
					<div class="controls">
						<form:input type="text" id="inputX2Total" placeholder="X2 total"
							path="x2_total" />
					</div>
				</div>


				<div class="control-group">
					<button type="submit" class="btn">Save</button>
				</div>

			</form:form>
		</div>
		<div>
			<a href="<c:url value="/chip/"/>${chip.id}">Cancel</a>
		</div>

	</div>



</body>
</html>
