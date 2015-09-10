<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Edit account</title>
<link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet" media="screen">

<!-- Boostrap and JQuery libraries, for the logout button and other JS features -->
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>

<!-- Script to set the highlight the active menu in the header -->
<script>
	$(document).ready(function(event) {
		$("#menuAccount").addClass("active");
	});
</script>

<!--  Scripts for Multiselect -->
<script type="text/javascript" src="<c:url value="/js/bootstrap-multiselect.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/st-multiselect-button.js"/>"></script>

</head>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<c:import url="header.jsp"></c:import>
	<div class="container">
		<div class="page-header">
			<h1>
				Edit account <small>${account.username}</small>
			</h1>
		</div>

		<c:if test="${not empty errors}">
			<div class="alert alert-error">
				<strong>Error: </strong>Your input is not valid. Please check the values in the form below.
			</div>
		</c:if>

		<div>
			<form:form method="POST" commandName="account"
				action="${contextPath}/account/submitedit" class="form-horizontal">

				<spring:bind path="username">
					<div class="control-group ${status.error ? 'error' : ''}">
						<label class="control-label" for="inputUsername">Username (email)</label>
						<div class="controls">
							<form:input type="text" id="inputUsername" placeholder="Username" path="username" />
							<span class='help-inline'>${status.errorMessage}</span>
						</div>
					</div>
				</spring:bind>

				<spring:bind path="role">
				<div class="control-group">
					<label class="control-label" for="inputRole">Role</label>
					<div class="controls">
						<form:select id="inputRole" path="role">
							<form:option label="User (default)" value="ROLE_USER"></form:option>
							<form:option label="Content manager" value="ROLE_CM"></form:option>
							<form:option label="Administrator" value="ROLE_ADMIN"></form:option>
						</form:select>
					</div>
				</div>
				</spring:bind>

				<spring:bind path="password">
					<div class="control-group  ${status.error ? 'error' : ''}">
						<label class="control-label" for="inputPassword">Password</label>
						<div class="controls">
							<form:input type="password" id="inputPassword"
								placeholder="Password" path="password" />
							<span class='help-inline'>${status.errorMessage}</span>
						</div>
					</div>
				</spring:bind>
                            
                                <spring:bind path="passwordRepeat">
					<div class="control-group  ${status.error ? 'error' : ''}">
						<label class="control-label" for="inputPasswordRepeat">Repeat password</label>
						<div class="controls">
							<form:input type="password" id="inputPasswordRepeat"
								placeholder="Password" path="passwordRepeat" />
							<span class='help-inline'>${status.errorMessage}</span>
						</div>
					</div>
				</spring:bind>

				<spring:bind path="enabled">
				<div class="control-group">
					<label class="control-label">Account enabled</label>
					<div class="controls">
						<form:checkbox path="enabled" />
					</div>
				</div>
				</spring:bind>

				<spring:bind path="institution">
					<div class="control-group">
						<label class="control-label" for="inputInstitution">Institution</label>
						<div class="controls">
							<form:input type="text" id="inputInstitution" placeholder="Institution"
								path="institution" />
						</div>
					</div>
				</spring:bind>
				
				<spring:bind path="first_name">
					<div class="control-group">
						<label class="control-label" for="inputFirst_name">First name</label>
						<div class="controls">
							<form:input type="text" id="inputFirst_name" placeholder="First name"
								path="first_name" />
						</div>
					</div>
				</spring:bind>
				
				<spring:bind path="last_name">
					<div class="control-group">
						<label class="control-label" for="inputLast_name">Last name</label>
						<div class="controls">
							<form:input type="text" id="inputLast_name" placeholder="Last name"
								path="last_name" />
						</div>
					</div>
				</spring:bind>
				
				<spring:bind path="street_address">
					<div class="control-group">
						<label class="control-label" for="inputStreet_address">Street address</label>
						<div class="controls">
							<form:input type="text" id="inputStreet_address" placeholder="Street address"
								path="street_address" />
						</div>
					</div>
				</spring:bind>

				<spring:bind path="city">
					<div class="control-group">
						<label class="control-label" for="inputCity">City</label>
						<div class="controls">
							<form:input type="text" id="inputCity" placeholder="City"
								path="city" />
						</div>
					</div>
				</spring:bind>
				
				<spring:bind path="postcode">
					<div class="control-group">
						<label class="control-label" for="inputPostcode">Post code</label>
						<div class="controls">
							<form:input type="text" id="inputPostcode" placeholder="Post code"
								path="postcode" />
						</div>
					</div>
				</spring:bind>

				<spring:bind path="country">
					<div class="control-group">
						<label class="control-label" for="inputCountry">Country</label>
						<div class="controls">
							<form:input type="text" id="inputCountry" placeholder="Country" path="country" />
						</div>
					</div>
				</spring:bind>
			    
			    
                                <spring:bind path="granted_datasets">
                                    <div class="control-group">
                                        <label class="control-label" for="inputGrantedDatasets">Granted datasets</label>
                                        <div class="controls">
                                            <form:select id="inputGrantedDatasets" path="granted_datasets" class="multiselect" multiple="multiple">
                                                <form:options items="${datasetChoices}"></form:options>
                                            </form:select>
                                        </div>
                                    </div>
                                </spring:bind>
                            
				<button type="submit" class="btn">Save</button>
				<form:input type="hidden" path="id" />
                                <form:input type="hidden" path="created_at" />
				<form:input type="hidden" path="last_modified" />
                                
			</form:form>
		</div>
		<div>
			<a href="<c:url value="/account/"/>${account.id}">Cancel</a>
		</div>

	</div>
</body>
</html>
