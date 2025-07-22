package com.bitwormhole.starter4j.swing.boxes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import com.bitwormhole.starter4j.swing.canvases.Box;
import com.bitwormhole.starter4j.swing.canvases.BoxEntity;
import com.bitwormhole.starter4j.swing.canvases.BoxStyle;
import com.bitwormhole.starter4j.swing.canvases.CanvasContext;
import com.bitwormhole.starter4j.swing.canvases.LayoutContext;
import com.bitwormhole.starter4j.swing.canvases.LineStyle;
import com.bitwormhole.starter4j.swing.canvases.MouseEventContext;
import com.bitwormhole.starter4j.swing.canvases.RenderContext;

public class CScrollBar extends BoxEntity {

    public CScrollBar(Direction dir) {

        this.direction = MyTools.normalize(dir);
        this.rectLineLess = new Rectangle();
        this.rectLineMore = new Rectangle();
        this.rectPageLess = new Rectangle();
        this.rectPageMore = new Rectangle();
        this.rectPageRange = new Rectangle();
        this.rectFullRange = new Rectangle();

        this.innerOnCreate();
    }

    public enum Direction {
        HORIZONTAL, VERTICAL, W, E, N, S,
    }

    public CScrollInfo getInfo() {
        return MyTools.normalize(info);
    }

    public void setInfo(CScrollInfo i) {
        this.info = MyTools.normalize(i);

        this.repaintMyself();
    }

    public Direction getDirection() {
        return MyTools.normalize(direction);
    }

    public void setDirection(Direction dir) {
        this.direction = MyTools.normalize(dir);
    }

    ////////////////////////////////////////////////////////////////////////////
    /// protected

    @Override
    protected void onMouseEvent(MouseEventContext mec) {
        super.onMouseEvent(mec);
        this.getInner().onMouseEvent(mec, this);
    }

    @Override
    protected void onPaintBackground(RenderContext rc) {
        super.onPaintBackground(rc);
    }

    @Override
    protected void onPaintForeground(RenderContext rc) {
        super.onPaintForeground(rc);
        this.getInner().paint(rc, this);
    }

    @Override
    protected void onUpdateLayout(LayoutContext lc) {
        super.onUpdateLayout(lc);
        this.getInner().layout(lc, this);
    }

    @Override
    protected boolean isWorkingForShortCircuitMouseEvent() {
        InnerBar in = this.getInner();
        return in.isWorkingAsDragging();
    }

    ////////////////////////////////////////////////////////////////////////////
    /// private

    private CScrollInfo info;
    private Direction direction;
    private InnerBar inner;

    private final Rectangle rectLineLess;
    private final Rectangle rectLineMore;
    private final Rectangle rectPageLess;
    private final Rectangle rectPageMore;
    private final Rectangle rectPageRange;
    private final Rectangle rectFullRange;

    private interface InnerBar {

        void paint(RenderContext rc, CScrollBar bar);

        void layout(LayoutContext lc, CScrollBar bar);

        void onMouseEvent(MouseEventContext mec, CScrollBar bar);

        boolean isDirectionOf(Direction dir);

        boolean isWorkingAsDragging();

    }

    private InnerBar createInner() {
        Direction dir = this.direction;
        if (Direction.HORIZONTAL.equals(dir)) {
            return new MyInnerBarH();
        } else {
            return new MyInnerBarV();
        }
    }

    private InnerBar getInner() {
        InnerBar in = this.inner;
        if (in != null) {
            if (!in.isDirectionOf(this.direction)) {
                in = null;
            }
        }
        if (in == null) {
            in = this.createInner();
            this.inner = in;
        }
        return in;
    }

    private static class MyPageDragging {

        Point startingPointAt; // @canvas
        CScrollInfo startingInfo;
        int startingFullPixel;

        int varPixelDiff;

    }

    private static abstract class MyInnerBar implements InnerBar {

        final Direction direction;
        MyPageDragging dragging;

        public MyInnerBar(Direction dir) {
            this.direction = dir;
        }

        @Override
        public boolean isWorkingAsDragging() {
            return (this.dragging != null);
        }

        @Override
        public boolean isDirectionOf(Direction dir) {
            if (this.direction == null || dir == null) {
                return false;
            }
            return dir.equals(this.direction);
        }

