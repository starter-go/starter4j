package com.bitwormhole.starter4j.swing;

import com.bitwormhole.starter4j.Starter;
import com.bitwormhole.starter4j.application.ModuleBuilder;
import com.bitwormhole.starter4j.application.Module;
import com.bitwormhole.starter4j.swing.res.m.SwingResMain;
import com.bitwormhole.starter4j.swing.config.ConfigAll;

final class ThisModule {

    private static final String theModuleName = SwingApplicationStarter.class.getName();
    private static final String theModuleVersion = "0.0.4";
    private static final int theModuleRevision = 8;

    private ThisModule() {
    }

    public static Module moduleCore() {

        ModuleBuilder mb = new ModuleBuilder();
        mb.setName(theModuleName + "#core");
        mb.setVersion(theModuleVersion);
        mb.setRevision(theModuleRevision);

        mb.setResources(SwingResMain.res());
        mb.setComponents(ConfigAll.all());

        mb.depend(Starter.module());

        return mb.create();
    }

    public static Module moduleShell(Module inner) {

        Module core = moduleCore();
        ModuleBuilder mb = new ModuleBuilder();

        mb.setName(theModuleName + "#shell");
        mb.setVersion(theModuleVersion);
        mb.setRevision(theModuleRevision);

        // mb.setResources(StarterSrcMain.res());
        // mb.setComponents(new ThisModule());

        mb.depend(core);
        mb.depend(inner);

        return mb.create();
    }

}
