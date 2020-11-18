package com.alsusp.cleveron.exception;

import static org.springframework.http.HttpStatus.CONFLICT;

import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(CONFLICT)
public class NotUniqueNameException extends Exception {

	public NotUniqueNameException(String message) {
		super(message);
	}
}
