package com.mageddo.config;

import com.mageddo.bookmarks.utils.ThymeleafUtils;
import nativeimage.Reflection;
import nativeimage.Reflections;
import org.flywaydb.core.internal.logging.javautil.JavaUtilLogCreator;

@Reflections({

	@Reflection(declaredConstructors = true, declaredMethods = true, scanPackage = "com.mageddo.bookmarks.jackson"),

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
public class ReflectionFileConfig {
}
