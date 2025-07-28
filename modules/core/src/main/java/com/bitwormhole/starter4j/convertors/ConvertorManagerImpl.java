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
    public Convertor findConvertor(ConvertorSelector sel) {
        return mMainProvider.findConvertor(sel);
    }

    @Override
    public Convertor[] findConvertors(ConvertorSelector sel) {
        return mMainProvider.findConvertors(sel);
    }

    @Override
    public Convertor findConvertor(Object in, Class<?> out) {
        if (in == null || out == null) {
            return null;
        }
        ConvertorSelector sel = new ConvertorSelector();
        sel.setInputType(in.getClass());
        sel.setOutputType(out);
        return this.findConvertor(sel);
    }

    @Override
    public Convertor[] findConvertors(Object in, Class<?> out) {
        if (in == null || out == null) {
            return new Convertor[] {};
        }
        ConvertorSelector sel = new ConvertorSelector();
        sel.setInputType(in.getClass());
        sel.setOutputType(out);
        return this.findConvertors(sel);
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
