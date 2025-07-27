package com.bitwormhole.starter4j.convertors;

public interface ConvertorProvider {

    Convertor findConvertor(Class<?> sourceType);

    Convertor[] findConvertors(Class<?> sourceType);

}
