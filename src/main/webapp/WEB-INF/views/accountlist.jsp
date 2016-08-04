<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Accounts</title>
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
                    $("#menuAccount").addClass("active");
                });
            </script>

            <!--  Script for Delete dialog -->
            <script>
                $(document)
                        .on("click",
                            ".open-DeleteDialog",
                            function () {
                                    var theId = $(this).data('id');
                                    $(".modal-footer #deleteBtn")
                                            .html(
                                                    '<a href="#" class="btn" data-dismiss="modal" aria-hidden="true">Cancel</a> <a href="<c:url value="/account/"/>'
                                                    + theId
                                                    + '/delete" class="btn btn-danger">Delete</a>');
                                });
            </script>


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


            <sec:authorize ifAnyGranted="ROLE_ADMIN">
                <div>
                    <a href="<c:url value="/account/add"/>">Create account</a>
                </div>
            </sec:authorize>

            <table class="table">
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
                        <th>Street address</th>
                        <th>City</th>
                        <th>Post code</th>
                        <th>Country</th>
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
                            <td>${account.street_address}</td>
                            <td>${account.city}</td>
                            <td>${account.postcode}</td>
                            <td>${account.country}</td>
                            <sec:authorize ifAnyGranted="ROLE_ADMIN">
                                <td><a href="#deleteModal" data-toggle="modal"
                                       data-id="${account.id}" class="open-DeleteDialog btn btn-danger btn-small">Delete</a>
                                </td>
                            </sec:authorize>
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
                        This may invalidate some related objects associated with the account.<br/>
                        Note that you may set the account unabled instead.
                    </div>

                </div>
                <div class="modal-footer">
                    <div id="deleteBtn"></div>
                </div>
            </div>

    </body>
</html>