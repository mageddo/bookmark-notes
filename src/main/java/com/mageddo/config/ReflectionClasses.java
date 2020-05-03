package com.mageddo.config;

import com.oracle.svm.core.annotate.AutomaticFeature;

import org.flywaydb.core.internal.logging.javautil.JavaUtilLogCreator;
import org.graalvm.nativeimage.hosted.Feature;

import nativeimage.Reflection;
import nativeimage.Reflections;
import thymeleaf.ThymeleafUtils;

@Reflections({

    @Reflection(declaredConstructors = true, declaredMethods = true, scanPackage = "com.mageddo.bookmarks.jackson"),

    // flyway migration
    @Reflection(declaredConstructors = true, scanClass = JavaUtilLogCreator.class),

    @Reflection(declaredMethods = true, scanClass = ThymeleafUtils.class),

    @Reflection(declaredConstructors = true, declaredMethods = true, declaredFields = true,
        scanPackage = "com.mageddo.bookmarks.entity"),

    @Reflection(declaredConstructors = true, declaredMethods = true, declaredFields = true,
        scanPackage = "com.mageddo.bookmarks.apiserver.res"),

	/*
	 	Hikari datasource
	 */
    @Reflection(publicConstructors = true, declaredConstructors = true, scanClassName = "java.sql.Statement[]")})
@AutomaticFeature
class ReflectionClasses implements Feature {

  @Override
  public void duringSetup(DuringSetupAccess access) {
    System.loadLibrary("sunec");
  }

}

