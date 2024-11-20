package com.bitwormhole.starter4j.stopper;

import org.junit.Test;

import com.bitwormhole.starter4j.Initializer;
import com.bitwormhole.starter4j.Starter;
import com.bitwormhole.starter4j.application.Module;

public class TestStopper {

    @Test
    public void testStopper() {
        Module m = Stopper.module();
        String[] args = {
                "--debug.enabled=1",
                "--debug.log-arguments=1",
                "--debug.log-properties=1",
                "--starter.stopper.action=start",
        };
        Initializer i = Starter.init(args);
        i.setMainModule(m);
        i.enableToThrowException(true).run();
    }
}
