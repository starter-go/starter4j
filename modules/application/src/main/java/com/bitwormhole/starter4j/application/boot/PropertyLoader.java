package com.bitwormhole.starter4j.application.boot;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bitwormhole.starter4j.application.Module;
import com.bitwormhole.starter4j.application.properties.Properties;
import com.bitwormhole.starter4j.application.resources.Resource;
import com.bitwormhole.starter4j.base.SafeMode;
import com.bitwormhole.starter4j.base.StarterException;

public class PropertyLoader {

	static final Logger logger = LoggerFactory.getLogger(PropertyLoader.class);

	public void load(AppContextCore core) {
		final SafeMode mode = core.mode;
		final Properties dst = Properties.Table.create(mode);
		String profile = null;
		try {
			this.loadWithProfile(null, core, dst);
			profile = dst.getProperty("application.profiles.active", "default");
			this.loadWithProfile(profile, core, dst);
		} catch (IOException e) {
			throw new StarterException(e);
		}
		core.profile = profile;
		core.properties = dst;
	}

	private void loadWithProfile(String profile, AppContextCore core, Properties dst) throws IOException {

		Module[] src = core.modules;
		String name = "application.properties";

		if (profile != null) {
			profile = profile.trim();
			if (profile.length() > 0) {
				name = "application-" + profile + ".properties";
			}
		}

		for (Module m : src) {
			this.loadFromModule(name, m, dst);
		}

		this.loadFromContext(core, dst);
		this.loadFromArgs(core, dst);
	}

	private void loadFromModule(String name, Module mod, Properties dst) throws IOException {
		Resource res = mod.resources().getRes(name, false);
		if (res == null) {
			return;
		}
		String text = res.readText();
		Properties.Table.parse(text, dst);
	}

	private void loadFromContext(AppContextCore core, Properties dst) throws IOException {
		Properties src = core.properties;
		if (src == null) {
			return;
		}
		dst.importFrom(src.exportTo(null));
	}

	private void loadFromArgs(AppContextCore core, Properties dst) throws IOException {
		String[] args = core.args;
		if (args == null) {
			args = core.arguments.getArgs();
		}
		if (args == null) {
			return;
		}
		for (String a : args) {
			this.parsePropertyFromArgument(a, dst);
		}
	}

	private void parsePropertyFromArgument(String arg, Properties dst) {
		if (arg == null || dst == null) {
			return;
		}
		final String mk1 = "--";
		final String mk2 = "=";
		arg = arg.trim();
		final int i1 = arg.indexOf(mk1);
		final int i2 = arg.indexOf(mk2);
		if (i1 == 0 && i1 < i2) {
			String name = arg.substring(i1 + mk1.length(), i2).trim();
			String value = arg.substring(i2 + 1).trim();
			dst.setProperty(name, value);
		}
	}
}
