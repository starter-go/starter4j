package com.bitwormhole.starter4j.application.parameters;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.bitwormhole.starter4j.base.Safe;
import com.bitwormhole.starter4j.base.SafeMode;

public class ParamTable implements Parameters {

	private final SafeMode mode;
	private final Map<String, String> table;

	ParamTable(SafeMode m) {
		m = Safe.normalize(m);
		Map<String, String> t = new HashMap<>();
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
	public void setParam(String name, String value) {
		if (name == null || value == null) {
			return;
		}
		table.put(name, value);
	}

	@Override
	public String getParam(String name) {
		boolean required = true;
		return this.getParam(name, required);
	}

	@Override
	public String getParam(String name, String defaultValue) {
		String value = table.get(name);
		if (value == null) {
			value = defaultValue;
		}
		return value;
	}

	@Override
	public String getParam(String name, boolean required) {
		String value = table.get(name);
		if (value == null) {
			if (required) {
				throw new RuntimeException("no required parameter, with name: " + name);
			}
		}
		return value;
	}

	@Override
	public Map<String, String> exportTo(Map<String, String> dst) {
		if (dst == null) {
			dst = new HashMap<>();
		}
		dst.putAll(this.table);
		return dst;
	}

	@Override
	public void importFrom(Map<String, String> src) {
		if (src == null) {
			return;
		}
		this.table.putAll(src);
	}
}
