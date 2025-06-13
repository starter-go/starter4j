package com.bitwormhole.starter4j.swing;

import java.lang.System;
import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

public final class Main {

    public static void main(String[] args) {
        try {
            Main m = new Main();
            m.display();
        } catch (InvocationTargetException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void display() throws InvocationTargetException, InterruptedException {
        System.out.println(this);
        SwingUtilities.invokeAndWait(() -> {
            ExampleFrame f = ExampleFrame.create();
            f.setVisible(true);
        });
    }

}
