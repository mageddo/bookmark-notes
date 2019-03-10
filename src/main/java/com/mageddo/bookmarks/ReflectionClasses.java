package com.mageddo.bookmarks;

import com.mageddo.bookmarks.dao.BookmarkDAOSqlite;
import com.mageddo.bookmarks.service.BookmarkService;
import com.mageddo.controller.BookmarkController;
import org.graalvm.nativeimage.RuntimeReflection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class ReflectionClasses {

	/**
	 * All classes defined here will have reflection support and be registered as spring beans
	 */
	static Class<?>[] getBeans(){
		return new Class[]{
			ApplicationContextProvider.class,
			BookmarkService.class,
			BookmarkDAOSqlite.class,
			BookmarkController.class,
			NamedParameterJdbcTemplate.class,
			JdbcTemplate.class,
			DataSourceTransactionManager.class
		};
	}

	/**
	 * All classes defined here will have reflection support
	 */
	static Class<?>[] getClasses(){
		return new Class[]{
			java.sql.Statement[].class
		};
	}

	static void setupClasses() {
		try {
			System.out.println("> Loading classes for future reflection support");
			for (final Class<?> clazz : getBeans()) {
				process(clazz);
			}
			for (final Class<?> clazz : getClasses()) {
				process(clazz);
			}
		} catch (Error e){
			if(!e.getMessage().contains("The class ImageSingletons can only be used when building native images")){
				throw e;
			}
		}
	}

	/**
	 * Register all constructors and methods on graalvm to reflection support at runtime
	 */
	private static void process(Class<?> clazz) {
		try {
			System.out.println("> Declaring class: " + clazz.getCanonicalName());
			RuntimeReflection.register(clazz);
			for (final Method method : clazz.getMethods()) {
				System.out.println("\t> method: " + method.getName() + "(" + method.getParameterCount() + ")");
				RuntimeReflection.register(method);
			}
			for (final Constructor<?> constructor : clazz.getDeclaredConstructors()) {
				System.out.println("\t> constructor: " + constructor.getName() + "(" + constructor.getParameterCount() + ")");
				RuntimeReflection.register(constructor);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
