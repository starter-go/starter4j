package com.bitwormhole.starter4j.application.boot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bitwormhole.starter4j.application.ApplicationContext;
import com.bitwormhole.starter4j.application.Injection;
import com.bitwormhole.starter4j.application.InjectionExt;
import com.bitwormhole.starter4j.application.LifeManager;
import com.bitwormhole.starter4j.application.components.ComponentHolder;
import com.bitwormhole.starter4j.application.components.ComponentInstance;
import com.bitwormhole.starter4j.application.components.Scope;
import com.bitwormhole.starter4j.base.SafeMode;
import com.bitwormhole.starter4j.base.StarterException;

public class InjectionImpl implements Injection {

	private final AppContextCore mCore;
	private final Scope mScope;
	private final LifeManager mLifeManager;
	private final Map<String, ComponentInstance> mComTable; // map<id,inst>
	private final InjectionExt mExt;

	public InjectionImpl(AppContextCore core, Scope scope) {

		Map<String, ComponentInstance> comTab = new HashMap<>();
		if (core.mode == SafeMode.Safe) {
			comTab = Collections.synchronizedMap(comTab);
		}

		this.mCore = core;
		this.mScope = scope;
		this.mComTable = comTab;
		this.mLifeManager = new LifeManagerImpl(core.mode);
		this.mExt = new InjectionExtImpl(this);
	}

	@Override
	public Scope scope() {
		return mScope;
	}

	private ComponentInstance innerLoadComInstance(ComponentHolder h) {
		return h.getInstance();
	}

	private ComponentInstance innerGetComInstance(ComponentHolder h) {
		final String id = h.info().id();
		final Map<String, ComponentInstance> table = this.mComTable;
		ComponentInstance inst = table.get(id);
		if (inst == null) {
			inst = this.innerLoadComInstance(h);
			if (inst == null) {
				throw new StarterException("cannot load component instance with id: " + id);
			}
			table.put(id, inst);
		}
		return inst;
	}

	@Override
	public List<Object> select(String selector, List<Object> dst) {
		if (dst == null) {
			dst = new ArrayList<>();
		}
		List<ComponentHolder> hlist = mCore.components.select(selector, null);
		for (ComponentHolder holder : hlist) {
			ComponentInstance inst = this.innerGetComInstance(holder);
			Object com = inst.get();
			dst.add(com);
		}
		return dst;
	}

	@Override
	public Object select(String selector) throws StarterException {
		List<ComponentHolder> hlist = mCore.components.select(selector, null);
		int count = hlist.size();
		if (count != 1) {
			String msg = "want only-one component by selector [" + selector + "], but have " + count;
			throw new StarterException(msg);
		}
		ComponentHolder holder = hlist.get(0);
		ComponentInstance inst = this.innerGetComInstance(holder);
		return inst.get();
	}

	@Override
	public ApplicationContext getApplicationContext() {
		return mCore.context;
	}

	@Override
	public String getProperty(String selector) throws StarterException {
		if (selector == null) {
			return "";
		}
		selector = selector.trim();
		final String prefix = "${";
		final String suffix = "}";
		if (selector.startsWith(prefix) && selector.endsWith(suffix)) {
			String name = selector.substring(prefix.length(), selector.length() - suffix.length()).trim();
			String value = mCore.properties.getProperty(name, true);
			return value;
		}
		return selector;
	}

	@Override
	public ComponentInstance getWithHolder(ComponentHolder holder) throws StarterException {
		return this.innerGetComInstance(holder);
	}

	@Override
	public ComponentInstance getById(String id) throws StarterException {
		ComponentHolder holder = mCore.components.get(id);
		return this.innerGetComInstance(holder);
	}

	@Override
	public InjectionExt ext() {
		return mExt;
	}

	@Override
	public ApplicationContext getContext() {
		return mCore.context;
	}

	@Override
	public LifeManager getLifeManager() {
		return mLifeManager;
	}

	@Override
	public void complete() {
		InjectionCompleter.complete(this, this.mComTable);
	}
}
