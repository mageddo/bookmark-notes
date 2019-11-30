package com.mageddo.config;

import com.mageddo.bookmarks.utils.ThymeleafUtils;
import com.mageddo.common.graalvm.SubstrateVM;
import com.oracle.svm.core.annotate.AutomaticFeature;
import nativeimage.Reflection;
import nativeimage.Reflections;
import org.flywaydb.core.internal.logging.javautil.JavaUtilLogCreator;
import org.graalvm.nativeimage.hosted.Feature;

import java.sql.Statement;

@Reflections({
	@Reflection(declaredConstructors = true, declaredMethods = true),

	// flyway migration
	@Reflection(declaredConstructors = true, scanClass = JavaUtilLogCreator.class),

	@Reflection(declaredMethods = true, scanClass = ThymeleafUtils.class),

	@Reflection(
		declaredConstructors = true, declaredMethods = true, declaredFields = true,
		scanPackage = "com.mageddo.bookmarks.entity"
	),

	@Reflection(
		declaredConstructors = true, declaredMethods = true, declaredFields = true,
		scanPackage = "com.mageddo.bookmarks.apiserver.res"
	)
})
@AutomaticFeature
class ReflectionClasses implements Feature {

	@Override
	public void duringSetup(DuringSetupAccess access) {
		System.loadLibrary("sunec");
	}

	@Override
	public void beforeAnalysis(BeforeAnalysisAccess access) {

		// hikari datasource
		SubstrateVM
			.builder()
			.constructors()
			.clazz(Statement[].class)
		.build();

	}

}

