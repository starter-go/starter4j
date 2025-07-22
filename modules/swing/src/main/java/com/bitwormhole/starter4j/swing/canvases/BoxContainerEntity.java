package com.bitwormhole.starter4j.swing.canvases;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

/****
 * BoxContainerEntity 是 BoxContainer 的实体类，它实现了 BoxContainer 中的所有抽象方法
 */

public class BoxContainerEntity extends BoxContainer {

    @Override
    protected void onPaint(RenderContext rc) {

        // super.onPaint(rc);

        this.onPaintBackground(rc);
        this.onPaintChildren(rc);
        this.onPaintForeground(rc);
    }

    @Override
    protected void onPaintChildren(RenderContext rc) {

        this.checkDepth(rc);

        final List<Box> children = this.getChildren();
        final int depth1 = rc.getDepth();
        final int depth2 = depth1 + 1;
        final Graphics g1 = rc.getGraphics();

        // 执行绘图时: 按 z 从小到大的顺序执行
        children.forEach((item) -> {
            Graphics g2 = g1.create();
            rc.setGraphics(g2);
            rc.setDepth(depth2);
            item.render(rc);
        });
    }

    @Override
    protected void onMouseEvent(MouseEventContext mec) {

        if (mec.isCancelled()) {
            return;
        }

        super.onMouseEvent(mec);

        final List<Box> children = this.getChildren();
        final int depth1 = mec.getDepth();
        final int depth2 = depth1 + 1;
        final int limit = mec.getDepthLimit();

        if (depth2 > limit) {
            throw new RuntimeException("too deep");
        }

        // 响应鼠标事件时: 按 z 从大到小的顺序执行
        for (int i = children.size() - 1; i >= 0; i--) {
            final Box child = children.get(i);
            mec.setDepth(depth2);
            if (mec.isCancelled()) {
                break;
            } else {
                child.handleMouseEvent(mec);
            }
        }
    }

    @Override
    public final void updateLayout(LayoutContext lc) {
        super.updateLayout(lc);
    }

    @Override
    public void updateLayoutForChildren(LayoutContext lc) {

        if (lc == null) {
            return;
        }

        BoxContainer parent = this;
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
            child.updateLayout(lc2);
        });

    }

    ////////////////////////////////////////////////////////////////////////////
    /// protected

    @Override
    protected void onUpdateLayout(LayoutContext lc) {
        super.onUpdateLayout(lc);

        this.innerSortChildrenByZ();

        final ILayout l = this.getLayoutSafe();
        l.updateLayout(lc, this);
    }

    ////////////////////////////////////////////////////////////////////////////
    /// private

    private void innerSortChildrenByZ() {

        List<Box> all = this.getChildren();
        if (all == null) {
            return;
        }

        // 首先检查是否需要排序: 如果所有的 z==0, 则不需要排序
        boolean need_sort = false;
        for (Box box : all) {
            int z = box.getZ();
            if (z != 0) {
                need_sort = true;
                break;
            }
        }
        if (!need_sort) {
            return;
        }

        // 按 z 从小到大排序
        all.sort((child1, child2) -> {
            if (child1 == null || child2 == null) {
                return 0;
            }
            int z1 = child1.getZ();
            int z2 = child2.getZ();
            return (z1 - z2);
        });

        all.hashCode(); // 调试点
    }

    private final ILayout getLayoutSafe() {
        final ILayout l1 = this.getLayout();
        final ILayout l2 = Getters.notNull(l1);
        if (l1 == null) {
            this.setLayout(l2);
        }
        return l2;
    }

    // private void checkDepth(LayoutContext lc) {

    // final int limit = lc.getDepthLimit();
    // final int depth = lc.getDepth();

    // if (depth > limit) {
    // StringBuilder sb = new StringBuilder();
    // sb.append("the layout call-stack is too deep");
    // sb.append(", depth=").append(depth);
    // sb.append(", limit=").append(limit);
    // throw new RuntimeException(sb.toString());
    // }

    // lc.setDepth(depth);
    // }

    private void checkDepth(RenderContext rc) {

        final int limit = rc.getDepthLimit();
        final int depth = rc.getDepth();

        if (depth > limit) {
            StringBuilder sb = new StringBuilder();
            sb.append("the render call-stack is too deep");
            sb.append(", depth=").append(depth);
            sb.append(", limit=").append(limit);
            throw new RuntimeException(sb.toString());
        }

        rc.setDepth(depth);
    }

}
