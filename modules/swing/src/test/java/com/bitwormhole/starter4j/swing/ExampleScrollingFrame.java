package com.bitwormhole.starter4j.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bitwormhole.starter4j.application.ApplicationContext;
import com.bitwormhole.starter4j.swing.boxes.CScrollView;
import com.bitwormhole.starter4j.swing.canvases.BoxContainerEntity;
import com.bitwormhole.starter4j.swing.canvases.BoxStyle;
import com.bitwormhole.starter4j.swing.canvases.Canvas;
import com.bitwormhole.starter4j.swing.canvases.CanvasAdapter;
import com.bitwormhole.starter4j.swing.canvases.LineStyle;
import com.bitwormhole.starter4j.swing.canvases.RenderContext;
import com.bitwormhole.starter4j.swing.layouts.CGridLayout;

public class ExampleScrollingFrame extends FrameWithLife {

    private static final Logger logger = LoggerFactory.getLogger(ExampleScrollingFrame.class);

    public static ExampleScrollingFrame create(Goal goal) {
        ApplicationContext ac = goal.getContext();
        ExampleScrollingFrame inst = new ExampleScrollingFrame(ac);
        inst.setSize(640, 480);
        inst.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        inst.setTitle("" + inst.getClass().getName());
        return inst;
    }

    public static FrameRegistration registration() {
        FrameRegistration fr = new FrameRegistration();
        fr.setFactory((goal) -> create(goal));
        fr.setName(ExampleScrollingFrame.class.getName());
        fr.setType(ExampleScrollingFrame.class);
        fr.setSingleton(false);
        return fr;
    }

    ///////////////////////////////////////////////////////////////////////////
    /// private

    private ExampleScrollingFrame(ApplicationContext ac) {
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        CanvasAdapter ada = new CanvasAdapter();
        Canvas canvas = ada.getCanvas();

        CScrollView scroll_view = new CScrollView();
        MyInnerContent content = new MyInnerContent();

        canvas.setLayout(new CGridLayout(1, 1));
        canvas.add(scroll_view);

        scroll_view.setContent(content);

        this.add(ada);
    }

    private class MyInnerContent extends BoxContainerEntity {

        MyInnerContent() {
            this.onCreate();
        }

        private void onCreate() {

            BoxStyle sty = this.getStyle();
            sty.setBorderColor(Color.RED);
            sty.setBorderStyle(LineStyle.SOLID);
            sty.setBorderWidth(3);

            this.setStyle(sty);
            this.setSize(new Dimension(1024, 768));

        }

        @Override
        protected void onPaintBackground(RenderContext rc) {
            super.onPaintBackground(rc);

            Dimension my_size = this.getSize();
            int w = my_size.width;
            int h = my_size.height;

            Graphics g = rc.getGraphics();
            BoxStyle sty = this.getStyle();

            g.setColor(sty.getForegroundColor());
            g.drawLine(0, 0, w, h);
            g.drawLine(0, h, w, 0);
        }

    }

}
