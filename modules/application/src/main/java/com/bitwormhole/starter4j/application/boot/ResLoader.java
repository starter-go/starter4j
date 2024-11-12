package com.bitwormhole.starter4j.application.boot;

import java.util.Map;

import com.bitwormhole.starter4j.application.Module;
import com.bitwormhole.starter4j.application.resources.Resource;
import com.bitwormhole.starter4j.application.resources.Resources;
import com.bitwormhole.starter4j.base.SafeMode;

public class ResLoader {

	public void load(AppContextCore core) {
		SafeMode mode = core.mode;
		Module[] src = core.modules;
		Resources dst = Resources.Table.create(mode);
		for (Module mod : src) {
			Map<String, Resource> tmp = mod.resources().exportTo(null);
			dst.importFrom(tmp);
		}
		core.resources = dst;
	}

}
