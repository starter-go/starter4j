package com.bitwormhole.starter4j.convertors;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConvertorTest {

    static final Logger logger = LoggerFactory.getLogger(ConvertorTest.class);

    @Test
    public void testConvertor() {

        ConvertorManager cm = ConvertorManager.getInstance();
        cm.register(this.getConvertorRegistry());

        DemoPojo1 o1 = new DemoPojo1();
        o1.value = 2333;

        Convertor convertor = cm.findConvertor(o1, DemoPojo2.class);
        DemoPojo2 o2 = convertor.convert(o1, DemoPojo2.class);

        logger.info("o1 = " + o1);
        logger.info("o2 = " + o2);
    }

    @Test
    public void testConvertorManager() {

        ConvertorManager cm = ConvertorManager.getInstance();
        cm.register(this.getConvertorRegistry());
        DemoPojo1 o1 = new DemoPojo1();

        Convertor convertor1 = cm.findConvertor(o1, DemoPojo2.class);
        Convertor convertor2 = null;
        try {
            convertor2 = cm.findConvertor(o1.getClass(), DemoPojo2.class);
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }

        logger.info("convertor1 = " + convertor1);
        logger.info("convertor2 = " + convertor2);
    }

    @Test
    public void testNoConvertorFound() {

        ConvertorManager cm = ConvertorManager.getInstance();
        Convertor c = null;
        Exception err = null;

        cm.register(this.getConvertorRegistry());

        try {
            c = cm.findConvertor(String.class, Long.class);
        } catch (Exception e) {
            System.err.println(e.getMessage()); // ok
            err = e;
        }

        if (c == null && err != null) {
            return;
        }

        throw new RuntimeException("want error, but no throws");
    }

    private ConvertorRegistry getConvertorRegistry() {
        return DemoPojoConvertor.getRegistry();
    }
}
