package com.bitwormhole.starter4j.application.boot;

import com.bitwormhole.starter4j.application.attributes.Attributes;
import com.bitwormhole.starter4j.application.environment.Environment;
import com.bitwormhole.starter4j.application.parameters.Parameters;
import com.bitwormhole.starter4j.application.properties.Properties;
import com.bitwormhole.starter4j.base.SafeMode;

public class BootOptions {

	public SafeMode mode;

	public String[] args;

	public Attributes attributes;
	public Environment environment;
	public Parameters parameters;
	public Properties properties;

}
