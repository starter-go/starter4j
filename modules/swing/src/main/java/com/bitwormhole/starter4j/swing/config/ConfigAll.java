package com.bitwormhole.starter4j.swing.config;

import java.util.ArrayList;
import java.util.List;

import com.bitwormhole.starter4j.application.ComponentRegistry;
import com.bitwormhole.starter4j.application.ComponentRegistryFunc;
import com.bitwormhole.starter4j.swing.SwingFrameManager;

public class ConfigAll {

    public static void registerAll(ComponentRegistry cr) {

        List<ComponentRegistryFunc> list = new ArrayList<>();

        // list.add(new DebugConfig());
        // list.add(new VLogConfig());

        list.add(SwingFrameManager.registry());

        for (ComponentRegistryFunc fn : list) {
            fn.invoke(cr);
        }
    }

}
