package com.bitwormhole.starter4j.application.tasks;

import java.util.concurrent.Executor;

final class PromiseRunner<T> implements Runnable {

    private final PromiseInner<T> inner;

    public PromiseRunner(PromiseInner<T> pi) {
        this.inner = pi;
    }

    @Override
    public void run() {
        Result<T> res = new Result<>();
        try {
            final Result<T> res2 = inner.task.run();
            if (res2 == null) {
                return;
            }
            res = res2;
        } catch (Exception e) {
            res.setError(e);
        } finally {
            this.dispatchResult(res);
        }
    }

    private void dispatchResult(Result<T> res) {
        Executor fg = inner.context.getForeground();
        fg.execute(() -> {
            inner.handlerChain.dispatch(res);
        });
    }
}
