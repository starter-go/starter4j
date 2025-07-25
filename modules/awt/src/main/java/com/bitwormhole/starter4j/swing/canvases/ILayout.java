package com.bitwormhole.starter4j.swing.canvases;

public interface ILayout {

    // void onBuildLayoutPre(LayoutContext lc, BoxContainer container);
    // void onBuildLayoutContainer(LayoutContext lc, BoxContainer container);
    // void onBuildLayoutPost(LayoutContext lc, BoxContainer container);

    void updateLayout(LayoutContext lc, BoxContainer container);

}
