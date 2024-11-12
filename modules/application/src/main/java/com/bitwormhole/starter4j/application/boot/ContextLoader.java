package com.bitwormhole.starter4j.application.boot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bitwormhole.starter4j.application.ApplicationContext;
import com.bitwormhole.starter4j.application.Injection;
import com.bitwormhole.starter4j.application.Module;
import com.bitwormhole.starter4j.application.arguments.Arguments;
import com.bitwormhole.starter4j.application.attributes.Attributes;
import com.bitwormhole.starter4j.application.components.ComponentHolder;
import com.bitwormhole.starter4j.application.components.ComponentInfo;
import com.bitwormhole.starter4j.application.components.ComponentInstance;
import com.bitwormhole.starter4j.application.components.Scope;
import com.bitwormhole.starter4j.application.environment.Environment;
import com.bitwormhole.starter4j.application.parameters.Parameters;
import com.bitwormhole.starter4j.application.properties.Properties;
import com.bitwormhole.starter4j.application.resources.Resources;
import com.bitwormhole.starter4j.base.SafeMode;
import com.bitwormhole.starter4j.base.StarterException;

public class ContextLoader {

	private static class MyComOpenParams {

		private final AppContextCore core;
		private Injection cached;

		MyComOpenParams(AppContextCore _core) {
			this.core = _core;
		}

		Injection getInjection(Scope scope) {
			Injection in = this.cached;
			if (in == null) {
				in = core.injections.selectInjection(scope);
				this.cached = in;
			}
			return in;
		}
	}

	private static class MyContextFacade implements ApplicationContext {

		private final AppContextCore core;

		public MyContextFacade(AppContextCore _core) {
			this.core = _core;
		}

		@Override
		public void close() throws IOException {
			// TODO Auto-generated method stub
			// throw new UnsupportedOperationException("Unimplemented method 'close'");
		}

		@Override
		public SafeMode mode() {
			return core.mode;
		}

		@Override
		public Injection newInjection(Scope scope) {
			return core.injections.selectInjection(scope);
		}

		@Override
		public Arguments getArguments() {
			return core.arguments;
		}

		@Override
		public Attributes getAttributes() {
			return core.attributes;
		}

		@Override
		public Environment getEnvironment() {
			return core.environment;
		}

		@Override
		public Parameters getParameters() {
			return core.parameters;
		}

		@Override
		public Properties getProperties() {
			return core.properties;
		}

		@Override
		public Resources getResources() {
			return core.resources;
		}

		@Override
		public Module[] getModules() {
			Module[] src = core.modules;
			return Arrays.copyOf(src, src.length);
		}

		@Override
		public Module getMainModule() {
			return core.moduleMain;
		}

		private ComponentInstance openComInstance(ComponentHolder h, MyComOpenParams cop) {
			ComponentInstance ci = h.getInstance();
			if (ci.ready()) {
				return ci;
			}
			ComponentInfo info = h.info();
			Injection inj = cop.getInjection(info.scope());
			ci.inject(inj);
			return ci;
		}

		@Override
		public List<Object> selectComponents(String selector, List<Object> dst) {
			if (dst == null) {
				dst = new ArrayList<>();
			}
			final MyComOpenParams cop = new MyComOpenParams(core);
			final List<ComponentHolder> src = core.components.select(selector, null);
			for (ComponentHolder h : src) {
				ComponentInstance inst = this.openComInstance(h, cop);
				dst.add(inst.get());
			}
			return dst;
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> T selectComponent(String selector, Class<T> t) {
			List<ComponentHolder> hlist = core.components.select(selector, null);
			int count = hlist.size();
			if (count != 1) {
				String msg = "want only-one component (selector:" + selector + "), but have " + count;
				throw new StarterException(msg);
			}
			final MyComOpenParams cop = new MyComOpenParams(core);
			ComponentHolder h0 = hlist.get(0);
			ComponentInstance inst = this.openComInstance(h0, cop);
			Object obj = inst.get();
			return (T) obj;
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> T getComponent(String id, Class<T> t) {
			final MyComOpenParams cop = new MyComOpenParams(core);
			ComponentHolder h = core.components.get(id);
			ComponentInstance inst = this.openComInstance(h, cop);
			Object obj = inst.get();
			return (T) obj;
		}

		@Override
		public String[] listComponentIds() {
			return core.components.ids();
		}
	}

	public void load(AppContextCore core) {
		core.context = new MyContextFacade(core);
	}
}
