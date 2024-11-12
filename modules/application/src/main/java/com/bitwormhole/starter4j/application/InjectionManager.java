package com.bitwormhole.starter4j.application;

import com.bitwormhole.starter4j.application.components.Scope;

public interface InjectionManager {

	Injection getSingletonInjection();

	Injection createNewPrototypeInjection();

	Injection selectInjection(Scope scope);

}
