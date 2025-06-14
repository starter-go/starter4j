package com.bitwormhole.starter4j.swing.config;

import java.util.ArrayList;
import java.util.List;

import com.bitwormhole.starter4j.application.ComponentRegistry;
import com.bitwormhole.starter4j.application.ComponentRegistryFunc;
import com.bitwormhole.starter4j.swing.ExampleFrame;

public class ExampleConfig {

    public static void registerAll(ComponentRegistry cr) {

        List<ComponentRegistryFunc> list = new ArrayList<>();

        list.add(ExampleFrame.registry());

        for (ComponentRegistryFunc fn : list) {
            fn.invoke(cr);
        }
    }
}
