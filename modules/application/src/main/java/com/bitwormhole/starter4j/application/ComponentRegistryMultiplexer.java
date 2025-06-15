package com.bitwormhole.starter4j.application;

import java.util.ArrayList;
import java.util.List;

public final class ComponentRegistryMultiplexer {

    private final List<ComponentRegistryFunc> items;

    public ComponentRegistryMultiplexer() {
        this.items = new ArrayList<>();
    }

    public ComponentRegistryMultiplexer add(ComponentAutoRegistrar item) {
        if (item == null) {
            return this;
        }
        List<ComponentRegistryFunc> tmp = new ArrayList<>();
        item.registerSelf(tmp);
        this.items.addAll(tmp);
        return this;
    }

    public ComponentRegistryMultiplexer add(ComponentRegistryFunc item) {
        if (item == null) {
            return this;
        }
        this.items.add(item);
        return this;
    }

    private void invokeAll(ComponentRegistry cr) {
        this.items.forEach((item) -> {
            item.invoke(cr);
        });
    }

    public ComponentRegistryFunc multiplex() {
        return (cr) -> invokeAll(cr);
    }
}
