package com.bitwormhole.starter4j.swing.canvases;

public final class ShortCircuitMouseEventDispatcher {

    ////////////////////////////////////////////////////////////////////////////
    /// public

    public ShortCircuitMouseEventDispatcher() {
    }

    public void add(Box box) {
        if (box == null) {
            return;
        }
        MyNode node = new MyNode(box, this.head);
        this.head = node;
    }

    public void reset() {
        this.head = null;
    }

    public void dispatch(MouseEventContext mec) {
        MyNode p = this.head;
        for (; p != null; p = p.next) {
            if (mec.isCancelled()) {
                break;
            }
            p.box.handleMouseEvent(mec);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    /// private

    private MyNode head;

    private static class MyNode {

        MyNode next;
        Box box;

        MyNode(Box b, MyNode n) {
            this.box = b;
            this.next = n;
        }
    }

}
