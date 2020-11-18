package com.alsusp.cleveron.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GeneralExceptionHandlerController {

	@ExceptionHandler(value = Exception.class)
	public String redirectToErrorPage(Exception e, Model model) {
		model.addAttribute("message", e.getMessage());
		return "errorPage";
	}
}
