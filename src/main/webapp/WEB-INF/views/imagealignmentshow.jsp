<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet"
	media="screen">
<title>Chip Details</title>

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
			<dt>Chip ID</dt>
			<dd>${imagealignment.chip_id}&nbsp;</dd>
			<dt>Figure red</dt>
			<dd>${imagealignment.figure_red}&nbsp;</dd>
			<dt>Figure blue</dt>
			<dd>${imagealignment.figure_blue}&nbsp;</dd>
			<dt>Alignment matrix</dt>
			<dd>${imagealignment.alignment_matrix}&nbsp;</dd>
			<dt>Last modified</dt>
			<dd>${imagealignment.last_modified}&nbsp;</dd>
		</dl>

		<div></div>

	</div>
	<!-- /container -->

</body>
</html>