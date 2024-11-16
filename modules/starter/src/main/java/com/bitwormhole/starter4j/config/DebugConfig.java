package com.bitwormhole.starter4j.config;

import java.util.Arrays;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bitwormhole.starter4j.application.ApplicationContext;
import com.bitwormhole.starter4j.application.ComponentRegistration;
import com.bitwormhole.starter4j.application.ComponentRegistry;
import com.bitwormhole.starter4j.application.ComponentRegistryFunc;
import com.bitwormhole.starter4j.application.ComponentTemplate;
import com.bitwormhole.starter4j.application.ComponentTemplate.RegistrationT;
import com.bitwormhole.starter4j.application.Life;
import com.bitwormhole.starter4j.application.LifeCycle;
import com.bitwormhole.starter4j.application.components.ComponentSelector;
import com.bitwormhole.starter4j.base.StarterException;

public class DebugConfig implements ComponentRegistryFunc {

	final static Logger logger = LoggerFactory.getLogger(DebugConfig.class);

	final static String sp4 = "    ";

	@Override
	public void invoke(ComponentRegistry cr) throws StarterException {
		// this.exportDebugger(cr);
		this.exportDebuggerV2(cr);
	}

	private void exportDebugger(ComponentRegistry cr) throws StarterException {

		ComponentRegistration r = cr.newRegistration();

		r.id = Debugger.class.getName();
		r.functionNew = () -> {
			return new Debugger();
		};
		r.functionInject = (ie, obj) -> {
			final ComponentSelector cs = new ComponentSelector();
			final Debugger o = (Debugger) obj;
			o.enabled = ie.getBoolean(cs.PROPERTY("debug.enabled"));
			o.en_log__args = ie.getBoolean(cs.PROPERTY("debug.log-arguments"));
			o.en_log___env = ie.getBoolean(cs.PROPERTY("debug.log-environment"));
			o.en_log_props = ie.getBoolean(cs.PROPERTY("debug.log-properties"));
			o.ac = ie.getContext();
		};

		cr.register(r);
	}

	private void exportDebuggerV2(ComponentRegistry cr) {
		ComponentTemplate ct = new ComponentTemplate(cr);
		RegistrationT<Debugger> rt = ct.component(Debugger.class);
		rt.setId(Debugger.class);
		rt.onNew(() -> {
			return new Debugger();
		});
		rt.onInject((ie, o) -> {
			final ComponentSelector cs = new ComponentSelector();
			o.enabled = ie.getBoolean(cs.PROPERTY("debug.enabled"));
			o.en_log__args = ie.getBoolean(cs.PROPERTY("debug.log-arguments"));
			o.en_log___env = ie.getBoolean(cs.PROPERTY("debug.log-environment"));
			o.en_log_props = ie.getBoolean(cs.PROPERTY("debug.log-properties"));
			o.ac = ie.getContext();
		});
		rt.register();
	}

	// Debugger 用于输出调试信息
	private static class Debugger implements LifeCycle {

		boolean enabled;
		boolean en_log__args;
		boolean en_log___env;
		boolean en_log_props;
		ApplicationContext ac;

		private void doLogProperties() {
			Map<String, String> props = this.ac.getProperties().exportTo(null);
			if (props == null) {
				return;
			}
			String[] keys = props.keySet().toArray(new String[0]);
			Arrays.sort(keys);
			logger.info("properties:");
			for (String k : keys) {
				String v = props.get(k);
				logger.info(sp4 + k + " = " + v);
			}
		}

		private void doLogEnv() {
			Map<String, String> env = this.ac.getEnvironment().exportTo(null);
			if (env == null) {
				return;
			}
			String[] keys = env.keySet().toArray(new String[0]);
			Arrays.sort(keys);
			logger.info("environment:");
			for (String k : keys) {
				String v = env.get(k);
				logger.info(sp4 + k + " = " + v);
			}
		}

		private void doLogArgs() {
			String[] args = this.ac.getArguments().getArgs();
			if (args == null) {
				return;
			}
			logger.info("arguments:");
			for (String a : args) {
				logger.info(sp4 + a);
			}
		}

		private void logInfo() {

			logger.info("to display properties, set {debug.enabled=true} & {debug.log-properties=true}");

			if (!this.enabled) {
				return;
			}

			if (this.en_log__args) {
				this.doLogArgs();
			}

			if (this.en_log___env) {
				this.doLogEnv();
			}

			if (this.en_log_props) {
				this.doLogProperties();
			}
		}

		@Override
		public Life life() {
			Life l = new Life();
			l.order = -1;
			l.onCreate = () -> {
				this.logInfo();
			};
			return l;
		}
	}
}
