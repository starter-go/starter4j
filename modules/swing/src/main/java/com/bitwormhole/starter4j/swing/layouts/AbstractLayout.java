package com.bitwormhole.starter4j.swing.layouts;

import java.util.List;

import com.bitwormhole.starter4j.swing.canvases.Box;
import com.bitwormhole.starter4j.swing.canvases.BoxContainer;
import com.bitwormhole.starter4j.swing.canvases.ILayout;
import com.bitwormhole.starter4j.swing.canvases.LayoutContext;

public abstract class AbstractLayout implements ILayout {

    public static void buildLayoutForChildren(LayoutContext lc, BoxContainer parent) {

        if (lc == null || parent == null) {
            return;
        }

        List<Box> children = parent.getChildren();
        final int depth1 = lc.getDepth();
        final int depth2 = depth1 + 1;
        final int limit = lc.getRoot().getDepthLimit();

        if (depth2 > limit) {
            throw new RuntimeException("too deep");
        }

        children.forEach((child) -> {
            final LayoutContext lc2 = new LayoutContext(lc);
            lc2.setDepth(depth2);
            lc2.setChild(child);
            lc2.setParent(parent);
            child.rebuildLayout(lc2);
        });
    }

}
