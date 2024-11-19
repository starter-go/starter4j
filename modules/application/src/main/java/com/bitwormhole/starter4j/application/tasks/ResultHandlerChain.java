package com.bitwormhole.starter4j.application.tasks;

final class ResultHandlerChain<T> {

    private Node<T> first;
    private Node<T> last;

    public ResultHandlerChain() {
    }

    public enum HandlerAs {
        THEN, CATCH, FINALLY,
    }

    private static final class Node<T> {
        Node<T> next;
        ResultHandler<T> handler;
        HandlerAs as;
    }

    public void add(ResultHandler<T> h, HandlerAs as) {
        if (h == null || as == null) {
            return;
        }
        final Node<T> node = new Node<>();
        node.as = as;
        node.handler = h;
        node.next = null;
        if (first == null || last == null) {
            first = node;
        } else {
            last.next = node;
        }
        last = node;
    }

    public void dispatch(Result<T> res) {
        for (Node<T> p = first; p != null; p = p.next) {
            res = this.dispatchResultToNode(p, res);
        }
    }

    private Result<T> dispatchResultToNode(Node<T> node, Result<T> res) {
        if (res == null) {
            res = new Result<>();
        }
        try {
            Result<T> r2 = null;
            if (this.accept(node, res)) {
                r2 = node.handler.handle(res);
            }
            if (r2 != null) {
                res = r2;
            }
        } catch (Exception e) {
            res.setError(e);
        }
        return res;
    }

    private boolean accept(Node<T> node, Result<T> res) {
        if (node == null || res == null) {
            return false;
        }
        if (node.as == HandlerAs.FINALLY) {
            return true;
        } else if (node.as == HandlerAs.THEN) {
            return (res.getError() == null);
        } else if (node.as == HandlerAs.CATCH) {
            return (res.getError() != null);
        }
        return false;
    }
}
