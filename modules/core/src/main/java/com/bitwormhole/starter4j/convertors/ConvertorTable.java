package com.bitwormhole.starter4j.convertors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConvertorTable implements ConvertorProvider {

    ////////////////////////////////////////////////////////////////////////////
    /// public

    public ConvertorTable() {
        Map<Class<?>, MyTableItem> t = new HashMap<>();
        this.table = Collections.synchronizedMap(t);
    }

    @Override
    public Convertor findConvertor(Class<?> sourceType) {
        MyTableItem item = table.get(sourceType);
        if (item == null) {
            return null;
        }
        return item.getFirstConvertor();
    }

    @Override
    public Convertor[] findConvertors(Class<?> sourceType) {
        MyTableItem item = table.get(sourceType);
        if (item == null) {
            return new Convertor[] {};
        }
        return item.listAll();
    }

    public void put(ConvertorRegistration registration) {

        if (!isAvailable(registration)) {
            return;
        }

        final Class<?> key = registration.getSourceType();
        MyTableItem older = table.get(key);
        if (older == null) {
            MyTableItem item = new MyTableItem(registration, older);
            table.put(key, item);
            return;
        }

        // has older ...
        if (older.contains(registration)) {
            return;
        }

        MyTableItem item = new MyTableItem(registration, older);
        table.put(key, item);
    }

    ////////////////////////////////////////////////////////////////////////////
    /// private

    private final Map<Class<?>, MyTableItem> table;

    private static class MyTableItem {

        final MyTableItem next;
        final ConvertorRegistration registration;

        MyTableItem(ConvertorRegistration cr, MyTableItem n) {
            this.next = n;
            this.registration = new ConvertorRegistration(cr);
        }

        Convertor getFirstConvertor() {
            return this.registration.getConvertor();
        }

        Convertor[] listAll() {
            List<Convertor> list = new ArrayList<>();
            MyTableItem p = this;
            for (; p != null; p = p.next) {
                Convertor c = p.registration.getConvertor();
                list.add(c);
            }
            return list.toArray(new Convertor[0]);
        }

        boolean contains(ConvertorRegistration cr1) {
            if (!isAvailable(cr1)) {
                return false;
            }
            MyTableItem p = this;
            for (; p != null; p = p.next) {
                ConvertorRegistration cr2 = p.registration;
                if (eq(cr1, cr2)) {
                    return true;
                }
            }
            return false;
        }

    }

    private static boolean eq(ConvertorRegistration r1, ConvertorRegistration r2) {
        if (r1 == null || r2 == null) {
            return false;
        }
        return r1.equals(r2);
    }

    private static boolean isAvailable(ConvertorRegistration registration) {

        if (registration == null) {
            return false;
        }

        String n = registration.getName();
        Class<?> t = registration.getSourceType();
        Convertor c = registration.getConvertor();

        if (t == null || c == null) {
            return false;
        }

        if (n == null) {
            n = c.getClass().getName();
            registration.setName(n);
        }

        return true;
    }

}
