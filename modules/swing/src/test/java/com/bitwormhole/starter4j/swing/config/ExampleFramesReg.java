package com.bitwormhole.starter4j.swing.config;

import java.util.List;

import com.bitwormhole.starter4j.application.ComponentRegistryFunc;
import com.bitwormhole.starter4j.application.ComponentTemplate;
import com.bitwormhole.starter4j.application.ComponentTemplate.RegistrationT;
import com.bitwormhole.starter4j.swing.ExampleFrame;
import com.bitwormhole.starter4j.swing.FrameRegistration;
import com.bitwormhole.starter4j.swing.FrameRegistry;

public class ExampleFramesReg implements FrameRegistry {

    private ExampleFramesReg() {
    }

    public static ComponentRegistryFunc registerSelf() {
        return (cr) -> {
            ComponentTemplate template = new ComponentTemplate(cr);
            RegistrationT<ExampleFramesReg> rt = template.component(ExampleFramesReg.class);

            rt.setId(ExampleFramesReg.class);
            rt.addClass("jframe");
            rt.addClass(ExampleFramesReg.class);
            rt.addClass(FrameRegistry.class);
            rt.onNew(() -> {
                return new ExampleFramesReg();
            });

            rt.register();
        };
    }

    @Override
    public List<FrameRegistration> listRegistrations(List<FrameRegistration> dst) {
        dst.add(ExampleFrame.registration());
        return dst;
    }

}
