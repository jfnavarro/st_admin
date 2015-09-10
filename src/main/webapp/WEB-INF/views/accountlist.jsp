<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Accounts</title>

<!-- Bootstrap -->
<link rel="stylesheet" type="text/css" href="<c:url value="/css/bootstrap.min.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/dataTables.bootstrap.css"/>">
<script src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>

<!-- JQuery and DataTable -->
<script type="text/javascript" language="javascript" src="//code.jquery.com/jquery-1.11.1.js"></script>
<script type="text/javascript" language="javascript" src="//cdn.datatables.net/1.10.3/js/jquery.dataTables.js"></script>
<script type="text/javascript" language="javascript" src="<c:url value="/js/dataTables.bootstrap.js"/>"></script>

<!-- Activate DataTable -->
<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
    $('#accounts').dataTable({
        "bJQueryUI": true,
        "columnDefs": [
            { "orderable": false, "targets": 8 }
        ]
    });
} );
</script>

<!-- Script to set the highlight the active menu in the header -->
<script>
    $(document).ready(function() {
	$("#menuAccount").addClass("active");
    });
</script>

<!--  Script for Delete dialog -->
<script src="<c:url value="/js/delete-dialog.js"/>"></script>

</head>
<body>

	<c:import url="header.jsp"></c:import>

	<div class="container">

		<div class="page-header">
			<h1>Accounts</h1>
		</div>

		<c:if test="${not empty msg}">
			<div class="alert alert-success">
				<strong>Success! </strong>${msg}
			</div>
		</c:if>


		<div>
			<a href="<c:url value="/account/add"/>">Create account</a>
		</div>

		<table class="table table-compact table-hover table-oder-column table-condensed" id="accounts">
			<thead>
				<tr>
					<th>Username (email)</th>
					<th>Role</th>
					<th>Enabled</th>
					<th>Institution</th>
					<th>First name</th>
					<th>Last name</th>
                                        <th>Created</th>
                                        <th>Last modified</th>
					<th></th>
				</tr>

			</thead>
			<tbody>
				<c:forEach var="account" items="${accountList}">
					<tr>
						<td><a href="<c:url value="/account/"/>${account.id}">${account.username}</a></td>
						<td>${account.role}</td>					        
                                                <td>
                                                <c:choose>
                                                    <c:when test="${account.enabled == true}">
                                                        <input type="checkbox" name="chkEnabled" value="" checked="checked" onclick="return false">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <input type="checkbox" name="chkEnabled" value="" onclick="return false">
                                                    </c:otherwise>
                                                </c:choose>
						</td>
						<td>${account.institution}</td>
						<td>${account.first_name}</td>
						<td>${account.last_name}</td>
                                                <td><small>${account.created_at.toDate()}</small></td>
                                                <td><small>${account.last_modified.toDate()}</small></td>
                                                
                                                <!-- TODO add a checkbox to tell to not do cascade delete -->
						<td><a href="#deleteModal" data-toggle="modal" data-endpoint="account" 
							data-id="${account.id}" class="open-DeleteDialog btn btn-danger btn-small">Delete</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

	</div>

	<div id="deleteModal" class="modal hide fade" tabindex="-1">
		<div class="modal-header">
			<h3 id="deleteModalLabel">Delete account</h3>
		</div>
		<div class="modal-body">
			<div>Are you sure you want to delete the account?<br/>
			This will delete some related objects associated with the account.<br/>
			Note that you may set the account disabled instead.
			</div>

		</div>
		<div class="modal-footer">
			<div id="deleteBtn"></div>
		</div>
	</div>

</body>
</html>