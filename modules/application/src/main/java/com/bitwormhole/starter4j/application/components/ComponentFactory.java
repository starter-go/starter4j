package com.bitwormhole.starter4j.application.components;

import com.bitwormhole.starter4j.base.StarterException;

public interface ComponentFactory {

	ComponentInstance createInstance(ComponentInfo info) throws StarterException;

}
