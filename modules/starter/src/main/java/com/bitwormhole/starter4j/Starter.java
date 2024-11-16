package com.bitwormhole.starter4j;

import com.bitwormhole.starter4j.application.Module;

public final class Starter {

	private Starter() {
	}

	public static Initializer init(String[] args) {
		Initializer i = new InnerInitializerImpl();
		i.setArguments(args);
		return i;
	}

	public static Module module() {
		return ThisModule.module();
	}
}
