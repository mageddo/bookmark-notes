package com.mageddo.commons;

import org.junit.jupiter.api.Test;

import static com.mageddo.commons.MarkdownUtils.parseMarkdown;
import static com.mageddo.config.TestUtils.readAsString;
import static org.junit.Assert.assertEquals;

class MarkdownUtilsTest {

	@Test
	void mustParseTable(){

		// arrange
		String markdown = "# Table\n" +
			"| Day     | Meal    | Price |\n" +
			"| --------|---------|-------|\n" +
			"| Monday  | pasta   | $6    |\n" +
			"| Tuesday | chicken | $8    |";

		// act
		final String renderedHtml = parseMarkdown(markdown);

		// assert
		assertEquals(readAsString("/markdown-utils-test/001.html"), renderedHtml);

	}
}
