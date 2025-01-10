package com.useragent_parser.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(request, options.including(ErrorAttributeOptions.Include.MESSAGE));

        Throwable error = getError(request);
        if (error instanceof UserAgentParsingException) {
            return handleUserAgentParsingException((UserAgentParsingException) error);
        } else if (error instanceof ResponseStatusException) {
            return handleResponseStatusException((ResponseStatusException) error);
        } else {
            errorAttributes.remove("trace");
            errorAttributes.remove("requestId");
            errorAttributes.remove("path");
        }

        return errorAttributes;
    }

    private static Map<String, Object> handleResponseStatusException(ResponseStatusException ex) {
        Map<String, Object> errorAttributes = new HashMap<>();
        errorAttributes.put("status", ex.getStatusCode().value());
        errorAttributes.put("message", ex.getReason());
        return errorAttributes;
    }

    private Map<String, Object> handleUserAgentParsingException(UserAgentParsingException ex) {
        Map<String, Object> errorAttributes = new HashMap<>();
        errorAttributes.put("status", HttpStatus.BAD_REQUEST.value());
        errorAttributes.put("error", "Bad Request");
        errorAttributes.put("message", ex.getMessage());
        return errorAttributes;
    }

}
