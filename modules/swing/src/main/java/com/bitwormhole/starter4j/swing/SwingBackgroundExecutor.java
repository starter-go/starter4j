package com.bitwormhole.starter4j.swing;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.bitwormhole.starter4j.application.tasks.BackgroundExecutor;

public class SwingBackgroundExecutor implements BackgroundExecutor {

    private Executor innerExecutor;

    public SwingBackgroundExecutor() {
    }

    private Executor getInnerExecutor() {
        Executor ie = this.innerExecutor;
        if (ie == null) {
            ie = Executors.newSingleThreadExecutor();
            this.innerExecutor = ie;
        }
        return ie;
    }

    @Override
    public void execute(Runnable command) {
        Executor ie = getInnerExecutor();
        ie.execute(command);
    }

}
