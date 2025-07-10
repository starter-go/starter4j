package com.bitwormhole.starter4j.swing.canvases;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import com.bitwormhole.starter4j.swing.layouts.SimpleLayout;

public final class Getters {

    private Getters() {
    }

    public static BoxStyle notNull(BoxStyle o) {
        if (o == null) {
            o = new BoxStyle();
        }
        return o;
    }

    public static Font notNull(Font o) {
        if (o == null) {
            o = new Font("Not-Null", Font.PLAIN, 12);
        }
        return o;
    }

    public static AlignEnum notNull(AlignEnum o) {
        if (o == null) {
            o = AlignEnum.CENTER;
        }
        return o;
    }

    public static Color notNull(Color o) {
        if (o == null) {
            o = Color.black;
        }
        return o;
    }

    public static ILayout notNull(ILayout l) {
        if (l == null) {
            l = new SimpleLayout();
        }
        return l;
    }

    public static Box notNull(Box o) {
        if (o == null) {
            o = new BoxEntity();
        }
        return o;
    }

    public static BoxContainer notNull(BoxContainer o) {
        if (o == null) {
            o = new BoxContainerEntity();
        }
        return o;
    }

    public static Dimension notNull(Dimension o) {
        if (o == null) {
            o = new Dimension();
        }
        return o;
    }

    public static Color getFirstAvailableValue(Color v1, Color v2) {
        if (v1 != null) {
            return v1;
        }
        if (v2 != null) {
            return v2;
        }
        return Color.black;
    }

    public static LineStyle getFirstAvailableValue(LineStyle v1, LineStyle v2) {
        if (v1 != null) {
            return v1;
        }
        if (v2 != null) {
            return v2;
        }
        return LineStyle.NONE;
    }

    public static int getFirstAvailableWidth(int v1, int v2) {
        if (v1 >= 0) {
            return v1;
        }
        if (v2 >= 0) {
            return v2;
        }
        return 0;
    }

}
