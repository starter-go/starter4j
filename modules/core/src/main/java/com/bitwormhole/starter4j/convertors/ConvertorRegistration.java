package com.bitwormhole.starter4j.convertors;

public final class ConvertorRegistration {

    private String name;
    private Class<?> sourceType;
    private Convertor convertor;

    public ConvertorRegistration() {
    }

    public ConvertorRegistration(ConvertorRegistration cr) {
        if (cr == null) {
            return;
        }
        this.name = cr.name;
        this.sourceType = cr.sourceType;
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

    public Class<?> getSourceType() {
        return sourceType;
    }

    public void setSourceType(Class<?> sourceType) {
        this.sourceType = sourceType;
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

        if (!isObjectEq(o1.getSourceType(), o2.getSourceType())) {
            return false;
        }
        if (!isObjectEq(o1.getName(), o2.getName())) {
            return false;
        }
        if (!isObjectEq(o1.getConvertor(), o2.getConvertor())) {
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
