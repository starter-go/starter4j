package com.bitwormhole.starter4j.application.environment;

import java.util.Map;

import com.bitwormhole.starter4j.base.SafeMode;

public interface Environment {

	public static class Table {
		public static Environment create(SafeMode mode) {
			return new EnvironmentTable(mode);
		}
	}

	SafeMode mode();

	String[] names();

	void setEnv(String name, String value);

	String getEnv(String name);

	String getEnv(String name, String defaultValue);

	String getEnv(String name, boolean required);

	Map<String, String> exportTo(Map<String, String> dst);

	void importFrom(Map<String, String> src);
}
