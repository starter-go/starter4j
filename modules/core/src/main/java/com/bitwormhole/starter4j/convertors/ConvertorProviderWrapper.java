package com.bitwormhole.starter4j.convertors;

public class ConvertorProviderWrapper implements ConvertorProvider {

    private final ConvertorProvider inner;

    public ConvertorProviderWrapper(ConvertorProvider in) {
        this.inner = in;
    }

    @Override
    public Convertor findConvertor(Class<?> sourceType) {
        return inner.findConvertor(sourceType);
    }

    @Override
    public Convertor[] findConvertors(Class<?> sourceType) {
        return inner.findConvertors(sourceType);
    }

}
