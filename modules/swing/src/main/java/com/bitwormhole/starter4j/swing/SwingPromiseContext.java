package com.bitwormhole.starter4j.swing;

import com.bitwormhole.starter4j.application.tasks.PromiseContext;

public class SwingPromiseContext extends PromiseContext {

    private static SwingPromiseContext inst;

    private SwingPromiseContext() {
    }

    public static SwingPromiseContext getInstance() {
        SwingPromiseContext ctx = inst;
        if (ctx == null) {
            ctx = new SwingPromiseContext();
            ctx.setForeground(new SwingForegroundExecutor());
            ctx.setBackground(new SwingBackgroundExecutor());
            inst = ctx;
        }
        return ctx;
    }

}
