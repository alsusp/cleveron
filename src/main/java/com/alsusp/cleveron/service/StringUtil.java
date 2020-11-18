package com.alsusp.cleveron.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil {

	private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);

	private StringUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static String capitalize(String sentence) {
		logger.info("Capitalize sentence {}", sentence);
		return sentence.substring(0, 1).toUpperCase() + sentence.substring(1).toLowerCase();
	}
}
