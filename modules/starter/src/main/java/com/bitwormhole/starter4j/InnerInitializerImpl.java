package com.bitwormhole.starter4j;

import com.bitwormhole.starter4j.application.Module;
import com.bitwormhole.starter4j.application.Tables;
import com.bitwormhole.starter4j.application.attributes.Attributes;
import com.bitwormhole.starter4j.application.boot.BootOptions;
import com.bitwormhole.starter4j.application.boot.Bootstrap;
import com.bitwormhole.starter4j.application.environment.Environment;
import com.bitwormhole.starter4j.application.parameters.Parameters;
import com.bitwormhole.starter4j.application.properties.Properties;
import com.bitwormhole.starter4j.base.Safe;
import com.bitwormhole.starter4j.base.SafeMode;

public class InnerInitializerImpl implements Initializer {

	private boolean mEnableThrowing;
	private Module mMainModule;
	private SafeMode mMode;
	private final Tables mCollections;

	InnerInitializerImpl() {
		SafeMode mode = SafeMode.Fast;
		Tables tab = new Tables(mode);

		this.mMode = mode;
		this.mCollections = tab;
		this.mEnableThrowing = true;
	}

	@Override
	public Initializer setMainModule(Module m) {
		this.mMainModule = m;
		return this;
	}

	@Override
	public Initializer enableToThrowException(boolean enable) {
		this.mEnableThrowing = enable;
		return this;
	}

	@Override
	public Initializer setArguments(String[] args) {
		this.mCollections.arguments.setArgs(args);
		return this;
	}

	@Override
	public Attributes getAttributes() {
		return this.mCollections.attributes;
	}

	@Override
	public Environment getEnvironment() {
		return this.mCollections.environment;
	}

	@Override
	public Parameters getParameters() {
		return this.mCollections.parameters;
	}

	@Override
	public Properties getProperties() {
		return this.mCollections.properties;
	}

	@Override
	public Exception run() {
		try {
			Tables tab = this.mCollections;
			SafeMode mode = this.mMode;
			Module mod = this.mMainModule;
			BootOptions opt = new BootOptions();
			if (mod == null) {
				return null;
			}
			mode = Safe.normalize(mode);
			tab = tab.complete(mode).clone(mode);

			opt.mode = mode;
			opt.args = tab.arguments.getArgs();
			opt.attributes = tab.attributes;
			opt.environment = tab.environment;
			opt.properties = tab.properties;
			opt.parameters = tab.parameters;

			Bootstrap.run(mod, opt);

		} catch (Exception e) {
			e.printStackTrace();
			if (!this.mEnableThrowing) {
				return e;
			}
			final RuntimeException e2;
			if (e instanceof RuntimeException) {
				e2 = (RuntimeException) e;
			} else {
				e2 = new RuntimeException(e);
			}
			throw e2;
		}
		return null;
	}

	@Override
	public Initializer setMode(SafeMode mode) {
		this.mMode = mode;
		return this;
	}
}
