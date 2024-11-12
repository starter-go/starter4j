package com.bitwormhole.starter4j.application.boot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bitwormhole.starter4j.application.Module;
import com.bitwormhole.starter4j.base.StarterException;

public class ModuleLoader {

	final static Logger logger = LoggerFactory.getLogger(ModuleLoader.class);

	public Module[] load(Module m0) {
		Loading ld = new Loading();
		this.load(ld, m0, 0);
		Module[] result = ld.result();
		for (Module m : result) {
			String msg = "use module [" + m.name() + "@" + m.version() + "-r" + m.revision() + "]";
			logger.info(msg);
		}
		return result;
	}

	private static class ModuleRef {

		final Map<String, Module> versions;
		final String module_name;

		int refcount;

		ModuleRef(String name) {
			this.versions = new HashMap<>();
			this.module_name = name;
		}

		Module get() {

			final Map<String, Module> tab = this.versions;
			final List<String> keys = new ArrayList<>(tab.keySet());
			final int size = keys.size();
			Collections.sort(keys);

			if (size < 1) {
				throw new StarterException("no version of module [" + this.module_name + "]");
			}

			if (size > 1) {
				logger.warn("more than one version of module [" + this.module_name + "]:");
				for (String key : keys) {
					Module m = tab.get(key);
					logger.warn("    version:" + m.version() + "-r" + m.revision() + "");
				}
			}

			return tab.get(keys.get(0));
		}

		public void put(Module m, int dep_weight) {
			final String key = m.version() + "-r" + m.revision();
			final Module older = this.versions.get(key);
			if (older == null) {
				this.versions.put(key, m);
			}
			this.refcount += dep_weight;
		}
	}

	private static class Loading {

		final Map<String, ModuleRef> modules;
		int depthLimit;

		Loading() {
			this.depthLimit = 32;
			this.modules = new HashMap<>();
		}

		Module[] result() {
			Collection<ModuleRef> src = this.modules.values();
			List<ModuleRef> mid = new ArrayList<>(src);
			List<Module> dst = new ArrayList<>();
			mid.sort((a, b) -> {
				final int diff = b.refcount - a.refcount;
				if (diff == 0) {
					return 0;
				}
				return (diff < 0) ? -1 : 1;
			});
			for (ModuleRef ref : mid) {
				dst.add(ref.get());
			}
			return dst.toArray(new Module[0]);
		}

		public void put(Module m, int dep_weight) {
			final String key = m.name();
			ModuleRef ref = this.modules.get(key);
			if (ref == null) {
				ref = new ModuleRef(key);
				this.modules.put(key, ref);
			}
			ref.put(m, dep_weight);
		}
	}

	private void load(Loading ld, Module m, int depth) {

		if (ld == null || m == null) {
			return;
		}

		if (depth > ld.depthLimit) {
			String mod_name = m.name() + "@" + m.version();
			throw new StarterException("the module dep path is too deep, module:" + mod_name);
		}

		ld.put(m, depth);

		Module[] deps = m.dependencies();
		if (deps == null) {
			return;
		}

		for (Module dep : deps) {
			this.load(ld, dep, depth + 1);
		}
	}
}
