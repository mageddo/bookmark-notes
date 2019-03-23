package com.mageddo;

import com.mageddo.config.ApplicationContextUtils;
import com.mageddo.config.DatasourceConfiguration;
import com.mageddo.utils.MigrationUtils;
import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.Micronaut;
import org.graalvm.nativeimage.Feature;

public class Application implements Feature {

	public static void main(String[] args) {
		ApplicationContext ctx = Micronaut.run(Application.class);
		ApplicationContextUtils.context(ctx);
		MigrationUtils.migrate(ctx.getEnvironment());
	}
}
