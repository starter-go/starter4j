package com.bitwormhole.starter4j.swing.boxes;

public class CScrollInfo {

    private int min;
    private int max;
    private int position;
    private int pageSize;

    public CScrollInfo() {
        this.initAsDefault();
    }

    public CScrollInfo(CScrollInfo src) {

        if (src == null) {
            this.initAsDefault();
            return;
        }

        this.min = src.min;
        this.position = src.position;
        this.pageSize = src.pageSize;
        this.max = src.max;
    }

    public static CScrollInfo normalize(CScrollInfo i) {

        if (i == null) {
            return new CScrollInfo();
        }

        // check total range (max)
        int total = i.max - i.min;
        if (total < 0) {
            total = 0;
            i.max = i.min;
        }

        // check page - size
        int ps = i.pageSize; // the page-size
        if (ps > total) {
            ps = total;
        }
        if (ps < 1) {
            ps = 1;
        }
        i.pageSize = ps;

        // check position
        int limit = i.min + (total - ps);
        int pos = i.position;
        if (pos > limit) {
            pos = limit;
        }
        if (pos < i.min) {
            pos = i.min;
        }
        i.position = pos;

        return i;
    }

    private final void initAsDefault() {
        this.min = 0;
        this.position = 0;
        this.pageSize = 25;
        this.max = 100;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CScrollInfo) {
            return equals(this, (CScrollInfo) obj);
        }
        return false;
    }

    public static boolean equals(CScrollInfo i1, CScrollInfo i2) {

        if (i1 == null || i2 == null) {
            return false;
        }
        if (i1 == i2) {
            return true;
        }

        if (i1.min != i2.min) {
            return false;
        }
        if (i1.max != i2.max) {
            return false;
        }
        if (i1.position != i2.position) {
            return false;
        }
        if (i1.pageSize != i2.pageSize) {
            return false;
        }

        return true;
    }

}