        @Override
        public void onMouseEvent(MouseEventContext mec, CScrollBar bar) {

            MouseEventContext.MouseEvent evt = mec.getEvent();

            if (evt == null) {
                // NOP

            } else if (evt.equals(MouseEventContext.MouseEvent.DRAGGED)) {
                this.handleMouseDragged(mec, bar);

            } else if (evt.equals(MouseEventContext.MouseEvent.CLICKED)) {
                this.handleMouseClicked(mec, bar);

            } else if (evt.equals(MouseEventContext.MouseEvent.PRESSED)) {
                this.handleMousePressed(mec, bar);

            } else if (evt.equals(MouseEventContext.MouseEvent.RELEASED)) {
                this.handleMouseReleased(mec, bar);
            }
        }

        abstract void onDraggingStart(MouseEventContext mec, CScrollBar bar, MyPageDragging dr);

        abstract void onDraggingMove(MouseEventContext mec, CScrollBar bar, MyPageDragging dr);

        private void startDragging(MouseEventContext mec, CScrollBar bar) {
            MyPageDragging dr = new MyPageDragging();
            dr.startingPointAt = mec.getLocationAtCanvas();
            dr.startingInfo = new CScrollInfo(bar.info);
            dr.startingFullPixel = 0;

            this.onDraggingStart(mec, bar, dr);
            this.dragging = dr;

            // add bar to SCME-dispatcher
            CanvasContext cc = mec.getCanvas().getCanvasContext();
            cc.getScmeDispatcher().add(bar);
        }

        private void handleMousePressed(MouseEventContext mec, CScrollBar bar) {
            Point pt = mec.getLocation();
            if (pt == null) {
                return;
            } else if (bar.rectPageRange.contains(pt)) {
                this.startDragging(mec, bar);
            } else {
                return;
            }
        }

        private void handleMouseReleased(MouseEventContext mec, CScrollBar bar) {

            this.dragging = null;

            CanvasContext cc = mec.getCanvas().getCanvasContext();
            cc.getScmeDispatcher().reset();
        }

        private void handleMouseDragged(MouseEventContext mec, CScrollBar bar) {
            MyPageDragging dr = this.dragging;
            if (dr == null) {
                return;
            }
            this.onDraggingMove(mec, bar, dr);
            CScrollInfo i2 = this.computeNewInfo(dr);
            bar.setInfo(i2);
            mec.getAdapter().repaint();
        }

        CScrollInfo computeNewInfo(MyPageDragging dr) {

            CScrollInfo info0 = dr.startingInfo;
            CScrollInfo info1 = new CScrollInfo(info0);

            // diff_px / full_px = diff_pos / total_pos
            // pos_diff = ( total_pos * diff_px) / full_px

            int px_full = dr.startingFullPixel;
            if (px_full < 1) {
                px_full = 1;
            }

            int pos_total = info0.getMax() - info0.getMin();
            int pos_diff = (pos_total * dr.varPixelDiff) / px_full;
            int pos0 = info0.getPosition();
            int pos1 = pos0 + pos_diff;

            info1.setPosition(pos1);
            return info1;
        }

        private void handleMouseClicked(MouseEventContext mec, CScrollBar bar) {

            Point pt = mec.getLocation();
            CScrollInfo info1 = bar.getInfo();
            CScrollInfo info2 = new CScrollInfo(info1);
            int delta;

            if (pt == null) {
                delta = 0;
            } else if (bar.rectLineLess.contains(pt)) {
                delta = -1;
            } else if (bar.rectLineMore.contains(pt)) {
                delta = 1;
            } else if (bar.rectPageLess.contains(pt)) {
                delta = 0 - info1.getPageSize();
            } else if (bar.rectPageMore.contains(pt)) {
                delta = info1.getPageSize();
            } else {
                delta = 0;
            }

            int pos1 = info1.getPosition();
            int pos2 = pos1 + delta;
            info2.setPosition(pos2);
            bar.setInfo(info2);

            mec.getAdapter().repaint();
        }

    }

    private static class MyInnerBarV extends MyInnerBar {

        public MyInnerBarV() {
            super(Direction.VERTICAL);
        }

