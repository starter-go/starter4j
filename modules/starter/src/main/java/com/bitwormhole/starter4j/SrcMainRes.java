package com.bitwormhole.starter4j;

import com.bitwormhole.starter4j.application.resources.ClasspathTreeResources;
import com.bitwormhole.starter4j.application.resources.Resources;
import com.bitwormhole.starter4j.base.SafeMode;

public class SrcMainRes {

    public static Resources resources() {
        SafeMode mode = SafeMode.Safe;
        return new ClasspathTreeResources(SrcMainRes.class, mode);
    }

}
