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
    public JFrame getFrame(Goal goal) {
        if (this.singleton) {
            return this.getFrameAsSingleton(goal);
        } else {
            return this.factory.createFrame(goal);
        }
    }

    private JFrame getFrameAsSingleton(Goal goal) {
        JFrame f = this.frame;
        if (f == null) {
            f = this.factory.createFrame(goal);
            this.frame = f;
        }
        return f;
    }

}
