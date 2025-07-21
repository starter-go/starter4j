package com.bitwormhole.starter4j.swing.canvases;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

/****
 * BoxEntity 是 Box 的实体类，它实现了 Box 中的所有抽象方法
 */
public class BoxEntity extends Box {

    public BoxEntity() {
        super();
    }

    @Override
    public void render(RenderContext rc) {
        final VisibilityEnum v = this.getVisibility();
        if (VisibilityEnum.isVisible(v)) {
            this.onPaintBackground(rc);
            this.onPaintForeground(rc);
        }
    }

    // @Override
    // public void rebuildLayout(LayoutContext lc) {
    // this.onBuildLayout(lc);
    // }

    @Override
    protected void onPaintBackground(RenderContext rc) {
        this.innerPaintBackground(rc);
    }

    @Override
    protected void onPaintForeground(RenderContext rc) {
        this.innerPaintBorder(rc);
    }

    private void innerInitDefaultSize(LayoutContext lc) {

        final Dimension dim1 = this.getSize();
        final Dimension dim2 = this.getWantSize();

        Dimension dim3 = (dim1 != null) ? dim1 : dim2;
        if (dim3 == null) {
            dim3 = new Dimension();
        }

        if (dim1 == null) {
            this.setSize(dim3);
        }
        if (dim2 == null) {
            this.setWantSize(dim3);
        }
    }

    // 绘制背景
    private void innerPaintBackground(RenderContext rc) {

        final Point pos = this.getPositionAtCanvas();
        final Dimension size = this.getSize();
        final BoxStyle bs = Getters.notNull(this.getStyle());
        final Graphics g = rc.getGraphics();

        Color color = bs.getBackgroundColor();
        if (pos == null || size == null || color == null) {
            return;
        }

        int x, y, w, h;
        x = pos.x;
        y = pos.y;
        w = size.width;
        h = size.height;

        g.setColor(color);
        g.fillRect(x, y, w, h);
    }

    // 绘制边框
    private void innerPaintBorder(RenderContext rc) {

        final Point pos = this.getPositionAtCanvas();
        final Dimension size = this.getSize();
        final Graphics2D g = (Graphics2D) rc.getGraphics();
        final BoxStyle bs = Getters.notNull(this.getStyle());
        Color color;
        LineStyle style;
        int width, x1, x2, y1, y2;

        if (pos == null || size == null) {
            return;
        }

        // top
        color = bs.getBorderTopColor();
        width = bs.getBorderTopWidth();
        style = bs.getBorderTopStyle();
        if (innerHasBorder(color, style, width)) {
            x1 = pos.x;
            y1 = pos.y;
            x2 = pos.x + size.width;
            y2 = pos.y;
            g.setStroke(new BasicStroke(width));
            g.setColor(color);
            g.drawLine(x1, y1, x2, y2);
        }

        // left
        color = bs.getBorderLeftColor();
        width = bs.getBorderLeftWidth();
        style = bs.getBorderLeftStyle();
        if (innerHasBorder(color, style, width)) {
            x1 = pos.x;
            y1 = pos.y;
            x2 = pos.x;
            y2 = pos.y + size.height;
            g.setStroke(new BasicStroke(width));
            g.setColor(color);
            g.drawLine(x1, y1, x2, y2);
        }

        // right
        color = bs.getBorderRightColor();
        width = bs.getBorderRightWidth();
        style = bs.getBorderRightStyle();
        if (innerHasBorder(color, style, width)) {
            x1 = pos.x + size.width;
            y1 = pos.y;
            x2 = pos.x + size.width;
            y2 = pos.y + size.height;
            g.setStroke(new BasicStroke(width));
            g.setColor(color);
            g.drawLine(x1, y1, x2, y2);
        }

        // bottom
        color = bs.getBorderBottomColor();
        width = bs.getBorderBottomWidth();
        style = bs.getBorderBottomStyle();
        if (innerHasBorder(color, style, width)) {
            x1 = pos.x;
            y1 = pos.y + size.height;
            x2 = pos.x + size.width;
            y2 = pos.y + size.height;
            g.setStroke(new BasicStroke(width));
            g.setColor(color);
            g.drawLine(x1, y1, x2, y2);
        }

        // return;
    }

    private static boolean innerHasBorder(Color c, LineStyle s, int w) {

        if (w < 1) {
            return false;
        }

        if (c == null || s == null) {
            return false;
        }

        if (s.equals(LineStyle.NONE)) {
            return false;
        }

        return true;
    }

    @Override
    public void handleMouseEvent(MouseEventContext ctx) {

        VisibilityEnum visi = this.getVisibility();
        if (!VisibilityEnum.isVisible(visi)) {
            return;
        }

        if (!isHit(ctx)) {
            return;
        }

        this.onMouseEvent(ctx);
    }

    @Override
    protected void onMouseEvent(MouseEventContext mec) {
    }

    private boolean isHit(MouseEventContext mectx) {
        Point pt = mectx.getLocationAtCanvas();
        return this.isHit(pt);
    }

    private boolean isHit(Point pt_at_canvas) {

        final Point ptr = pt_at_canvas;
        final Point position = this.getPositionAtCanvas();
        final Dimension size = this.getSize();

        if (ptr == null || position == null || size == null) {
            return false;
        }

        final int left, right, top, bottom;
        top = position.y;
        left = position.x;
        right = position.x + size.width;
        bottom = position.y + size.height;

        return ((left <= ptr.x) && (ptr.x <= right) && (top <= ptr.y) && (ptr.y <= bottom));
    }

    @Override
    public void updateLayout(LayoutContext lc) {
        this.onUpdateLayout(lc);
    }

    @Override
    protected void onUpdateLayout(LayoutContext lc) {

        lc.add(this);

        this.setCanvasContext(lc.getCanvasContext());
        this.setParent(lc.getParent());
        this.innerInitDefaultSize(lc);

    }

}
