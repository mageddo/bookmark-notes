package com.mageddo.utils;

import com.mageddo.config.DatasourceConfiguration;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.graalvm.nativeimage.ImageInfo;
import org.graalvm.nativeimage.ProcessProperties;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public final class MigrationUtils {
	private MigrationUtils() {
	}

	public static void migrate(DatasourceConfiguration dc){

		FluentConfiguration configuration = Flyway
			.configure()
//			.configure(getClassLoader())
//			.configuration(Flyway.configure())
//			.locations(System.getenv("MIGRATION_PATH"))
//			.resolvers()
			.locations("classpath:db/migration/postgres")
			.dataSource(dc.getJdbcUrl(), dc.getUsername(), dc.getPassword());
//			.load();

		if(ImageInfo.inImageRuntimeCode()){
			final Path path = Paths.get(ProcessProperties.getExecutableName()).getParent();
			configuration = configuration.locations(String.format("filesystem:%s/conf/db/migration/postgres", path));
		}

		final Flyway flyway = configuration.load();
		flyway.repair();
		flyway.migrate();
	}

}
