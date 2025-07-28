package com.bitwormhole.starter4j.convertors;

public interface ConvertorProvider {

    Convertor findConvertor(ConvertorSelector sel);

    Convertor[] findConvertors(ConvertorSelector sel);

}
