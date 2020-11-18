package com.alsusp.cleveron.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(BAD_REQUEST)
public class NotValidDepthException extends Exception {

	public NotValidDepthException(String message) {
		super(message);
	}
}
