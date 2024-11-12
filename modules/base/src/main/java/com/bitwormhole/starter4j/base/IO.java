package com.bitwormhole.starter4j.base;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public final class IO {

	private IO() {
	}

	public static void close(Closeable c) {
		if (c == null) {
			return;
		}
		try {
			c.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String readText(InputStream in) throws IOException {
		return readText(in, null, null, 0);
	}

	public static String readText(InputStream in, Charset cs) throws IOException {
		return readText(in, cs, null, 0);
	}

	public static String readText(InputStream in, Charset cs, byte[] buffer, int size_limit) throws IOException {
		cs = prepareCharset(cs);
		byte[] bin = readBinary(in, buffer, size_limit);
		return new String(bin, cs);
	}

	public static byte[] readBinary(InputStream in) throws IOException {
		return readBinary(in, null, 0);
	}

	public static byte[] readBinary(InputStream in, byte[] buffer, int size_limit) throws IOException {
		size_limit = prepareLimitSize32(size_limit);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		pump(in, out, buffer, size_limit);
		return out.toByteArray();
	}

	public static long pump(InputStream src, OutputStream dst) throws IOException {
		return pump(src, dst, null, 0);
	}

	public static long pump(InputStream src, OutputStream dst, byte[] buffer, long size_limit) throws IOException {
		if (src == null || dst == null) {
			return 0;
		}
		buffer = prepareTempBuffer(buffer);
		size_limit = prepareLimitSize64(size_limit);
		long total = 0;
		for (;;) {
			if (total > size_limit) {
				throw new IOException("the stream length limit is " + size_limit);
			}
			int cnt = src.read(buffer);
			if (cnt < 0) {
				break;
			} else if (cnt == 0) {
				Time.sleep(10);
				continue;
			}
			dst.write(buffer, 0, cnt);
			total += cnt;
		}
		return total;
	}

	private static byte[] prepareTempBuffer(byte[] buffer) {
		if (buffer == null) {
			buffer = new byte[1024 * 4];
		}
		return buffer;
	}

	private static Charset prepareCharset(Charset cs) {
		if (cs == null) {
			cs = StandardCharsets.UTF_8;
		}
		return cs;
	}

	private static int prepareLimitSize32(int limit) {
		if (limit < 1) {
			limit = Integer.MAX_VALUE;
		}
		return limit;
	}

	private static long prepareLimitSize64(long limit) {
		if (limit < 1) {
			limit = Long.MAX_VALUE;
		}
		return limit;
	}
}
