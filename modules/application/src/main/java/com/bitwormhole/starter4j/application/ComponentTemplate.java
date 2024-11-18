package com.bitwormhole.starter4j.application;

import com.bitwormhole.starter4j.application.components.Scope;
import com.bitwormhole.starter4j.base.StarterException;

public final class ComponentTemplate {

    private final ComponentRegistry mCR;

    public ComponentTemplate(ComponentRegistry cr) {
        this.mCR = cr;
    }

    public interface NewFunctionT<T> {
        T createNewInstance();
    }

    public interface InjectFunctionT<T> {
        void inject(InjectionExt ext, T inst);
    }

    public interface RegistrationT<T> {

        RegistrationT<T> onNew(NewFunctionT<T> fn);

        RegistrationT<T> onInject(InjectFunctionT<T> fn);

        RegistrationT<T> setId(String id);

        RegistrationT<T> setId(Class<?> id);

        RegistrationT<T> setScope(Scope scope);

        RegistrationT<T> addClass(String clazz);

        RegistrationT<T> addClass(Class<?> clazz);

        RegistrationT<T> addAlias(String alias);

        RegistrationT<T> addAlias(Class<?> alias);

        void register();
    }

    private static class MyRegistrationT<T> implements RegistrationT<T> {

        private final ComponentRegistry mRegistry;
        private final ComponentRegistration mRegistration;
        private final Class<T> mType;

        MyRegistrationT(ComponentRegistry cr, Class<T> t) {
            ComponentRegistration reg = cr.newRegistration();
            this.mRegistry = cr;
            this.mRegistration = reg;
            this.mType = t;
        }

        @Override
        public RegistrationT<T> onNew(NewFunctionT<T> fn) {
            this.mRegistration.functionNew = () -> {
                return fn.createNewInstance();
            };
            return this;
        }

        @SuppressWarnings("unchecked")
        @Override
        public RegistrationT<T> onInject(InjectFunctionT<T> fn) {
            this.mRegistration.functionInject = (ext, o1) -> {
                T o2 = (T) o1;
                fn.inject(ext, o2);
            };
            return this;
        }

        @Override
        public RegistrationT<T> setId(String id) {
            this.mRegistration.id = id;
            return this;
        }

        @Override
        public RegistrationT<T> setId(Class<?> id) {
            this.mRegistration.id = id.getName();
            return this;
        }

        @Override
        public RegistrationT<T> setScope(Scope scope) {
            if (scope == null) {
                scope = Scope.Singleton;
            }
            this.mRegistration.scope = scope.name();
            return this;
        }

        @Override
        public RegistrationT<T> addClass(String clazz) {
            String older = this.mRegistration.classes;
            this.mRegistration.classes = concatStringList(older, clazz);
            return this;
        }

        @Override
        public RegistrationT<T> addClass(Class<?> clazz) {
            String older = this.mRegistration.classes;
            String add = clazz.getName();
            this.mRegistration.classes = concatStringList(older, add);
            return this;
        }

        @Override
        public RegistrationT<T> addAlias(String alias) {
            String older = this.mRegistration.aliases;
            this.mRegistration.aliases = concatStringList(older, alias);
            return this;
        }

        @Override
        public RegistrationT<T> addAlias(Class<?> alias) {
            String older = this.mRegistration.aliases;
            String add = alias.getName();
            this.mRegistration.aliases = concatStringList(older, add);
            return this;
        }

        private String getDefaultId() {
            return this.mType.getName();
        }

        private ComponentRegistration.NewFunc getDefaultNewFn() {
            ComponentRegistration.NewFunc fn = () -> {
                try {
                    Class<T> t = this.mType;
                    return t.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new StarterException(e);
                }
            };
            return fn;
        }

        private ComponentRegistration.InjectFunc getDefaultInjectFn() {
            ComponentRegistration.InjectFunc fn = (ext, obj) -> {
            };
            return fn;
        }

        @Override
        public void register() {
            if (this.mRegistration.functionNew == null) {
                this.mRegistration.functionNew = this.getDefaultNewFn();
            }
            if (this.mRegistration.functionInject == null) {
                this.mRegistration.functionInject = this.getDefaultInjectFn();
            }
            if (this.mRegistration.id == null) {
                this.mRegistration.id = this.getDefaultId();
            }
            if (this.mRegistration.scope == null) {
                this.mRegistration.scope = Scope.Singleton.name();
            }
            this.mRegistry.register(this.mRegistration);
        }
    }

    private static String concatStringList(String older, String addition) {
        StringBuilder b = new StringBuilder();
        if (older != null) {
            b.append(older);
        }
        if (b.length() > 0) {
            b.append(' '); // sep
        }
        if (addition != null) {
            b.append(addition);
        }
        return b.toString();
    }

    public <T> RegistrationT<T> component(Class<T> t) {
        return new MyRegistrationT<>(this.mCR, t);
    }
}
