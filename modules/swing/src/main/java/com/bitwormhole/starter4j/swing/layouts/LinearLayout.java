package com.bitwormhole.starter4j.swing.layouts;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.bitwormhole.starter4j.swing.canvases.Box;
import com.bitwormhole.starter4j.swing.canvases.BoxContainer;
import com.bitwormhole.starter4j.swing.canvases.Getters;
import com.bitwormhole.starter4j.swing.canvases.ILayout;
import com.bitwormhole.starter4j.swing.canvases.LayoutContext;

public class LinearLayout implements ILayout {

    public enum Direction {
        HORIZONTAL, VERTICAL,
    }

    public static final Direction HORIZONTAL = Direction.HORIZONTAL;
    public static final Direction VERTICAL = Direction.VERTICAL;

    public LinearLayout(Direction direction) {
        this.mDirection = direction;
    }

    @Override
    public void onBuildLayoutPre(LayoutContext lc, BoxContainer cantainer) {
    }

    @Override
    public void onBuildLayoutContainer(LayoutContext lc, BoxContainer cantainer) {

        if (this.isHorizontal()) {
            this.doBuildLayoutH(lc, cantainer);
        } else {
            this.doBuildLayoutV(lc, cantainer);
        }

        AbstractLayout.buildLayoutForChildren(lc, cantainer);
    }

    @Override
    public void onBuildLayoutPost(LayoutContext lc, BoxContainer cantainer) {
    }

    //////////////////////////////////////////////////////////////////
    /// private
    ///
    ///

    private final Direction mDirection;

    private boolean isHorizontal() {
        return (this.mDirection == HORIZONTAL);
    }

    private static class ChildHolder {
        Box child;
        int weight; // 当 weight==0, 使用 length 作为尺寸
        int lengthRaw;
        int lengthFinal;

        // 判断这个 box 是否是固定大小的
        boolean isFixed() {
            return ((weight == 0) && (lengthRaw > 0));
        }
    }

    private List<ChildHolder> makeHolderList(List<Box> src) {
        List<ChildHolder> dst = new ArrayList<>();
        if (src == null) {
            return dst;
        }
        final boolean is_horz = this.isHorizontal();
        for (Box item : src) {
            if (item == null) {
                continue;
            }
            Dimension want_size = Getters.notNull(item.getWantSize());
            ChildHolder holder = new ChildHolder();
            holder.child = item;
            holder.weight = item.getWeight();
            holder.lengthRaw = is_horz ? want_size.width : want_size.height;
            dst.add(holder);
        }
        return dst;
    }

    private static void computeLengths(List<ChildHolder> list, final int total_length) {

        int fixed_length = 0;
        int total_weight = 0;

        for (ChildHolder ch : list) {
            if (ch.isFixed()) {
                fixed_length += ch.lengthRaw;
                ch.lengthFinal = ch.lengthRaw;
            } else {
                total_weight += ch.weight;
            }
        }

        int pos1, pos2;
        pos1 = pos2 = 0;
        int sum_weight = 0;
        final int full_lenght = total_length - fixed_length; // @weight 部分的长度

        for (ChildHolder ch : list) {
            if (!ch.isFixed()) {
                sum_weight += ch.weight;
                pos2 = computePosWithWeight(sum_weight, total_weight, full_lenght);
                ch.lengthFinal = pos2 - pos1;
                pos1 = pos2;
            }
        }
    }

    private static int computePosWithWeight(float at, int full_weight, int full_lenght) {
        if (full_weight < 1) {
            full_weight = 1;
        }
        float x = at / full_weight;
        return (int) (x * full_lenght);
    }

    private void doBuildLayoutV(LayoutContext lc, final BoxContainer parent) {

        // layout like:
        // 口
        // 口
        // 口

        Dimension p_size = getParentSize(parent);
        List<Box> children = parent.getChildren();
        List<ChildHolder> holders = this.makeHolderList(children);

        int x, y, w, h;
        x = y = 0;
        w = p_size.width;

        computeLengths(holders, p_size.height);

        for (ChildHolder holder : holders) {
            final Box child = holder.child;
            h = holder.lengthFinal;
            child.setPosition(new Point(x, y));
            child.setSize(new Dimension(w, h));
            y += h;
        }
    }

    private void doBuildLayoutH(LayoutContext lc, final BoxContainer parent) {

        // layout like: 口口口口口

        Dimension p_size = getParentSize(parent);
        List<Box> children = parent.getChildren();
        List<ChildHolder> holders = this.makeHolderList(children);

        int x, y, w, h;
        x = y = 0;
        h = p_size.height;

        computeLengths(holders, p_size.width);

        for (ChildHolder holder : holders) {
            final Box child = holder.child;
            w = holder.lengthFinal;
            child.setPosition(new Point(x, y));
            child.setSize(new Dimension(w, h));
            x += w;
        }
    }

    private static Dimension getParentSize(BoxContainer parent) {

        Dimension size;

        size = parent.getSize();
        if (size != null) {
            return size;
        }

        // size = parent.getWantSize();
        // if (size != null) {
        // return size;
        // }

        return new Dimension();
    }

}
