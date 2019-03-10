package com.mageddo.thymeleaf;

import com.mageddo.bookmarks.Main;
import com.mageddo.db.Utils;
import org.apache.commons.io.IOUtils;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;
import org.thymeleaf.templateresource.ITemplateResource;
import org.thymeleaf.templateresource.StringTemplateResource;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Map;
import java.util.Optional;

public final class TemplatingUtils {

	private static final String DEFAULT_PREFIX = "templates/";
	private static final String DEFAULT_SUFFIX = ".html";
	private static final long DEFAULT_CACHE_TTL_MS = 3600000L;
	private static final ThymeleafTemplateEngine instance = new ThymeleafTemplateEngine(createTemplateResolver());

	private TemplatingUtils() {
	}

	private static ITemplateResolver createTemplateResolver() {
		final StringTemplateResolver resolver = new StringTemplateResolver() {
			@Override
			protected ITemplateResource computeTemplateResource(
				IEngineConfiguration configuration, String ownerTemplate, String template, Map<String, Object> templateResolutionAttributes
			) {
				try (InputStream in = getResourceAsStream(template)) {
					return new StringTemplateResource(IOUtils.toString(in, StandardCharsets.UTF_8));
				} catch (IOException e) {
					throw new UncheckedIOException(e);
				}
			}
		};
		resolver.setCacheable(true);
		resolver.setCacheTTLMs(DEFAULT_CACHE_TTL_MS);
		resolver.setTemplateMode(TemplateMode.HTML);
		return resolver;
	}

	private static InputStream getResourceAsStream(String template) {
		final String resource = String.format("%s%s%s", DEFAULT_PREFIX, template, DEFAULT_SUFFIX);
		return Optional
			.ofNullable(Utils.getResourceAsStream("/" + resource))
			.orElseGet(() -> {
				try {
					return Files.newInputStream(FileSystems.getDefault().getPath(resource), StandardOpenOption.READ);
				} catch (IOException e) {
					throw new UncheckedIOException(e);
				}
			});
	}

	public static ThymeleafTemplateEngine template(){
		return instance;
	}
}
