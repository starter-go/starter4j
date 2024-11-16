package com.bitwormhole.starter4j.application;

import com.bitwormhole.starter4j.base.StarterException;

public interface ComponentRegistryFunc {

	void invoke(ComponentRegistry cr) throws StarterException;

}
