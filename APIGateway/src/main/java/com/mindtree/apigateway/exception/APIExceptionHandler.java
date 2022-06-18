package com.mindtree.apigateway.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.WebProperties.Resources;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
//import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;

import reactor.core.publisher.Mono;


public class APIExceptionHandler extends DefaultErrorWebExceptionHandler {
	
	final static Log LOGGER = LogFactory.getLog(APIExceptionHandler.class);

	@Override
	protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
		Map<String, Object> error = getErrorAttributes(request, getErrorAttributeOptions(request, MediaType.ALL));
		return ServerResponse.status(getHttpStatus(error)).contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(error));
	}

	public APIExceptionHandler(ErrorAttributes errorAttributes, Resources resources, ErrorProperties errorProperties,
			ApplicationContext applicationContext) {
		super(errorAttributes, resources, errorProperties, applicationContext);
	}

	@Override
	protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
		return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
	}

	

	private String buildMessage(ServerRequest request, Throwable error) {
		StringBuilder message = new StringBuilder("Failed to handle request [");
        message.append(request.methodName());
        message.append(" ");
        message.append("]");
        if (error != null) {
            message.append(": ");
            message.append(error.getMessage());
        }
        return message.toString();
	}

	@Override
	protected Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
		 int code = 500;
	        Throwable error = super.getError(request);
	       
	        LOGGER.error("error:"+error);
	        if (error instanceof ResponseStatusException) {
	            ResponseStatusException ex=(ResponseStatusException)error;
	            code=ex.getRawStatusCode();
	        }
	        return response(code, this.buildMessage(request, error));
	}
	public static Map<String, Object> response(int status, String errorMessage) {
        Map<String, Object> map = new HashMap<>();
        LOGGER.error(errorMessage);
        map.put("code", status);
        map.put("message", errorMessage);
        map.put("time stamp",LocalDateTime.now());
        System.out.println(map);
        return map;
    }

	@Override
	protected int getHttpStatus(Map<String, Object> errorAttributes) {
		int statusCode = (int) errorAttributes.get("code");
		LOGGER.error(statusCode);
        return HttpStatus.valueOf(statusCode).value();
	}

}
