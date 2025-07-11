package com.bitwormhole.starter4j.swing.canvases;

import java.awt.Point;

public class MouseEventContext {

    private Canvas canvas;
    private int depth;
    private int depthLimit;

    private MouseEvent event;
    private Point locationAtCanvas;
    private boolean cancelled;

    public MouseEventContext() {
        this.depthLimit = 32;
    }

    public enum MouseEvent {
        UNKNOWN, PRESSED, RELEASED, MOVED, OVER, ENTER, LEAVE, CLICKED,
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public Point getLocationAtCanvas() {
        return locationAtCanvas;
    }

    public void setLocationAtCanvas(Point locationAtCanvas) {
        this.locationAtCanvas = locationAtCanvas;
    }

    public MouseEvent getEvent() {
        return event;
    }

    public void setEvent(MouseEvent event) {
        this.event = event;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getDepthLimit() {
        return depthLimit;
    }

    public void setDepthLimit(int depthLimit) {
        this.depthLimit = depthLimit;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

}
