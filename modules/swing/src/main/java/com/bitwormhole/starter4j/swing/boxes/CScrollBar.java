package com.bitwormhole.starter4j.swing.boxes;

import java.awt.Color;

import com.bitwormhole.starter4j.swing.canvases.BoxEntity;
import com.bitwormhole.starter4j.swing.canvases.BoxStyle;
import com.bitwormhole.starter4j.swing.canvases.LineStyle;

public class CScrollBar extends BoxEntity {

    public CScrollBar(Direction dir) {
        this.direction = MyTools.normalize(dir);
        this.innerOnCreate();
    }

    public enum Direction {
        HORIZONTAL, VERTICAL,
    }

    public CScrollInfo getInfo() {
        return MyTools.normalize(info);
    }

    public void setInfo(CScrollInfo i) {
        this.info = MyTools.normalize(i);
    }

    public Direction getDirection() {
        return MyTools.normalize(direction);
    }

    public void setDirection(Direction dir) {
        this.direction = MyTools.normalize(dir);
    }

    ////////////////////////////////////////////////////////////////////////////
    /// private

    private CScrollInfo info;
    private Direction direction;

    private final static class MyTools {

        static Direction normalize(Direction dir) {
            if (dir == null) {
                dir = Direction.VERTICAL;
            }
            return dir;
        }

        static CScrollInfo normalize(CScrollInfo i) {
            if (i == null) {
                i = new CScrollInfo();
            }
            return i;
        }

    }

    private void innerOnCreate() {

        BoxStyle sty = this.getStyle();
        sty.setBorderStyle(LineStyle.SOLID);
        sty.setBorderColor(Color.black);
        sty.setBorderWidth(1);

    }

}
