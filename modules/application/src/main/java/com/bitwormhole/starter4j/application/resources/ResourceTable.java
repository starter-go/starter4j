package com.bitwormhole.starter4j.application.resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.bitwormhole.starter4j.base.Paths;
import com.bitwormhole.starter4j.base.Safe;
import com.bitwormhole.starter4j.base.SafeMode;

class ResourceTable implements Resources {

	private final Map<String, Resource> table;
	private final SafeMode mode;

	ResourceTable(SafeMode m) {
		m = Safe.normalize(m);
		Map<String, Resource> t = new HashMap<>();
		if (m == SafeMode.Safe) {
			t = Collections.synchronizedMap(t);
		}
		this.table = t;
		this.mode = m;
	}

	private static String normalizePath(String path) {
		String[] elist = Paths.path2elements(path);
		elist = Paths.resolve(elist);
		if (elist.length > 0) {
			final String str0 = elist[0];
			if ("res:".equals(str0) || "resource:".equals(str0) || "resources:".equals(str0)) {
				return Paths.elements2path(elist, 1, elist.length - 1);
			}
		}
		return Paths.elements2path(elist);
	}

	@Override
	public String[] paths() {
		return table.keySet().toArray(new String[0]);
	}

	@Override
	public Resource getRes(String path, boolean required) {
		path = normalizePath(path);
		Resource res = table.get(path);
		if (res == null) {
			if (required) {
				throw new RuntimeException("no required resource, with path [" + path + "]");
			}
		}
		return res;
	}

	@Override
	public byte[] readBinary(String path) throws IOException {
		boolean required = true;
		Resource res = this.getRes(path, required);
		return res.readBinary();
	}

	@Override
	public String readText(String path) throws IOException {
		boolean required = true;
		Resource res = this.getRes(path, required);
		return res.readText();
	}

	@Override
	public InputStream open(String path) throws IOException {
		boolean required = true;
		Resource res = this.getRes(path, required);
		return res.open();
	}

	@Override
	public void importFrom(Map<String, Resource> src) {
		if (src == null) {
			return;
		}
		this.table.putAll(src);
	}

	@Override
	public Map<String, Resource> exportTo(Map<String, Resource> dst) {
		if (dst == null) {
			dst = new HashMap<>();
		}
		dst.putAll(this.table);
		return dst;
	}

	@Override
	public SafeMode mode() {
		return this.mode;
	}
}
