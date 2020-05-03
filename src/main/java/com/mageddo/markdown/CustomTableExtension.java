package com.mageddo.markdown;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.internal.TableBlockParser;
import org.commonmark.ext.gfm.tables.internal.TableTextContentNodeRenderer;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.commonmark.renderer.text.TextContentRenderer;

public class CustomTableExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension,
        TextContentRenderer.TextContentRendererExtension {

    private final String cssClasses;

    private CustomTableExtension(String cssClasses) {
        this.cssClasses = cssClasses;
    }

    public static Extension create(final String cssClasses) {
        return new CustomTableExtension(cssClasses);
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customBlockParserFactory(new TableBlockParser.Factory());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder) {
        rendererBuilder.nodeRendererFactory(context -> new CssTableHtmlNodeRenderer(context, cssClasses));
    }

    @Override
    public void extend(TextContentRenderer.Builder rendererBuilder) {
        rendererBuilder.nodeRendererFactory(TableTextContentNodeRenderer::new);
    }

}
