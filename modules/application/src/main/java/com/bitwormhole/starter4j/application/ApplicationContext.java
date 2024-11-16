package com.bitwormhole.starter4j.application;

import java.io.Closeable;
import java.util.List;

import com.bitwormhole.starter4j.application.arguments.Arguments;
import com.bitwormhole.starter4j.application.attributes.Attributes;
import com.bitwormhole.starter4j.application.components.Scope;
import com.bitwormhole.starter4j.application.environment.Environment;
import com.bitwormhole.starter4j.application.parameters.Parameters;
import com.bitwormhole.starter4j.application.properties.Properties;
import com.bitwormhole.starter4j.application.resources.Resources;
import com.bitwormhole.starter4j.base.SafeMode;

public interface ApplicationContext extends Closeable {

	SafeMode mode();

	Injection newInjection(Scope scope);

	// attributes

	Arguments getArguments();

	Attributes getAttributes();

	Environment getEnvironment();

	Parameters getParameters();

	Properties getProperties();

	Resources getResources();

	// modules

	Module[] getModules();

	Module getMainModule();

	// components

	List<Object> selectComponents(String selector, List<Object> dst);

	<T> T selectComponent(String selector, Class<T> t);

	<T> T getComponent(String id, Class<T> t);

	String[] listComponentIds();

}
