package com.bitwormhole.starter4j.application;

public interface InjectionExt {

	Injection getInjection();

	ApplicationContext getContext();

	Object getComponent(String selector);

	Object[] listComponents(String selector);

	// integer

	int getInt(String selector);

	byte getInt8(String selector);

	short getInt16(String selector);

	int getInt32(String selector);

	long getInt64(String selector);

	// float

	float getFloat(String selector);

	double getDouble(String selector);

	// other types

	String getString(String selector);

	boolean getBoolean(String selector);

	char getChar(String selector);

	byte getByte(String selector);

	Object getObject(String selector);

}
