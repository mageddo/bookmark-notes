package com.mageddo;

import com.mageddo.config.ApplicationContextUtils;
import com.mageddo.commons.MigrationUtils;
import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.Micronaut;
import org.graalvm.nativeimage.Feature;

public class Application implements Feature {

	public static void main(String[] args) {

		setupEnv();

		ApplicationContext ctx = Micronaut.run(Application.class);
		ApplicationContextUtils.context(ctx);
		MigrationUtils.migrate(ctx.getEnvironment());
	}

	static void setupEnv() {
		final String osEnvs = System.getenv("MICRONAUT_ENVIRONMENTS");
		final String javaEnvs = System.getProperty("micronaut.environments");
		if(osEnvs == null && javaEnvs == null){
			System.setProperty("micronaut.environments", "pg");
		}
	}
}
