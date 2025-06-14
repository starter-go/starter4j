package com.bitwormhole.starter4j.swing;

import java.util.HashMap;
import java.util.Map;

import com.bitwormhole.starter4j.Initializer;
import com.bitwormhole.starter4j.Starter;
import com.bitwormhole.starter4j.base.SafeMode;
import com.bitwormhole.starter4j.application.Module;

public final class SwingApplicationStarter {

    private SwingApplicationStarter() {
    }

    public static void run(SwingApplicationConfig cfg) {

        Initializer i = Starter.init(cfg.getArguments());
        Module m = cfg.getModule();
        Map<String, Object> attrs = new HashMap<>();

        m = ThisModule.moduleShell(m);

        attrs.put(SwingConst.MAIN_FRAME_CLASS, cfg.getMainFrameClass());

        i.setMainModule(m);
        i.setMode(SafeMode.Fast);
        i.getProperties().importFrom(cfg.getProperties());
        i.getAttributes().importFrom(attrs);
        i.enableToThrowException(true).run();
    }

    public static Module module() {
        return ThisModule.moduleCore();
    }
}
