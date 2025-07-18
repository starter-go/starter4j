package com.bitwormhole.starter4j.swing.canvases;

public class Canvas extends BoxContainerEntity {

    private Box hover; // 指向当前鼠标指针所悬停的 box

    public Box getHover() {
        return hover;
    }

    public void setHover(Box hover) {
        this.hover = hover;
    }

}
