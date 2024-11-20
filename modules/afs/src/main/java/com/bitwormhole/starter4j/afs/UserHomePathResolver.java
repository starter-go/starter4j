package com.bitwormhole.starter4j.afs;

import com.bitwormhole.starter4j.base.Paths;

final class UserHomePathResolver {

    public static String resolve(final String p1) {
        StringBuilder builder = new StringBuilder();
        if (p1.startsWith("/") || p1.startsWith("\\")) {
            builder.append('/');
        }
        String[] elist = Paths.path2elements(p1);
        boolean has = false;
        for (String el : elist) {
            if (el == null) {
                continue;
            }
            String el2 = el.trim();
            if (el2.length() == 0) {
                continue;
            }
            if (el2.equals("~")) {
                has = true;
                builder.setLength(0);
                builder.append(getUserHome());
            } else {
                if (builder.length() > 0) {
                    builder.append('/');
                }
                builder.append(el);
            }
        }
        if (has) {
            return builder.toString();
        }
        return p1;
    }

    public static boolean needResolve(String p1) {
        if (p1 == null) {
            return false;
        }
        if (p1.indexOf('~') < 0) {
            return false;
        }
        return true;
    }

    private static String theCurrentUserHome;

    private static String getUserHome() {
        String dir = theCurrentUserHome;
        if (dir == null) {
            final String name = "user.home";
            dir = System.getProperties().getProperty(name);
            theCurrentUserHome = dir;
        }
        return dir;
    }
}
