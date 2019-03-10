package com.mageddo.db;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

import static org.graalvm.nativeimage.RuntimeReflection.register;

public final class DBUtils {

	private static final DataSourceTransactionManager TX_MANAGER;
	private static final DataSource DATA_SOURCE;
	private static final JdbcTemplate JDBC_TEMPLATE;
	private static final NamedParameterJdbcTemplate NAMED_JDBC_TEMPLATE;

	static {
		register(java.sql.Statement[].class);
		DATA_SOURCE = createDataSource();
		TX_MANAGER = new DataSourceTransactionManager(DATA_SOURCE);
		JDBC_TEMPLATE = new JdbcTemplate(DATA_SOURCE);
		NAMED_JDBC_TEMPLATE = new NamedParameterJdbcTemplate(DATA_SOURCE);
	}

	private DBUtils() {
	}

	public static PlatformTransactionManager tx(){
		return TX_MANAGER;
	}

	public static JdbcTemplate template(){
		return JDBC_TEMPLATE;
	}

	public static NamedParameterJdbcTemplate namedTemplate(){
		return NAMED_JDBC_TEMPLATE;
	}

	private static HikariDataSource createDataSource() {
		final Properties props = Utils.loadProps();
		final HikariDataSource ds = new HikariDataSource();
		ds.setJdbcUrl(props.getProperty("spring.datasource.url"));
		ds.setUsername(props.getProperty("spring.datasource.username"));
		ds.setPassword(props.getProperty("spring.datasource.password"));
		ds.setConnectionTestQuery(props.getProperty("spring.datasource.hikari.connectionTestQuery"));
		ds.setAutoCommit(Objects.equals(props.getProperty("spring.datasource.defaultAutoCommit"), "true"));
		ds.setTransactionIsolation(props.getProperty("spring.datasource.hikari.transactionIsolation"));
		ds.setMaximumPoolSize(Integer.valueOf(props.getProperty("spring.datasource.hikari.maximumPoolSize")));
		ds.setMinimumIdle(Integer.valueOf(props.getProperty("spring.datasource.hikari.minimumIdle")));
		ds.setDriverClassName(props.getProperty("spring.datasource.driverClassName"));
		return ds;
	}
}
