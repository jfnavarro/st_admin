<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet"
	media="screen">
<title>Account Details</title>

<!-- Boostrap and JQuery libraries, for the logout button and other JS features -->
<script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script
	src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>
	
	<!-- Script to set the highlight the active menu in the header -->
<script>
	$(document).ready(function(event) {
		$("#menuAccount").addClass("active");
	});
</script>


</head>
<body>


	<c:import url="header.jsp"></c:import>

	<div class="container">

		<div class="page-header">
			<div></div>
			<h1>
				Account <small>${account.username}</small>
			</h1>
		</div>

		<div>
			<a href="<c:url value="/account/"/>">Back</a> | <a
				href="<c:url value="/account/"/>${account.id}/edit">Edit Account</a>
		</div>


		<dl class="dl-horizontal">
			
			<dt>Role</dt>
			<dd>${account.role}&nbsp;
			</dd>
			<dt>Enabled</dt>
			<dd>${account.enabled}&nbsp;
			</dd>
		</dl>



		<dl class="dl-horizontal">
			<dt>Granted Datasets</dt>
			<dd>
				<c:forEach var="dataset" items="${datasets}">
				${dataset.name} <br />
				</c:forEach>
			</dd>
		</dl>






	</div>

</body>
</html>