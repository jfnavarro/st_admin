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
                $(document).ready(function (event) {
                    $("#menuDataset").addClass("active");
                });
            </script>

    </head>
    <body>

        <c:import url="header.jsp"></c:import>
            <div class="container">
                <div class="page-header">
                    <h1> Dataset <small>${dataset.name}</small> </h1>
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

                <dt>Comments</dt>
                <dd>${dataset.comment}&nbsp;</dd>

                <dt>Created</dt>
                <dd>${dataset.created_at.toDate()}&nbsp;</dd>
                <dt>Last modified</dt>
                <dd>${dataset.last_modified.toDate()}&nbsp;</dd>
                
                <dt>ST Data file</dt>
                <dd>
                    <a href="<c:url value="/dataset/files/"/>${dataset.id}?=filename=${dataset.dataFile}" 
                       target="_blank">Download ${dataset.dataFile}
                    </a> (right click and select "Save link as...")
                </dd>

            </dl>

            <dl class="dl-horizontal">
                
                <dt>Figure (HE)</dt>
                <dd>
                    <td><a href="<c:url value="/image/compressed/"/>${dataset.figureHE}" target="_blank">${dataset.figureHE}</a></td>
                </dd>
                    
                <dt>Figure (Cy3) (optional)</dt>
                <dd>
                    <td><a href="<c:url value="/image/compressed/"/>${dataset.figureCy3}" target="_blank">${dataset.figureCy3}</a></td>
                </dd>
                    
                <dt>Alignment matrix (optional)</dt>
                <dd>
                    <table border="1">
                        <tr><td>${dataset.alignmentMatrix[0]}</td><td>${dataset.alignmentMatrix[1]}</td><td>${dataset.alignmentMatrix[2]}</td></tr>
                        <tr><td>${dataset.alignmentMatrix[3]}</td><td>${dataset.alignmentMatrix[4]}</td><td>${dataset.alignmentMatrix[5]}</td></tr>
                        <tr><td>${dataset.alignmentMatrix[6]}</td><td>${dataset.alignmentMatrix[7]}</td><td>${dataset.alignmentMatrix[8]}</td></tr>
                    </table>
                </dd>

                <dt>Files</dt>
                <dd>
                    <c:forEach var="filename" items="${dataset.files}">
                        <a href="<c:url value="/dataset/files/"/>${dataset.id}?filename=${filename}" 
                           target="_blank">Download ${filename} 
                        </a> (right click and select "Save link as...")
                    </c:forEach>    
                </dd>
                
                <dt>Granted accounts</dt>
                <dd>
                    <c:forEach var="account" items="${accounts}"> ${account.username} <br/>
                    </c:forEach>
                </dd>
                
            </dl>

        </div>
        <!-- /container -->

    </body>
</html>