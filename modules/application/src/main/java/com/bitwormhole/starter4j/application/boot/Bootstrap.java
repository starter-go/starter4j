package com.bitwormhole.starter4j.application.boot;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.bitwormhole.starter4j.application.Module;
import com.bitwormhole.starter4j.application.Tables;
import com.bitwormhole.starter4j.application.arguments.Arguments;
import com.bitwormhole.starter4j.base.Safe;
import com.bitwormhole.starter4j.base.SafeMode;
import com.bitwormhole.starter4j.base.StarterException;

public final class Bootstrap {

	final static Logger logger = LoggerFactory.getLogger(Bootstrap.class);

	public final AppContextCore core;

	private Bootstrap(AppContextCore acc) {
		this.core = acc;
	}

	public static void run(Module m, BootOptions opt) throws StarterException {
		try {
			AppContextCore acc = new AppContextCore();
			Bootstrap b = new Bootstrap(acc);
			b.init(opt);
			b.run(m);
		} finally {
			logger.info("The End");
		}
	}

	private void init(BootOptions opt) {

		if (opt == null) {
			opt = new BootOptions();
		}

		final Tables tab = new Tables();
		final SafeMode safe = Safe.normalize(opt.mode);

		tab.arguments = new Arguments(opt.args);
		tab.attributes = opt.attributes;
		tab.environment = opt.environment;
		tab.parameters = opt.parameters;
		tab.properties = opt.properties;

		final Tables tab2 = tab.complete(safe).clone(safe);

		this.core.mode = safe;
		this.core.set(tab2);
	}

	private void run(Module m) throws StarterException {

		this.core.moduleMain = m;

		this.loadModules();
		this.loadResources();
		this.loadProperties();

		this.loadAttributes();
		this.loadEnvironment();
		this.loadParameters();

		this.loadContext();
		this.loadComponents();
		this.loadBanner();

		this.runMainLoop();
	}

	private void loadModules() throws StarterException {
		ModuleLoader loader = new ModuleLoader();
		core.modules = loader.load(core.moduleMain);
	}

	private void loadResources() throws StarterException {
		ResLoader loader = new ResLoader();
		loader.load(this.core);
	}

	private void loadProperties() throws StarterException {
		PropertyLoader loader = new PropertyLoader();
		loader.load(this.core);
	}

	private void loadParameters() throws StarterException {
	}

	private void loadEnvironment() throws StarterException {
		EnvironmentLoader loader = new EnvironmentLoader();
		loader.load(this.core);
	}

	private void loadBanner() throws StarterException {
		BannerLoader loader = new BannerLoader();
		loader.loadAndLog(this.core);
	}

	private void loadAttributes() throws StarterException {
		AttrLoader loader = new AttrLoader();
		loader.load(this.core);
	}

	private void loadContext() throws StarterException {
		ContextLoader loader = new ContextLoader();
		loader.load(this.core);
	}

	private void loadComponents() throws StarterException {
		SingletonComponentLoader loader = new SingletonComponentLoader();
		loader.load(this.core);
	}

	private void runMainLoop() throws StarterException {
		MainLoopRunner.run(this.core);
	}
}
