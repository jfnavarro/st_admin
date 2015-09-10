<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Datasets</title>

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
    $('#datasets').dataTable({
        "bJQueryUI": true,
        "columnDefs": [
            { "orderable": false, "targets": 7 }
        ]
    });
} );
</script>

<!-- Script for Delete dialog -->
<script> 
/*$(document).ready(function() {
    $('#delete-dialog').dialog({
        autoOpen: false, 
        width: 400,
        modal: true
    });
    
    $("body").on("click", ".delete_modal", function (e) {
        e.preventDefault();
        var targetUrl = $(this).attr("href");
        $('#delete-dialog').dialog({
          buttons: {
            "Delete": function() {
              window.location.href = targetUrl;
            },
            "Cancel": function() {
              $(this).dialog("close");
            }
          }
        });
        $('#delete-dialog').dialog("open");
    });
});*/

$(document).on("click", ".open-DeleteDialog", function() {
    $('#confirm-delete').on('show.bs.modal', function(e) {
        $(this).find('.danger').attr('href', $(e.relatedTarget).data('href'));
    });
});
</script>

<!-- Script to set the highlight the active menu in the header --> 
<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
    $("#menuDataset").addClass("active");
});
</script>


</head>

<body>
	
<div class="container">
			
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
                    <a href="<c:url value="/dataset/add"/>">Create dataset</a> <br></br>
		</div>
                
		<table cellpadding="0" cellspacing="0" border="0" class="table table-compact table-hover table-oder-column table-condensed" id="datasets">
			<thead>
				<tr>
					<th>Name</th>
					<th>Enabled</th>
					<th>Tissue</th>
					<th>Species</th>
                                        <th>Account creator</th>
                                        <th>Created</th>
                                        <th>Last modified</th>
                                        <th></th>
				</tr>

			</thead>
			<tbody>
				<c:forEach var="dataset" items="${datasetList}">
					<tr>
						<td><a href="<c:url value="/dataset/"/>${dataset.id}">${dataset.name}</a></td>
                                                <td>
							<c:choose>
                                                            <c:when test="${dataset.enabled == true}">
                                                                <input type="checkbox" name="chkEnabled" value="" checked="checked" onclick="return false">
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input type="checkbox" name="chkEnabled" value="" onclick="return false">
                                                            </c:otherwise>
							</c:choose>
						</td>
						<td>${dataset.tissue}</td>
						<td>${dataset.species}</td>
                                                <td>${accountChoices[dataset.created_by_account_id]}</td>
                                                <td><small><fmt:formatDate value="${dataset.created_at.toDate()}" pattern="yyyy-MM-dd HH:mm:ss" /></small></td>
                                                <td><small><fmt:formatDate value="${dataset.last_modified.toDate()}" pattern="yyyy-MM-dd HH:mm:ss" /></small></td>
                                                <!--<td><a href="<c:url value="/dataset/"/>${dataset.id}/delete" class="delete_modal"><span class="icos-trash"></span>Delete</a></td>-->
                                                <td><a data-href="<c:url value="/dataset/"/>${dataset.id}/delete" data-toggle="modal" data-target="#confirm-delete" href="#">Delete</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>	
	</div>
                
                
<div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                ...
            </div>
            <div class="modal-body">
                ...
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <a href="#" class="btn btn-danger danger">Delete</a>
            </div>
        </div>
    </div>
</div>
                
        <!--<div id="delete-dialog" title="Remove User">
            <p>Are you sure you want to delete this user?</p>
        </div>-->
                
        <!--<div id="deleteModal" class="modal hide fade" tabindex="-1">
                <div class="modal-header">
                        <h3 id="deleteModalLabel">Delete dataset</h3>
                </div>
                <div class="modal-body">
                        <div>Are you sure you want to delete the dataset?<br/>
                        This will delete all features and selections of the dataset.<br/>
                        Note that you may set it disable instead.
                        </div>

                </div>
                <div class="modal-footer">
                        <div id="deleteBtn"></div>
                </div>
        </div>-->

</body>
</html>