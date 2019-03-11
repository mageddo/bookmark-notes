package com.mageddo.bookmarks.spring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class TransactionalAspectProcessor {
	@Around("@annotation(org.springframework.transaction.annotation.Transactional)")
	public Object transactionalMethods(ProceedingJoinPoint point) throws Throwable {
		System.out.println("before");
		Object r = point.proceed();
		System.out.println("after");
		return r;
	}
}
