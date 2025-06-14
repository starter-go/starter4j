package com.bitwormhole.starter4j.swing;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import com.bitwormhole.starter4j.application.tasks.ForegroundExecutor;

public class SwingForegroundExecutor implements ForegroundExecutor {

    public SwingForegroundExecutor() {
    }

    @Override
    public void execute(Runnable command) {
        try {
            SwingUtilities.invokeAndWait(command);
        } catch (InvocationTargetException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
