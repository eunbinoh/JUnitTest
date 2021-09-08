package com.kh.sharethevision.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class TestInterceptor implements HandlerInterceptor {

	private Logger logger = LoggerFactory.getLogger(TestInterceptor.class);
	
	
	
	//Controller로 요청이 들어가기 전에 수행
	//handler는 preHandle()을 수행하고, 수행될 컨트롤러 메소드에 대한 정보를 담고 있음
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) 
			throws Exception {
		if(logger.isDebugEnabled()) {
			logger.debug("======================= START =======================");
			logger.debug(request.getRequestURI());
		}

		return HandlerInterceptor.super.preHandle(request, response, handler); //항상 true 반환	
	}
	
	
	//Controller에서 DispatcherServlet으로 리턴되는 순간에 수행
	//modelAndView를 통해 작업결과를 참조 가능
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception{
		
		if(logger.isDebugEnabled()) {
			logger.debug("--------------------- view ------------------------");
		}
		
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) throws Exception{
		
		if(logger.isDebugEnabled()) {
			logger.debug("======================= END =======================\n");
		}
		
	}
	
	
}