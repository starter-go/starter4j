package com.bitwormhole.starter4j;

import java.util.ArrayList;
import java.util.List;

import com.bitwormhole.starter4j.application.ComponentRegistration;
import com.bitwormhole.starter4j.application.ComponentRegistry;
import com.bitwormhole.starter4j.application.ComponentRegistryFunc;
import com.bitwormhole.starter4j.base.StarterException;
import com.bitwormhole.starter4j.config.DebugConfig;
import com.bitwormhole.starter4j.config.VLogConfig;

class ThisModule implements ComponentRegistryFunc {

	private ThisModule() {
	}

	public static ComponentRegistryFunc components() {
		return new ThisModule();
	}

	@Override
	public void invoke(ComponentRegistry cr) throws StarterException {
		List<ComponentRegistryFunc> list = new ArrayList<>();
		list.add(new DebugConfig());
		list.add(new VLogConfig());
		for (ComponentRegistryFunc fn : list) {
			fn.invoke(cr);
		}
	}

	public static class Example {

		public int value1;

	}

	private void com_example(ComponentRegistry cr) throws StarterException {

		Class<?> cl = Example.class;
		ComponentRegistration reg = cr.newRegistration();

		reg.id = cl.getSimpleName();
		reg.aliases = "";
		reg.classes = cl.getName();
		reg.scope = ComponentRegistration.SCOPE_SINGLETON;

		reg.functionNew = () -> {
			return new Example();
		};
		reg.functionInject = (injection, instance) -> {
			final Example inst = (Example) instance;

			// injection .
			inst.value1 = 0;

		};

		reg.registry = cr;
		cr.register(reg);
	}
}
