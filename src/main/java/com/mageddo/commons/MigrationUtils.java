package com.mageddo.commons;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.flywaydb.core.Flyway;
import org.graalvm.nativeimage.ImageInfo;
import org.graalvm.nativeimage.ProcessProperties;

import io.micronaut.context.env.Environment;

public final class MigrationUtils {

  private MigrationUtils() {
  }

  public static void migrate(final Environment env) {
    final Flyway flyway = getFlyway(env);
    flyway.repair();
    flyway.migrate();
  }

  public static Flyway getFlyway(Environment env) {
    return Flyway.configure()
        .locations(getLocations(env))
        .dataSource(get(env, "datasources.default.jdbc-url"), get(env, "datasources.default.username"),
            get(env, "datasources.default.password")
        )
        .schemas(get(env, "flyway.schema"))
        .load();
  }

  private static String get(Environment env, String k) {
    return env.get(k, String.class, "");
  }

  private static String getLocations(Environment env) {
    if (ImageInfo.inImageRuntimeCode()) {
      return String.format("filesystem:%s/%s", getExecutablePath(), get(env, "flyway.locations-image"));
    }
    return "classpath:" + get(env, "flyway.locations-java");
  }

  private static Path getExecutablePath() {
    return Paths.get(ProcessProperties.getExecutableName())
        .getParent();
  }

}
