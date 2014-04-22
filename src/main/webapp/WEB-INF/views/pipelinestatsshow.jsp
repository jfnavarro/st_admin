<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Pipeline experiment statistics</title>
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
		$("#menuPipelineExperiment").addClass("active");
	});
</script>

</head>
<body>

	<c:import url="header.jsp"></c:import>

	<div class="container">
		<div class="page-header">
			<h1>
				Statistics for pipeline experiment <small>${pipelineexperiment.name}</small>
			</h1>
		</div>
		<div>
			<a href="<c:url value="/pipelineexperiment/${pipelinestats.experiment_id}"/>">Back</a>
		</div>

		<div class="row">
			<div class="span2 pull-right">
				<a href="javascript:history.go(0)" class="btn">Refresh</a>
			</div>
		</div>

		<c:if test="${not empty msg}">
			<div class="alert alert-success">
				<strong>Success! </strong>${msg}
			</div>
		</c:if>

		<dl class="dl-horizontal">

			<dt>Document ID</dt>
			<dd>${pipelinestats.doc_id}&nbsp;</dd>
			<dt>Input files</dt>
			<dd>
				<c:forEach var="filename" items="${pipelinestats.input_files}">
				${filename} <br/>
				</c:forEach>
			</dd>
			<dt>Output files</dt>
			<dd>
				<c:forEach var="filename" items="${pipelinestats.output_files}">
				${filename} <br/>
				</c:forEach>
			</dd>
			<dt>Parameters</dt>
			<dd>${pipelinestats.parameters}&nbsp;</dd>
			<dt>Status</dt>
			<dd>${pipelinestats.status}&nbsp;</dd>
			<dt># of reads mapped</dt>
			<dd>${pipelinestats.no_of_reads_mapped}&nbsp;</dd>
			<dt># of reads annotated</dt>
			<dd>${pipelinestats.no_of_reads_annotated}&nbsp;</dd>
			<dt># of reads mapped with find_indexes</dt>
			<dd>${pipelinestats.no_of_reads_mapped_with_find_indexes}&nbsp;</dd>
			<dt># of reads contaminated</dt>
			<dd>${pipelinestats.no_of_reads_contaminated}&nbsp;</dd>
			<dt># of barcodes found</dt>
			<dd>${pipelinestats.no_of_barcodes_found}&nbsp;</dd>
			<dt># of genes found</dt>
			<dd>${pipelinestats.no_of_genes_found}&nbsp;</dd>
			<dt># of transcripts found</dt>
			<dd>${pipelinestats.no_of_transcripts_found}&nbsp;</dd>
			<dt># of reads found</dt>
			<dd>${pipelinestats.no_of_reads_found}&nbsp;</dd>
			<dt>Mapper tool</dt>
			<dd>${pipelinestats.mapper_tool}&nbsp;</dd>
			<dt>Mapper genome</dt>
			<dd>${pipelinestats.mapper_genome}&nbsp;</dd>
			<dt>Annotation tool</dt>
			<dd>${pipelinestats.annotation_tool}&nbsp;</dd>
			<dt>Annotation genome</dt>
			<dd>${pipelinestats.annotation_genome}&nbsp;</dd>
			<dt>Quality plots file</dt>
			<dd>${pipelinestats.quality_plots_file}&nbsp;</dd>
			<dt>Log file</dt>
			<dd>${pipelinestats.log_file}&nbsp;</dd>
		</dl>
		
	</div>

	<div id="deleteModal" class="modal hide fade" tabindex="-1">
		<div class="modal-header">
			<h3 id="deleteModalLabel">Delete pipeline experiment statistics</h3>
		</div>
		<div class="modal-body">
			<div>Are you sure you want to delete the pipeline experiment statistics?</div>
		</div>
		<div class="modal-footer">
			<div id="deleteBtn"></div>
		</div>
	</div>
</body>
</html>