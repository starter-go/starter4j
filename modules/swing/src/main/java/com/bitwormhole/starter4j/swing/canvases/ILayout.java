package com.bitwormhole.starter4j.swing.canvases;

public interface ILayout {

    void onBuildLayoutPre(LayoutContext lc, BoxContainer cantainer);

    void onBuildLayoutContainer(LayoutContext lc, BoxContainer cantainer);

    void onBuildLayoutPost(LayoutContext lc, BoxContainer cantainer);

}
