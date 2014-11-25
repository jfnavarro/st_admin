<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet"
	media="screen">
<title>Dataset Details</title>

<!-- Boostrap and JQuery libraries, for the logout button and other JS features -->
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
				Dataset <small>${dataset.name}</small>
			</h1>
		</div>
		<div>
			<a href="<c:url value="/dataset/"/>">Back</a> | <a
				href="<c:url value="/dataset/"/>${dataset.id}/edit">Edit dataset</a>
		</div>

		<dl class="dl-horizontal">

			<dt>Enabled</dt>
			<dd>
			<c:choose>
    			<c:when test="${dataset.enabled == true}">
        			<input type="checkbox" name="chkEnabled" value="" checked="checked" onclick="return false">&nbsp;
    			</c:when>
    			<c:otherwise>
        			<input type="checkbox" name="chkEnabled" value="" onclick="return false">&nbsp;
    			</c:otherwise>
			</c:choose>
			</dd>

			<dt>Tissue</dt>
			<dd>${dataset.tissue}&nbsp;</dd>

			<dt>Species</dt>
			<dd>${dataset.species}&nbsp;</dd>
						
			<dt>Image alignment</dt>
			<dd>${imagealignment.name}&nbsp;</dd>
			
                        <dt>Created by</dt>
			<dd>${accountcreator}&nbsp;</dd>
                        
			<dt>Overall # of features (# of unique events)</dt>
			<dd>${dataset.overall_feature_count}&nbsp;</dd>
			
			<dt>Unique # of genes</dt>
			<dd>${dataset.unique_gene_count}&nbsp;</dd>
			
			<dt>Unique # of barcodes</dt>
			<dd>${dataset.unique_barcode_count}&nbsp;</dd>
			
			<dt>Overall # of hits</dt>
			<dd>${dataset.overall_hit_count}&nbsp;</dd>
			
			<dt>Hit quartiles</dt>
			<dd>[${dataset.overall_hit_quartiles[0]}, ${dataset.overall_hit_quartiles[1]}, ${dataset.overall_hit_quartiles[2]}, ${dataset.overall_hit_quartiles[3]}, ${dataset.overall_hit_quartiles[4]}]&nbsp;</dd>
			
			<dt>Gene-pooled hit quartiles</dt>
			<dd>[${dataset.gene_pooled_hit_quartiles[0]}, ${dataset.gene_pooled_hit_quartiles[1]}, ${dataset.gene_pooled_hit_quartiles[2]}, ${dataset.gene_pooled_hit_quartiles[3]}, ${dataset.gene_pooled_hit_quartiles[4]}]&nbsp;</dd>
			
			<dt>OBO Foundry terms</dt>
			<dd>
			<c:forEach var="term" items="${dataset.obo_foundry_terms}">
				${term}&nbsp;&nbsp;
			</c:forEach>
			&nbsp;
			</dd>
			
			<dt>Comments</dt>
			<dd>${dataset.comment}&nbsp;</dd>
			
                        <dt>Created</dt>
			<dd>${dataset.created_at.toDate()}&nbsp;</dd>
			<dt>Last modified</dt>
			<dd>${dataset.last_modified.toDate()}&nbsp;</dd>
                        
			<dt>Features file</dt>
			<dd>
                                <a href="<c:url value="/dataset/features/"/>${dataset.id}" target="_blank">Download [${featuresMetadata[dataset.id].getReadableSize()}]</a> (right click and select "Save link as...")
			</dd>
                        
		</dl>
		
		<dl class="dl-horizontal">
			<dt>Granted accounts</dt>
			<dd>
				<c:forEach var="account" items="${accounts}">
				${account.username} <br/>
				</c:forEach>
			</dd>
		</dl>

	</div>
	<!-- /container -->

</body>
</html>