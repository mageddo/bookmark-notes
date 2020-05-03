package com.mageddo.config;

import javax.inject.Singleton;

import io.micronaut.context.ApplicationContext;

@Singleton
public class ApplicationContextInitilaizer {

  private final ApplicationContext context;

  public ApplicationContextInitilaizer(ApplicationContext context) {
    this.context = context;
    ApplicationContextUtils.context(context);
  }
}
