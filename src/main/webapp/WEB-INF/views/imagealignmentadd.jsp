<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">

    <head>
        <title>Create image alignment</title>

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
                    $("#menuTask").addClass("active");
                });
            </script>

            <!--  Scripts for Multiselect -->	
            <script type="text/javascript"
            src="<c:url value="/js/bootstrap-multiselect.js"/>"></script>
            <script type="text/javascript"
            src="<c:url value="/js/st-multiselect-button.js"/>"></script>


    </head>
    <body>
        <c:set var="contextPath" value="${pageContext.request.contextPath}" />

        <c:import url="header.jsp"></c:import>

            <div class="container">



                <div class="page-header">
                    <h1>Create image alignment</h1>
                </div>


            <c:if test="${not empty errors}">
                <div class="alert alert-error">
                    <strong>Error: </strong>Your input is not valid. Please check the
                    values in the form below.
                </div>
            </c:if>

            <!--<c:forEach var="err" items="${errors}" varStatus="idx">
                    <div class="alert alert-error">${err.defaultMessage}</div>
            </c:forEach> -->

            <div>
                <form:form method="POST" commandName="imagealignment"
                           action="${contextPath}/imagealignment/submitadd" class="form-horizontal">



                    <spring:bind path="name">
                        <div class="control-group ${status.error ? 'error' : ''}">
                            <label class="control-label" for="inputName">Name</label>
                            <div class="controls">
                                <form:input type="text" id="inputName" placeholder="Name"
                                            path="name" />
                                <span class='help-inline'>${status.errorMessage}</span>
                            </div>
                        </div>
                    </spring:bind>


                    <spring:bind path="chip_id">
                        <div class="control-group  ${status.error ? 'error' : ''}">
                            <label class="control-label" for="inputChip">Chip</label>
                            <div class="controls">
                                <form:select id="inputChip" path="chip_id">
                                    <form:options items="${chipChoices}"></form:options>
                                </form:select>
                                <span class='help-inline'>${status.errorMessage}</span>
                            </div>
                        </div>
                    </spring:bind>

                    <spring:bind path="figure_red">
                        <div class="control-group  ${status.error ? 'error' : ''}">
                            <label class="control-label" for="imageRed">Image red</label>
                            <div class="controls">
                                <form:select id="imageRed" path="figure_red">
                                    <form:options items="${imageChoices}"></form:options>
                                </form:select>
                                <span class='help-inline'>${status.errorMessage}</span>
                            </div>
                        </div>
                    </spring:bind>


                    <spring:bind path="figure_blue">
                        <div class="control-group  ${status.error ? 'error' : ''}">
                            <label class="control-label" for="imageBlue">Image blue</label>
                            <div class="controls">
                                <form:select id="imageBlue" path="figure_blue">
                                    <form:options items="${imageChoices}"></form:options>
                                </form:select>
                                <span class='help-inline'>${status.errorMessage}</span>
                            </div>
                        </div>
                    </spring:bind>

                    <spring:bind path="alignment_field1">
                        <div class="control-group  ${status.error ? 'error' : ''}">
                            <label class="control-label" for="alignment">Alignment
                                matrix</label>
                            <div class="controls controls-row">
                                <form:input class="span1" type="text" id="alignmentField1"
                                            path="alignment_field1" />
                                <form:input class="span1" type="text" id="alignmentField2"
                                            path="alignment_field2" />
                                <form:input class="span1" type="text" id="alignmentField3"
                                            path="alignment_field3" />
                            </div>
                            <div class="controls controls-row">
                                <form:input class="span1" type="text" id="alignmentField4"
                                            path="alignment_field4" />
                                <form:input class="span1" type="text" id="alignmentField5"
                                            path="alignment_field5" />
                                <form:input class="span1" type="text" id="alignmentField6"
                                            path="alignment_field6" />
                            </div>
                            <div class="controls controls-row">
                                <form:input class="span1" type="text" id="alignmentField7"
                                            path="alignment_field7" />
                                <form:input class="span1" type="text" id="alignmentField8"
                                            path="alignment_field8" />
                                <form:input class="span1" type="text" id="alignmentField9"
                                            path="alignment_field9" />
                            </div>

                        </div>

                    </spring:bind>


                    <div>
                        <button type="submit" class="btn">Create</button>
                    </div>
                </form:form>
            </div>
            <div>
                <a href="<c:url value="/imagealignment/"/>">Cancel</a>
            </div>

        </div>

    </body>
</html>
