<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Create dataset</title>
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

            <!-- Script to style file upload -->
            <script type="text/javascript"
            src="<c:url value="/js/bootstrap-filestyle.min.js"/>"></script>


    </head>
    <body>
        <c:import url="header.jsp"></c:import>
        <c:set var="contextPath" value="${pageContext.request.contextPath}" />

        <div class="container">


            <div class="page-header">
                <h1>Create dataset</h1>
            </div>


            <c:if test="${not empty errors}">
                <div class="alert alert-error">
                    <strong>Error: </strong>Your input is not valid. Please check the
                    values in the form below.
                </div>
            </c:if>

            <c:if test="${not empty featureerror}">
                <div class="alert alert-error">
                    <strong>Error: </strong>${featureerror}
                </div>
            </c:if>

            <div>

                <div class="row">
                    <form:form method="POST" commandName="datasetform"
                               action="${contextPath}/dataset/submitadd" class="form-horizontal"
                               enctype="multipart/form-data">

                        <fieldset>

                            <div class="span6">
                                <legend>General</legend>

                                <spring:bind path="dataset.name">
                                    <div class="control-group  ${status.error ? 'error' : ''}">
                                        <label class="control-label" for="inputName">Name</label>
                                        <div class="controls">
                                            <form:input type="text" id="inputName" placeholder="Name"
                                                        path="dataset.name" />
                                            <span class='help-inline'>${status.errorMessage}</span>
                                        </div>
                                    </div>
                                </spring:bind>

                                <spring:bind path="dataset.enabled">
                                    <div class="control-group">
                                        <label class="control-label">Dataset enabled</label>
                                        <div class="controls">
                                            <form:checkbox path="dataset.enabled" checked="true"/>
                                        </div>
                                    </div>
                                </spring:bind>

                                <spring:bind path="dataset.species">
                                    <div class="control-group  ${status.error ? 'error' : ''}">
                                        <label class="control-label" for="species">Species</label>
                                        <div class="controls">
                                            <form:input type="text" id="species" placeholder="Species"
                                                        path="dataset.species" />
                                            <span class='help-inline'>${status.errorMessage}</span>
                                        </div>
                                    </div>
                                </spring:bind>

                                <spring:bind path="dataset.tissue">
                                    <div class="control-group  ${status.error ? 'error' : ''}">
                                        <label class="control-label" for="tissue">Tissue</label>
                                        <div class="controls">
                                            <form:input type="text" id="tissue" placeholder="Tissue"
                                                        path="dataset.tissue" />
                                            <span class='help-inline'>${status.errorMessage}</span>
                                        </div>
                                    </div>
                                </spring:bind>

                                <spring:bind path="dataset.tissueHE">
                                    <div class="control-group  ${status.error ? 'error' : ''}">
                                        <label class="control-label" for="tissueHE">Image HE</label>
                                        <div class="controls">
                                            <form:select id="tissueHE" path="tissueHE">
                                                <form:options items="${imageChoices}"></form:options>
                                            </form:select>
                                            <span class='help-inline'>${status.errorMessage}</span>
                                        </div>
                                    </div>
                                </spring:bind>

                                <spring:bind path="dataset.tissueCy3">
                                    <div class="control-group  ${status.error ? 'error' : ''}">
                                        <label class="control-label" for="tissueCy3">Image Cy3 (Optional)</label>
                                        <div class="controls">
                                            <form:select id="tissueCy3" path="tissueCy3">
                                                <form:options items="${imageChoices}"></form:options>
                                            </form:select>
                                            <span class='help-inline'>${status.errorMessage}</span>
                                        </div>
                                    </div>
                                </spring:bind>
a
                                <spring:bind path="dataset.grantedAccounts">
                                    <div class="control-group">
                                        <label class="control-label" for="inputGrantedAccounts">Granted accounts</label>
                                        <div class="controls">
                                            <form:select id="inputGrantedAccounts" path="dataset.grantedAccounts" class="multiselect" multiple="multiple">
                                                <form:options items="${accountChoices}"></form:options>
                                            </form:select>
                                        </div>
                                    </div>
                                </spring:bind>

                                <!--  start features -->

                                <legend>
                                    ST Data <small>(Data file from the pipeline in TSV format)</small>
                                </legend>

                                <spring:bind path="dataset.dataFile">
                                    <div class="control-group  ${status.error ? 'error' : ''}">
                                        <label class="control-label" for="featureFile">ST Data file</label>
                                        <div class="control-group">
                                            <div class="controls">
                                                <form:input type="file" id="featureFile"
                                                            placeholder="Feature file" path="featureFile"
                                                            class="filestyle" />
                                                <span class='help-inline'>${status.errorMessage}</span>
                                            </div>
                                        </div>
                                    </div>
                                </spring:bind>

                                <!-- end features -->

                            </div>


                            <div class="span4">

                                <legend>Comments</legend>

                                <spring:bind path="dataset.comment">
                                    <div class="control-group  ${status.error ? 'error' : ''}">
                                        <label class="control-label" for="comments">Comments</label>
                                        <div class="controls">
                                            <form:textarea rows="5" id="comments"
                                                           placeholder="Comments" path="dataset.comment"></form:textarea>
                                            <span class='help-inline'>${status.errorMessage}</span>
                                        </div>
                                    </div>
                                </spring:bind>

                            </div>

                        </fieldset>

                        <div class="control-group">
                            <button type="submit" class="btn">Create</button>
                        </div>

                    </form:form>
                </div>

            </div>

            <div>
                <a href="<c:url value="/dataset/"/>">Cancel</a>
            </div>

        </div>


        <!-- Load File upload style -->
        <script>
                $(":file").filestyle({
                    buttonText: "Choose .json.gz file",
                    classInput: "input-small"
                });
        </script>


    </body>
</html>
