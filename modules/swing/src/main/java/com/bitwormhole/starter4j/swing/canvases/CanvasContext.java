package com.bitwormhole.starter4j.swing.canvases;

import java.awt.Graphics;

public class CanvasContext {

    private Graphics graphics; // optional 当前的图形渲染上下文
    private Canvas canvas; // 与这个上下文绑定的画布
    private int layoutRevision; // 用于检查是否需要重排版
    private int paintRevision; // 用于检查是否需要重绘

    public CanvasContext() {
    }

    public void requestUpdateLayout() {
        this.layoutRevision++;
    }

    public void requestPaint() {
        this.paintRevision++;
    }

    public int getLayoutRevision() {
        return layoutRevision;
    }

    public void setLayoutRevision(int layoutRevision) {
        this.layoutRevision = layoutRevision;
    }

    public int getPaintRevision() {
        return paintRevision;
    }

    public void setPaintRevision(int paintRevision) {
        this.paintRevision = paintRevision;
    }

    public Graphics getGraphics() {
        return graphics;
    }

    public void setGraphics(Graphics graphics) {
        this.graphics = graphics;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

}
