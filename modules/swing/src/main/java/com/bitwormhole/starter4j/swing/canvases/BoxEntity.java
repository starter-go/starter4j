package com.bitwormhole.starter4j.swing.canvases;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/****
 * BoxEntity 是 Box 的实体类，它实现了 Box 中的所有抽象方法
 */
public class BoxEntity extends Box {

    static final Logger logger = LoggerFactory.getLogger(BoxEntity.class);

    public BoxEntity() {
        super();
    }

    @Override
    public final void render(RenderContext rc) {

        if (!this.isPresence()) {
            return;
        }

        final VisibilityEnum v = this.getVisibility();
        if (!VisibilityEnum.isVisible(v)) {
            return;
        }

        Graphics g = rc.getGraphics();
        Point pt = this.getPosition();
        boolean is_clip = this.isClipped();
        Dimension my_size = this.getSize();

        // apply translate
        g.translate(pt.x, pt.y);

        // apply clip
        if (is_clip) {
            g.clipRect(0, 0, my_size.width, my_size.height);
            // g.setClip(0, 0, my_size.width, my_size.height);
        }

        this.onPaint(rc);
    }

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

        // final Point pos = this.getPositionAtCanvas();

        final Dimension size = this.getSize();
        final Graphics2D g = (Graphics2D) rc.getGraphics();
        final BoxStyle bs = Getters.notNull(this.getStyle());
        Color color;
        LineStyle style;
        int width, x1, x2, y1, y2;

        if (size == null) {
            return;
        }

        // logger.info("paintBorder@ size:" + size + " pos:" + pos);

        // top
        color = bs.getBorderTopColor();
        width = bs.getBorderTopWidth();
        style = bs.getBorderTopStyle();
        if (innerHasBorder(color, style, width)) {
            x1 = 0;
            y1 = 0;
            x2 = size.width;
            y2 = 0;
            g.setStroke(new BasicStroke(width));
            g.setColor(color);
            g.drawLine(x1, y1, x2, y2);
        }

        // left
        color = bs.getBorderLeftColor();
        width = bs.getBorderLeftWidth();
        style = bs.getBorderLeftStyle();
        if (innerHasBorder(color, style, width)) {
            x1 = 0;
            y1 = 0;
            x2 = 0;
            y2 = size.height;
            g.setStroke(new BasicStroke(width));
            g.setColor(color);
            g.drawLine(x1, y1, x2, y2);
        }

        // right
        color = bs.getBorderRightColor();
        width = bs.getBorderRightWidth();
        style = bs.getBorderRightStyle();
        if (innerHasBorder(color, style, width)) {
            x1 = size.width;
            y1 = 0;
            x2 = size.width;
            y2 = size.height;
            g.setStroke(new BasicStroke(width));
            g.setColor(color);
            g.drawLine(x1, y1, x2, y2);
        }

        // bottom
        color = bs.getBorderBottomColor();
        width = bs.getBorderBottomWidth();
        style = bs.getBorderBottomStyle();
        if (innerHasBorder(color, style, width)) {
            x1 = 0;
            y1 = size.height;
            x2 = size.width;
            y2 = size.height;
            g.setStroke(new BasicStroke(width));
            g.setColor(color);
            g.drawLine(x1, y1, x2, y2);
        }

        // return;
    }

    private static Point computePositionAtCanvas(Box item, int limit) {
        int cx, cy, depth;
        depth = cx = cy = 0;
        Box p = item;
        for (; p != null; p = p.getParent()) {
            if (depth > limit) {
                String msg = "the layout stack is too deep, limit=" + limit;
                logger.error(msg);
                throw new RuntimeException(msg);
            }
            Point pos = p.getPosition();
            if (pos == null) {
                pos = new Point();
                p.setPosition(pos);
            }
            cx += pos.x;
            cy += pos.y;
            depth++;
        }
        return new Point(cx, cy);
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
    public final void handleMouseEvent(MouseEventContext ctx) {

        VisibilityEnum visi = this.getVisibility();
        if (!VisibilityEnum.isVisible(visi)) {
            return;
        }

        final boolean scme = this.isWorkingForShortCircuitMouseEvent();
        if (!scme) {
            if (!isHit(ctx)) {
                return;
            }
        }

        Point l1 = ctx.getLocationAtCanvas();
        Point l2 = this.convertCanvasToLocal(l1);
        ctx.setLocation(l2);

        this.onMouseEvent(ctx);
    }

    @Override
    protected void onMouseEvent(MouseEventContext mec) {
        Canvas canvas = mec.getCanvas();
        this.setCanvasContext(canvas.getCanvasContext());
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

    @Override
    protected void onPaint(RenderContext rc) {
        this.onPaintBackground(rc);
        this.onPaintForeground(rc);
    }

    @Override
    protected Point computeMyPositionAtCanvas() {
        return computePositionAtCanvas(this, 64);
    }

    @Override
    protected boolean isWorkingForShortCircuitMouseEvent() {
        return false;
    }

}
