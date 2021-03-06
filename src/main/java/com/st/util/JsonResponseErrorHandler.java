package com.st.util;

import java.io.IOException;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import com.st.exceptions.GenericException;
import com.st.exceptions.GenericExceptionResponse;

/**
 * This class parses JSON error messages returned by the ST API to display them
 * in a view.
 */
public class JsonResponseErrorHandler implements ResponseErrorHandler {

    @SuppressWarnings("unused")
    private static final Logger logger = Logger
            .getLogger(JsonResponseErrorHandler.class);

    private final ObjectMapper mapper;

    /**
     * Constructor.
     */
    public JsonResponseErrorHandler() {
        mapper = new ObjectMapper();
    }

    /**
     * Validates a client http response.
     *
     * @param response the response.
     * @return true if invalid, false of OK.
     * @throws IOException
     */
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {

        if (response.getStatusCode() != HttpStatus.OK) {

            handleError(response); // should not be here, but done automatically
            // when this returns true. not sure why it
            // doesnt work without this
            return true;
        }
        return false;
    }

    /**
     * Helper that generates a client HTTP response.
     *
     * @param response the response.
     * @throws IOException
     */
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {

        GenericExceptionResponse errorResponse = mapper.readValue(response.getBody(),
                GenericExceptionResponse.class);
        throw new GenericException(errorResponse);
    }

}
