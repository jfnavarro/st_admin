/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.exceptions;


/**
 * This class is used to return an Exception with a customized response (see GenericExceptionResponse)
 * Used in FeatureServiceImpl.class to return a custom exception if a parsing error occurs. 
 */

public class GenericException extends RuntimeException {

	private GenericExceptionResponse errorResponse;

	public GenericException(GenericExceptionResponse errorResponse) {
		this.errorResponse = errorResponse;
	}

	public GenericExceptionResponse getErrorResponse() {
		return this.errorResponse;
	}

	public void setErrorResponse(GenericExceptionResponse errorResponse) {
		this.errorResponse = errorResponse;
	}
}
