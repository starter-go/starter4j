package com.bitwormhole.starter4j.swing.canvases;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.List;

import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CanvasAdapter extends JPanel {

    private static final Logger logger = LoggerFactory.getLogger(CanvasAdapter.class);

    private final Canvas canvas;

    public CanvasAdapter() {
        this.canvas = new Canvas();
        this.onCreate();
    }

    @Override
    public void paintAll(Graphics g) {
        super.paintAll(g);
        logger.info(this + ".paintAll()");
    }

    public Canvas getCanvas() {
        return this.canvas;
    }

    ////////////////////////////////////////////////////////////////////////////
    /// private

    private class MyCompListener implements ComponentListener {

        @Override
        public void componentResized(ComponentEvent e) {

            Component com = e.getComponent();
            Rectangle rect = com.getBounds();

            rebuildLayout(rect);

            Dimension size = com.getSize();
            logger.info("MyCompListener.componentResized( w:" + size.width + ", h:" + size.height + " )");
        }

        @Override
        public void componentMoved(ComponentEvent e) {
            logger.info("MyCompListener.componentMoved()");
        }

        @Override
        public void componentShown(ComponentEvent e) {
            logger.info("MyCompListener.componentShown()");
        }

        @Override
        public void componentHidden(ComponentEvent e) {
            logger.info("MyCompListener.componentHidden()");
        }
    }

    private class MySwingMouseListener implements MouseListener, MouseMotionListener, MouseWheelListener {

        void dispatchEvent(MouseEventContext ctx) {
            CanvasAdapter adapter = CanvasAdapter.this;
            adapter.canvas.handleMouseEvent(ctx);
        }

        MouseEventContext prepareEvent(MouseEventContext.MouseEvent me) {
            final CanvasAdapter adapter = CanvasAdapter.this;
            final MouseEventContext ctx = new MouseEventContext();
            ctx.setCanvas(adapter.canvas);
            ctx.setEvent(me);
            return ctx;
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseDragged(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            MouseEventContext ctx = this.prepareEvent(MouseEventContext.MouseEvent.MOVED);
            ctx.setLocationAtCanvas(e.getPoint());
            this.dispatchEvent(ctx);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            MouseEventContext ctx = this.prepareEvent(MouseEventContext.MouseEvent.CLICKED);
            ctx.setLocationAtCanvas(e.getPoint());
            this.dispatchEvent(ctx);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            MouseEventContext ctx = this.prepareEvent(MouseEventContext.MouseEvent.PRESSED);
            ctx.setLocationAtCanvas(e.getPoint());
            this.dispatchEvent(ctx);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            MouseEventContext ctx = this.prepareEvent(MouseEventContext.MouseEvent.RELEASED);
            ctx.setLocationAtCanvas(e.getPoint());
            this.dispatchEvent(ctx);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            MouseEventContext ctx = this.prepareEvent(MouseEventContext.MouseEvent.ENTER);
            ctx.setLocationAtCanvas(e.getPoint());
            this.dispatchEvent(ctx);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            MouseEventContext ctx = this.prepareEvent(MouseEventContext.MouseEvent.LEAVE);
            ctx.setLocationAtCanvas(e.getPoint());
            this.dispatchEvent(ctx);
        }
    }

    private void onCreate() {

        final MySwingMouseListener sml = new MySwingMouseListener();
        this.addMouseListener(sml);
        this.addMouseMotionListener(sml);
        this.addMouseWheelListener(sml);

        this.addComponentListener(new MyCompListener());
    }

    private void rebuildLayout(Rectangle rect) {

        Dimension want_size = rect.getSize();
        LayoutContext lc = new LayoutContext();
        LayoutContextRoot root = lc.getRoot();

        root.setCanvas(this.canvas);
        lc.setChild(this.canvas);

        this.canvas.setSize(want_size);
        this.canvas.setWantSize(want_size);
        this.canvas.rebuildLayout(lc);

        // 最后, 计算所有 box 的绝对 position (pos@canvas)
        List<Box> all = root.getBoxes();
        all.forEach((box) -> {
            box.setPositionAtCanvas(computePositionAtCanvas(box, 32));
            // logger.info("todo: compute box.pos@canvas [" + box + "]");
        });

        this.repaint();
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

    ////////////////////////////////////////////////////////////////////////////
    /// protected

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dimension size = this.getSize();
        // int l, r, t, b, padding;
        // padding = 10;
        // t = 0 + padding;
        // l = 0 + padding;
        // r = size.width - padding;
        // b = size.height - padding;
        // g.setColor(Color.red);
        // g.drawRect(l, t, r - l, b - t);
        // g.fillRect(10, 10, 100, 100);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        RenderContext rc = new RenderContext();
        rc.setGraphics(g);
        rc.setCanvas(this.canvas);
        this.canvas.render(rc);

    }

}
