package com.kh.sharethevision.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerAspect1 {
	
	private Logger logger = LoggerFactory.getLogger(LoggerAspect1.class);
	
	public Object loggerAdvice(ProceedingJoinPoint joinPoint) {
		Signature signature =joinPoint.getSignature();
		//현재 AOP가 적용된 메소드의 정보 가져옴 
		logger.debug("signature:"+signature);
	
		String type = signature.getDeclaringTypeName();
		//getDeclaringTypeName() : 메소드가 있는 클래스 풀네임 가져옴
		String methodName = signature.getName();
		logger.debug("type:"+type);
		logger.debug("methodName:"+methodName);
		
		String componentName = null;
		if(type.indexOf("Controller")>-1) {
			componentName = "Controller \t :";
		}else if(type.indexOf("Service")>-1) {
			componentName = "Service \t :";
		}else if(type.indexOf("DAO")>-1) {
			componentName = "DAO \t\t :";
		}
		
		
		logger.debug("[Before]" + componentName + type + "." + methodName + "()");
		Object obj = null;
		try {
				obj = joinPoint.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		logger.debug("[After]"+componentName+type+"."+methodName+"()");
		
		return obj;
	}
}
