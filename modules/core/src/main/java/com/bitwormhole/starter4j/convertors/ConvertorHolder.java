package com.bitwormhole.starter4j.convertors;

public class ConvertorHolder {

    private Convertor convertor;
    private Class<?> sourceType;

    public ConvertorHolder(Class<?> source_type) {
        this.sourceType = source_type;
    }

    public Convertor getConvertor() {
        Convertor c = this.convertor;
        if (c == null) {
            c = this.loadConvertor();
            this.convertor = c;
        }
        return c;
    }

    private Convertor loadConvertor() {
        ConvertorManager cm = ConvertorManager.getInstance();
        return cm.findConvertor(this.sourceType);
    }

}
