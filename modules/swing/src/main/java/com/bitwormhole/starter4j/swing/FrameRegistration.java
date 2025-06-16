package com.bitwormhole.starter4j.swing;

public class FrameRegistration {

    private FrameHolder holder;
    private FrameFactory factory;
    private String id; // 每个必需有一个唯一的ID
    private String name; // name 是可选的 , 即匿名
    private boolean singleton;
    private Class<?> type;
    private FrameRegistrationFilter filter;

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

    public FrameRegistrationFilter getFilter() {
        return filter;
    }

    public void setFilter(FrameRegistrationFilter filter) {
        this.filter = filter;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
