<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Features</title>
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
                    $("#menuDataset").addClass("active");
                });
            </script>

    </head>
    <body>

        <c:import url="header.jsp"></c:import>

            <div class="container">



                <div class="page-header">
                    <h1>
                        ST Data <small>Dataset ${dataset.name}</small>
                </h1>
            </div>


            <div>
                <a href="<c:url value="/dataset/"/>${dataset.id}">Back</a>
            </div>



            <table class="table table-condensed">
                <thead>
                    <tr>
                        <th>Gene nomenclature</th>
                        <th>Barcode</th>
                        <th>Hits</th>
                        <th>X</th>
                        <th>Y</th>
                    </tr>

                </thead>
                <tbody>
                    <c:forEach var="feature" items="${featureList}">
                        <tr>
                            <td>${feature.gene}</td>
                            <td>${feature.barcode}</td>
                            <td>${feature.hits}</td>
                            <td>${feature.x}</td>
                            <td>${feature.y}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>



        </div>
        <!-- /container -->

    </body>
</html>