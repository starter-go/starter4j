package com.bitwormhole.starter4j.application;

import java.util.List;

import com.bitwormhole.starter4j.application.resources.EmbeddedRes;
import com.bitwormhole.starter4j.application.resources.EmbeddedResources;
import com.bitwormhole.starter4j.application.resources.Resources;

public class ModuleBuilder {

	/*
	 * private String name; private String version; private int revision; private
	 * Resources res; private List<Module> deps; private Object registry;
	 */

	private final InnerModuleImpl.Builder inner;

	public ModuleBuilder() {
		this.inner = new InnerModuleImpl.Builder();
	}

	public ModuleBuilder setName(String name) {
		this.inner.name = name;
		return this;
	}

	public ModuleBuilder setVersion(String ver) {
		this.inner.ver = ver;
		return this;
	}

	public ModuleBuilder setRevision(int rev) {
		this.inner.rev = rev;
		return this;
	}

	public ModuleBuilder setComponents(ComponentRegistryFunc fn) {
		this.inner.crf = fn;
		return this;
	}

	public ModuleBuilder setResources(Resources res) {
		this.inner.res = res;
		return this;
	}

	public ModuleBuilder setEmbedResources(List<EmbeddedRes> src) {
		// SafeMode mode = SafeMode.Safe;
		// this.inner.res = new ClasspathTreeResources(at, mode);

		this.inner.res = EmbeddedResources.create(src);
		return this;
	}

	public ModuleBuilder depend(Module m) {
		this.inner.deps.add(m);
		return this;
	}

	public ModuleBuilder depend(Module... mlist) {
		if (mlist == null) {
			return this;
		}
		for (Module m : mlist) {
			if (m == null) {
				continue;
			}
			this.inner.deps.add(m);
		}
		return this;
	}

	public Module create() {
		return this.inner.create();
	}
}
