package com.kh.sharethevision.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log4jTest {
		
	private Logger logger = LoggerFactory.getLogger(Log4jTest.class);
		
	public static void main(String[] args) {
		new Log4jTest().test();
		
	}
	public void test() {
		logger.trace("trace 로그");
		logger.debug("debug 로그");
		logger.info("info 로그");
		logger.warn("warn 로그");
		logger.error("error 로그");
		//fatal에 대한 로거는 제공하지 않음.
		
	}
}
