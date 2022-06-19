package com.mindtree.apigateway.filter;

import java.time.LocalDateTime;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

//import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mindtree.apigateway.dto.ErrorInfo;

import com.mindtree.apigateway.exception.APIGatewayException;
import com.mindtree.apigateway.utility.JwtUtility;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Flux;

//import reactor.core.publisher.Mono;
@Component
@RefreshScope
public class APIGatewayFilter extends AbstractGatewayFilterFactory<APIGatewayFilter.Config> {

	@Autowired
	JwtUtility jwtUtility;

	public APIGatewayFilter() {
		super(Config.class);
		// TODO Auto-generated constructor stub
	}

	public static class Config {

	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			ServerHttpRequest request = (ServerHttpRequest) exchange.getRequest();

			final List<String> apiEndpoints = List.of("/login");
//			int a=0/0;
			Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream()
					.noneMatch(uri -> r.getURI().getPath().contains(uri));
			ServerHttpResponse response = exchange.getResponse();
			byte[] bite = null;
			ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());
			mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			if (isApiSecured.test(request)) {
				if (!request.getHeaders().containsKey("Authorization")) {

					response.setStatusCode(HttpStatus.UNAUTHORIZED);
					ErrorInfo error = new ErrorInfo();
					error.setErrorCode(HttpStatus.UNAUTHORIZED.value());
					error.setErrorMsg("SERVICE.AUTH");
					error.setTimeStamp(LocalDateTime.now());

					try {

						bite = mapper.writeValueAsBytes(error);
					} catch (JsonProcessingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					DataBuffer buffer = response.bufferFactory().wrap(bite);
					return response.writeWith(Flux.just(buffer));

				} else if (request.getHeaders().containsKey("Authorization")) {
					final String token = request.getHeaders().getOrEmpty("Authorization").get(0).split(" ")[1];
					try {
						jwtUtility.validateToken(token);
					} catch (APIGatewayException exception) {
						// e.printStackTrace();

						
						response.setStatusCode(HttpStatus.BAD_REQUEST);
						ErrorInfo error = new ErrorInfo();
						error.setErrorCode(HttpStatus.UNAUTHORIZED.value());
						error.setErrorMsg("SERVIC");
						error.setTimeStamp(LocalDateTime.now());

						try {

							bite = mapper.writeValueAsBytes(error);
						} catch (JsonProcessingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						DataBuffer buffer = response.bufferFactory().wrap(bite);
						return response.writeWith(Flux.just(buffer));
						
					}

					Claims claims = jwtUtility.getClaims(token);
					exchange.getRequest().mutate().header("id", String.valueOf(claims.get("sub"))).build();
				}

			}

			return chain.filter(exchange);
		};
	}

}
