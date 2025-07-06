package com.bitwormhole.starter4j.swing.canvases;

import java.util.List;

/****
 * BoxContainerEntity 是 BoxContainer 的实体类，它实现了 BoxContainer 中的所有抽象方法
 */

public class BoxContainerEntity extends BoxContainer {

    @Override
    protected void onPaintChildren(RenderContext rc) {

        final List<Box> children = this.getChildren();
        final int depth1 = rc.getDepth();
        final int depth2 = depth1 + 1;

        children.forEach((item) -> {
            rc.setDepth(depth2);
            item.render(rc);
        });
    }

    @Override
    protected void onBuildLayoutPost(LayoutContext lc) {
        super.onBuildLayoutPost(lc);
        final ILayout l = this.getLayoutSafe();
        l.onBuildLayoutPost(lc, this);
    }

    @Override
    protected void onBuildLayoutPre(LayoutContext lc) {

        this.innerSortChildrenByZ();
        super.onBuildLayoutPre(lc);

        final ILayout l = this.getLayoutSafe();
        l.onBuildLayoutPre(lc, this);
    }

    @Override
    protected void onBuildLayoutSelf(LayoutContext lc) {
        super.onBuildLayoutSelf(lc);
        final ILayout l = this.getLayoutSafe();
        l.onBuildLayoutContainer(lc, this);
    }

    // @Override
    // protected void onBuildLayoutChildren(final LayoutContext lc1) {
    // // super.onBuildLayoutChildren(lc);

    // final List<Box> children = this.getChildren();
    // final BoxContainer parent = this;

    // if (children == null) {
    // return;
    // }

    // children.forEach((child) -> {
    // LayoutContext lc2 = new LayoutContext(lc1);
    // lc2.setParent(parent);
    // lc2.setChild(child);
    // lc2.setDepth(lc1.getDepth() + 1);

    // child.setParent(parent);
    // child.rebuildLayout(lc2);
    // });
    // }

    @Override
    public final void render(RenderContext rc) {

        this.checkDepth(rc);

        this.onPaintBackground(rc);
        this.onPaintChildren(rc);
        this.onPaintForeground(rc);
    }

    @Override
    public final void rebuildLayout(LayoutContext lc) {

        // this.checkDepth(lc);
        // this.onBuildLayoutChildren(lc);

        this.onBuildLayoutPre(lc);
        this.onBuildLayoutSelf(lc);
        this.onBuildLayoutPost(lc);
    }

    ////////////////////////////////////////////////////////////////////////////
    /// private

    private void innerSortChildrenByZ() {

        List<Box> all = this.getChildren();
        if (all == null) {
            return;
        }

        all.sort((child1, child2) -> {
            if (child1 == null || child2 == null) {
                return 0;
            }
            int z1 = child1.getZ();
            int z2 = child2.getZ();
            return (z1 - z2);
        });
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
