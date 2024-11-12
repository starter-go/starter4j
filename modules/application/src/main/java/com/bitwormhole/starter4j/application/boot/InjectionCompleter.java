package com.bitwormhole.starter4j.application.boot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bitwormhole.starter4j.application.Injection;
import com.bitwormhole.starter4j.application.components.ComponentInstance;
import com.bitwormhole.starter4j.base.StarterException;

public final class InjectionCompleter {

    private InjectionCompleter() {
    }

    public static void complete(Injection injection, Map<String, ComponentInstance> table) {
        for (int retry = 20; retry > 0; retry--) {
            List<ComponentInstance> all = new ArrayList<>(table.values());
            int count = 0;
            for (ComponentInstance ci : all) {
                if (ci.ready()) {
                    continue;
                } else {
                    ci.inject(injection);
                    count++;
                }
            }
            if (count == 0) {
                // done
                return;
            }
        }
        throw new StarterException("cannot complete the injection (timeout)");
    }
}
