package com.alsusp.cleveron.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class TestConfig {

	public static InternalResourceViewResolver setupViewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("classpath:/templates/");
		viewResolver.setSuffix(".html");
		return viewResolver;
	}
}