        @Override
        public void paint(RenderContext rc, CScrollBar bar) {

            fillBackground(rc, bar, bar.rectLineLess);
            fillBackground(rc, bar, bar.rectPageRange);
            fillBackground(rc, bar, bar.rectLineMore);

            paintArrow(rc, bar, bar.rectLineLess, Direction.N);
            paintArrow(rc, bar, bar.rectLineMore, Direction.S);

            paintBorder(rc, bar, bar.rectLineLess);
            paintBorder(rc, bar, bar.rectLineMore);
            paintBorder(rc, bar, bar.rectPageRange);
            paintBorder(rc, bar, bar.rectFullRange);
        }

        @Override
        public void layout(LayoutContext lc, CScrollBar bar) {

            // todo (vert)

            final int bw, bh, line_btn_size, full_size;
            final Dimension b_size = bar.getSize();
            final CScrollInfo info = bar.getInfo();
            final long pos, vpt, total;

            long total_var = info.getMax() - info.getMin();
            if (total_var < 1) {
                total_var = 1;
            }

            total = total_var;
            pos = info.getPosition();
            vpt = info.getPageSize();
            bw = b_size.width;
            bh = b_size.height;
            line_btn_size = 20;
            full_size = bh - (line_btn_size * 2);

            final int x, y, w, h;
            x = 0;
            w = bw;

            // y0 (line_less) y1 (page_less) y2 (page_range)
            // y3 (page_more) y4 (line_more) y5
            int y0, y1, y2, y3, y4, y5;
            y0 = 0;
            y1 = line_btn_size;
            y2 = y1 + (int) (pos * full_size / total);
            y3 = y2 + (int) (vpt * full_size / total);
            y4 = bh - line_btn_size;
            y5 = bh;

            bar.rectLineLess.setBounds(x, y0, w, y1 - y0);
            bar.rectPageLess.setBounds(x, y1, w, y2 - y1);
            bar.rectPageRange.setBounds(x, y2, w, y3 - y2);
            bar.rectPageMore.setBounds(x, y3, w, y4 - y3);
            bar.rectLineMore.setBounds(x, y4, w, y5 - y4);

            bar.rectFullRange.setBounds(x, y1, w, y4 - y1);

        }

        @Override
        public void onMouseEvent(MouseEventContext mec, CScrollBar bar) {
            super.onMouseEvent(mec, bar);
        }

        @Override
        void onDraggingStart(MouseEventContext mec, CScrollBar bar, MyPageDragging dr) {
            dr.startingFullPixel = bar.rectFullRange.height;
        }

        @Override
        void onDraggingMove(MouseEventContext mec, CScrollBar bar, MyPageDragging dr) {
            Point pt0 = dr.startingPointAt;
            Point pt1 = mec.getLocationAtCanvas();
            dr.varPixelDiff = pt1.y - pt0.y;
        }

    }

    private static class MyInnerBarH extends MyInnerBar {

        public MyInnerBarH() {
            super(Direction.HORIZONTAL);
        }

        @Override
        public void paint(RenderContext rc, CScrollBar bar) {

            fillBackground(rc, bar, bar.rectLineLess);
            fillBackground(rc, bar, bar.rectPageRange);
            fillBackground(rc, bar, bar.rectLineMore);

            paintArrow(rc, bar, bar.rectLineLess, Direction.W);
            paintArrow(rc, bar, bar.rectLineMore, Direction.E);

            paintBorder(rc, bar, bar.rectLineLess);
            paintBorder(rc, bar, bar.rectLineMore);
            paintBorder(rc, bar, bar.rectPageRange);
            paintBorder(rc, bar, bar.rectFullRange);
        }

        @Override
        public void layout(LayoutContext lc, CScrollBar bar) {

            final int bw, bh, line_btn_size, full_size;
            final Dimension b_size = bar.getSize();
            final CScrollInfo info = bar.getInfo();
            final long pos, vpt, total;

            long total_var = info.getMax() - info.getMin();
            if (total_var < 1) {
                total_var = 1;
            }

            total = total_var;
            pos = info.getPosition();
            vpt = info.getPageSize();
            bw = b_size.width;
            bh = b_size.height;
            line_btn_size = 20;
            full_size = bw - (line_btn_size * 2);

            final int x, y, w, h;
            y = 0;
            h = bh;

            // x0 (line_less) x1 (page_less) x2 (page_range)
            // x3 (page_more) x4 (line_more) x5
            int x0, x1, x2, x3, x4, x5;
            x0 = 0;
            x1 = line_btn_size;
            x2 = x1 + (int) (pos * full_size / total);
            x3 = x2 + (int) (vpt * full_size / total);
            x4 = bw - line_btn_size;
            x5 = bw;

            bar.rectLineLess.setBounds(x0, y, x1 - x0, h);
            bar.rectPageLess.setBounds(x1, y, x2 - x1, h);
            bar.rectPageRange.setBounds(x2, y, x3 - x2, h);
            bar.rectPageMore.setBounds(x3, y, x4 - x3, h);
            bar.rectLineMore.setBounds(x4, y, x5 - x4, h);

            bar.rectFullRange.setBounds(x1, y, x4 - x1, h);

        }

