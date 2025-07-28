package com.bitwormhole.starter4j.convertors;

public abstract class ConvertorManager implements ConvertorProvider {

    ////////////////////////////////////////////////////////////////////////////
    /// public

    public abstract Convertor findConvertor(Object in, Class<?> out);

    public abstract Convertor[] findConvertors(Object in, Class<?> out);

    public abstract void register(ConvertorRegistration reg);

    public abstract void register(ConvertorRegistry reg);

    public static ConvertorManager getInstance() {
        ConvertorManager inst = theInstance;
        if (inst == null) {
            inst = new ConvertorManagerImpl();
            theInstance = inst;
        }
        return inst;
    }

    ////////////////////////////////////////////////////////////////////////////
    /// private

    private static ConvertorManager theInstance;

}
