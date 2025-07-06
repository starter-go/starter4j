package com.bitwormhole.starter4j.swing.canvases;

public interface ILayoutable {

    /***
     * 重新计算对象的布局参数, 返回该对象布局后的尺寸
     */
    void rebuildLayout(LayoutContext lc);

}
