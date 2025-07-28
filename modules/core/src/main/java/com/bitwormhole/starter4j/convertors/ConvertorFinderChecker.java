package com.bitwormhole.starter4j.convertors;

public class ConvertorFinderChecker extends ConvertorProviderWrapper {

    public ConvertorFinderChecker(ConvertorProvider in) {
        super(in);
    }

    @Override
    public Convertor findConvertor(ConvertorSelector sel) {
        Convertor c = super.findConvertor(sel);
        if (c == null) {
            throw new RuntimeException("no convertor for selector of " + sel);
        }
        return c;
    }

    @Override
    public Convertor[] findConvertors(ConvertorSelector sel) {
        Convertor[] array = super.findConvertors(sel);
        if (!hasConvertors(array)) {
            throw new RuntimeException("no convertor for selector of " + sel);
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
