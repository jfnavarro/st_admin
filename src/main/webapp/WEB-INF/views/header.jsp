<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    
<link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet" media="screen">

<style>
body {
	padding-top: 60px;
	/* 60px to make the container go all the way to the bottom of the topbar */
}
</style>

<!--  Logout Button script  -->
<script>
	$('.dropdown-toggle').dropdown();
</script>

</head>
<body>

	<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
				</button>

				<div class="nav-collapse collapse">
					<ul class="nav">
						<li id="menuHome"><a href="<c:url value="/home"/>">Home</a></li>
						<li id="menuDataset"><a href="<c:url value="/dataset"/>">Datasets</a></li>
						<li id="menuPipelineExperiment"><a href="<c:url value="/pipelineexperiment"/>">Experiments</a></li>					
						<li id="menuChip"><a href="<c:url value="/chip"/>">Chips</a></li>
						<li id="menuImage"><a href="<c:url value="/image"/>">Images</a></li>
						<li id="menuAligner"><a href="<c:url value="/imagealignment"/>">Alignments</a></li>

						<sec:authorize ifAnyGranted="ROLE_ADMIN">
							<li id="menuAccount"><a href="<c:url value="/account"/>">Accounts</a></li>
						</sec:authorize>
                                                
						<li id="menuHelp"><a href="<c:url value="/help"/>">Help</a></li>
					</ul>
					
					<div class="btn-group pull-right ">
						<a class="btn dropdown-toggle btn-small btn-primary"
							data-toggle="dropdown" href="#"> <i
							class="icon-user icon-white"></i> <sec:authentication property="principal" /> 
                                                        <span class="caret"></span>
						</a>
						<ul class="dropdown-menu">
							<li><a href="<c:url value="/logout.do"/>">Sign out</a></li>
						</ul>
					</div>

				</div>

			</div>
		</div>
	</div>

</body>
</html>
