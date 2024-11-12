package com.bitwormhole.starter4j.vlog.log4j12;

import org.apache.log4j.BasicConfigurator;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

public class TestVLogLog4j {

	final static Logger logger = LoggerFactory.getLogger(TestVLogLog4j.class);

	@Test
	public void testVlog() {		
		BasicConfigurator.configure();
		logger.debug("hello, vlog-slf4j-log4j");
	}

}
