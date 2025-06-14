package com.bitwormhole.starter4j.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bitwormhole.starter4j.application.ApplicationContext;
import com.bitwormhole.starter4j.application.ComponentRegistry;
import com.bitwormhole.starter4j.application.ComponentRegistryFunc;
import com.bitwormhole.starter4j.application.ComponentTemplate;
import com.bitwormhole.starter4j.application.LifeCycle;
import com.bitwormhole.starter4j.application.ComponentTemplate.RegistrationT;
import com.bitwormhole.starter4j.application.Life;
import com.bitwormhole.starter4j.base.StarterException;

public final class LifeCom implements ComponentRegistryFunc, LifeCycle {

    final static Logger logger = LoggerFactory.getLogger(LifeCom.class);

    private long t1; // @starting
    private long t2; // @started
    private ApplicationContext ac;

    public LifeCom() {
        this.t1 = System.currentTimeMillis();
    }

    @Override
    public void invoke(ComponentRegistry cr) throws StarterException {
        ComponentTemplate ct = new ComponentTemplate(cr);
        RegistrationT<LifeCom> rt = ct.component(LifeCom.class);

        // rt.setId(LifeCom.class);
        // rt.onNew(() -> {
        // return new LifeCom();
        // });

        rt.onInject((ext, obj) -> {
            obj.ac = ext.getContext();
        });
        rt.register();
    }

    @Override
    public Life life() {
        Life l = new Life();
        l.onStartPost = this::onStarted;
        l.onStopPre = this::onStopping;
        l.onDestroy = this::onStopped;
        return l;
    }

    private void onStarted() {
        this.t2 = System.currentTimeMillis();
        final long span = t2 - t1;
        float sec = span / 1000.0f;
        String appname = this.ac.getMainModule().name();
        String msg = "started app [" + appname + "] in " + sec + " sec";
        logger.info(msg);
    }

    private void onStopping() {
        logger.info("stopping ...");
    }

    private void onStopped() {
        logger.info("stopped");
    }
}
