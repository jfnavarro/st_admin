<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Image alignments</title>
        <link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet"
              media="screen">

            <!-- Boostrap and JQuery libraries, for the logout button and other JS features -->
            <script
            src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
            <script
            src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>

            <!-- Script to set the highlight the active menu in the header -->
            <script>
                $(document).ready(function (event) {
                    $("#menuImageAlignment").addClass("active");
                });
            </script>

            <!-- Script for Delete dialog -->
            <script>
                $(document)
                        .on(
                                "click",
                                ".open-DeleteDialog",
                                function () {
                                    var theId = $(this).data('id');
                                    $(".modal-footer #deleteBtn")
                                            .html(
                                                    '<a href="#" class="btn" data-dismiss="modal" aria-hidden="true">Cancel</a> <a href="<c:url value="/imagealignment/"/>'
                                                    + theId
                                                    + '/delete" class="btn btn-danger">Delete</a>');
                                });
            </script>

    </head>
    <body>

        <c:import url="header.jsp"></c:import>

            <div class="container">

                <div class="page-header">
                    <h1>Image alignments</h1>
                </div>

            <c:if test="${not empty msg}">
                <div class="alert alert-success">
                    <strong>Success! </strong>${msg}
                </div>
            </c:if>

            <div>
                <a href="<c:url value="/imagealignment/add"/>">Create image alignment</a>
            </div>

            <table class="table">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Chip</th>
                        <th>Figure red</th>
                        <th>Figure blue</th>
                        <th>Created</th>
                        <th>Last modified</th>
                    </tr>

                </thead>
                <tbody>
                    <c:forEach var="imal" items="${imagealignmentList}">
                        <tr>
                            <td><a href="<c:url value="/imagealignment/"/>${imal.id}">${imal.name}</a></td>
                            <td>${chipChoices[imal.chip_id]}</td>
                            <td>${imal.figure_red}</td>
                            <td>${imal.figure_blue}</td>
                            <td><small><fmt:formatDate value="${imal.created_at.toDate()}" pattern="yyyy-MM-dd HH:mm:ss" /></small></td>
                            <td><small><fmt:formatDate value="${imal.last_modified.toDate()}" pattern="yyyy-MM-dd HH:mm:ss" /></small></td>
                            <td><a href="#deleteModal" data-toggle="modal"
                                   data-id="${imal.id}"
                                   class="open-DeleteDialog btn btn-danger btn-small">Delete</a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

        </div>
        <!-- /container -->

        <div id="deleteModal" class="modal hide fade" tabindex="-1">
            <div class="modal-header">
                <h3 id="deleteModalLabel">Delete image alignment</h3>
            </div>
            <div class="modal-body">
                <div>Are you sure you want to delete the image alignment?<br/>
                    This will unable (but not delete) all datasets that use the image alignment, and remove associated files from Amazon.</div>

            </div>
            <div class="modal-footer">
                <div id="deleteBtn"></div>
            </div>
        </div>

    </body>
</html>