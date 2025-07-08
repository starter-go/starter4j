package com.bitwormhole.starter4j.swing.canvases;

import java.util.ArrayList;
import java.util.List;

import com.bitwormhole.starter4j.swing.layouts.SimpleLayout;

public abstract class BoxContainer extends BoxContainerAbs {

    private final List<Box> mChildren;

    private ILayout layout;

    public BoxContainer() {
        this.mChildren = new ArrayList<>();
        this.layout = new SimpleLayout();
    }

    @Override
    public BoxContainer add(Box child) {
        if (child != null) {
            if (!mChildren.contains(child)) {
                this.mChildren.add(child);
            }
        }
        return this;
    }

    public ILayout getLayout() {
        return layout;
    }

    public void setLayout(ILayout layout) {
        this.layout = layout;
    }

    public List<Box> getChildren() {
        return mChildren;
    }

    public List<Box> getChildren(List<Box> dst) {
        if (dst == null) {
            dst = new ArrayList<>();
        }
        dst.addAll(mChildren);
        return dst;
    }

}
