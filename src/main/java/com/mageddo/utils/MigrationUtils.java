package com.mageddo.utils;

import com.mageddo.config.DatasourceConfiguration;
import org.flywaydb.core.Flyway;

public final class MigrationUtils {
	private MigrationUtils() {
	}

	public static void migrate(DatasourceConfiguration dc){
		Flyway
			.configure()
			.dataSource(dc.getJdbcUrl(), dc.getUsername(), dc.getPassword())
			.load()
			.migrate();
	}
}
