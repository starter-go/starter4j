package com.bitwormhole.starter4j.convertors;

public class DemoPojoConvertor implements Convertor {

    @Override
    public Object convert(Object src) {
        DemoPojo1 o1 = (DemoPojo1) src;
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
    public boolean acceptInputType(Class<?> cl) {
        return DemoPojo1.class.equals(cl);
    }

    @Override
    public boolean acceptInputObject(Object src) {
        return (src instanceof DemoPojo1);
    }

    private static class MyConvertorRegistry implements ConvertorRegistry {

        @Override
        public ConvertorRegistration[] listConvertorRegistrations() {
            Convertor convertor = new DemoPojoConvertor();
            ConvertorRegistration cr = convertor.getRegistration();

            ConvertorRegistration cr1 = new ConvertorRegistration(cr);
            ConvertorRegistration cr2 = new ConvertorRegistration(cr);
            ConvertorRegistration cr3 = new ConvertorRegistration(cr);

            cr1.setName("a");
            cr2.setName("b");
            cr3.setName(null);

            cr1.setPriority(88);
            cr2.setPriority(22);
            cr3.setPriority(55);

            return new ConvertorRegistration[] { cr1, cr2, cr3 };
        }
    }

    public static ConvertorRegistry getRegistry() {
        return new MyConvertorRegistry();
    }

    @Override
    public ConvertorRegistration getRegistration() {
        ConvertorRegistration cr;

        cr = new ConvertorRegistration();
        cr.setConvertor(this);
        cr.setInputType(DemoPojo1.class);
        cr.setOutputType(DemoPojo2.class);
        cr.setPriority(1);
        cr.setName("a");

        return cr;
    }
}
