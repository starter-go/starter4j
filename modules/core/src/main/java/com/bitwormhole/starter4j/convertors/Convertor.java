package com.bitwormhole.starter4j.convertors;

public interface Convertor {

    Object convert(Object in);

    <T> T convert(Object in, Class<T> output_type);

    boolean acceptInputType(Class<?> cl);

    boolean acceptInputObject(Object in);

    ConvertorRegistration getRegistration();

}
