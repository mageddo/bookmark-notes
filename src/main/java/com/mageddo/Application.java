package com.mageddo;

import com.mageddo.config.ApplicationContextUtils;
import io.micronaut.runtime.Micronaut;
import org.graalvm.nativeimage.Feature;

public class Application implements Feature {

	public static void main(String[] args) {
		ApplicationContextUtils.context(Micronaut.run(Application.class));
	}
}
