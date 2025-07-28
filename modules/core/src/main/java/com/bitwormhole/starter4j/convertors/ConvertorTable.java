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
        Map<MyTableKey, MyTableValue> t = new HashMap<>();
        this.table = Collections.synchronizedMap(t);
    }

    @Override
    public Convertor findConvertor(ConvertorSelector sel) {
        MyTableKey key = new MyTableKey(sel);
        MyTableValue value = table.get(key);
        if (value == null) {
            return null;
        }
        Convertor res = value.findByName(sel.getName());
        if (res == null) {
            res = value.getFirstConvertor();
        }
        return res;
    }

    @Override
    public Convertor[] findConvertors(ConvertorSelector sel) {
        MyTableKey key = new MyTableKey(sel);
        MyTableValue value = table.get(key);
        if (value == null) {
            return new Convertor[] {};
        }
        return value.listAll();
    }

    public void put(ConvertorRegistration registration) {
        if (!isAvailable(registration)) {
            return;
        }
        final MyTableKey key = new MyTableKey(registration);
        MyTableValue value = table.get(key);
        if (value == null) {
            value = new MyTableValue();
            table.put(key, value);
        }
        if (value.contains(registration)) {
            return;
        }
        value.add(registration);
    }

    ////////////////////////////////////////////////////////////////////////////
    /// private

    private final Map<MyTableKey, MyTableValue> table;

    private static class MyTableKey {

        private final String key;

        MyTableKey(ConvertorRegistration registration) {
            String str = "";
            if (registration != null) {
                str = makeKeyString(registration.getInputType(), registration.getOutputType());
            }
            this.key = str;
        }

        MyTableKey(ConvertorSelector sel) {
            String str = "";
            if (sel != null) {
                str = makeKeyString(sel.getInputType(), sel.getOutputType());
            }
            this.key = str;
        }

        static String makeKeyString(Class<?> c1, Class<?> c2) {
            String s1 = "null";
            String s2 = "null";
            if (c1 != null) {
                s1 = c1.getName();
            }
            if (c2 != null) {
                s2 = c2.getName();
            }
            return s1 + ">>>" + s2;
        }

        @Override
        public int hashCode() {
            return this.key.hashCode();
        }

        @Override
        public boolean equals(Object obj) {

            if (obj == null) {
                return false;
            }

            if (obj == this) {
                return true;
            }

            String s1 = this.key;
            String s2 = null;

            if (obj instanceof MyTableKey) {
                s2 = obj.toString();
            } else {
                return false;
            }
            return s1.equals(s2);
        }

        @Override
        public String toString() {
            return this.key;
        }

    }

    private static class MyTableValue {

        MyTableItem head;
        MyTableItem[] cache;

        Convertor findByName(String name) {
            if (name == null) {
                return null;
            }
            MyTableItem[] src = this.getCache();
            for (MyTableItem item : src) {
                if (item != null) {
                    if (isAvailable(item.registration)) {
                        if (name.equals(item.registration.getName())) {
                            return item.registration.getConvertor();
                        }
                    }
                }
            }
            return null;
        }

        Convertor getFirstConvertor() {
            MyTableItem[] src = this.getCache();
            for (MyTableItem item : src) {
                if (item != null) {
                    if (isAvailable(item.registration)) {
                        return item.registration.getConvertor();
                    }
                }
            }
            return null;
        }

        Convertor[] listAll() {
            MyTableItem[] src = this.getCache();
            Convertor[] dst = new Convertor[src.length];
            for (int i = 0; i < dst.length; i++) {
                MyTableItem item = src[i];
                dst[i] = item.registration.getConvertor();
            }
            return dst;
        }

        boolean contains(ConvertorRegistration cr1) {
            if (!isAvailable(cr1)) {
                return false;
            }
            MyTableItem p = this.head;
            for (; p != null; p = p.next) {
                ConvertorRegistration cr2 = p.registration;
                if (eq(cr1, cr2)) {
                    return true;
                }
            }
            return false;
        }

        MyTableItem[] loadCache() {
            MyTableItem p = this.head;
            List<MyTableItem> list = new ArrayList<>();
            for (; p != null; p = p.next) {
                list.add(p);
            }
            list.sort((item1, item2) -> {
                int p1 = item1.registration.getPriority();
                int p2 = item2.registration.getPriority();
                return (p2 - p1);
            });
            return list.toArray(new MyTableItem[0]);
        }

        MyTableItem[] getCache() {
            MyTableItem[] c = this.cache;
            if (c == null) {
                c = this.loadCache();
                this.cache = c;
            }
            return c;
        }

        void add(ConvertorRegistration registration) {
            if (!isAvailable(registration)) {
                return;
            }
            MyTableItem item = new MyTableItem(registration, this.head);
            this.head = item;
            this.cache = null;
        }
    }

    private static class MyTableItem {

        final MyTableItem next;
        final ConvertorRegistration registration;

        MyTableItem(ConvertorRegistration cr, MyTableItem n) {
            this.next = n;
            this.registration = new ConvertorRegistration(cr);
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
        Class<?> t1 = registration.getInputType();
        Class<?> t2 = registration.getOutputType();
        Convertor c = registration.getConvertor();

        if (t1 == null || t2 == null || c == null) {
            return false;
        }

        if (n == null) {
            n = c.getClass().getName();
            registration.setName(n);
        }

        return true;
    }

}
