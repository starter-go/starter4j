package com.bitwormhole.starter4j.swing;

public interface FrameManager {

    void show(Class<?> frameClass);

    void show(String frameName);

    FrameRegistration find(Class<?> frameClass);

    FrameRegistration find(String frameName);

    String[] listNames();

}
