package com.mageddo.thymeleaf;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.io.StringWriter;

public final class TemplatingUtils {

	private static final ThymeleafTemplateEngine instance = new ThymeleafTemplateEngine();

	private TemplatingUtils() {
	}

	public static String processHMTLTemplate(String templateName) {
		TemplateEngine templateEngine = new TemplateEngine();
		StringTemplateResolver templateResolver = new StringTemplateResolver();
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateEngine.setTemplateResolver(templateResolver);
		Context context = new Context();
		context.setVariable("name", "World");
		StringWriter stringWriter = new StringWriter();
		templateEngine.process(templateName, context, stringWriter);
		return stringWriter.toString();
	}

	public static ThymeleafTemplateEngine template(){
		return instance;
	}
}
