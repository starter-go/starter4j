package com.bitwormhole.starter4j.swing.boxes;

import java.awt.Dimension;
import java.awt.Point;

import com.bitwormhole.starter4j.swing.boxes.CScrollBar.ScrollingEvent;
import com.bitwormhole.starter4j.swing.canvases.Box;
import com.bitwormhole.starter4j.swing.canvases.BoxContainer;
import com.bitwormhole.starter4j.swing.canvases.BoxContainerEntity;
import com.bitwormhole.starter4j.swing.canvases.ILayout;
import com.bitwormhole.starter4j.swing.canvases.LayoutContext;

/*****
 * CScrollView 提供一个可以滚动显示内容的区域视图, 如果内容大小超出视图窗口范围,将会以滚动的方式显示
 */

public class CScrollView extends BoxContainerEntity {

    public CScrollView() {
        this.scrollBarH = new CScrollBar(CScrollBar.Direction.HORIZONTAL);
        this.scrollBarV = new CScrollBar(CScrollBar.Direction.VERTICAL);
        this.client = new BoxContainerEntity();
        this.displayPolicyH = DisplayPolicy.AUTO;
        this.displayPolicyV = DisplayPolicy.AUTO;

        this.innerOnCreate();
    }

    public enum DisplayPolicy {
        AUTO, ALWAYS, NEVER
    }

    public Box getContent() {
        return content;
    }

    public void setContent(Box c) {
        this.innerSetContent(c);
    }

    public CScrollBar getScrollBarH() {
        return scrollBarH;
    }

    public CScrollBar getScrollBarV() {
        return scrollBarV;
    }

    public DisplayPolicy getDisplayPolicyH() {
        return displayPolicyH;
    }

    public void setDisplayPolicyH(DisplayPolicy displayPolicyH) {
        this.displayPolicyH = displayPolicyH;
    }

    public DisplayPolicy getDisplayPolicyV() {
        return displayPolicyV;
    }

    public void setDisplayPolicyV(DisplayPolicy displayPolicyV) {
        this.displayPolicyV = displayPolicyV;
    }

    ////////////////////////////////////////////////////////////////////////////
    /// private

    private final CScrollBar scrollBarH;
    private final CScrollBar scrollBarV;
    private final BoxContainer client;

    private Box content;

    private DisplayPolicy displayPolicyH;
    private DisplayPolicy displayPolicyV;

    private class MyLayout1 implements ILayout {

        // MyLayout1 :: layout for outer

        @Override
        public void updateLayout(LayoutContext lc, BoxContainer container) {

            CScrollView self = CScrollView.this;
            final Dimension p_size = container.getSize();
            final int bar_width = 16;

            Dimension client_size = new Dimension(p_size.width, p_size.height);
            Point client_pos = new Point(0, 0);

            client_size.width -= bar_width;
            client_size.height -= bar_width;

            // client
            self.client.setPosition(client_pos);
            self.client.setSize(client_size);

            // sbar-h
            self.scrollBarH.setPosition(new Point(client_pos.x, client_pos.y + client_size.height));
            self.scrollBarH.setSize(new Dimension(client_size.width, bar_width));

            // sbar-v
            self.scrollBarV.setPosition(new Point(client_pos.x + client_size.width, client_pos.y));
            self.scrollBarV.setSize(new Dimension(bar_width, client_size.height));

            // children
            container.updateLayoutForChildren(lc);

            innerUpdateScrollingInfo();
            innerUpdateContentPosition();
        }
    }

    private class MyLayout2 implements ILayout {

        // MyLayout2 :: layout for inner

        @Override
        public void updateLayout(LayoutContext lc, BoxContainer container) {
            container.updateLayoutForChildren(lc);
        }
    }

    private class MyScrollingListenerV implements CScrollBar.ScrollingListener {
        @Override
        public void onScrolled(ScrollingEvent evt) {
            innerUpdateContentPosition();
        }
    }

    private class MyScrollingListenerH implements CScrollBar.ScrollingListener {

        @Override
        public void onScrolled(ScrollingEvent evt) {
            innerUpdateContentPosition();
        }
    }

    private void innerUpdateContentPosition() {
        final Box c = this.content;
        final CScrollInfo info_h = this.scrollBarH.getInfo();
        final CScrollInfo info_v = this.scrollBarV.getInfo();
        if (info_h == null || info_v == null || c == null) {
            return;
        }
        Point pos = c.getPosition();
        pos.x = 0 - info_h.getPosition();
        pos.y = 0 - info_v.getPosition();
        c.setPosition(pos);
    }

    private void innerUpdateScrollingInfo() {

        final Box con = this.content;
        if (con == null) {
            return;
        }

        final Dimension content_size = con.getSize();
        final Dimension outer_size = this.getSize();
        final Dimension inner_size = this.client.getSize();
        if (content_size == null || outer_size == null || inner_size == null) {
            return;
        }

        final CScrollInfo bar_info_h = this.scrollBarH.getInfo();
        final CScrollInfo bar_info_v = this.scrollBarV.getInfo();

        bar_info_h.setMin(0);
        bar_info_h.setMax(content_size.width);
        bar_info_h.setPageSize(inner_size.width);

        bar_info_v.setMin(0);
        bar_info_v.setMax(content_size.height);
        bar_info_v.setPageSize(inner_size.height);

        this.scrollBarH.setInfo(bar_info_h);
        this.scrollBarV.setInfo(bar_info_v);
    }

    private void innerSetContent(Box c) {
        this.client.getChildren().clear();
        this.content = c;
        if (c == null) {
            return;
        }
        this.client.add(c);
        c.setPosition(new Point());
    }

    private void innerOnCreate() {

        this.scrollBarH.setInfo(new CScrollInfo());
        this.scrollBarH.addListener(new MyScrollingListenerH());

        this.scrollBarV.setInfo(new CScrollInfo());
        this.scrollBarV.addListener(new MyScrollingListenerV());

        this.add(this.client);
        this.add(this.scrollBarH);
        this.add(this.scrollBarV);

        this.setLayout(new MyLayout1());
        this.client.setLayout(new MyLayout2());
    }

}
