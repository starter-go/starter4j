package com.bitwormhole.starter4j.application.tasks;

import java.util.concurrent.Executor;

public final class Promise<T> {

    private final PromiseInner<T> inner;

    private Promise(PromiseInner<T> pi) {
        this.inner = pi;
    }

    static <T> Promise<T> create(PromiseBuilder<T> b) {
        PromiseInner<T> it = new PromiseInner<>(b);
        return new Promise<>(it);
    }

    // public

    public interface Task<T> {
        Result<T> run();
    }

    public static <T> PromiseBuilder<T> init(PromiseContext ctx, Class<T> type) {
        PromiseBuilder<T> b = new PromiseBuilder<>();
        b.setContext(ctx);
        b.setType(type);
        return b;
    }

    public static <T> PromiseBuilder<T> init(Class<T> type) {
        PromiseBuilder<T> b = new PromiseBuilder<>();
        b.setType(type);
        return b;
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

    public PromiseContext context() {
        return new PromiseContext(this.inner.context);
    }

    public Class<T> type() {
        return this.inner.type;
    }

    public void start() {
        Executor bg = this.inner.context.getBackground();
        PromiseRunner<T> runner = new PromiseRunner<>(this.inner);
        bg.execute(runner);
    }
}
