package com.bitwormhole.starter4j;

import com.bitwormhole.starter4j.application.ComponentRegistry;
import com.bitwormhole.starter4j.application.ComponentRegistryFunc;
import com.bitwormhole.starter4j.application.Module;
import com.bitwormhole.starter4j.application.ModuleBuilder;
import com.bitwormhole.starter4j.base.StarterException;
import com.bitwormhole.starter4j.config.ConfigAll;

final class ThisModule implements ComponentRegistryFunc {

	private static final String theModuleName = Starter.class.getName();
	private static final String theModuleVersion = "0.0.1";
	private static final int theModuleRevision = 1;

	private ThisModule() {
	}

	public static Module module() {

		ModuleBuilder mb = new ModuleBuilder();
		mb.setName(theModuleName);
		mb.setVersion(theModuleVersion);
		mb.setRevision(theModuleRevision);

		mb.setResources(SrcMainRes.resources());
		mb.setComponents(new ThisModule());

		return mb.create();
	}

	@Override
	public void invoke(ComponentRegistry cr) throws StarterException {
		ConfigAll.registerAll(cr);
	}
}
