package com.bitwormhole.starter4j.swing.canvases;

public class LayoutContext {

    private LayoutContextRoot root;
    private CanvasContext canvasContext;
    private CanvasAdapter adapter;

    private int depth;
    private Box child; // the current child item
    private BoxContainer parent; // the current parent container

    public LayoutContext() {
        this.root = new LayoutContextRoot();
    }

    public LayoutContext(LayoutContext src) {
        if (src == null) {
            return;
        }
        this.root = src.root;
        this.child = src.child;
        this.parent = src.parent;
        this.depth = src.depth;
    }

    public LayoutContextRoot getRoot() {
        return root;
    }

    public void setRoot(LayoutContextRoot root) {
        this.root = root;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public Box getChild() {
        return child;
    }

    public void setChild(Box child) {
        this.child = child;
    }

    public BoxContainer getParent() {
        return parent;
    }

    public void setParent(BoxContainer parent) {
        this.parent = parent;
    }

    public void add(Box b) {
        this.root.add(b);
    }

    public CanvasContext getCanvasContext() {
        return canvasContext;
    }

    public void setCanvasContext(CanvasContext canvasContext) {
        this.canvasContext = canvasContext;
    }

    public CanvasAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(CanvasAdapter adapter) {
        this.adapter = adapter;
    }

}
