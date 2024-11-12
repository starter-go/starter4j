package com.bitwormhole.starter4j;

import com.bitwormhole.starter4j.application.Module;
import com.bitwormhole.starter4j.application.attributes.Attributes;
import com.bitwormhole.starter4j.application.environment.Environment;
import com.bitwormhole.starter4j.application.parameters.Parameters;
import com.bitwormhole.starter4j.application.properties.Properties;
import com.bitwormhole.starter4j.base.SafeMode;

public interface Initializer {

	Initializer setMainModule(Module m);

	Initializer enableToThrowException(boolean enable);

	Initializer setArguments(String[] args);

	Initializer setMode(SafeMode mode);

	Attributes getAttributes();

	Environment getEnvironment();

	Parameters getParameters();

	Properties getProperties();

	Exception run();

}
