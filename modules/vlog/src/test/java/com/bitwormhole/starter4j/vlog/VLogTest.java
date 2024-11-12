package com.bitwormhole.starter4j.vlog;

import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class VLogTest {

	final static Logger logger = LoggerFactory.getLogger(VLogTest.class);

	@Test
	public void testVlog() {
		logger.debug("hello, vlog");
	}

}
