package com.bitwormhole.starter4j.application.components;

public interface ComponentInfo {

	String id();

	String[] aliases();

	String[] classes();

	Scope scope();

}
