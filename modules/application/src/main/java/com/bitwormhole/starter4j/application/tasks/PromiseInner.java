package com.bitwormhole.starter4j.application.tasks;

import java.util.concurrent.Executor;

import com.bitwormhole.starter4j.application.tasks.Promise.Task;

final class PromiseInner<T> {

    public final PromiseContext context;
    public final Task<T> task;
    public final ResultHandlerChain<T> handlerChain;

    public PromiseInner(PromiseContext ctx, Task<T> t) {

        ctx = prepareContext(ctx);
        t = prepareTask(t);

        this.context = ctx;
        this.handlerChain = new ResultHandlerChain<>();
        this.task = t;
    }

    private static final class MyDefaultWorker implements Executor {
        @Override
        public void execute(Runnable r) {
            r.run();
        }
    }

    private static final MyDefaultWorker theWorker = new MyDefaultWorker();

    private static PromiseContext prepareContext(PromiseContext ctx) {

        if (ctx == null) {
            ctx = new PromiseContext();
        }

        if (ctx.getBackground() == null) {
            ctx.setBackground(theWorker);
        }

        if (ctx.getForeground() == null) {
            ctx.setForeground(theWorker);
        }

        return ctx;
    }

    private static <T> Task<T> prepareTask(Task<T> t) {
        if (t == null) {
            t = () -> {
                return new Result<>();
            };
        }
        return t;
    }
}
