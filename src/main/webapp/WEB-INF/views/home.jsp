<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Home</title>
        <link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet"
              media="screen">

            <!-- Boostrap and JQuery libraries, for the logout button and other JS features -->
            <script
            src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
            <script
            src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>

    </head>
    <body>

        <c:import url="header.jsp"></c:import>


            <div class="container">

                <!-- Main hero unit for a primary marketing message or call to action -->
                <div class="hero-unit">
                    <h1>ST Research Admin Console</h1>
                    <p>Manage datasets, chips and images or administrate user accounts.</p>
                    <p>
                        <a href="<c:url value="/help"/>" class="btn btn-success btn-large">Learn
                        how &raquo;</a>
                </p>
            </div>




        </div>
        <!-- /container -->




    </body>
</html>