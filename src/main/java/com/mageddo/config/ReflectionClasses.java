package com.mageddo.config;

import com.mageddo.common.graalvm.SubstrateVM;
import com.oracle.svm.core.annotate.AutomaticFeature;
import org.graalvm.nativeimage.hosted.Feature;

import java.sql.Statement;

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

