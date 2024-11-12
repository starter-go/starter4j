package com.bitwormhole.starter4j.application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bitwormhole.starter4j.application.resources.Resources;

class InnerModuleImpl implements Module {

	private final String mName;
	private final String mVersion;
	private final int mRevision;
	private final Resources mRes;
	private final Module[] mModules;
	private final ComponentRegistryFunc mCRF;

	private InnerModuleImpl(Builder b) {
		this.mName = b.name;
		this.mVersion = b.ver;
		this.mRevision = b.rev;
		this.mRes = b.res;
		this.mModules = b.deps.toArray(new Module[0]);
		this.mCRF = b.crf;
	}

	public static class Builder {
		public String name; // NOT null
		public String ver; // NOT null
		public int rev; // NOT null
		public Resources res; // NOT null
		public ComponentRegistryFunc crf; // maybe null

		public final List<Module> deps;

		public Builder() {
			this.deps = new ArrayList<>();
		}

		private void prepareCreate() {
			if (name == null) {
				name = "undefined";
			}
			if (ver == null) {
				ver = "undefined";
			}
			if (res == null) {
				res = Resources.Table.create(null);
			}
		}

		public Module create() {
			this.prepareCreate();
			return new InnerModuleImpl(this);
		}
	}

	@Override
	public String name() {
		return mName;
	}

	@Override
	public String version() {
		return mVersion;
	}

	@Override
	public int revision() {
		return mRevision;
	}

	@Override
	public Module[] dependencies() {
		Module[] src = this.mModules;
		return Arrays.copyOf(src, src.length);
	}

	@Override
	public Resources resources() {
		return mRes;
	}

	@Override
	public void registerComponents(ComponentRegistry cr) {
		ComponentRegistryFunc crf = this.mCRF;
		if (cr == null || crf == null) {
			return;
		}
		crf.invoke(cr);
	}
}
