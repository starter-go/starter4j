package com.bitwormhole.starter4j.base;

import java.io.ByteArrayOutputStream;

public final class Hex {

    private Hex() {
    }

    private static final char[] hex_char_set = "0123456789abcdef".toCharArray();

    public static String stringify(byte[] bin) {
        if (bin == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : bin) {
            sb.append(hex_char_set[(b >> 4) & 0x0f]);
            sb.append(hex_char_set[b & 0x0f]);
        }
        return sb.toString();
    }

    public static byte[] parse(String str) {
        if (str == null) {
            return new byte[0];
        }
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final char[] clist = str.toCharArray();
        int index = 0;
        int d0 = 0;
        for (char ch : clist) {
            int d = 0;
            if (('0' <= ch) && (ch <= '9')) {
                d = ch - '0';
            } else if (('a' <= ch) && (ch <= 'f')) {
                d = ch - 'a' + 0x0a;
            } else if (('A' <= ch) && (ch <= 'F')) {
                d = ch - 'A' + 0x0a;
            } else {
                continue;
            }
            if ((index & 0x01) == 0) {
                d0 = d << 4;
            } else {
                out.write((d0 & 0xf0) | (d & 0x0f));
            }
            index++;
        }
        return out.toByteArray();
    }
}
