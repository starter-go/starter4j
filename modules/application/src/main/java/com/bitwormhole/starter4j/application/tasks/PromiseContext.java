package com.bitwormhole.starter4j.application.tasks;

import java.util.concurrent.Executor;

public class PromiseContext {

    private Executor foreground;
    private Executor background;

    public PromiseContext() {
    }

    public PromiseContext(PromiseContext src) {
        if (src == null) {
            return;
        }
        this.background = src.background;
        this.foreground = src.foreground;
    }

    public Executor getBackground() {
        return background;
    }

    public void setBackground(Executor background) {
        this.background = background;
    }

    public Executor getForeground() {
        return foreground;
    }

    public void setForeground(Executor foreground) {
        this.foreground = foreground;
    }

}
