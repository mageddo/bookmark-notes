package com.mageddo.bookmarks.spring;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.lang.reflect.Modifier;

/**
 * Configura suporte para @Transaction em Beans que sejam anotados como @Service
 */
public class TransactionalPostProcessor implements BeanPostProcessor {

	private final TransactionInterceptor transactionInterceptor;

	public TransactionalPostProcessor(PlatformTransactionManager platformTransactionManager) {
		this.transactionInterceptor = new TransactionInterceptor();
		this.transactionInterceptor.setTransactionManager(platformTransactionManager);
		this.transactionInterceptor.setTransactionAttributeSource(new AnnotationTransactionAttributeSource());
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

		if (Modifier.isFinal(bean.getClass().getModifiers()) || !bean.getClass().isAnnotationPresent(Service.class)) {
			return bean;
		}
		final ProxyFactory factory = new ProxyFactory(bean);
		factory.setProxyTargetClass(false);
		for (Class<?> anInterface : bean.getClass().getInterfaces()) {
			factory.addInterface(anInterface);
		}
		factory.addAdvice(transactionInterceptor);
		return factory.getProxy();
	}
}
