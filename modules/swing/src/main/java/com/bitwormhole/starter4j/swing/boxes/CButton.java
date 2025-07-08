package com.bitwormhole.starter4j.swing.boxes;

import java.awt.Point;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bitwormhole.starter4j.swing.canvases.MouseEventContext;

public class CButton extends CLabel {

    static final Logger logger = LoggerFactory.getLogger(CButton.class);

    public CButton() {
    }

    public CButton(String label) {
        super(label);
    }

    @Override
    protected void onMouseEvent(MouseEventContext mec) {
        super.onMouseEvent(mec);

        MouseEventContext.MouseEvent me = mec.getEvent();
        if (MouseEventContext.MouseEvent.CLICKED == me) {

            Point pt = mec.getLocationAtCanvas();
            pt = this.convertCanvasToLocal(pt);
            logger.info("on_mouse_clicked, X:" + pt.x + ", Y:" + pt.y);

        }

    }

}
