package com.bitwormhole.starter4j.swing;

import java.util.List;

public interface FrameManager {

    void show(Goal goal);

    List<FrameRegistration> find(Goal goal);

    FrameRegistration find(Class<?> frameClass);

    FrameRegistration find(String frameName);

    String[] listNames();

}
