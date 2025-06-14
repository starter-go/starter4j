package com.bitwormhole.starter4j.swing;

import javax.swing.JFrame;

public class DefaultFrameHolder implements FrameHolder {

    private final boolean singleton;
    private final FrameFactory factory;
    private JFrame frame;

    public DefaultFrameHolder(FrameRegistration fr) {
        this.singleton = fr.isSingleton();
        this.factory = fr.getFactory();
    }

    @Override
    public JFrame getFrame() {
        if (this.singleton) {
            return this.getFrameAsSingleton();
        } else {
            return this.factory.createFrame();
        }
    }

    private JFrame getFrameAsSingleton() {
        JFrame f = this.frame;
        if (f == null) {
            f = this.factory.createFrame();
            this.frame = f;
        }
        return f;
    }

}
