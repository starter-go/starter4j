package com.bitwormhole.starter4j.swing.canvases;

import java.awt.Point;

/***
 * BoxAbs 是 Box 类的抽象父类，它包含了一些 Box 需要实现的抽象方法
 */
public abstract class BoxAbs implements IRenderable, ILayoutable, IMouseEventListener {

    // paint

    protected abstract void onPaint(RenderContext rc);

    protected abstract void onPaintBackground(RenderContext rc);

    protected abstract void onPaintForeground(RenderContext rc);

    // layout

    protected abstract void onUpdateLayout(LayoutContext lc);

    protected abstract Point computeMyPositionAtCanvas();

    // mouse-event
    protected abstract void onMouseEvent(MouseEventContext mec);

}
