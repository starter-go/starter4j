package com.bitwormhole.starter4j.swing;

public class FrameRegistration {

    private FrameHolder holder;
    private FrameFactory factory;
    private String name;
    private boolean singleton;
    private Class<?> type;

    public FrameRegistration() {
    }

    public FrameRegistration(FrameRegistration src) {
        if (src == null) {
            return;
        }
        this.holder = src.holder;
        this.factory = src.factory;
        this.name = src.name;
        this.singleton = src.singleton;
        this.type = src.type;
    }

    public FrameHolder getHolder() {
        return holder;
    }

    public void setHolder(FrameHolder holder) {
        this.holder = holder;
    }

    public FrameFactory getFactory() {
        return factory;
    }

    public void setFactory(FrameFactory factory) {
        this.factory = factory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSingleton() {
        return singleton;
    }

    public void setSingleton(boolean singleton) {
        this.singleton = singleton;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

}
