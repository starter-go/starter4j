package com.bitwormhole.starter4j.application.attributes;

import java.util.Map;

import com.bitwormhole.starter4j.base.SafeMode;

public interface Attributes {

	public static class Table {
		public static Attributes create(SafeMode mode) {
			return new AttributeTable(mode);
		}
	}

	SafeMode mode();

	String[] names();

	void setAttr(String name, Object value);

	<T> T getAttr(String name, Class<T> t);

	Object getAttr(String name);

	Object getAttr(String name, Object defaultValue);

	Object getAttr(String name, boolean required);

	Map<String, Object> exportTo(Map<String, Object> dst);

	void importFrom(Map<String, Object> src);

}
