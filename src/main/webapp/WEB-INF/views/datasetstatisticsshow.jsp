<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Experiment Details</title>
<link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet"
	media="screen">

<!-- Bootstrap and JQuery libraries, for the logout button and other JS features -->
<script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script
	src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>

<!-- Script to set the highlight the active menu in the header -->
<script>
	$(document).ready(function(event) {
		$("#menuDataset").addClass("active");
	});
</script>

</head>
<body>

	<c:import url="header.jsp"></c:import>

	<div class="container">
		<div class="page-header">
			<h1>
			    Hit statistics <small>Dataset ${datasetstatistics.datasetId}</small>
			</h1>
		</div>
		<div>
			<a href="<c:url value="/dataset/"/>">Back</a>
		</div>

		<dl class="dl-horizontal">
			<dt>Hits sum</dt>
			<dd>${datasetstatistics.hitsSum}&nbsp;</dd>
			<dt>Hits min</dt>
			<dd>${datasetstatistics.getHitsMin()}&nbsp;</dd>
			<dt>Hits max</dt>
			<dd>${datasetstatistics.getHitsMax()}&nbsp;</dd>
			<dt>Pooled hits min</dt>
			<dd>${datasetstatistics.getPooledHitsMin()}&nbsp;</dd>
			<dt>Pooled hits max</dt>
			<dd>${datasetstatistics.getPooledHitsMax()}&nbsp;</dd>
			<dt>Hits quartiles</dt>
			<dd>${datasetstatistics.getHitsQuartilesAsString()}&nbsp;</dd>
			<dt>Pooled hits quartiles</dt>
			<dd>${datasetstatistics.getPooledHitsQuartilesAsString()}&nbsp;</dd>
			<dt>Hits "inliers" range</dt>
			<dd>${datasetstatistics.getHitsInliersRangeAsString()}&nbsp;</dd>
			<dt>Pooled hits "inliers" range</dt>
			<dd>${datasetstatistics.getPooledHitsInliersRangeAsString()}&nbsp;</dd>
		</dl>

	</div>

</body>
</html>