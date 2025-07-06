package com.bitwormhole.starter4j.swing.canvases;

import java.awt.Dimension;
import java.awt.Point;

public abstract class Box extends BoxAbs {

    private BoxContainer parent;
    private BoxStyle style;
    private int z; // the z-index
    private int weight;

    private Dimension maxSize;
    private Dimension minSize;
    private Dimension wantSize;
    private Dimension size;

    private Point position; // @parent
    private Point positionAtCanvas;

    public Box() {
        this.style = new BoxStyle();
        this.weight = 1;
    }

    public BoxStyle getStyle() {
        return style;
    }

    public void setStyle(BoxStyle style) {
        this.style = style;
    }

    public BoxContainer getParent() {
        return parent;
    }

    public void setParent(BoxContainer parent) {
        this.parent = parent;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public Dimension getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Dimension maxSize) {
        this.maxSize = maxSize;
    }

    public Dimension getMinSize() {
        return minSize;
    }

    public void setMinSize(Dimension minSize) {
        this.minSize = minSize;
    }

    public Dimension getWantSize() {
        return wantSize;
    }

    public void setWantSize(Dimension wantSize) {
        this.wantSize = wantSize;
    }

    public Dimension getSize() {
        return size;
    }

    public void setSize(Dimension size) {
        this.size = size;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Point getPositionAtCanvas() {
        return positionAtCanvas;
    }

    public void setPositionAtCanvas(Point positionAtCanvas) {
        this.positionAtCanvas = positionAtCanvas;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

}
