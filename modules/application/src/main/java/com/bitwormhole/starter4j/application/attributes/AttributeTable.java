package com.bitwormhole.starter4j.application.attributes;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.bitwormhole.starter4j.base.Safe;
import com.bitwormhole.starter4j.base.SafeMode;

class AttributeTable implements Attributes {

	private final SafeMode mode;
	private final Map<String, Object> table;

	AttributeTable(SafeMode m) {
		m = Safe.normalize(m);
		Map<String, Object> t = new HashMap<>();
		if (m == SafeMode.Safe) {
			t = Collections.synchronizedMap(t);
		}
		this.table = t;
		this.mode = m;
	}

	@Override
	public SafeMode mode() {
		return this.mode;
	}

	@Override
	public String[] names() {
		return table.keySet().toArray(new String[0]);
	}

	@Override
	public void setAttr(String name, Object value) {
		if (name == null || value == null) {
			return;
		}
		table.put(name, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAttr(String name, Class<T> t) {
		Object value = this.getAttr(name);
		return (T) value;
	}

	@Override
	public Object getAttr(String name) {
		boolean required = true;
		return this.getAttr(name, required);
	}

	@Override
	public Object getAttr(String name, Object defaultValue) {
		Object value = table.get(name);
		if (value == null) {
			value = defaultValue;
		}
		return value;
	}

	@Override
	public Object getAttr(String name, boolean required) {
		Object value = table.get(name);
		if (value == null) {
			if (required) {
				throw new RuntimeException("no required attribute, with name: " + name);
			}
		}
		return value;
	}

	@Override
	public Map<String, Object> exportTo(Map<String, Object> dst) {
		if (dst == null) {
			dst = new HashMap<>();
		}
		dst.putAll(this.table);
		return dst;
	}

	@Override
	public void importFrom(Map<String, Object> src) {
		if (src == null) {
			return;
		}
		this.table.putAll(src);
	}
}
