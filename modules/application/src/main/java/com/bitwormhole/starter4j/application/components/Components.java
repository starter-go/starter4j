package com.bitwormhole.starter4j.application.components;

import java.util.List;
import java.util.Map;

import com.bitwormhole.starter4j.base.SafeMode;
import com.bitwormhole.starter4j.base.StarterException;

public interface Components {

	public static class Table {
		public static Components create(SafeMode mode) {
			return new InnerComTable(mode);
		}
	}

	SafeMode mode();

	ComponentHolder get(String id) throws StarterException;

	void put(ComponentHolder holder) throws StarterException;

	List<ComponentHolder> select(String selector, List<ComponentHolder> dst) throws StarterException;

	String[] ids();

	Map<String, ComponentHolder> exportTo(Map<String, ComponentHolder> dst);

	void importFrom(Map<String, ComponentHolder> src);

}
