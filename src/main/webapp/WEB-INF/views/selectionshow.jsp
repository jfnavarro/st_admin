<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet"
	media="screen">
<title>Selection details</title>

<!-- Boostrap and JQuery libraries, for the logout button and other JS features -->
<script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script
	src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>
	
	<!-- Script to set the highlight the active menu in the header -->
<script>
	$(document).ready(function(event) {
		$("#menuSelection").addClass("active");
	});
</script>


</head>
<body>

	<c:import url="header.jsp"></c:import>

	<div class="container">

		<div class="page-header">
			<div></div>
			<h1>
				Selection <small>${selection.name}</small>
			</h1>
		</div>

		<div>
			<a href="<c:url value="/selection/"/>">Back</a> | <a
				href="<c:url value="/selection/"/>${selection.id}/edit">Edit selection</a>
		</div>

		<dl class="dl-horizontal">
			<dt>Account</dt>
			<dd>${account.username}&nbsp;
			</dd>
			<dt>Dataset</dt>
			<dd>${dataset.name}&nbsp;
			</dd>
			<dt>Type</dt>
			<dd>${selection.type}&nbsp;
			</dd>
			<dt>Status</dt>
			<dd>${selection.status}&nbsp;
			</dd>
			<dt>OBO Foundry terms</dt>
			<dd>
			
				<c:if test="${empty selection.obo_foundry_terms}">
				    &nbsp;
				</c:if>
				<c:if test="${not empty selection.obo_foundry_terms}">
				    <c:forEach var="term" items="${selection.obo_foundry_terms}">
						${term} &nbsp;&nbsp;
					</c:forEach>
				</c:if>
			</dd>
			<dt>Comments</dt>
			<dd>${selection.comment}&nbsp;
			</dd>
			
			<dt>Gene hits</dt>
			<dd>
				<c:forEach var="genehits" items="${selection.gene_hits}">
						${genehits.key} &nbsp;&nbsp; ${genehits.value}
				</c:forEach>
			</dd>
		</dl>
	
	</div>

</body>
</html>