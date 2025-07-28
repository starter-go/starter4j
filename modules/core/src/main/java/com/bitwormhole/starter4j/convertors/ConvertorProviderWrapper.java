package com.bitwormhole.starter4j.convertors;

public class ConvertorProviderWrapper implements ConvertorProvider {

    private final ConvertorProvider inner;

    public ConvertorProviderWrapper(ConvertorProvider in) {
        this.inner = in;
    }

    @Override
    public Convertor findConvertor(ConvertorSelector sel) {
        return inner.findConvertor(sel);
    }

    @Override
    public Convertor[] findConvertors(ConvertorSelector sel) {
        return inner.findConvertors(sel);
    }

}
