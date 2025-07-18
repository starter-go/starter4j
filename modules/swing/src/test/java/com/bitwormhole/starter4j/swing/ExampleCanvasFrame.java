package com.bitwormhole.starter4j.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bitwormhole.starter4j.swing.canvases.Box;
import com.bitwormhole.starter4j.swing.canvases.BoxStyle;
import com.bitwormhole.starter4j.swing.canvases.Canvas;
import com.bitwormhole.starter4j.swing.canvases.CanvasAdapter;
import com.bitwormhole.starter4j.swing.canvases.LineStyle;
import com.bitwormhole.starter4j.swing.canvases.RenderContext;
import com.bitwormhole.starter4j.swing.layouts.LinearLayout;
import com.bitwormhole.starter4j.swing.boxes.CButton;
import com.bitwormhole.starter4j.swing.boxes.CGroup;
import com.bitwormhole.starter4j.swing.boxes.CLabel;

public class ExampleCanvasFrame extends FrameWithLife {

    static final Logger logger = LoggerFactory.getLogger(ExampleCanvasFrame.class);

    private CanvasAdapter adapter;

    protected void onCreate() {

        super.onCreate();

        CanvasAdapter ada = createCanvasAdapter();
        this.adapter = ada;
        this.setLayout(new BorderLayout());
        this.add(this.adapter, BorderLayout.CENTER);
    }

    private CanvasAdapter createCanvasAdapter() {

        CanvasAdapter ada = new CanvasAdapter();
        Canvas canvas = ada.getCanvas();

        CGroup vg = new CGroup();
        CGroup hg = new CGroup();
        CButton btn1 = new CButton("button-1");
        CButton btn2 = new CButton("button-2");
        CButton btn3 = new CButton("button-3");
        CButton btn4 = new CButton("哈哈哈-looooong");
        Box boxItemsH = this.makeItemsBox(61, LinearLayout.HORIZONTAL);
        Box boxItemsV = this.makeItemsBox(61, LinearLayout.VERTICAL);

        btn1.setZ(0);
        btn2.setZ(0);
        btn3.setZ(0);
        btn4.setZ(0);

        btn1.setWeight(2);
        btn2.setWeight(2);
        btn3.setWeight(2);
        btn4.setWeight(2);
        boxItemsH.setWeight(9);
        boxItemsV.setWeight(2);

        Box pbox = btn3;
        BoxStyle style = pbox.getStyle();
        style = makeDemoStyle(style);
        pbox.setStyle(style);

        // btn2.setVisibility(VisibilityEnum.GONE);

        vg.setLayout(new LinearLayout(LinearLayout.VERTICAL));
        hg.setLayout(new LinearLayout(LinearLayout.HORIZONTAL));

        hg.add(btn1);
        hg.add(btn3);
        hg.add(btn2);
        hg.add(boxItemsH);
        hg.add(boxItemsV);
        hg.add(btn4);

        vg.add(hg);
        canvas.add(vg);

        return ada;
    }

    private static BoxStyle makeDemoStyle(BoxStyle style) {

        if (style == null) {
            style = new BoxStyle();
        }

        style.setFont(new Font("hahaha", Font.PLAIN, 36));
        style.setBackgroundColor(Color.green);
        style.setForegroundColor(Color.white);

        style.setBorderColor(Color.yellow);
        style.setBorderStyle(LineStyle.SOLID);
        style.setBorderWidth(30);

        style.setBorderTopColor(Color.red);
        style.setBorderTopStyle(LineStyle.SOLID);
        style.setBorderTopWidth(10);

        // style.setBorderLeftColor(Color.red);
        // style.setBorderLeftStyle(LineStyle.SOLID);
        // style.setBorderLeftWidth(10);

        // style.setBorderRightColor(Color.blue);
        // style.setBorderRightStyle(LineStyle.SOLID);
        // style.setBorderRightWidth(10);

        // style.setBorderBottomColor(Color.blue);
        // style.setBorderBottomStyle(LineStyle.SOLID);
        // style.setBorderBottomWidth(10);

        return style;
    }

    private CGroup makeItemsBox(int count, LinearLayout.Direction dir) {

        BoxStyle g_style = new BoxStyle();
        BoxStyle i_style = new BoxStyle();

        i_style.setBorderColor(Color.gray);
        i_style.setBorderStyle(LineStyle.SOLID);
        i_style.setBorderWidth(1);

        i_style.setBorderTopColor(Color.blue);
        i_style.setBorderTopWidth(10);
        i_style.setBorderBottomColor(Color.red);
        i_style.setBorderBottomWidth(10);

        CGroup group = new CGroup();
        CGroup item = null;
        CLabel label = null;

        for (int i = 0; i < count; i++) {
            item = new MyItemView();
            label = new CLabel();

            item.setZ(30 - i);
            item.setStyle(i_style);
            label.setText("" + i);

            item.add(label);
            group.add(item);
        }
        group.setStyle(g_style);
        group.setLayout(new LinearLayout(dir));
        return group;
    }

    public static ExampleCanvasFrame create(Goal goal) {
        ExampleCanvasFrame inst = new ExampleCanvasFrame();
        inst.setSize(640, 480);
        // inst.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inst.setTitle("" + inst.getClass().getName());
        // inst.onCreate();
        return inst;
    }

    static class MyItemView extends CGroup {

        @Override
        protected void onPaintForeground(RenderContext rc) {
            super.onPaintForeground(rc);

            int z, x, y, w, h;
            Dimension s = this.getSize();
            Point p = this.getPosition();
            z = this.getZ();
            x = p.x;
            y = p.y;
            w = s.width;
            h = s.height;

            if (z == 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("MyItemView.[onPaintForeground");
                sb.append(" h:").append(h);
                sb.append(" w:").append(w);
                sb.append(" x:").append(x);
                sb.append(" y:").append(y);
                sb.append(" z:").append(z);
                sb.append(']');
                logger.info(sb.toString());
            }
        }
    }

    // private final static class MyFactory implements FrameFactory {
    // @Override
    // public JFrame createFrame(Goal goal) {
    // return create(goal);
    // }
    // }

    public static FrameRegistration registration() {
        FrameRegistration fr = new FrameRegistration();
        fr.setFactory((goal) -> create(goal));
        fr.setName(ExampleCanvasFrame.class.getName());
        fr.setType(ExampleCanvasFrame.class);
        fr.setSingleton(false);
        return fr;
    }

}
