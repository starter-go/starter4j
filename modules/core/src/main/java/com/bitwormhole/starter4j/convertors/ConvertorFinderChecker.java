package com.bitwormhole.starter4j.convertors;

public class ConvertorFinderChecker extends ConvertorProviderWrapper {

    public ConvertorFinderChecker(ConvertorProvider in) {
        super(in);
    }

    @Override
    public Convertor findConvertor(Class<?> sourceType) {
        Convertor c = super.findConvertor(sourceType);
        if (c == null) {
            throw new RuntimeException("no convertor for type of " + sourceType);
        }
        return c;
    }

    @Override
    public Convertor[] findConvertors(Class<?> sourceType) {
        Convertor[] array = super.findConvertors(sourceType);
        if (!hasConvertors(array)) {
            throw new RuntimeException("no convertor for type of " + sourceType);
        }
        return array;
    }

    private static boolean hasConvertors(Convertor[] array) {
        if (array == null) {
            return false;
        }
        for (Convertor c : array) {
            if (c != null) {
                return true;
            }
        }
        return false;
    }

}
