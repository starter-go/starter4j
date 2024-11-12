package com.bitwormhole.starter4j.application;

public interface ComponentRegistry {

	ComponentRegistration newRegistration();

	void register(ComponentRegistration r);

}
