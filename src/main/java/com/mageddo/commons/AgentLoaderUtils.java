package com.mageddo.commons;

import com.mageddo.utils.GraalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

public final class AgentLoaderUtils {

	private static final Logger LOG = LoggerFactory.getLogger(AgentLoaderUtils.class);

	private AgentLoaderUtils() {
	}

	public static void loadAgents(){
		if(GraalUtils.isRunningOnBinary()){
			LOG.warn("status=executing-on-binary-file, aspectj-load-time-disabled=true");
			return ;
		}
		try {
			final Class<?> agentLoaderClazz = Class.forName("com.ea.agentloader.AgentLoader");
			agentLoaderClazz
			.getDeclaredMethod("loadAgentClass", String.class, String.class)
			.invoke(agentLoaderClazz, "org.aspectj.weaver.loadtime.Agent", "");
			LOG.warn("status=loaded-agents, mode=load-time");
		} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			LOG.warn("status=loaded-agents, mode=load-time-disabled");
		}
	}
}
