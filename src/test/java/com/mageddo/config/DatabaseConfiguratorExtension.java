package com.mageddo.config;

import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import org.junit.platform.commons.support.AnnotationSupport;

import static com.mageddo.config.ApplicationContextUtils.context;

public class DatabaseConfiguratorExtension implements TestInstancePostProcessor, BeforeTestExecutionCallback {
	@Override
	public void postProcessTestInstance(Object o, ExtensionContext extensionContext) throws Exception {
		AnnotationSupport.findAnnotation(extensionContext.getRequiredTestClass(), MicronautTest.class);
	}

	@Override
	public void beforeTestExecution(ExtensionContext extensionContext) throws Exception {
		final DatabaseConfigurator databaseConfigurator = context().getBean(DatabaseConfigurator.class);
		databaseConfigurator.migrate();
	}
}
