package com.bitwormhole.starter4j.application.components;

import com.bitwormhole.starter4j.base.StarterException;

public interface ComponentInstance {

	boolean ready();

	Object get();

	<T> T get(Class<T> t);

	ComponentInfo info();

	void inject(ComponentInjection i) throws StarterException;

}
