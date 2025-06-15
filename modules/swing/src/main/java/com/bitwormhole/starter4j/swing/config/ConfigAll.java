package com.bitwormhole.starter4j.swing.config;

import com.bitwormhole.starter4j.application.ComponentRegistryFunc;
import com.bitwormhole.starter4j.application.ComponentRegistryMultiplexer;
import com.bitwormhole.starter4j.swing.SwingFrameManager;

public class ConfigAll {

    /**
     * 导出所有需要注册的组件
     */
    public static ComponentRegistryFunc all() {
        ComponentRegistryMultiplexer multi = new ComponentRegistryMultiplexer();
        multi.add(SwingFrameManager.autoComRegistrar());
        return multi.multiplex();
    }

}
