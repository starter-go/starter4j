package com.bitwormhole.starter4j.swing;

import com.bitwormhole.starter4j.application.ComponentRegistry;
import com.bitwormhole.starter4j.application.ComponentRegistryFunc;
import com.bitwormhole.starter4j.application.Module;
import com.bitwormhole.starter4j.application.ModuleBuilder;
import com.bitwormhole.starter4j.base.StarterException;
import com.bitwormhole.starter4j.res.m.StarterSrcMain;
import com.bitwormhole.starter4j.swing.config.ExampleConfig;

public class ExampleModule implements ComponentRegistryFunc {

    private static final String theModuleName = ExampleModule.class.getName();
    private static final String theModuleVersion = "0.0.0";
    private static final int theModuleRevision = 0;

    private ExampleModule() {
    }

    public static Module module() {

        ModuleBuilder mb = new ModuleBuilder();
        mb.setName(theModuleName);
        mb.setVersion(theModuleVersion);
        mb.setRevision(theModuleRevision);

        mb.setResources(StarterSrcMain.res());
        mb.setComponents(new ExampleModule());

        return mb.create();
    }

    @Override
    public void invoke(ComponentRegistry cr) throws StarterException {
        ExampleConfig.registerAll(cr);
    }

}
