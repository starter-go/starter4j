package com.bitwormhole.starter4j.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;

import com.bitwormhole.starter4j.swing.canvases.Box;
import com.bitwormhole.starter4j.swing.canvases.BoxStyle;
import com.bitwormhole.starter4j.swing.canvases.Canvas;
import com.bitwormhole.starter4j.swing.canvases.CanvasAdapter;
import com.bitwormhole.starter4j.swing.canvases.LineStyle;
import com.bitwormhole.starter4j.swing.layouts.LinearLayout;
import com.bitwormhole.starter4j.swing.boxes.CButton;
import com.bitwormhole.starter4j.swing.boxes.CGroup;

public class ExampleCanvasFrame extends JFrame {

    private CanvasAdapter adapter;

    private void onCreate() {
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

        btn1.setWeight(10);
        btn2.setWeight(10);
        btn3.setWeight(20);
        btn4.setWeight(20);

        Box pbox = btn3;
        BoxStyle style = pbox.getStyle();
        style = makeDemoStyle(style);
        pbox.setStyle(style);

        vg.setLayout(new LinearLayout(LinearLayout.VERTICAL));
        hg.setLayout(new LinearLayout(LinearLayout.HORIZONTAL));

        hg.add(btn1);
        hg.add(btn2);
        hg.add(btn3);
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

        style.setBorderTopColor(Color.red);
        style.setBorderTopStyle(LineStyle.SOLID);
        style.setBorderTopWidth(10);

        style.setBorderLeftColor(Color.red);
        style.setBorderLeftStyle(LineStyle.SOLID);
        style.setBorderLeftWidth(10);

        style.setBorderRightColor(Color.blue);
        style.setBorderRightStyle(LineStyle.SOLID);
        style.setBorderRightWidth(10);

        style.setBorderBottomColor(Color.blue);
        style.setBorderBottomStyle(LineStyle.SOLID);
        style.setBorderBottomWidth(10);

        return style;
    }

    public static ExampleCanvasFrame create(Goal goal) {
        ExampleCanvasFrame inst = new ExampleCanvasFrame();
        inst.setSize(640, 480);
        // inst.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inst.setTitle("" + inst.getClass().getName());
        inst.onCreate();
        return inst;
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
