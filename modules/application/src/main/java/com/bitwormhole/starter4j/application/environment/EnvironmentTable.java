package com.bitwormhole.starter4j.application.environment;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.bitwormhole.starter4j.base.Safe;
import com.bitwormhole.starter4j.base.SafeMode;

class EnvironmentTable implements Environment {

	private final Map<String, String> table;
	private final SafeMode mode;

	EnvironmentTable(SafeMode _mode) {
		_mode = Safe.normalize(_mode);
		Map<String, String> t = new HashMap<>();
		if (_mode == SafeMode.Safe) {
			t = Collections.synchronizedMap(t);
		}
		this.table = t;
		this.mode = _mode;
	}

	@Override
	public String[] names() {
		return table.keySet().toArray(new String[0]);
	}

	@Override
	public void setEnv(String name, String value) {
		if (name == null || value == null) {
			return;
		}
		table.put(name, value);
	}

	@Override
	public String getEnv(String name) {
		boolean required = true;
		return this.getEnv(name, required);
	}

	@Override
	public String getEnv(String name, String defaultValue) {
		String value = table.get(name);
		if (value == null) {
			value = defaultValue;
		}
		return value;
	}

	@Override
	public String getEnv(String name, boolean required) {
		String value = table.get(name);
		if (value == null) {
			if (required) {
				throw new RuntimeException("no required env, with name: " + name);
			}
		}
		return value;
	}

	@Override
	public SafeMode mode() {
		return this.mode;
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
