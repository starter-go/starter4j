package com.bitwormhole.starter4j;

import com.bitwormhole.starter4j.application.Module;
import com.bitwormhole.starter4j.application.ModuleBuilder;

public final class Starter {

	private static final String theModuleName = Starter.class.getName();
	private static final String theModuleVersion = "0.0.1";
	private static final int theModuleRevision = 1;

	private Starter() {
	}

	public static Initializer init(String[] args) {
		Initializer i = new InnerInitializerImpl();
		i.setArguments(args);

		return i;
	}

	public static Module module() {
		ModuleBuilder mb = new ModuleBuilder();
		mb.setName(theModuleName);
		mb.setVersion(theModuleVersion);
		mb.setRevision(theModuleRevision);

		mb.setResources(SrcMainRes.resources());
		mb.setComponents(ThisModule.components());

		return mb.create();
	}
}
