package com.bitwormhole.starter4j.swing;

import java.lang.reflect.InvocationTargetException;

public final class Main {

    public static void main(String[] args) {
        try {
            Main m = new Main();
            m.display(args);
        } catch (InvocationTargetException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void display(String[] args) throws InvocationTargetException, InterruptedException {

        SwingApplicationConfig cfg = new SwingApplicationConfig();
        cfg.setArguments(args);
        cfg.setModule(ExampleModule.module());
        cfg.setMainFrameClass(ExampleFrame.class);
        SwingApplicationStarter.run(cfg);

    }

}
