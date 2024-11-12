package com.bitwormhole.starter4j.application;

import com.bitwormhole.starter4j.application.arguments.Arguments;
import com.bitwormhole.starter4j.application.attributes.Attributes;
import com.bitwormhole.starter4j.application.components.Components;
import com.bitwormhole.starter4j.application.environment.Environment;
import com.bitwormhole.starter4j.application.parameters.Parameters;
import com.bitwormhole.starter4j.application.properties.Properties;
import com.bitwormhole.starter4j.application.resources.Resources;
import com.bitwormhole.starter4j.base.Safe;
import com.bitwormhole.starter4j.base.SafeMode;

public class Tables {

	public Arguments arguments;
	public Attributes attributes;
	public Environment environment;
	public Parameters parameters;
	public Properties properties;
	public Resources resources;
	public Components components;

	public Tables() {
	}

	public Tables(SafeMode mode) {
		this.complete(mode);
	}

	public void set(Tables src) {
		if (src == null) {
			return;
		}
		this.arguments = src.arguments;
		this.attributes = src.attributes;
		this.environment = src.environment;
		this.parameters = src.parameters;
		this.properties = src.properties;
		this.resources = src.resources;
		this.components = src.components;
	}

	public Tables complete(SafeMode mode) {

		mode = Safe.normalize(mode);

		if (arguments == null) {
			arguments = new Arguments();
		}

		if (attributes == null) {
			attributes = Attributes.Table.create(mode);
		}

		if (environment == null) {
			environment = Environment.Table.create(mode);
		}

		if (parameters == null) {
			parameters = Parameters.Table.create(mode);
		}

		if (properties == null) {
			properties = Properties.Table.create(mode);
		}

		if (resources == null) {
			resources = Resources.Table.create(mode);
		}

		if (components == null) {
			components = Components.Table.create(mode);
		}

		return this;
	}

	public Tables clone(SafeMode mode) {

		Tables src = this;
		Tables dst = new Tables();
		mode = Safe.normalize(mode);
		dst = dst.complete(mode);

		final Arguments args = src.arguments;
		final Attributes attrs = src.attributes;
		final Environment env = src.environment;
		final Parameters params = src.parameters;
		final Properties props = src.properties;
		final Resources res = src.resources;
		final Components comp = src.components;

		if (args != null) {
			dst.arguments.setArgs(args.getArgs());
		}

		if (attrs != null) {
			dst.attributes.importFrom(attrs.exportTo(null));
		}

		if (env != null) {
			dst.environment.importFrom(env.exportTo(null));
		}

		if (params != null) {
			dst.parameters.importFrom(params.exportTo(null));
		}

		if (props != null) {
			dst.properties.importFrom(props.exportTo(null));
		}

		if (res != null) {
			dst.resources.importFrom(res.exportTo(null));
		}

		if (comp != null) {
			dst.components.importFrom(comp.exportTo(null));
		}

		return dst;
	}

}
