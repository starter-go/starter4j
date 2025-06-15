package com.bitwormhole.starter4j.swing.config;

import com.bitwormhole.starter4j.application.ComponentRegistryFunc;
import com.bitwormhole.starter4j.application.ComponentRegistryMultiplexer;

public class ExampleConfig {

    public static ComponentRegistryFunc all() {
        ComponentRegistryMultiplexer mult = new ComponentRegistryMultiplexer();
        mult.add(ExampleFramesReg.registerSelf());
        return mult.multiplex();
    }
}
