package com.bitwormhole.starter4j.application.properties;

import java.util.Map;

import com.bitwormhole.starter4j.base.SafeMode;

public interface Properties {

	public static class Table {
		public static Properties create(SafeMode mode) {
			return new PropertyTable(mode);
		}

		public static void parse(String text, Properties dst) {
			PropertyParser.parse(text, dst);
		}
	}

	SafeMode mode();

	String[] names();

	void setProperty(String name, String value);

	String getProperty(String name);

	String getProperty(String name, String defaultValue);

	String getProperty(String name, boolean required);

	Map<String, String> exportTo(Map<String, String> dst);

	void importFrom(Map<String, String> src);
}
