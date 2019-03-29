package com.mageddo.config;

import io.micronaut.context.ApplicationContext;

import javax.inject.Singleton;

@Singleton
public class ApplicationContextInitilaizer {

	private final ApplicationContext context;

	public ApplicationContextInitilaizer(ApplicationContext context) {
		this.context = context;
		ApplicationContextUtils.context(context);
	}
}
