package com.bitwormhole.starter4j.swing.canvases;

/***
 * BoxAbs 是 Box 类的抽象父类，它包含了一些 Box 需要实现的抽象方法
 */
public abstract class BoxAbs implements IRenderable, ILayoutable {

    // paint

    protected abstract void onPaintBackground(RenderContext rc);

    protected abstract void onPaintForeground(RenderContext rc);

    // layout

    protected abstract void onBuildLayout(LayoutContext lc);

    protected abstract void onBuildLayoutPre(LayoutContext lc);

    protected abstract void onBuildLayoutSelf(LayoutContext lc);

    protected abstract void onBuildLayoutPost(LayoutContext lc);

}
