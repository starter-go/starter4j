package com.bitwormhole.starter4j.base;

import java.util.ArrayList;
import java.util.List;

public class Paths {

	public static String[] path2elements(String path) {
		if (path == null) {
			return new String[] {};
		}
		final char c1 = '\\';
		final char c2 = '/';
		return path.replace(c1, c2).split(String.valueOf(c2));
	}

	public static String elements2path(String[] elements) {
		Builder b = new Builder();
		b.initAsDefault();
		b.append(elements);
		return b.create();
	}

	public static String elements2path(String[] elements, int offset, int length) {
		Builder b = new Builder();
		b.initAsDefault();
		b.append(elements, offset, length);
		return b.create();
	}

	public static class Builder {

		private String prefix;
		private String suffix;
		private String separator;
		private final List<String> elements;

		public Builder() {
			this.elements = new ArrayList<>();
		}

		void initAsDefault() {
			this.separator = "/";
			this.prefix = "/";
			this.suffix = "";
		}

		public String getPrefix() {
			return prefix;
		}

		public void setPrefix(String prefix) {
			this.prefix = prefix;
		}

		public String getSuffix() {
			return suffix;
		}

		public void setSuffix(String suffix) {
			this.suffix = suffix;
		}

		public String getSeparator() {
			return separator;
		}

		public void setSeparator(String separator) {
			this.separator = separator;
		}

		public Builder append(String element) {
			this.elements.add(element);
			return this;
		}

		public Builder append(String... _elements) {
			for (String el : _elements) {
				this.elements.add(el);
			}
			return this;
		}

		public Builder append(String[] items, int offset, int length) {
			final int end = offset + length;
			for (int i = offset; i < end; i++) {
				this.elements.add(items[i]);
			}
			return this;
		}

		private static void appendWithoutNull(String str, StringBuilder dst) {
			if (str == null || dst == null) {
				return;
			}
			dst.append(str);
		}

		public String create() {
			int count = 0;
			StringBuilder b = new StringBuilder();
			appendWithoutNull(this.prefix, b);
			for (String item : this.elements) {
				if (item == null) {
					continue;
				}
				if (count > 0) {
					appendWithoutNull(this.separator, b);
				}
				b.append(item);
				count++;
			}
			appendWithoutNull(this.suffix, b);
			return b.toString();
		}
	}

	public static String[] normalize(String[] elements) {
		return inner_normalize_or_resolve(elements, false);
	}

	public static String[] resolve(String[] elements) {
		return inner_normalize_or_resolve(elements, true);
	}

	private static String[] inner_normalize_or_resolve(String[] elements, boolean resolve) {
		List<String> dst = new ArrayList<>();
		for (String item : elements) {
			if (item == null) {
				continue;
			} else if (item.length() == 0) {
				continue;
			} else if (item.equals(".")) {
				continue;
			} else if (item.equals("..") && resolve) {
				final int size = dst.size();
				if (size > 0) {
					dst.remove(size - 1);
				} else {
					final String path = elements2path(elements);
					throw new NumberFormatException("cannot resolve path [" + path + "]");
				}
			} else {
				dst.add(item);
			}
		}
		return dst.toArray(new String[0]);
	}

	public static String normalize(String path) {
		String[] elist = path2elements(path);
		elist = normalize(elist);
		return elements2path(elist);
	}

	public static String resolve(String path) {
		String[] elist = path2elements(path);
		elist = resolve(elist);
		return elements2path(elist);
	}
}
