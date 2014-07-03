<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Dataset access for accounts</title>
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
		$("#menuDatasetInfo").addClass("active");
	});
</script>

<!-- Script for Delete dialog -->
<script>
	$(document)
			.on(
					"click",
					".open-DeleteDialog",
					function() {
						var theId = $(this).data('id');
						$(".modal-footer #deleteBtn")
								.html(
										'<a href="#" class="btn" data-dismiss="modal" aria-hidden="true">Cancel</a> <a href="<c:url value="/datasetinfo/"/>'
												+ theId
												+ '/delete" class="btn btn-danger">Delete</a>');
					});
</script>

</head>
<body>

	<c:import url="header.jsp"></c:import>

	<div class="container">

		<div class="page-header">
			<h1>Dataset access for accounts</h1>
		</div>

		<c:if test="${not empty msg}">
			<div class="alert alert-success">
				<strong>Success! </strong>${msg}
			</div>
		</c:if>

		<div>
			<a href="<c:url value="/datasetinfo/add"/>">Create dataset access for account</a>
		</div>

		<table class="table">
			<thead>
				<tr>
					<th>Dataset - account</th>
					<th>Comment</th>
				</tr>

			</thead>
			<tbody>
				<c:forEach var="dain" items="${datasetinfoList}">
					<tr>
						<td><a href="<c:url value="/datasetinfo/"/>${dain.id}">${datasetChoices[dain.dataset_id]} - ${accountChoices[dain.account_id]}</a></td>
						<td>${dain.comment}</td>
						<td><a href="#deleteModal" data-toggle="modal"
							data-id="${dain.id}"
							class="open-DeleteDialog btn btn-danger btn-small">Delete</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

	</div>
	<!-- /container -->

	<div id="deleteModal" class="modal hide fade" tabindex="-1">
		<div class="modal-header">
			<h3 id="deleteModalLabel">Delete dataset access for account</h3>
		</div>
		<div class="modal-body">
			<div>Are you sure you want to delete the dataset access for the account?</div>

		</div>
		<div class="modal-footer">
			<div id="deleteBtn"></div>
		</div>
	</div>

</body>
</html>