package com.bitwormhole.starter4j.application.boot;

import java.util.Map;

import com.bitwormhole.starter4j.application.environment.Environment;

final class EnvironmentLoader {

	public void load(AppContextCore core) {

		final Environment env = core.environment;
		final Map<String, String> framework = env.exportTo(null);
		final Map<String, String> sys = System.getenv();

		// from system
		env.importFrom(sys);

		// from framework
		env.importFrom(framework);

	}

}
