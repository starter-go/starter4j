package com.bitwormhole.starter4j.application;

import com.bitwormhole.starter4j.application.resources.Resources;
import com.bitwormhole.starter4j.base.StarterException;

public interface Module {

	String name();

	String version();

	int revision();

	Module[] dependencies();

	Resources resources();

	void registerComponents(ComponentRegistry r) throws StarterException;

}
