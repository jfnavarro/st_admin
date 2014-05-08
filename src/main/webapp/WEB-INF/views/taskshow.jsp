<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet"
	media="screen">
<title>Task details</title>

<!-- Boostrap and JQuery libraries, for the logout button and other JS features -->
<script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script
	src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>
	
	<!-- Script to set the highlight the active menu in the header -->
<script>
	$(document).ready(function(event) {
		$("#menuTask").addClass("active");
	});
</script>


</head>
<body>

	<c:import url="header.jsp"></c:import>

	<div class="container">

		<div class="page-header">
			<div></div>
			<h1>
				Task <small>${task.name}</small>
			</h1>
		</div>

		<div>
			<a href="<c:url value="/task/"/>">Back</a> | <a
				href="<c:url value="/task/"/>${task.id}/edit">Edit task</a>
		</div>

		<dl class="dl-horizontal">
			<dt>Account</dt>
			<dd>${account.username}&nbsp;
			</dd>
			<dt>Status</dt>
			<dd>${task.status}&nbsp;
			</dd>
			<dt>Start time</dt>
			<dd>${task.start}&nbsp;
			</dd>
			<dt>End time</dt>
			<dd>${task.end}&nbsp;
			</dd>
			<dt>Selections</dt>
			<dd>
				<c:if test="${empty selections}">
				    &nbsp;
				</c:if>
				<c:if test="${not empty selections}">
				    <c:forEach var="selection" items="${selections}">
						<a href="<c:url value="/selection/"/>${selection.id}">${selection.name}</a><br/>
					</c:forEach>
				</c:if>
			</dd>
			<dt>Parameters</dt>
			<dd>${task.parameters}&nbsp;
			</dd>
			<dt>Result file</dt>
			<dd>${task.result_file}&nbsp;
			</dd>
		</dl>
	</div>
	
</body>
</html>