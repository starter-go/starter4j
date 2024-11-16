package com.bitwormhole.starter4j.application;

import com.bitwormhole.starter4j.application.components.ComponentInjection;

public interface Injection extends ComponentInjection {

	InjectionExt ext();

	ApplicationContext getContext();

	LifeManager getLifeManager();

	void complete();

}
