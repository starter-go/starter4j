package com.bitwormhole.starter4j.application;

import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.bitwormhole.starter4j.application.tasks.Promise;
import com.bitwormhole.starter4j.application.tasks.Result;

public class PromiseTest {

    static final Logger logger = LoggerFactory.getLogger(PromiseTest.class);

    @Test
    public void testPromise() {
        Promise.init(null, () -> {
            Result<Long> res = new Result<>();
            res.setValue(666L);
            logger.info("Promise.run ... ");
            return res;
        }).Then((res) -> {
            Long n = res.getValue();
            logger.info("Promise.Then: {}", n);

            long value = res.getValue();
            if (value > 0) {
                throw new RuntimeException("bad value: " + value);
            }

            return res;
        }).Catch((res) -> {
            Throwable err = res.getError();
            logger.error("Promise.Catch: {}", err.getMessage());
            return res;
        }).Finally((res) -> {
            logger.info("Promise.Finally: {}", res);
            return res;
        }).Start();
    }

}
