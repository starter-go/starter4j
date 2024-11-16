package com.bitwormhole.starter4j.application.boot;

import com.bitwormhole.starter4j.application.Life;
import com.bitwormhole.starter4j.application.LifeManager;

public final class MainLoopRunner {

    private MainLoopRunner() {
    }

    public static void run(AppContextCore core) {
        MainLoopRunner runner = new MainLoopRunner();
        LifeManager lm = core.injections.getSingletonInjection().getLifeManager();
        Life life = lm.getMaster();
        life.normalize();
        runner.run1(life);
    }

    private void run1(Life l) {
        try {
            l.onCreate.invoke();
            this.run2(l);
        } finally {
            l.onDestroy.invoke();
        }
    }

    private void run2(Life l) {
        try {
            l.onStartPre.invoke();
            l.onStart.invoke();
            l.onStartPost.invoke();
            this.run3(l);
        } finally {
            l.onStopPre.invoke();
            l.onStop.invoke();
            l.onStopPost.invoke();
        }
    }

    private void run3(Life l) {
        l.onLoop.invoke();
    }
}
