package com.mageddo;

import com.mageddo.commons.MigrationUtils;
import com.mageddo.config.ApplicationContextUtils;

import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.Micronaut;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import nativeimage.Reflection;

import nativeimage.Reflections;

import org.flywaydb.core.internal.logging.javautil.JavaUtilLogCreator;

import thymeleaf.ThymeleafUtils;

import java.lang.reflect.InvocationTargetException;

public class Application {

  public static void main(String[] args) {

    setupProperties();
    setupEnv();

    Micronaut.run(Application.class);

  }

  static void setupEnv() {
    final String osEnvs = System.getenv("MICRONAUT_ENVIRONMENTS");
    final String javaEnvs = System.getProperty("micronaut.environments");
    if (osEnvs == null && javaEnvs == null) {
      System.setProperty("micronaut.environments", "pg");
    }
  }

  static void setupProperties() {
    commonsLoggingFix();
  }

  private static void commonsLoggingFix() {
    System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
    System.setProperty("org.apache.commons.logging.diagnostics.dest", "STDOUT");
  }

  @EventListener
  public void onStartup(ServerStartupEvent event)
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    final ApplicationContext ctx = event.getSource()
        .getApplicationContext();
    ApplicationContextUtils.context(ctx);
    MigrationUtils.migrate(ctx.getEnvironment());
  }
}
