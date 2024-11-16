package com.bitwormhole.starter4j.application;

public class ComponentRegistration {

	/***
	 * 组件的 ID
	 */
	public String id;

	/**
	 * 别名列表，项与项之间以空格符(SPACE)分隔
	 */
	public String aliases;

	/**
	 * 类名列表，项与项之间以空格符(SPACE)分隔
	 */
	public String classes;

	/**
	 * 作用域 'singleton'|'prototype'
	 */
	public String scope;

	/**
	 * 提交后，这个字段会重置为nil
	 */
	public ComponentRegistry registry;

	// 定义 scope 的值

	public static final String SCOPE_SINGLETON = "singleton";
	public static final String SCOPE_PROTOTYPE = "prototype";

	// 定义函数接口

	public interface NewFunc {
		Object invoke();
	}

	public interface InjectFunc {
		void invoke(InjectionExt i, Object instance);
	}

	public NewFunc functionNew;
	public InjectFunc functionInject;
}
