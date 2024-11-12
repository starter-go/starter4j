package com.bitwormhole.starter4j.application.boot;

import com.bitwormhole.starter4j.application.Injection;
import com.bitwormhole.starter4j.application.Module;
import com.bitwormhole.starter4j.application.components.ComponentHolder;
import com.bitwormhole.starter4j.application.components.ComponentTableBuilder;
import com.bitwormhole.starter4j.application.components.Components;
import com.bitwormhole.starter4j.application.components.Scope;

public class SingletonComponentLoader {

	private void load1(AppContextCore core) {
		Module[] src = core.modules;
		ComponentTableBuilder builder = new ComponentTableBuilder();
		for (Module m : src) {
			m.registerComponents(builder);
		}
		core.components.importFrom(builder.create());
	}

	private void load2(AppContextCore core) {
		Injection inj = core.injections.getSingletonInjection();
		Components src = core.components;
		String[] ids = src.ids();
		for (String id : ids) {
			ComponentHolder holder = src.get(id);
			if (holder.info().scope() == Scope.Singleton) {
				inj.getById(id);
			}
		}
		inj.complete();
	}

	public void load(AppContextCore core) {
		this.load1(core);
		this.load2(core);
	}
}
