package com.bitwormhole.starter4j.res.m;

import java.util.List;

import com.bitwormhole.starter4j.application.resources.EmbeddedRes;
import com.bitwormhole.starter4j.application.resources.EmbeddedResources;
import com.bitwormhole.starter4j.application.resources.Resources;

public final class StarterSrcMain {

    private StarterSrcMain() {
    }

    public static Resources res() {
        List<EmbeddedRes> list = EmbeddedResMain.all();
        return EmbeddedResources.create(list);
    }
}