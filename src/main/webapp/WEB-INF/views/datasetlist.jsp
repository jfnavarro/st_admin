<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Datasets</title>
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
		$("#menuDataset").addClass("active");
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
										'<a href="#" class="btn" data-dismiss="modal" aria-hidden="true">Cancel</a> <a href="<c:url value="/dataset/"/>'
												+ theId
												+ '/delete" class="btn btn-danger">Delete</a>');
					});
</script>



</head>
<body>

	<c:import url="header.jsp"></c:import>

	<div class="container">



		<div class="page-header">
			<h1>Datasets</h1>
		</div>

		<c:if test="${not empty msg}">
			<div class="alert alert-success">
				<strong>Success! </strong>${msg}
			</div>
		</c:if>


		<div>
			<a href="<c:url value="/dataset/add"/>">Create Dataset</a>
		</div>


		<table class="table">
			<thead>
				<tr>
					<th>Name</th>
					<th></th>
				</tr>

			</thead>
			<tbody>
				<c:forEach var="dataset" items="${datasetList}">
					<tr>
						<td><a href="<c:url value="/dataset/"/>${dataset.id}">${dataset.name}</a></td>
						<td><a href="#deleteModal" data-toggle="modal"
							data-id="${dataset.id}"
							class="open-DeleteDialog btn btn-danger btn-small">Delete</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>



	</div>
	<!-- /container -->


	<div id="deleteModal" class="modal hide fade" tabindex="-1">
		<div class="modal-header">
			<h3 id="deleteModalLabel">Delete Dataset</h3>
		</div>
		<div class="modal-body">
			<div>Are you sure you want to delete the dataset?</div>

		</div>
		<div class="modal-footer">
			<div id="deleteBtn"></div>
		</div>
	</div>





</body>
</html>