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

        DemoPojo o1 = new DemoPojo();
        o1.value = 2333;

        Convertor convertor = cm.forObject(o1);
        DemoPojo2 o2 = convertor.convert(o1, DemoPojo2.class);

        logger.info("o1 = " + o1);
        logger.info("o2 = " + o2);
    }

    @Test
    public void testConvertorManager() {

        ConvertorManager cm = ConvertorManager.getInstance();
        cm.register(this.getConvertorRegistry());

        Convertor convertor1 = cm.findConvertor(DemoPojo.class);
        Convertor convertor2 = cm.findConvertor(DemoPojo2.class);

        logger.info("convertor1 = " + convertor1);
        logger.info("convertor2 = " + convertor2);
    }

    private ConvertorRegistry getConvertorRegistry() {
        return DemoPojoConvertor.getRegistry();
    }
}
