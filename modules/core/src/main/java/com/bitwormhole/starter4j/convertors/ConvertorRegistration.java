package com.bitwormhole.starter4j.convertors;

public final class ConvertorRegistration {

    private String name;
    private int priority;
    private Class<?> inputType;
    private Class<?> outputType;
    private Convertor convertor;

    public ConvertorRegistration() {
    }

    public ConvertorRegistration(ConvertorRegistration cr) {
        if (cr == null) {
            return;
        }
        this.name = cr.name;
        this.priority = cr.priority;
        this.inputType = cr.inputType;
        this.outputType = cr.outputType;
        this.convertor = cr.convertor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Convertor getConvertor() {
        return convertor;
    }

    public void setConvertor(Convertor convertor) {
        this.convertor = convertor;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Class<?> getInputType() {
        return inputType;
    }

    public void setInputType(Class<?> inputType) {
        this.inputType = inputType;
    }

    public Class<?> getOutputType() {
        return outputType;
    }

    public void setOutputType(Class<?> outputType) {
        this.outputType = outputType;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        ConvertorRegistration o1 = this;
        ConvertorRegistration o2 = null;
        if (obj instanceof ConvertorRegistration) {
            o2 = (ConvertorRegistration) obj;
        } else {
            return false;
        }

        if (!isObjectEq(o1.inputType, o2.inputType)) {
            return false;
        }
        if (!isObjectEq(o1.outputType, o2.outputType)) {
            return false;
        }
        if (!isObjectEq(o1.name, o2.name)) {
            return false;
        }
        if (!isObjectEq(o1.convertor, o2.convertor)) {
            return false;
        }
        return true;
    }

    private static <T extends Object> boolean isObjectEq(T t1, T t2) {
        if (t1 == null || t2 == null) {
            return false;
        }
        return t1.equals(t2);
    }

}
