package com.bitwormhole.starter4j.swing.layouts;

import java.awt.Dimension;
import java.awt.Point;
import java.util.List;

import com.bitwormhole.starter4j.swing.canvases.Box;
import com.bitwormhole.starter4j.swing.canvases.BoxContainer;
import com.bitwormhole.starter4j.swing.canvases.ILayout;
import com.bitwormhole.starter4j.swing.canvases.LayoutContext;

/***
 * SimpleLayout 这个布局方式会把 Container 中的所有子 Box 统统设置为跟 parent 一样大小, 并填充满整个
 * Container. 所有的子 Box 都重叠在一起.
 */
public class SimpleLayout implements ILayout {

    @Override
    public void updateLayout(LayoutContext lc, BoxContainer parent) {

        // AbstractLayout.buildLayoutForChildren(lc, parent);

        this.innerPrepareContainerSize(lc, parent);
        this.innerComputeChildrenSize(lc, parent);

        parent.updateLayoutForChildren(lc);

    }

    private void innerPrepareContainerSize(LayoutContext lc, BoxContainer parent) {
        Dimension size = parent.getSize();
        if (size == null) {
            size = parent.getWantSize();
            if (size == null) {
                size = new Dimension();
            }
        }
        parent.setSize(size);
    }

    private void innerComputeChildrenSize(LayoutContext lc, BoxContainer parent) {
        final List<Box> children = parent.getChildren();
        if (children == null) {
            return;
        }
        Dimension size1 = parent.getSize();
        children.forEach((child) -> {
            child.setSize(new Dimension(size1));
            child.setPosition(new Point());
        });
    }

}
