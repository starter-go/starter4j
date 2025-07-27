package com.bitwormhole.starter4j.convertors;

final class ConvertorManagerImpl extends ConvertorManager {

    private final ConvertorTable mTable;
    private final ConvertorProvider mMainProvider;

    public ConvertorManagerImpl() {
        ConvertorTable table = new ConvertorTable();
        this.mTable = table;
        this.mMainProvider = new ConvertorFinderChecker(table);
    }

    @Override
    public Convertor findConvertor(Class<?> sourceType) {
        return mMainProvider.findConvertor(sourceType);
    }

    @Override
    public Convertor[] findConvertors(Class<?> sourceType) {
        return mMainProvider.findConvertors(sourceType);
    }

    @Override
    public Convertor forObject(Object sourceObject) {
        if (sourceObject == null) {
            return null;
        }
        Class<?> t = sourceObject.getClass();
        return this.findConvertor(t);
    }

    @Override
    public Convertor[] findConvertorsForObject(Object sourceObject) {
        if (sourceObject == null) {
            return new Convertor[] {};
        }
        Class<?> t = sourceObject.getClass();
        return this.findConvertors(t);
    }

    @Override
    public void register(ConvertorRegistration reg) {
        mTable.put(reg);
    }

    @Override
    public void register(ConvertorRegistry reg) {
        if (reg == null) {
            return;
        }
        ConvertorRegistration[] list = reg.listConvertorRegistrations();
        if (list == null) {
            return;
        }
        for (ConvertorRegistration r2 : list) {
            mTable.put(r2);
        }
    }

}
