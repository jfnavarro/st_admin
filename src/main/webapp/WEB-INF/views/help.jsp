<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Help</title>
<link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet"
	media="screen">

<!-- Boostrap and JQuery libraries, for the logout button and other JS features -->
<script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script
	src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>

<!-- Script to set the highlight the active menu in the header -->
<script>
	$(document).ready(function(event) {
		$("#menuHelp").addClass("active");
	});
</script>

</head>
<body>

	<c:import url="header.jsp"></c:import>


	<div class="container">

		<div class="page-header">
			<h1>Help</h1>
		</div>

		<h2>Datasets</h2>


		<div>
			Create Datasets and assign Chips and images to Datasets:
			<p />

			<ul>
				<li>Before you can create a new Dataset the Chip and images
					need to be imported to the system.</li>
				<li>Go to the page "Dataset" --&gt; "Create Dataset".</li>
				<li>Select image alignment, granted accounts and enter the dataset information.</li>
				<li>Select a .json Feature file from your local computer or an
					experiment that you ran earlier.</li>
				<li>Click "Create" to import the Features and create the
					Dataset.</li>
			</ul>

		</div>

		<div>
			Edit the Dataset information after the import:
			<p />

			<ul>
				<li>Go to the page "Dataset".</li>
				<li>Click on the Dataset you want to edit.</li>
				<li>On the "Dataset" details page click on "Edit Dataset".</li>
				<li>Adjust the values you want to edit.</li>
				<li>To update the Features, select a Feature file or an
					Experiment. To keep the current Features, don't select anything
					here.</li>
				<li>Click "Save".</li>
			</ul>

		</div>


		<h2>Chips</h2>

		<div>
			Import Chips from a .ndf file on your local computer:
			<p />
			<ul>
				<li>Go to the page "Chips" --&gt; "Import Chip".</li>
				<li>Choose a chip name and select a .ndf file from your local
					computer.</li>
				<li>Click "Import" to upload the file and import the Chip.</li>
				<li>Find the ID of the new Chip on the "Chips" page.</li>
			</ul>

			Edit Chip information after the import:
			<p />

			<ul>
				<li>Go to the page "Chips".</li>
				<li>Click on the Chip you want to edit.</li>
				<li>On the "Chip" details page click on "Edit Chip".</li>
				<li>Adjust the values you want to edit and click "Save".</li>
			</ul>
		</div>

		<h2>Images</h2>

		<div>
                        Import images from .jpg files on your local computer:

			<p />

			<ul>
				<li>Go to the page "Images" --&gt; "Import image".</li>
				<li>Choose a .jpg file from your local computer.</li>
				<li>Click "Import" to upload the file and import the image.</li>
			</ul>

		</div>
                
                <h2>Alignments</h2>

		<div>
                        Image alignments are used to co-align images with the chip that was used.
                        The matrix that governs the alignment is currently computed by an external
                        script whose output need be set manually.
			<p />

			<ul>
				<li>Go to the page "Alignments" --&gt; "Create image alignment".</li>
				<li>Specify the images and the chip used, along with the alignment matrix.</li>
				<li>Click "Create".</li>
			</ul>

		</div>

		<h2>Accounts</h2>

		Create user accounts and grant access to datasets:

		<p />

		<ul>
			<li>Go to the page "Accounts" --&gt; "Create Account".</li>
			<li>Choose a user name. Note that this must be an email address.</li>
			<li>Select a user role. To create a normal account (with client rights only), select
				"User". The Content manager role is allowed not only to the client,
                                but also to access parts of the admin console, such as for executing
                                jobs. The Administrator role has full access to the admin console, such as
                                for administering accounts.</li>
			<li>Select the Datasets you want to grant to the user.</li>
			<li>Choose a password for the account.</li>
			<li>Check "Enable Account" box to enable the account. You can
				also enable the account later.</li>
			<li>Click "Create"</li>
		</ul>

		Edit user accounts:
		<p />

		<ul>
			<li>Go to the page "Accounts".</li>
			<li>Click on the Account you want to edit.</li>
			<li>On the "Account" details page click on "Edit Account".</li>
			<li>Adjust the values you want to edit.</li>
			<li>Click "Save".</li>
		</ul>


	</div>
	<!-- /container -->




</body>
</html>