package com.bitwormhole.starter4j.swing.canvases;

import com.bitwormhole.starter4j.swing.layouts.SimpleLayout;

public class Canvas extends BoxContainerEntity {

    private Box hover; // 指向当前鼠标指针所悬停的 box

    public Canvas() {

        CanvasContext cc = new CanvasContext();
        cc.setCanvas(this);

        this.setLayout(new SimpleLayout());
        this.setCanvasContext(cc);
    }

    public Box getHover() {
        return hover;
    }

    public void setHover(Box hover) {
        this.hover = hover;
    }

}
