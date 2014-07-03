<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet"
	media="screen">
<title>Image alignment details</title>

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

	<c:import url="header.jsp"></c:import>

	<div class="container">


		<div class="page-header">
			<h1>
				Image alignment <small>${imagealignment.name}</small>
			</h1>
		</div>
		<div>
			<a href="<c:url value="/imagealignment/"/>">Back</a> | <a
				href="<c:url value="/imagealignment/"/>${imagealignment.id}/edit">Edit image alignment</a>
		</div>

		<dl class="dl-horizontal">

			<dt>Name</dt>
			<dd>${imagealignment.name}&nbsp;</dd>
			<dt>Chip</dt>
			<dd>${chip.name}&nbsp;</dd>
			<dt>Figure red</dt>
			<dd>${imagealignment.figure_red}&nbsp;</dd>
			<dt>Figure blue</dt>
			<dd>${imagealignment.figure_blue}&nbsp;</dd>
			<dt>Alignment matrix</dt>
			<dd>
			  <table border="1">
			    <tr><td>${imagealignment.alignment_matrix[0]}</td><td>${imagealignment.alignment_matrix[1]}</td><td>${imagealignment.alignment_matrix[2]}</td></tr>
			    <tr><td>${imagealignment.alignment_matrix[3]}</td><td>${imagealignment.alignment_matrix[4]}</td><td>${imagealignment.alignment_matrix[5]}</td></tr>
			    <tr><td>${imagealignment.alignment_matrix[6]}</td><td>${imagealignment.alignment_matrix[7]}</td><td>${imagealignment.alignment_matrix[8]}</td></tr>
			  </table>
			</dd>
                        <dt>Created</dt>
			<dd>${imagealignment.created_at.toDate()}&nbsp;</dd>
			<dt>Last modified</dt>
			<dd>${imagealignment.last_modified.toDate()}&nbsp;</dd>
		</dl>

	</div>
	<!-- /container -->

</body>
</html>