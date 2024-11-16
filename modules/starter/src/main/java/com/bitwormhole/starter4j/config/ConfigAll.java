package com.bitwormhole.starter4j.config;

import java.util.ArrayList;
import java.util.List;

import com.bitwormhole.starter4j.application.ComponentRegistry;
import com.bitwormhole.starter4j.application.ComponentRegistryFunc;
import com.bitwormhole.starter4j.base.StarterException;

public final class ConfigAll {

    private ConfigAll() {
    }

    public static void registerAll(ComponentRegistry cr) throws StarterException {
        List<ComponentRegistryFunc> list = new ArrayList<>();

        list.add(new DebugConfig());
        list.add(new VLogConfig());
        list.add(new LifeCom());

        for (ComponentRegistryFunc fn : list) {
            fn.invoke(cr);
        }
    }
}
