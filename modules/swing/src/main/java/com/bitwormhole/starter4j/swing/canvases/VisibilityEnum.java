package com.bitwormhole.starter4j.swing.canvases;

public enum VisibilityEnum {

    VISIBLE, INVISIBLE, GONE

    ;

    public static boolean isVisible(VisibilityEnum value) {
        if (value == null) {
            return false;
        }
        return VISIBLE.equals(value);
    }

}
