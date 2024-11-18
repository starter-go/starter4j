package com.bitwormhole.starter4j.application.resources;

public interface EmbeddedRes {

    String path();

    int size();

    void hex(StringBuilder out);

}
