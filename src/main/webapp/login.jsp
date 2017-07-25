<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Spatial Transcriptomics Research Admin Console</title>
        <link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet"
              media="screen">
            <style type="text/css">
                body {
                    padding-top: 60px;
                    padding-bottom: 40px;
                    background-color: #f5f5f5;
                }

                .form-signin {
                    max-width: 300px;
                    padding: 19px 29px 29px;
                    margin: 0 auto 20px;
                    background-color: #fff;
                    border: 1px solid #e5e5e5;
                    -webkit-border-radius: 5px;
                    -moz-border-radius: 5px;
                    border-radius: 5px;
                    -webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
                    -moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
                    box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
                }

                .form-signin .form-signin-heading,.form-signin .checkbox {
                    margin-bottom: 10px;
                }

                .form-signin input[type="text"],.form-signin input[type="password"] {
                    font-size: 16px;
                    height: auto;
                    margin-bottom: 15px;
                    padding: 7px 9px;
                }
            </style>
    </head>
    <body>

        <div class="navbar navbar-inverse navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container">
                    <button type="button" class="btn btn-navbar" data-toggle="collapse"
                            data-target=".nav-collapse">
                        <span class="icon-bar"></span> <span class="icon-bar"></span> <span
                            class="icon-bar"></span>
                    </button>
                    <a class="brand" href="#">ST Research Admin Console</a>

                    <!--/.nav-collapse -->
                </div>
            </div>
        </div>

        <div class="container">

            <c:if test="${not empty param.authentication_error}">
                <div class="alert alert-error">Login failed. Wrong credentials?
                </div>
            </c:if>
            <c:if test="${not empty param.authorization_error}">
                <div class="alert alert-error">Login failed. You have to have
                    permissions to access this resource.
                </div>
            </c:if>


            <form class="form-signin" id="loginForm" name="loginForm"
                  action="<c:url value="/login.do"/>" method="post">
                <h2 class="form-signin-heading">Please sign in</h2>
                <input type="text" class="input-block-level" placeholder="Username (email)" name='j_username'> 
                <input type="password" class="input-block-level" placeholder="Password" name='j_password'>
                <button class="btn btn-large btn-primary" type="submit" name="login">Sign in</button>
            </form>

        </div>
         <!-- /container -->
    </body>
</html>
