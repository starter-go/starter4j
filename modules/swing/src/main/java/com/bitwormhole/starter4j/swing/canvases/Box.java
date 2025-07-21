package com.bitwormhole.starter4j.swing.canvases;

import java.awt.Dimension;
import java.awt.Point;

public abstract class Box extends BoxAbs {

    private BoxContainer parent;
    private CanvasContext canvasContext;

    private BoxStyle style;
    private int z; // the z-index
    private int weight;
    private boolean presence; // 由 Layout 计算决定: 是否出现在 Paint & MouseEvent 队列中

    /**
     * style 中的 visibility 具有更高优先级, 如果 style 没有提供, 则使用这里的值
     */
    private VisibilityEnum visibility;

    private Dimension maxSize; // box 大小的极限 (最大值)
    private Dimension minSize; // box 大小的极限 (最小值)
    private Dimension wantSize; // 推荐给 Layout 的大小
    private Dimension contentSize; // 用来保存 Layout 计算所得的内容大小
    private Dimension size; // box 实际的大小

    private Point position; // @parent: box 实际的位置
    private Point positionAtCanvas; // @canvas: box 相对于画布坐标系的位置

    public Box() {
        this.style = new BoxStyle();
        this.weight = 1;
        this.presence = true; // 默认是存在的
    }

    public Point convertCanvasToLocal(Point at_canvas) {
        final Point pos = this.getPositionAtCanvas();
        if (pos == null || at_canvas == null) {
            return new Point();
        }
        int x, y;
        x = at_canvas.x - pos.x;
        y = at_canvas.y - pos.y;
        return new Point(x, y);
    }

    public Point convertLocalToCanvas(Point at_local) {
        final Point pos = this.getPositionAtCanvas();
        if (pos == null || at_local == null) {
            return new Point();
        }
        int x, y;
        x = at_local.x + pos.x;
        y = at_local.y + pos.y;
        return new Point(x, y);
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

    public VisibilityEnum getVisibility() {

        VisibilityEnum v = null;
        BoxStyle st = this.style;

        if (st != null) {
            v = st.getVisibility();
        }

        if (v == null) {
            v = this.visibility;
        }

        if (v == null) {
            v = VisibilityEnum.VISIBLE;
        }

        return v;
    }

    public void setVisibility(VisibilityEnum visibility) {
        this.visibility = visibility;
    }

    public Dimension getContentSize() {
        return contentSize;
    }

    public void setContentSize(Dimension contentSize) {
        this.contentSize = contentSize;
    }

    public CanvasContext getCanvasContext() {
        return canvasContext;
    }

    public void setCanvasContext(CanvasContext canvasContext) {
        this.canvasContext = canvasContext;
    }

    public boolean isPresence() {
        return presence;
    }

    public void setPresence(boolean presence) {
        this.presence = presence;
    }

}