        @Override
        public void onMouseEvent(MouseEventContext mec, CScrollBar bar) {
            super.onMouseEvent(mec, bar);
        }

        @Override
        void onDraggingMove(MouseEventContext mec, CScrollBar bar, MyPageDragging dr) {
            Point pt0 = dr.startingPointAt;
            Point pt1 = mec.getLocationAtCanvas();
            dr.varPixelDiff = pt1.x - pt0.x;
        }

        @Override
        void onDraggingStart(MouseEventContext mec, CScrollBar bar, MyPageDragging dr) {
            dr.startingFullPixel = bar.rectFullRange.width;
        }

    }

    private final static class MyTools {

        static Direction normalize(Direction dir) {
            if (dir == null) {
                dir = Direction.VERTICAL;
            }
            return dir;
        }

        static CScrollInfo normalize(CScrollInfo i) {
            return CScrollInfo.normalize(i);
        }

    }

    private static void paintBorder(RenderContext rc, Box box, Rectangle rect) {

        Color color = box.getStyle().getForegroundColor();
        Graphics g = rc.getGraphics();

        g.setColor(color);
        g.drawRect(rect.x, rect.y, rect.width, rect.height);
    }

    private static void fillBackground(RenderContext rc, Box box, Rectangle rect) {

        Color color = box.getStyle().getBackgroundColor();

        Graphics g = rc.getGraphics();
        g.setColor(color);
        g.fillRect(rect.x, rect.y, rect.width, rect.height);
    }

    private static void paintArrow(RenderContext rc, Box box, Rectangle rect, Direction dir) {

        final int cx, cy, w1, h1, r;
        w1 = rect.width; // rect.w
        h1 = rect.height; // rect.h
        cx = (int) rect.getCenterX(); // center.x
        cy = (int) rect.getCenterY(); // center.y
        r = (int) (Math.min(w1, h1) * 0.2); // 半径

        Color color = box.getStyle().getForegroundColor();
        Graphics g = rc.getGraphics();

        int x1, x2, x3;
        int y1, y2, y3;

        // x1 = x2 = x3 = 0;
        // y1 = y2 = y3 = 0;

        if (dir == null) {
            dir = Direction.E;
        }

        switch (dir) {
            case E:
                x1 = cx - r;
                x2 = cx - r;
                x3 = cx + r;
                y1 = cy - r;
                y2 = cy + r;
                y3 = cy;
                break;
            case N:
                x1 = cx;
                x2 = cx - r;
                x3 = cx + r;
                y1 = cy - r;
                y2 = cy + r;
                y3 = cy + r;
                break;
            case S:
                x1 = cx;
                x2 = cx - r;
                x3 = cx + r;
                y1 = cy + r;
                y2 = cy - r;
                y3 = cy - r;
                break;
            case W:
            default:
                x1 = cx + r;
                x2 = cx + r;
                x3 = cx - r;
                y1 = cy - r;
                y2 = cy + r;
                y3 = cy;
                break;
        }

        g.setColor(color);
        g.drawLine(x1, y1, x2, y2);
        g.drawLine(x1, y1, x3, y3);
        g.drawLine(x3, y3, x2, y2);
    }

    private void innerOnCreate() {

        BoxStyle sty = this.getStyle();
        sty.setBorderStyle(LineStyle.SOLID);
        sty.setBorderColor(Color.black);
        sty.setBorderWidth(1);

        sty.setBackgroundColor(Color.gray);
    }

    private void repaintMyself() {

        CanvasContext cc = this.getCanvasContext();
        if (cc == null) {
            return;
        }

        cc.requestUpdateLayout();
        cc.requestPaint();

    }

}
