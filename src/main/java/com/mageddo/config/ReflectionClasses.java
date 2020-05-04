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
    @Reflection(scanClass = JavaUtilLogCreator.class, declaredConstructors = true),

    @Reflection(scanClass = ThymeleafUtils.class, declaredMethods = true),

    @Reflection(scanPackage = "com.mageddo.bookmarks.entity", declaredConstructors = true, declaredMethods = true,
        declaredFields = true
    ),

    @Reflection(scanPackage = "com.mageddo.bookmarks.apiserver.res", declaredConstructors = true,
        declaredMethods = true,
        declaredFields = true
    ),

    @Reflection(scanClass = thymeleaf.ThymeleafUtils.class, declaredMethods = true, constructors = true,
        declaredConstructors = true),

    /*
      Hikari datasource
     */
    @Reflection(scanClassName = "java.sql.Statement[]", publicConstructors = true, declaredConstructors = true),

})
@AutomaticFeature
class ReflectionClasses implements Feature {
  @Override
  public void duringSetup(DuringSetupAccess access) {
    System.loadLibrary("sunec");
  }
}

