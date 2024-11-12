package com.bitwormhole.starter4j.application.boot;

import com.bitwormhole.starter4j.application.Injection;
import com.bitwormhole.starter4j.application.InjectionManager;
import com.bitwormhole.starter4j.application.components.Scope;

public class InjectionManagerImpl implements InjectionManager {

	private Injection mSingleton;
	private final AppContextCore mCore;

	public InjectionManagerImpl(AppContextCore core) {
		this.mCore = core;
	}

	@Override
	public Injection selectInjection(Scope scope) {
		if (scope == Scope.Prototype) {
			return this.createNewPrototypeInjection();
		} else if (scope == Scope.Singleton) {
			// NOP
		}
		return this.getSingletonInjection();
	}

	@Override
	public Injection getSingletonInjection() {
		Injection single = this.mSingleton;
		if (single == null) {
			single = this.createNewInjection(Scope.Singleton);
			this.mSingleton = single;
		}
		return single;
	}

	@Override
	public Injection createNewPrototypeInjection() {
		return this.createNewInjection(Scope.Prototype);
	}

	private Injection createNewInjection(Scope scope) {
		if (scope == null) {
			scope = Scope.Prototype;
		}
		return new InjectionImpl(this.mCore, scope);
	}
}
