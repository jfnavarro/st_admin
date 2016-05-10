/*
 *Copyright © 2012 Spatial Transcriptomics AB
 *Read LICENSE for more information about licensing terms
 *Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
 * 
 */
package com.st.exceptions;

/**
 * This class is used to return an Exception with a customized response (see
 * GenericExceptionResponse) for runtime errors, such as if parsing error
 * occurs.
 */
public class GenericException extends RuntimeException {

    /** Auto-generated UID. */
    private static final long serialVersionUID = -1905068296555489554L;

    private GenericExceptionResponse errorResponse;

    /**
     * Constructor.
     * @param errorResponse error response.
     */
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
