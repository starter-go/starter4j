package com.bitwormhole.starter4j.application.boot;

import java.util.ArrayList;
import java.util.List;

import com.bitwormhole.starter4j.application.ApplicationContext;
import com.bitwormhole.starter4j.application.Injection;
import com.bitwormhole.starter4j.application.InjectionExt;

public class InjectionExtImpl implements InjectionExt {

	private final Injection mInjection;

	public InjectionExtImpl(Injection inj) {
		this.mInjection = inj;
	}

	@Override
	public Injection getInjection() {
		return this.mInjection;
	}

	@Override
	public ApplicationContext getContext() {
		return this.mInjection.getApplicationContext();
	}

	@Override
	public Object getComponent(String selector) {
		return this.mInjection.select(selector);
	}

	@Override
	public Object[] listComponents(String selector) {
		List<Object> list = new ArrayList<>();
		list = this.mInjection.select(selector, list);
		return list.toArray();
	}

	@Override
	public int getInt(String selector) {
		String text = innerGetPropertyRequired(selector);
		return Integer.parseInt(text);
	}

	@Override
	public byte getInt8(String selector) {
		String text = innerGetPropertyRequired(selector);
		return Byte.parseByte(text);
	}

	@Override
	public short getInt16(String selector) {
		String text = innerGetPropertyRequired(selector);
		return Short.parseShort(text);
	}

	@Override
	public int getInt32(String selector) {
		String text = innerGetPropertyRequired(selector);
		return Integer.parseInt(text);
	}

	@Override
	public long getInt64(String selector) {
		String text = innerGetPropertyRequired(selector);
		return Long.parseLong(text);
	}

	@Override
	public float getFloat(String selector) {
		String text = innerGetPropertyRequired(selector);
		return Float.parseFloat(text);
	}

	@Override
	public double getDouble(String selector) {
		String text = innerGetPropertyRequired(selector);
		return Double.parseDouble(text);
	}

	@Override
	public String getString(String selector) {
		String text = innerGetPropertyRequired(selector);
		return text;
	}

	@Override
	public boolean getBoolean(String selector) {
		String text = innerGetPropertyRequired(selector);
		if (text == null) {
			return false;
		} else if (text.length() == 0) {
			return false;
		}
		text = text.toLowerCase();
		if ("1".equals(text)) {
		} else if ("yes".equals(text)) {
		} else if ("true".equals(text)) {
		} else {
			return false;
		}
		return true;
	}

	@Override
	public char getChar(String selector) {
		String text = innerGetPropertyRequired(selector);
		return text.charAt(0);
	}

	@Override
	public byte getByte(String selector) {
		String text = innerGetPropertyRequired(selector);
		return Byte.parseByte(text);
	}

	private String innerGetPropertyRequired(String selector) {
		return this.mInjection.getProperty(selector);
	}

	@Override
	public Object getObject(String selector) {
		return this.getComponent(selector);
	}

}
