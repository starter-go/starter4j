package com.bitwormhole.starter4j.application.parameters;

import java.util.Map;

import com.bitwormhole.starter4j.base.SafeMode;

public interface Parameters {

	public static class Table {
		public static Parameters create(SafeMode mode) {
			return new ParamTable(mode);
		}
	}

	SafeMode mode();

	String[] names();

	void setParam(String name, String value);

	String getParam(String name);

	String getParam(String name, String defaultValue);

	String getParam(String name, boolean required);

	Map<String, String> exportTo(Map<String, String> dst);

	void importFrom(Map<String, String> src);

}
