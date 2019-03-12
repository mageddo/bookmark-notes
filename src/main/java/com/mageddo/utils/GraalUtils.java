package com.mageddo.utils;

public final class GraalUtils {
	private GraalUtils() {
	}

	public static boolean isRunningOnBinary(){
		return System.getProperty("org.graalvm.nativeimage.kind") != null;
	}
}
