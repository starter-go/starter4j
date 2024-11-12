package com.bitwormhole.starter4j.application.boot;

import com.bitwormhole.starter4j.application.ApplicationContext;
import com.bitwormhole.starter4j.application.InjectionManager;
import com.bitwormhole.starter4j.application.Module;
import com.bitwormhole.starter4j.application.Tables;
import com.bitwormhole.starter4j.base.SafeMode;

public final class AppContextCore extends Tables {

	public SafeMode mode;
	public String profile;
	public String[] args;
	public Module[] modules;
	public Module moduleMain;
	public ApplicationContext context;
	public InjectionManager injections;
	public String banner;

	public AppContextCore() {
		this.injections = new InjectionManagerImpl(this);
	}

}
