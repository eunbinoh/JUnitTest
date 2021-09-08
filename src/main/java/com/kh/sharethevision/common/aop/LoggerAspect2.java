package com.kh.sharethevision.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggerAspect2 {
	
	private Logger logger = LoggerFactory.getLogger(LoggerAspect2.class);
	
	/*
	 * @Pointcut("execution(* com.kh.sharethevision.board..*(..))" ) public void
	 * myPointcut() {} //pointcut 이름 지어주는 용도로 끝//
	 * 
	 * 
	 * @Around("myPointcut()")
	 */
	
	@Around("execution(* com.kh.sharethevision.board..*(..))")
	public Object loggerAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
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
//		Object obj = null;
//		try {
//				obj = joinPoint.proceed();
//		} catch (Throwable e) {
//			e.printStackTrace();
//		}
		
		Object obj = joinPoint.proceed();
		
		logger.debug("[After]"+componentName+type+"."+methodName+"()");
		
		return obj;
	}
}
