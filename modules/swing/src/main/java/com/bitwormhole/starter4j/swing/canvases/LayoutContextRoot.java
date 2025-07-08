package com.bitwormhole.starter4j.swing.canvases;

import java.util.ArrayList;
import java.util.List;

public class LayoutContextRoot {

    private Canvas canvas;
    private int depthLimit;
    private final List<Box> boxes;

    public LayoutContextRoot() {
        this.depthLimit = 32;
        this.boxes = new ArrayList<>();
    }

    public void add(Box b) {
        if (b == null) {
            return;
        }
        this.boxes.add(b);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public int getDepthLimit() {
        return depthLimit;
    }

    public void setDepthLimit(int depthLimit) {
        this.depthLimit = depthLimit;
    }

    public List<Box> getBoxes() {
        return boxes;
    }

}
