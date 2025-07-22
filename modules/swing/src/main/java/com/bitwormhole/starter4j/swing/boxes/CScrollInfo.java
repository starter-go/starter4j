package com.bitwormhole.starter4j.swing.boxes;

public class CScrollInfo {

    private int min;
    private int max;
    private int position;
    private int viewportSize;

    public CScrollInfo() {
    }

    public int getViewportSize() {
        return viewportSize;
    }

    public void setViewportSize(int viewportSize) {
        this.viewportSize = viewportSize;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}
