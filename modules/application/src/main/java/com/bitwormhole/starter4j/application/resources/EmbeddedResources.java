package com.bitwormhole.starter4j.application.resources;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bitwormhole.starter4j.base.Hex;
import com.bitwormhole.starter4j.base.Paths;
import com.bitwormhole.starter4j.base.SafeMode;

public final class EmbeddedResources {

    private EmbeddedResources() {
    }

    public static Resources create(List<EmbeddedRes> src) {
        SafeMode mode = SafeMode.Safe;
        Resources dst = Resources.Table.create(mode);
        MyLoader.load(src, dst);
        return dst;
    }

    private static class MyLoader {
        public static void load(List<EmbeddedRes> src, Resources dst) {
            Map<String, Resource> tmp = new HashMap<>();
            for (EmbeddedRes item1 : src) {
                MyItem item2 = new MyItem();
                item2.init(item1);
                String path = item2.path();
                tmp.put(path, item2);
            }
            dst.importFrom(tmp);
        }

        public static byte[] loadBinaryData(EmbeddedRes src) {
            StringBuilder buffer = new StringBuilder();
            src.hex(buffer);
            return Hex.parse(buffer.toString());
        }

        public static String getSimpleName(EmbeddedRes src) {
            String str = src.path() + "";
            String[] list = Paths.path2elements(str);
            for (int i = list.length; i > 0;) {
                i--;
                String item = list[i].trim();
                if (item.length() > 0) {
                    return item;
                }
            }
            return "";
        }
    }

    private static class MyItem implements Resource {

        private String mPath;
        private String mSimpleName;
        private int mSize;
        private EmbeddedRes mSource;

        public void init(EmbeddedRes src) {
            this.mSimpleName = MyLoader.getSimpleName(src);
            this.mPath = src.path();
            this.mSize = src.size();
            this.mSource = src;
        }

        @Override
        public String path() {
            return this.mPath;
        }

        @Override
        public String simpleName() {
            return this.mSimpleName;
        }

        @Override
        public long size() {
            return this.mSize;
        }

        @Override
        public byte[] readBinary() throws IOException {
            return MyLoader.loadBinaryData(this.mSource);
        }

        @Override
        public String readText() throws IOException {
            Charset cs = StandardCharsets.UTF_8;
            byte[] data = this.readBinary();
            return new String(data, cs);
        }

        @Override
        public InputStream open() throws IOException {
            byte[] data = this.readBinary();
            return new ByteArrayInputStream(data);
        }
    }
}
