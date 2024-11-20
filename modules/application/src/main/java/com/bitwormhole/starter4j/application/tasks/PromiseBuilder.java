package com.bitwormhole.starter4j.application.tasks;

import java.util.concurrent.Executor;

public class PromiseBuilder<T> {

    private PromiseContext context;
    private Promise.Task<T> task;
    private Class<T> type;

    public PromiseBuilder() {
    }

    private PromiseContext innerGetPC(boolean create) {
        PromiseContext ctx = this.context;
        if (ctx == null && create) {
            ctx = new PromiseContext();
            this.context = ctx;
        }
        return ctx;
    }

    public PromiseContext getContext() {
        return context;
    }

    public PromiseBuilder<T> setContext(PromiseContext context) {
        this.context = context;
        return this;
    }

    public Promise.Task<T> getTask() {
        return task;
    }

    public PromiseBuilder<T> setTask(Promise.Task<T> task) {
        this.task = task;
        return this;
    }

    public Class<T> getType() {
        return type;
    }

    public PromiseBuilder<T> setType(Class<T> type) {
        this.type = type;
        return this;
    }

    public PromiseBuilder<T> setForeground(Executor fg) {
        if (fg == null) {
            return this;
        }
        this.innerGetPC(true).setForeground(fg);
        return this;
    }

    public PromiseBuilder<T> setBackground(Executor bg) {
        if (bg == null) {
            return this;
        }
        this.innerGetPC(true).setBackground(bg);
        return this;
    }

    public Promise<T> create() {
        return Promise.create(this);
    }

    public Promise<T> Try(Promise.Task<T> task) {
        this.setTask(task);
        return this.create();
    }
}
