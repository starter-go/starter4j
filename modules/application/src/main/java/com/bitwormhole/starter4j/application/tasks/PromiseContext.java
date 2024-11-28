package com.bitwormhole.starter4j.application.tasks;

public class PromiseContext {

    private ForegroundExecutor foreground;
    private BackgroundExecutor background;

    public PromiseContext() {
    }

    public PromiseContext(PromiseContext src) {
        if (src == null) {
            return;
        }
        this.background = src.background;
        this.foreground = src.foreground;
    }

    public ForegroundExecutor getForeground() {
        return foreground;
    }

    public void setForeground(ForegroundExecutor foreground) {
        this.foreground = foreground;
    }

    public BackgroundExecutor getBackground() {
        return background;
    }

    public void setBackground(BackgroundExecutor background) {
        this.background = background;
    }
}
