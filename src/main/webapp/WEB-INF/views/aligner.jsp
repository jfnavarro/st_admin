<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Aligner</title>
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
		$("#menuAligner").addClass("active");
	});
</script>

</head>
<body>

	<c:import url="header.jsp"></c:import>


	<div class="container">

		<div class="page-header">
			<h1>Aligner</h1>
		</div>

		Coming soon...

		<!-- dl class="dl-horizontal">

		<dt>Image red</dt>
			<dd>
				<a href="<c:url value="/image/"/>${dataset.figure_red}"
					target="_blank">${dataset.figure_red}</a>
			</dd>
			<dt>Image blue</dt>
			<dd>
				<a href="<c:url value="/image/"/>${dataset.figure_blue}"
					target="_blank">${dataset.figure_blue}</a>
			</dd>
			
			<dt>Alignment Matrix</dt>
			<dd>

				<table class="table table-bordered table-condensed">
					<tr>
						<td>${dataset.alignment_matrix[0]}</td>
						<td>${dataset.alignment_matrix[1]}</td>
						<td>${dataset.alignment_matrix[2]}</td>
					</tr>
					<tr>
						<td>${dataset.alignment_matrix[3]}</td>
						<td>${dataset.alignment_matrix[4]}</td>
						<td>${dataset.alignment_matrix[5]}</td>
					</tr>
					<tr>
						<td>${dataset.alignment_matrix[6]}</td>
						<td>${dataset.alignment_matrix[7]}</td>
						<td>${dataset.alignment_matrix[8]}</td>
					</tr>
				</table>
			</dd>
		</dl -->

	</div>
	<!-- /container -->




</body>
</html>