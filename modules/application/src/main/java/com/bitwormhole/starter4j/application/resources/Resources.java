package com.bitwormhole.starter4j.application.resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import com.bitwormhole.starter4j.base.SafeMode;

public interface Resources {

	public static class Table {
		public static Resources create(SafeMode mode) {
			return new ResourceTable(mode);
		}
	}

	SafeMode mode();

	String[] paths();

	Resource getRes(String path, boolean required);

	byte[] readBinary(String path) throws IOException;

	String readText(String path) throws IOException;

	InputStream open(String path) throws IOException;

	void importFrom(Map<String, Resource> src);

	Map<String, Resource> exportTo(Map<String, Resource> dst);
}
