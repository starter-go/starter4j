package com.bitwormhole.starter4j.application.components;

public interface ComponentHolder {

	ComponentInfo info();

	ComponentFactory factory();

	ComponentInstance getInstance();

	ComponentRef newRef(String selector);

}
