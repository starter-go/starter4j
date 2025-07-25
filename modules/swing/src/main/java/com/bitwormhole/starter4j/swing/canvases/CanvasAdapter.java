package com.bitwormhole.starter4j.swing.canvases;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
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

    private static final Logger logger = LoggerFactory.getLogger(CanvasAdapter.class);

    private final Canvas canvas;
    private final MyRevisionTracker mRevisionTracker = new MyRevisionTracker();

    private static class MyRevisionTracker {
        int layoutRevision;
        int paintRevision;
    }

    private class MyCompListener implements ComponentListener {

        @Override
        public void componentResized(ComponentEvent e) {

            Component com = e.getComponent();
            Rectangle rect = com.getBounds();

            rebuildLayout(rect);

            // Dimension size = com.getSize();
            // logger.info("MyCompListener.componentResized( w:" + size.width + ", h:" +
            // size.height + " )");
        }

        @Override
        public void componentMoved(ComponentEvent e) {
            // logger.info("MyCompListener.componentMoved()");
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
            ctx.setAdapter(adapter);
            return ctx;
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            MouseEventContext ctx = this.prepareEvent(MouseEventContext.MouseEvent.WHEEL_MOVED);
            ctx.setLocationAtCanvas(e.getPoint());
            ctx.setWheel(new MouseEventContext.WheelInfo()); // TODO ...
            this.dispatchEvent(ctx);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            MouseEventContext ctx = this.prepareEvent(MouseEventContext.MouseEvent.DRAGGED);
            ctx.setLocationAtCanvas(e.getPoint());

            // dispatch to SCME at first
            ShortCircuitMouseEventDispatcher scme_disp = ctx.getCanvas().getCanvasContext().getScmeDispatcher();
            scme_disp.dispatch(ctx);

            if (ctx.isCancelled()) {
                return;
            }

            this.dispatchEvent(ctx);
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

        if (rect == null) {
            rect = this.getBounds();
        }

        // make revision sync
        int rev = this.canvas.getCanvasContext().getLayoutRevision();
        this.mRevisionTracker.layoutRevision = rev;

        Dimension want_size = rect.getSize();
        LayoutContext lc = new LayoutContext();
        LayoutContextRoot root = lc.getRoot();
        CanvasContext cc = this.canvas.getCanvasContext();

        root.setCanvas(this.canvas);
        lc.setChild(this.canvas);
        lc.setCanvasContext(cc);

        this.canvas.setSize(want_size);
        this.canvas.setWantSize(want_size);
        this.canvas.updateLayout(lc);

        // 最后, 清除缓存的 p@c (position@canvas)
        List<Box> all = root.getBoxes();
        all.forEach((box) -> {
            box.setPositionAtCanvas(null);
        });

        this.repaint();
    }

    private boolean isNeedRebuildLayout() {
        CanvasContext cc = this.canvas.getCanvasContext();
        int r1 = cc.getLayoutRevision();
        int r2 = this.mRevisionTracker.layoutRevision;
        return (r1 != r2);
    }

    ////////////////////////////////////////////////////////////////////////////
    /// protected

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void paint(Graphics g) {

        super.paint(g);

        // 渲染之前,先检查是否需要重排版
        if (this.isNeedRebuildLayout()) {
            this.rebuildLayout(null);
        }

        int rev = this.canvas.getCanvasContext().getPaintRevision();
        this.mRevisionTracker.paintRevision = rev;

        RenderContext rc = new RenderContext();
        rc.setGraphics(g);
        rc.setCanvas(this.canvas);
        this.canvas.render(rc);
    }

}
