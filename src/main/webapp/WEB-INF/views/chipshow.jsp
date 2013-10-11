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
		$("#menuChip").addClass("active");
	});
</script>
</head>
<body>

	<c:import url="header.jsp"></c:import>

	<div class="container">


		<div class="page-header">
			<h1>
				Chip <small>${chip.name}</small>
			</h1>
		</div>
		<div>
			<a href="<c:url value="/chip/"/>">Back</a> | <a
				href="<c:url value="/chip/"/>${chip.id}/edit">Edit Chip</a>
		</div>

		<dl class="dl-horizontal">

			<dt>Barcodes</dt>
			<dd>${chip.barcodes}&nbsp;</dd>
			<dt>X1</dt>
			<dd>${chip.x1}&nbsp;</dd>
			<dt>X1 Border</dt>
			<dd>${chip.x1_border}&nbsp;</dd>
			<dt>X1 Total</dt>
			<dd>${chip.x1_total}&nbsp;</dd>
			<dt>X2</dt>
			<dd>${chip.x2}&nbsp;</dd>
			<dt>X2 Border</dt>
			<dd>${chip.x2_border}&nbsp;</dd>
			<dt>X2 Total</dt>
			<dd>${chip.x2_total}&nbsp;</dd>
			<dt>Y1</dt>
			<dd>${chip.y1}&nbsp;</dd>
			<dt>Y1 Border</dt>
			<dd>${chip.y1_border}&nbsp;</dd>
			<dt>Y1 Total</dt>
			<dd>${chip.y1_total}&nbsp;</dd>
			<dt>Y2</dt>
			<dd>${chip.y2}&nbsp;</dd>
			<dt>Y2 Border</dt>
			<dd>${chip.y2_border}&nbsp;</dd>
			<dt>Y2 Total</dt>
			<dd>${chip.y2_total}&nbsp;</dd>
		</dl>

		<div></div>



	</div>
	<!-- /container -->






</body>
</html>