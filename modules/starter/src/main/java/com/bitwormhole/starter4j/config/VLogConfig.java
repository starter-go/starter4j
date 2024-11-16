package com.bitwormhole.starter4j.config;

import com.bitwormhole.starter4j.application.ComponentRegistration;
import com.bitwormhole.starter4j.application.ComponentRegistry;
import com.bitwormhole.starter4j.application.ComponentRegistryFunc;
import com.bitwormhole.starter4j.application.Life;
import com.bitwormhole.starter4j.application.LifeCycle;
import com.bitwormhole.starter4j.application.components.ComponentSelector;
import com.bitwormhole.starter4j.base.StarterException;

public class VLogConfig implements ComponentRegistryFunc {

	@Override
	public void invoke(ComponentRegistry cr) throws StarterException {
		this.exportConfiguration(cr);
	}

	private void exportConfiguration(ComponentRegistry cr) throws StarterException {

		ComponentRegistration r = cr.newRegistration();

		r.id = LoggerConfig.class.getName();
		r.functionNew = () -> {
			return new LoggerConfig();
		};
		r.functionInject = (ie, obj) -> {
			final ComponentSelector cs = new ComponentSelector();
			final LoggerConfig o = (LoggerConfig) obj;

			o.formattersDefaultFormat = ie.getString(cs.PROPERTY("vlog.formatters.default.format"));
			o.groupsMainFilters = ie.getString(cs.PROPERTY("vlog.groups.main.filters"));
			o.level = ie.getString(cs.PROPERTY("vlog.level"));
			o.main = ie.getString(cs.PROPERTY("vlog.main"));
		};

		cr.register(r);
	}

	private static class LoggerConfig implements LifeCycle {
		String formattersDefaultFormat = "";
		String groupsMainFilters = "";
		String level = "";
		String main = "";

		private void apply() {

		}

		@Override
		public Life life() {
			Life l = new Life();
			l.order = -2;
			l.onCreate = () -> {
				this.apply();
			};
			return l;
		}
	}
}
