package com.bitwormhole.starter4j.swing.canvases;

import java.awt.Point;

public class MouseEventContext {

    private Canvas canvas;
    private CanvasAdapter adapter;

    private int depth;
    private int depthLimit;
    private boolean cancelled;
    private MouseEvent event;

    private Point location; // 相对于本地坐标系的位置
    private Point locationAtCanvas; // 相对于 canvas 坐标系的位置

    private WheelInfo wheel; // optional

    public MouseEventContext() {
        this.depthLimit = 32;
    }

    public enum MouseEvent {
        UNKNOWN, PRESSED, RELEASED, MOVED, OVER, ENTER, LEAVE, CLICKED, DRAGGED, WHEEL_MOVED,
    }

    public static class WheelInfo {

        // todo ...
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

    public WheelInfo getWheel() {
        return wheel;
    }

    public void setWheel(WheelInfo wheel) {
        this.wheel = wheel;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public CanvasAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(CanvasAdapter adapter) {
        this.adapter = adapter;
    }

}
