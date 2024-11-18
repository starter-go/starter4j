package com.bitwormhole.starter4j.stopper;

import com.bitwormhole.starter4j.Starter;
import com.bitwormhole.starter4j.application.ComponentRegistry;
import com.bitwormhole.starter4j.application.ComponentRegistryFunc;
import com.bitwormhole.starter4j.application.ComponentTemplate;
import com.bitwormhole.starter4j.application.ComponentTemplate.RegistrationT;
import com.bitwormhole.starter4j.application.components.ComponentSelector;
import com.bitwormhole.starter4j.application.Module;
import com.bitwormhole.starter4j.application.ModuleBuilder;
import com.bitwormhole.starter4j.base.StarterException;
import com.bitwormhole.starter4j.stopper.res.m.StopperSrcMain;

class ThisModule implements ComponentRegistryFunc {

    final static String theModuleName = Stopper.class.getName();
    final static String theModuleVersion = "0.0.0";
    final static int theModuleRev = 0;

    private ThisModule() {
    }

    public static Module module() {
        ModuleBuilder mb = new ModuleBuilder();
        mb.setName(theModuleName);
        mb.setVersion(theModuleVersion);
        mb.setRevision(theModuleRev);

        mb.setComponents(new ThisModule());
        mb.setResources(StopperSrcMain.res());

        mb.depend(Starter.module());

        return mb.create();
    }

    @Override
    public void invoke(ComponentRegistry cr) throws StarterException {
        this.com_stopper(cr);
    }

    private void com_stopper(ComponentRegistry cr) {
        ComponentTemplate ct = new ComponentTemplate(cr);
        RegistrationT<Stopper> rt = ct.component(Stopper.class);
        final String prop_name_prefix = "starter.stopper";
        final ComponentSelector cs = ComponentSelector.getInstance();

        rt.onNew(() -> {
            return new Stopper();
        });
        rt.onInject((ext, o) -> {
            o.setAction(ext.getString(cs.PROPERTY(prop_name_prefix + ".action")));
            o.setControlFile(ext.getString(cs.PROPERTY(prop_name_prefix + ".control.file")));
            o.setEnabled(ext.getBoolean(cs.PROPERTY(prop_name_prefix + ".enabled")));
            o.setLoopInterval(ext.getInt(cs.PROPERTY(prop_name_prefix + ".control.interval")));
            o.setScope(ext.getString(cs.PROPERTY(prop_name_prefix + ".scope")));
        });

        rt.register();
    }
}
