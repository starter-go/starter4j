package com.bitwormhole.starter4j.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bitwormhole.starter4j.application.ApplicationContext;
import com.bitwormhole.starter4j.swing.boxes.CLabel;
import com.bitwormhole.starter4j.swing.canvases.AlignEnum;
import com.bitwormhole.starter4j.swing.canvases.BoxStyle;
import com.bitwormhole.starter4j.swing.canvases.Canvas;
import com.bitwormhole.starter4j.swing.canvases.CanvasAdapter;
import com.bitwormhole.starter4j.swing.canvases.LineStyle;
import com.bitwormhole.starter4j.swing.layouts.CGridLayout;

public final class Example9LabelsFrame extends FrameWithLife {

    private static final Logger logger = LoggerFactory.getLogger(Example9LabelsFrame.class);

    ////////////////////////////////////////////////////////////////////////////
    /// public

    public static Example9LabelsFrame create(Goal goal) {
        ApplicationContext ac = goal.getContext();
        Example9LabelsFrame inst = new Example9LabelsFrame(ac);
        return inst;
    }

    public static FrameRegistration registration() {
        FrameRegistration fr = new FrameRegistration();
        fr.setFactory((goal) -> create(goal));
        fr.setName(Example9LabelsFrame.class.getName());
        fr.setType(Example9LabelsFrame.class);
        fr.setSingleton(false);
        return fr;
    }

    ////////////////////////////////////////////////////////////////////////////
    /// protected

    protected void onCreate() {
        super.onCreate();

        logger.info(this + "");

        this.setSize(640, 480);
        this.setTitle("" + this.getClass().getName());
        this.setLayout(new BorderLayout());

        CanvasAdapter ada = new CanvasAdapter();
        this.add(ada, BorderLayout.CENTER);
        Canvas canvas = ada.getCanvas();
        canvas.setLayout(new CGridLayout(3, 3));

        for (int i = 0; i < 9; i++) {
            CLabel label = new CLabel();
            this.setupLabelStyle(i, label);
            AlignEnum align = label.getStyle().getTextAlign();
            label.setText("label-" + i + ":" + align);
            canvas.add(label);
        }

    }

    ////////////////////////////////////////////////////////////////////////////
    /// private

    private final ApplicationContext mAC;

    private Example9LabelsFrame(ApplicationContext ac) {
        this.mAC = ac;
    }

    private AlignEnum getAlignByIndex(int index) {
        AlignEnum align = AlignEnum.CENTER;
        switch (index) {
            case 0:
                align = AlignEnum.TOP_LEFT;
                break;
            case 1:
                align = AlignEnum.TOP;
                break;
            case 2:
                align = AlignEnum.TOP_RIGHT;
                break;
            case 3:
                align = AlignEnum.LEFT;
                break;
            case 4:
                align = AlignEnum.CENTER;
                break;
            case 5:
                align = AlignEnum.RIGHT;
                break;
            case 6:
                align = AlignEnum.BOTTOM_LEFT;
                break;
            case 7:
                align = AlignEnum.BOTTOM;
                break;
            case 8:
                align = AlignEnum.BOTTOM_RIGHT;
                break;
            default:
                break;
        }
        return align;
    }

    private void setupLabelStyle(int index, CLabel label) {

        BoxStyle style = label.getStyle();

        style.setBorderColor(Color.blue);
        style.setBorderStyle(LineStyle.SOLID);
        style.setBorderWidth(1);
        style.setFont(new Font("text-9-labels", Font.PLAIN, 20));
        style.setForegroundColor(Color.RED);
        style.setPadding(5);
        style.setTextAlign(this.getAlignByIndex(index));

        label.setStyle(style);
    }

}
