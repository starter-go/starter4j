package com.bitwormhole.starter4j.convertors;

public interface Convertor {

    Object convert(Object src);

    <T> T convert(Object src, Class<T> targetType);

    boolean acceptSourceType(Class<?> cl);

    boolean acceptSourceObject(Object src);

    Class<?> getSourceType();

}
