package com.bitwormhole.starter4j.swing;

import javax.swing.JFrame;

import com.bitwormhole.starter4j.application.LifeCycle;
import com.bitwormhole.starter4j.application.LifeManager;

public interface IFrameLifeAdapter {

    JFrame getFrame();

    LifeManager getLifeManager();

    LifeCycle getLifeCycle();

}
