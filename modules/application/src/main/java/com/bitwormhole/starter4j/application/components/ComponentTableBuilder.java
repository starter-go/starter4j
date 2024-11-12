package com.bitwormhole.starter4j.application.components;

import java.util.HashMap;
import java.util.Map;

import com.bitwormhole.starter4j.application.ComponentRegistration;
import com.bitwormhole.starter4j.application.ComponentRegistry;

public class ComponentTableBuilder implements ComponentRegistry {

	private Map<String, ComponentHolder> table;

	public ComponentTableBuilder() {
		this.table = new HashMap<>();
	}

	@Override
	public ComponentRegistration newRegistration() {
		return new ComponentRegistration();
	}

	@Override
	public void register(ComponentRegistration r) {
		ComponentHolder holder = this.loadComponentHolder(r);
		ComponentInfo info = holder.info();
		String id = info.id();
		this.table.put(id, holder);
	}

	private ComponentHolder loadComponentHolder(ComponentRegistration r) {
		ComponentHolderBuilder chb = new ComponentHolderBuilder();
		chb.init(r);
		return chb.create();
	}

	public Map<String, ComponentHolder> create() {
		Map<String, ComponentHolder> older = this.table;
		this.table = new HashMap<>();
		return older;
	}
}
