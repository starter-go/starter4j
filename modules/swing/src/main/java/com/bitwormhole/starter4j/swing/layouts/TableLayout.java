package com.bitwormhole.starter4j.swing.layouts;

import java.awt.Dimension;
import java.awt.Point;
import java.util.List;

import com.bitwormhole.starter4j.swing.canvases.Box;
import com.bitwormhole.starter4j.swing.canvases.BoxContainer;
import com.bitwormhole.starter4j.swing.canvases.ILayout;
import com.bitwormhole.starter4j.swing.canvases.LayoutContext;

/***
 * TableLayout 这个布局提供一个类似表格的 "行-列" 式布局
 */
public class TableLayout implements ILayout {

    private int rows;
    private int columns;

    public TableLayout() {
    }

    public TableLayout(int _rows, int _cols) {
        this.rows = _rows;
        this.columns = _cols;
    }

    @Override
    public void onBuildLayoutPre(LayoutContext lc, BoxContainer container) {
    }

    @Override
    public void onBuildLayoutContainer(LayoutContext lc, BoxContainer container) {
        this.doMakeLayout(lc, container);
    }

    @Override
    public void onBuildLayoutPost(LayoutContext lc, BoxContainer container) {
        AbstractLayout.buildLayoutForChildren(lc, container);
    }

    private void doMakeLayout(LayoutContext lc, BoxContainer container) {

        final int row_count = this.rows;
        final int col_count = this.columns;
        List<Box> all = container.getChildren();
        Dimension size = container.getSize();

        if (row_count < 1 || col_count < 1) {
            return;
        }

        int x, y, w, h;
        x = 0;
        y = 0;
        w = size.width / col_count;
        h = size.height / row_count;

        for (int row = 0; row < row_count; row++) {
            for (int col = 0; col < col_count; col++) {
                final int index = (row * col_count) + (col);
                final Box child = this.getChildAt(index, all);
                x = w * col;
                y = h * row;
                this.makeChildLayout(child, x, y, w, h);
            }
        }
    }

    private void makeChildLayout(Box child, int x, int y, int w, int h) {
        if (child == null) {
            return;
        }
        child.setPosition(new Point(x, y));
        child.setSize(new Dimension(w, h));
    }

    private Box getChildAt(int index, List<Box> all) {
        if (all == null || index < 0) {
            return null;
        }
        if (index < all.size()) {
            return all.get(index);
        }
        return null;
    }

}
