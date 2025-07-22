package com.bitwormhole.starter4j.swing.boxes;

import java.awt.Dimension;
import java.awt.Point;
import java.util.List;

import com.bitwormhole.starter4j.swing.canvases.Box;
import com.bitwormhole.starter4j.swing.canvases.BoxContainer;
import com.bitwormhole.starter4j.swing.canvases.BoxContainerEntity;
import com.bitwormhole.starter4j.swing.canvases.ILayout;
import com.bitwormhole.starter4j.swing.canvases.LayoutContext;

public class CScrollView extends BoxContainerEntity {

    public CScrollView() {
        this.scrollBarH = new CScrollBar(CScrollBar.Direction.HORIZONTAL);
        this.scrollBarV = new CScrollBar(CScrollBar.Direction.VERTICAL);

        this.scrollBarH.setInfo(new CScrollInfo());
        this.scrollBarV.setInfo(new CScrollInfo());

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

    ////////////////////////////////////////////////////////////////////////////
    /// private

    private Box content;
    private final CScrollBar scrollBarH;
    private final CScrollBar scrollBarV;

    private class MyLayout implements ILayout {

        @Override
        public void updateLayout(LayoutContext lc, BoxContainer container) {

            CScrollView self = CScrollView.this;
            final Box inner_content = self.content;
            final Dimension p_size = container.getSize();
            final int bar_w = 25;
            final int p_w = p_size.width;
            final int p_h = p_size.height;

            self.scrollBarH.setSize(new Dimension(p_w - bar_w, bar_w));
            self.scrollBarV.setSize(new Dimension(bar_w, p_h - bar_w));
            // inner_content.setSize(new Dimension( p_w , p_h ));

            self.scrollBarH.setPosition(new Point(0, p_h - bar_w));
            self.scrollBarV.setPosition(new Point(p_w - bar_w, 0));
            inner_content.setPosition(new Point()); // todo: compute by pos

            container.updateLayoutForChildren(lc);
        }
    }

    private void innerSetContent(Box c) {
        this.innerRemoveOlderContent();
        this.content = c;
        if (c == null) {
            return;
        }

        this.setLayout(new MyLayout());

        this.add(c);
        this.add(this.scrollBarH);
        this.add(this.scrollBarV);

        c.setZ(1);
        this.scrollBarH.setZ(2);
        this.scrollBarV.setZ(3);

        c.setPosition(new Point());
    }

    private void innerRemoveOlderContent() {
        List<Box> list = this.getChildren();
        if (list == null) {
            return;
        }
        list.clear();
    }

}
