package com.bitwormhole.starter4j.application.components;

public class ComponentSelector {

	public ComponentSelector() {
	}

	/***
	 * return the id-selector of type(t)
	 */
	public String ID(Class<?> t) {
		String cn = t.getName();
		return "#" + cn;
	}

	/**
	 * return the class-selector of type(t)
	 */
	public String CLASS(Class<?> t) {
		String cn = t.getName();
		return "." + cn;
	}

	/**
	 * return the property-selector with (name)
	 */
	public String PROPERTY(String name) {
		return "${" + name + "}";
	}

}
