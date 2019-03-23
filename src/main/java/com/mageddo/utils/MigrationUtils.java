package com.mageddo.utils;

import com.mageddo.config.DatasourceConfiguration;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.graalvm.nativeimage.ImageInfo;
import org.graalvm.nativeimage.ProcessProperties;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class MigrationUtils {

	private MigrationUtils() {
	}

	public static void migrate(DatasourceConfiguration dc){
		final Flyway flyway = Flyway
			.configure()
			.locations(getLocations())
			.dataSource(dc.getJdbcUrl(), dc.getUsername(), dc.getPassword())
			.load()
			;
		flyway.repair();
		flyway.migrate();
	}

	private static String getLocations(){
		if(ImageInfo.inImageRuntimeCode()){
			return String.format("filesystem:%s/conf/db/migration/postgres", getExecutablePath());
		}
		return "classpath:db/migration/postgres";
	}

	private static Path getExecutablePath() {
		return Paths.get(ProcessProperties.getExecutableName()).getParent();
	}

}
