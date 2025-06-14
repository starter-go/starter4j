package com.bitwormhole.starter4j.vlog.simple;

import com.bitwormhole.starter4j.application.ModuleBuilder;
import com.bitwormhole.starter4j.application.Module;

public class VLogSimple {

    final static String theModuleName = VLogSimple.class.getName();
    final static String theModuleVersion = "0.0.2";
    final static int theModuleRevision = 6;

    public static Module module() {
        ModuleBuilder mb = new ModuleBuilder();

        mb.setName(theModuleName);
        mb.setVersion(theModuleVersion);
        mb.setRevision(theModuleRevision);

        return mb.create();
    }

}
