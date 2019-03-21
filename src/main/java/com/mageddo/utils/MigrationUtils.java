package com.mageddo.utils;

import com.mageddo.config.DatasourceConfiguration;
import org.flywaydb.core.Flyway;

public final class MigrationUtils {
	private MigrationUtils() {
	}

	public static void migrate(DatasourceConfiguration dc){
		final Flyway flyway = Flyway
			.configure()
			.locations("db/migration/postgres")
			.dataSource(dc.getJdbcUrl(), dc.getUsername(), dc.getPassword())
			.load();
		flyway.repair();
		flyway.migrate();
	}
}
