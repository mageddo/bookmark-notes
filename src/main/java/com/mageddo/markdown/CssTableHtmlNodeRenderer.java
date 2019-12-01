package com.mageddo.markdown;

import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.internal.TableHtmlNodeRenderer;
import org.commonmark.node.Node;
import org.commonmark.renderer.html.HtmlNodeRendererContext;
import org.commonmark.renderer.html.HtmlWriter;

import static io.micronaut.core.util.CollectionUtils.mapOf;

public class CssTableHtmlNodeRenderer extends TableHtmlNodeRenderer {

	private final HtmlWriter htmlWriter;
	private final HtmlNodeRendererContext context;
	private String cssClasses;

	public CssTableHtmlNodeRenderer(HtmlNodeRendererContext context, final String cssClasses) {
		super(context);
		this.htmlWriter = context.getWriter();
		this.context = context;
		this.cssClasses = cssClasses;
	}

	@Override
	protected void renderBlock(TableBlock tableBlock) {
		htmlWriter.line();
		htmlWriter.tag("table", context.extendAttributes(tableBlock, "table", mapOf("class", cssClasses)));
		renderChildren(tableBlock);
		htmlWriter.tag("/table");
		htmlWriter.line();
	}

	private void renderChildren(Node parent) {
		Node node = parent.getFirstChild();
		while (node != null) {
			Node next = node.getNext();
			context.render(node);
			node = next;
		}
	}
}
