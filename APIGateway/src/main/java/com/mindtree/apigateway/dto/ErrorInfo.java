package com.mindtree.apigateway.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorMsg;
	private Integer errorCode;
	@DateTimeFormat(pattern="yyyy.MM.dd HH:mm:ss")
	private LocalDateTime timeStamp;
}
