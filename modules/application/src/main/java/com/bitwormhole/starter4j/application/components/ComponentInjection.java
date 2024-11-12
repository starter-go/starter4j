package com.bitwormhole.starter4j.application.components;

import java.util.List;

import com.bitwormhole.starter4j.application.ApplicationContext;
import com.bitwormhole.starter4j.base.StarterException;

public interface ComponentInjection {

	Scope scope();

	// selector

	List<Object> select(String selector, List<Object> dst);

	Object select(String selector) throws StarterException;

	// getter

	ApplicationContext getApplicationContext();

	String getProperty(String selector) throws StarterException;

	ComponentInstance getWithHolder(ComponentHolder holder) throws StarterException;

	ComponentInstance getById(String id) throws StarterException;

}
