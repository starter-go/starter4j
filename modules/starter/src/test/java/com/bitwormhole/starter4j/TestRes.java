package com.bitwormhole.starter4j;

import com.bitwormhole.starter4j.application.resources.ClasspathTreeResources;
import com.bitwormhole.starter4j.application.resources.Resources;
import com.bitwormhole.starter4j.base.SafeMode;

public class TestRes {

    public static Resources getResources() {
        SafeMode mode = SafeMode.Safe;
        return new ClasspathTreeResources(TestRes.class, mode);
    }

}
