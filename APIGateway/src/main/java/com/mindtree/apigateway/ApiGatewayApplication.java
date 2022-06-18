package com.mindtree.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.web.WebProperties.Resources;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiGatewayApplication {
//	 @Bean
//	    public Resources resources() {
//	        return new Resources();
//	    }

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);

	}

}
