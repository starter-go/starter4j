package com.bitwormhole.starter4j.convertors;

public class ConvertorHolderT<T1, T2> {

    private final ConvertorHolder inner;

    public ConvertorHolderT(Class<T1> in, Class<T2> out) {
        this.inner = new ConvertorHolder(in, out);
    }

    public ConvertorHolderT(Class<T1> in, Class<T2> out, String convertor_name) {
        this.inner = new ConvertorHolder(in, out, convertor_name);
    }

    @SuppressWarnings("unchecked")
    public T2 convert(T1 o1) {
        Convertor con = this.inner.getConvertor();
        Object o2 = con.convert(o1);
        return (T2) o2;
    }

}
