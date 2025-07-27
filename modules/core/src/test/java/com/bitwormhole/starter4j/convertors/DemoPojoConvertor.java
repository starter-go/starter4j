package com.bitwormhole.starter4j.convertors;

public class DemoPojoConvertor implements Convertor {

    @Override
    public Object convert(Object src) {
        DemoPojo o1 = (DemoPojo) src;
        DemoPojo2 o2 = new DemoPojo2();
        o2.value = "demo_pojo:" + o1.value;
        return o2;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T convert(Object src, Class<T> targetType) {
        Object dst = this.convert(src);
        return (T) dst;
    }

    @Override
    public boolean acceptSourceType(Class<?> cl) {
        return this.getSourceType().equals(cl);
    }

    @Override
    public boolean acceptSourceObject(Object src) {
        return (src instanceof DemoPojo);
    }

    @Override
    public Class<?> getSourceType() {
        return DemoPojo.class;
    }

    private static class MyConvertorRegistry implements ConvertorRegistry {

        @Override
        public ConvertorRegistration[] listConvertorRegistrations() {
            Convertor convertor = new DemoPojoConvertor();
            ConvertorRegistration cr = new ConvertorRegistration();
            cr.setConvertor(convertor);
            cr.setSourceType(convertor.getSourceType());
            return new ConvertorRegistration[] { cr };
        }
    }

    public static ConvertorRegistry getRegistry() {
        return new MyConvertorRegistry();
    }
}
