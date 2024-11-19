package com.bitwormhole.starter4j.application.tasks;

import java.util.concurrent.Executor;

public final class Promise<T> {

    private final PromiseInner<T> inner;

    private Promise(PromiseInner<T> _it) {
        this.inner = _it;
    }

    public interface Task<T> {
        Result<T> run();
    }

    public static <T> Promise<T> init(PromiseContext ctx, Task<T> t) {
        PromiseInner<T> it = new PromiseInner<>(ctx, t);
        return new Promise<>(it);
    }

    public Promise<T> Then(ResultHandler<T> h) {
        this.inner.handlerChain.add(h, ResultHandlerChain.HandlerAs.THEN);
        return this;
    }

    public Promise<T> Catch(ResultHandler<T> h) {
        this.inner.handlerChain.add(h, ResultHandlerChain.HandlerAs.CATCH);
        return this;
    }

    public Promise<T> Finally(ResultHandler<T> h) {
        this.inner.handlerChain.add(h, ResultHandlerChain.HandlerAs.FINALLY);
        return this;
    }

    public void Start() {
        Executor bg = this.inner.context.getBackground();
        PromiseRunner<T> runner = new PromiseRunner<>(this.inner);
        bg.execute(runner);
    }
}
