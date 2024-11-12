package com.bitwormhole.starter4j.application.resources;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bitwormhole.starter4j.base.IO;
import com.bitwormhole.starter4j.base.Paths;
import com.bitwormhole.starter4j.base.SafeMode;
import com.bitwormhole.starter4j.base.StarterException;

public class ClasspathTreeResources implements Resources {

	final static Logger logger = LoggerFactory.getLogger(ClasspathTreeResources.class);

	private final ResContext context;

	public ClasspathTreeResources(Class<?> at, SafeMode mode) {
		ResContext ctx = new ResContext(at, mode);
		ResLoader loader = new ResLoader(ctx);
		loader.load();
		this.context = ctx;
	}

	public static class ResContext {

		final Class<?> at;
		final SafeMode mode;
		final Map<String, Resource> all;

		ResFile tree; // the '.tree' file

		ResContext(Class<?> ref, SafeMode m) {

			Map<String, Resource> tab = new HashMap<>();
			if (m == SafeMode.Safe) {
				tab = Collections.synchronizedMap(tab);
			}

			this.all = tab;
			this.at = ref;
			this.mode = m;
		}

		InputStream open(ResFile file) throws IOException {
			if (file == null) {
				this.makeOpenException("[no_path]");
			}

			URL url = this.at.getResource(file.innerPath);
			if (url == null) {
				this.makeOpenException(file.innerPath);
			}
			InputStream in = url.openStream();
			if (in == null) {
				this.makeOpenException(file.innerPath);
			}
			return in;
		}

		void makeOpenException(String path) throws IOException {
			throw new IOException("cannot open resource at [" + path + "]");
		}
	}

	public static class ResMeta {
		long size;
	}

	private static class ResPath {

		String name;
		String pathInner;
		String pathOuter;

		public void init(ResContext ctx, String path) {

			final int prefix_parts = 2;
			String pkg = ctx.at.getPackageName();
			String cls = ctx.at.getSimpleName();

			String[] elist = Paths.path2elements(pkg + "/" + cls + "/" + path);
			elist = Paths.resolve(elist);

			this.pathInner = Paths.elements2path(elist);
			this.pathOuter = Paths.elements2path(elist, prefix_parts, elist.length - prefix_parts);
			this.name = elist[elist.length - 1];
		}
	}

	public static class ResFile {

		final ResContext context;
		final String innerPath;
		final String outerPath;
		final String name; // the simple-name
		final Resource facade;

		ResMeta meta;

		ResFile(ResContext ctx, String _path) {

			ResPath rp = new ResPath();
			rp.init(ctx, _path);

			this.context = ctx;
			this.innerPath = rp.pathInner;
			this.outerPath = rp.pathOuter;
			this.name = rp.name;
			this.facade = new ResFacade(this);
		}
	}

	public static class ResDir extends ResFile {

		// dir 是一个特殊的 file

		String[] items;

		ResDir(ResContext ctx, String _path) {
			super(ctx, _path);
		}
	}

	public static class ResFacade implements Resource {

		final ResFile file;

		ResFacade(ResFile f) {
			this.file = f;
		}

		ResMeta getMeta() {
			ResMeta meta = this.file.meta;
			if (meta == null) {
				meta = this.loadMeta();
				this.file.meta = meta;
			}
			return meta;
		}

		ResMeta loadMeta() {
			ResMeta meta = new ResMeta();
			try {
				byte[] data = this.readBinary();
				meta.size = data.length;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return meta;
		}

		@Override
		public String path() {
			return file.outerPath;
		}

		@Override
		public String simpleName() {
			return this.file.name;
		}

		@Override
		public long size() {
			return this.getMeta().size;
		}

		@Override
		public byte[] readBinary() throws IOException {
			InputStream in = this.open();
			try {
				return IO.readBinary(in);
			} finally {
				IO.close(in);
			}
		}

		@Override
		public String readText() throws IOException {
			InputStream in = this.open();
			try {
				return IO.readText(in);
			} finally {
				IO.close(in);
			}
		}

		@Override
		public InputStream open() throws IOException {
			return this.file.context.open(this.file);
		}
	}

	private static class ResLoader {

		private final ResContext context;

		ResLoader(ResContext ctx) {
			this.context = ctx;
		}

		public void load() {
			try {
				List<String> dir_paths = this.loadTreeFile();
				for (String dirpath : dir_paths) {
					this.loadDirItems(dirpath);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private List<String> parseListItems(String text) {
			final char c1 = '\r';
			final char c2 = '\n';
			List<String> list = new ArrayList<>();
			String[] rows = text.replace(c1, c2).split(String.valueOf(c2));
			for (String row : rows) {
				row = row.trim();
				if (row.length() == 0) {
					continue;
				} else if (row.startsWith("#")) {
					continue;
				}
				list.add(row);
			}
			return list;
		}

		private void loadDirItems(String dirpath) throws IOException {
			ResDir dir = new ResDir(context, dirpath);
			String text = dir.facade.readText();
			List<String> items = this.parseListItems(text);
			for (String item : items) {
				String childpath = dirpath + "/" + item;
				ResFile child = new ResFile(context, childpath);
				context.all.put(child.outerPath, child.facade);
			}
		}

		private List<String> loadTreeFile() throws IOException {
			ResFile tree = new ResFile(context, ".tree");
			String text = tree.facade.readText();
			return this.parseListItems(text);
		}
	}

	private static String normalizePath(String path) {
		String p2 = Paths.normalize(path);
		return p2;
	}

	@Override
	public SafeMode mode() {
		return this.context.mode;
	}

	@Override
	public String[] paths() {
		Set<String> keys = this.context.all.keySet();
		return keys.toArray(new String[0]);
	}

	@Override
	public Resource getRes(String path, boolean required) {
		path = normalizePath(path);
		Resource res = this.context.all.get(path);
		if (res == null && required) {
			throw new StarterException("no resource with path [" + path + "]");
		}
		return res;
	}

	@Override
	public byte[] readBinary(String path) throws IOException {
		Resource res = this.getRes(path, true);
		return res.readBinary();
	}

	@Override
	public String readText(String path) throws IOException {
		Resource res = this.getRes(path, true);
		return res.readText();
	}

	@Override
	public InputStream open(String path) throws IOException {
		Resource res = this.getRes(path, true);
		return res.open();
	}

	@Override
	public void importFrom(Map<String, Resource> src) {
		Map<String, Resource> dst = this.context.all;
		if (src == null || dst == null) {
			return;
		}
		dst.putAll(src);
	}

	@Override
	public Map<String, Resource> exportTo(Map<String, Resource> dst) {
		if (dst == null) {
			dst = new HashMap<>();
		}
		Map<String, Resource> src = this.context.all;
		if (src == null) {
			return dst;
		}
		dst.putAll(src);
		return dst;
	}
}
