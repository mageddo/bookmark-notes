package com.mageddo.markdown;

import java.util.Arrays;
import java.util.List;

import org.commonmark.Extension;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public final class MarkdownUtils {
  private MarkdownUtils() {
  }

  public static String parseMarkdown(String markdown) {
    final List<Extension> extensions = Arrays.asList(CustomTableExtension.create("table table-bordered table-striped"));
    final Parser parser = Parser.builder()
        .extensions(extensions)
        .build();
    return HtmlRenderer.builder()
        .extensions(extensions)
        .build()
        .render(parser.parse(markdown));
  }
}
