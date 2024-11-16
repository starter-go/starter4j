package com.bitwormhole.starter4j.application.resources;

import java.io.IOException;
import java.io.InputStream;

public interface Resource {

	String path();

	String simpleName();

	long size();

	byte[] readBinary() throws IOException;

	String readText() throws IOException;

	InputStream open() throws IOException;

}
