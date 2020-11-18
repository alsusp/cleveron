package com.alsusp.cleveron.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(NOT_FOUND)
public class NotFoundException extends RuntimeException {

	public NotFoundException(String errorMessage) {
		super(errorMessage);
	}
}
