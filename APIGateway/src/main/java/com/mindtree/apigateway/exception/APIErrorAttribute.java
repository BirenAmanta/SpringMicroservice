package com.mindtree.apigateway.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;

@Component
public class APIErrorAttribute extends DefaultErrorAttributes {

	@Override
	public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
		
		Throwable error = super.getError(request);
		System.out.println(error);
        Map<String, Object> errorAttributes = new HashMap<>();
        errorAttributes.put("message", error.getMessage());
        errorAttributes.put("method", request.methodName());
        errorAttributes.put("path", request.path());
        MergedAnnotation<ResponseStatus> responseStatusAnnotation = MergedAnnotations
                .from(error.getClass(), MergedAnnotations.SearchStrategy.TYPE_HIERARCHY).get(ResponseStatus.class);
        HttpStatus errorStatus = determineHttpStatus(error, responseStatusAnnotation);
                 // Must set, otherwise an error will be reported because the RendereRRRRESPONSE method of DefaultErrorWebexceptionHandler gets this property, re-implementing defaultErrorWebexceptionHandler.
        errorAttributes.put("status", errorStatus.value());
        errorAttributes.put("code", errorStatus.value());
                 // html view
        errorAttributes.put("timestamp", LocalDateTime.now());
                 // html view
        errorAttributes.put("requestId", request.exchange().getRequest().getId());
    
        return errorAttributes;
	}
	 private HttpStatus determineHttpStatus(Throwable error, MergedAnnotation<ResponseStatus> responseStatusAnnotation) {
	        if (error instanceof ResponseStatusException) {
	            return ((ResponseStatusException) error).getStatus();
	        }
	        return responseStatusAnnotation.getValue("code", HttpStatus.class).orElse(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
}
