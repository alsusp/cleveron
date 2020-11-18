package com.alsusp.cleveron.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StringUtilTest {

	@Test
	void givenString_whenCapitalize_thenStringWasCapitalized() {
		String expected = "Hello world!";

		String actual = StringUtil.capitalize("Hello world!");

		assertEquals(expected, actual);
	}
}
