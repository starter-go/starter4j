package com.bitwormhole.starter4j.swing.canvases;

/***
 * BoxContainerAbs 是 BoxContainer 类的抽象父类，它包含了一些 BoxContainer 需要实现的抽象方法
 */

public abstract class BoxContainerAbs extends BoxEntity {

    // public

    public abstract BoxContainer add(Box child);

    // protected

    protected abstract void onPaintChildren(RenderContext rc);

    // protected abstract void onBuildLayoutChildren(LayoutContext lc);

}
