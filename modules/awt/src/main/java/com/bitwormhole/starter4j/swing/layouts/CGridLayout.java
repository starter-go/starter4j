package com.bitwormhole.starter4j.swing.layouts;

import java.awt.Dimension;
import java.awt.Point;
import java.util.List;

import com.bitwormhole.starter4j.swing.canvases.Box;
import com.bitwormhole.starter4j.swing.canvases.BoxContainer;
import com.bitwormhole.starter4j.swing.canvases.ILayout;
import com.bitwormhole.starter4j.swing.canvases.LayoutContext;

/***
 * CGridLayout 这个布局提供一个类似表格的 "行-列" 式布局
 */
public class CGridLayout implements ILayout {

    private int rows;
    private int columns;

    public CGridLayout() {
    }

    public CGridLayout(int _rows, int _cols) {
        this.rows = _rows;
        this.columns = _cols;
    }

    @Override
    public void updateLayout(LayoutContext lc, BoxContainer container) {

        this.doMakeLayout(lc, container);

        container.updateLayoutForChildren(lc);

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
        int w2, h2;

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
                w2 = w;
                h2 = h;

                if (col == col_count - 1) {
                    // 如果是最后一列
                    w2 = size.width - x;
                }
                if (row == row_count - 1) {
                    // 如果是最后一行
                    h2 = size.height - y;
                }

                this.makeChildLayout(child, x, y, w2, h2);
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
