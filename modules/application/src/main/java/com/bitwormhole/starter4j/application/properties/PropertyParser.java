package com.bitwormhole.starter4j.application.properties;

public final class PropertyParser {

	private PropertyParser() {
	}

	public static void parse(String text, Properties dst) {
		if (text == null || dst == null) {
			return;
		}
		final char c1 = '\r';
		final char c2 = '\n';
		text = text.replace(c1, c2);
		String[] rows = text.split(String.valueOf(c2));
		Handler h = new Handler(dst);
		for (String row : rows) {
			h.handle(row.trim());
		}
	}

	private static class Handler {

		private final Properties output;
		private String segmentPrefix;

		public Handler(Properties dst) {
			this.output = dst;
			this.segmentPrefix = "";
		}

		public void handle(String row) {
			if (row.length() == 0) {
				return; // 忽略空行
			} else if (row.startsWith("#")) {
				return; // 忽略注释
			}
			if (row.startsWith("[") && row.endsWith("]")) {
				this.handleSegmentHeadRow(row);
			} else {
				this.handlePropertyRow(row);
			}
		}

		private void handleSegmentHeadRow(String row) {
			String str = row;
			final char dot = '.';
			str = str.replace('[', dot);
			str = str.replace(']', dot);
			str = str.replace('"', dot);
			str = str.replace('\'', dot);
			String[] parts = str.split("\\" + dot);
			StringBuilder b = new StringBuilder();
			for (String part : parts) {
				part = part.trim();
				if (part.length() == 0) {
					continue;
				}
				b.append(part);
				b.append(dot);
			}
			this.segmentPrefix = b.toString();
		}

		private void handlePropertyRow(String row) {
			final int idx = row.indexOf('=');
			if (idx < 1) {
				return;
			}
			String key = row.substring(0, idx).trim();
			String val = row.substring(idx + 1).trim();
			String name = this.segmentPrefix + key;
			this.output.setProperty(name, val);
		}
	}
}
