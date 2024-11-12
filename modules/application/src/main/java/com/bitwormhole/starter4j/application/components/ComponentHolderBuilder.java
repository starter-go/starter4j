package com.bitwormhole.starter4j.application.components;

import java.util.ArrayList;
import java.util.List;

import com.bitwormhole.starter4j.application.ComponentRegistration;
import com.bitwormhole.starter4j.application.ComponentRegistration.InjectFunc;
import com.bitwormhole.starter4j.application.ComponentRegistration.NewFunc;
import com.bitwormhole.starter4j.application.Injection;
import com.bitwormhole.starter4j.application.InjectionExt;
import com.bitwormhole.starter4j.application.LifeCycle;
import com.bitwormhole.starter4j.application.LifeManager;
import com.bitwormhole.starter4j.base.StarterException;

public class ComponentHolderBuilder {

	private String id;
	private String[] aliases;
	private String[] classes;
	private Scope scope;
	private ComponentRegistration.NewFunc fnNew;
	private ComponentRegistration.InjectFunc fnInject;

	public ComponentHolderBuilder() {
	}

	private static class MyRef implements ComponentRef {

		final String mSelector;
		final ComponentHolder mHolder;

		MyRef(String sel, ComponentHolder h) {
			mSelector = sel;
			mHolder = h;
		}

		@Override
		public String selector() {
			return mSelector;
		}

		@Override
		public ComponentHolder holder() {
			return mHolder;
		}
	}

	private static class MyComInfoLoader {

		private MyComInfoLoader() {
		}

		public static String getId(ComponentRegistration r) {
			String id = r.id;
			if (id != null) {
				if (id.length() > 0) {
					return id;
				}
			}
			return "undefined_component_id";
		}

		public static String[] loadClasses(ComponentRegistration r) {
			return loadStringList(r.classes);
		}

		public static Scope getScope(ComponentRegistration r) {
			String str = r.scope;
			final String pt = Scope.Prototype.toString();
			if (pt.equals(str)) {
				return Scope.Prototype;
			}
			return Scope.Singleton;
		}

		public static String[] loadAliases(ComponentRegistration r) {
			return loadStringList(r.aliases);
		}

		private static String[] loadStringList(String str) {
			final String[] empty = new String[0];
			if (str == null) {
				return empty;
			}
			if (str.length() == 0) {
				return empty;
			}
			final String sep = String.valueOf(' ');
			final String[] parts = str.split(sep);
			final List<String> list = new ArrayList<>();
			for (String part : parts) {
				part = part.trim();
				if (part.length() == 0) {
					continue;
				}
				list.add(part);
			}
			return list.toArray(empty);
		}
	}

	private static class MyInfo implements ComponentInfo {

		final MyCompCore com;

		MyInfo(MyCompCore core) {
			this.com = core;
		}

		@Override
		public String id() {
			return this.com.id;
		}

		@Override
		public String[] aliases() {
			return this.com.aliases;
		}

		@Override
		public String[] classes() {
			return this.com.classes;
		}

		@Override
		public Scope scope() {
			return this.com.scope;
		}
	}

	private static class MyCompCore {

		// inner

		String id;
		String[] aliases;
		String[] classes;
		Scope scope;

		ComponentRegistration.NewFunc fnNew;
		ComponentRegistration.InjectFunc fnInject;

		// facade
		ComponentInfo info;
		ComponentFactory factory;
		ComponentHolder holder;

		MyCompCore() {
			this.info = new MyInfo(this);
		}

		public ComponentInstance createNewInstance() {
			return this.factory.createInstance(info);
		}
	}

	private static class MyPrototypeHolder implements ComponentHolder {

		final MyCompCore com;

		MyPrototypeHolder(MyCompCore comp) {
			this.com = comp;
			comp.holder = this;
		}

		@Override
		public ComponentInfo info() {
			return this.com.info;
		}

		@Override
		public ComponentFactory factory() {
			return this.com.factory;
		}

		@Override
		public ComponentInstance getInstance() {
			return this.com.createNewInstance();
		}

		@Override
		public ComponentRef newRef(String selector) {
			return new MyRef(selector, this);
		}
	}

	private static class MySingletonHolder implements ComponentHolder {

		final MyCompCore com;
		private ComponentInstance instance;

		MySingletonHolder(MyCompCore comp) {
			this.com = comp;
			comp.holder = this;
		}

		@Override
		public ComponentInfo info() {
			return this.com.info;
		}

		@Override
		public ComponentFactory factory() {
			return this.com.factory;
		}

		@Override
		public ComponentInstance getInstance() {
			ComponentInstance i = this.instance;
			if (i == null) {
				i = this.com.createNewInstance();
				this.instance = i;
			}
			return i;
		}

		@Override
		public ComponentRef newRef(String selector) {
			return new MyRef(selector, this);
		}
	}

	private static class MyCompFactory implements ComponentFactory {

		final MyCompCore com;

		public MyCompFactory(MyCompCore cc) {
			this.com = cc;
		}

		@Override
		public ComponentInstance createInstance(ComponentInfo info) throws StarterException {
			MyCompInstance inst = new MyCompInstance(this.com);
			inst.init();
			return inst;
		}
	}

	private static class MyCompInstance implements ComponentInstance {

		final MyCompCore com;
		Object rawInstance;
		InjectFunc fnInject; // 注意： 这个字段是一次性的，当被调用后，就会被设置为 null

		public MyCompInstance(MyCompCore cc) {
			this.com = cc;
		}

		void init() {
			final NewFunc fn_new = this.com.fnNew;
			if (fn_new == null) {
				String cid = this.com.id;
				throw new StarterException("the NewFunc of component is null, com_id:" + cid);
			}
			this.fnInject = this.com.fnInject;
			this.rawInstance = fn_new.invoke();
		}

		@Override
		public boolean ready() {
			return this.fnInject == null;
		}

		@Override
		public Object get() {
			return this.rawInstance;
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> T get(Class<T> t) {
			return (T) this.rawInstance;
		}

		@Override
		public ComponentInfo info() {
			return this.com.info;
		}

		@Override
		public void inject(ComponentInjection i) throws StarterException {
			InjectFunc fn = this.fnInject;
			this.fnInject = null;
			if (fn == null) {
				return;
			}
			final Object ri = this.rawInstance;
			Injection i2 = (Injection) i;
			InjectionExt i3 = i2.ext();
			fn.invoke(i3, ri);
			LifeManager lm = i2.getLifeManager();
			tryAddToLifeManager(lm, ri);
		}
	}

	private static void tryAddToLifeManager(LifeManager lm, Object obj) {
		if (obj instanceof LifeCycle) {
			LifeCycle lc = (LifeCycle) obj;
			lm.add(lc.life());
		}
	}

	public void init(ComponentRegistration r) {
		this.id = MyComInfoLoader.getId(r);
		this.aliases = MyComInfoLoader.loadAliases(r);
		this.classes = MyComInfoLoader.loadClasses(r);
		this.scope = MyComInfoLoader.getScope(r);
		this.fnInject = r.functionInject;
		this.fnNew = r.functionNew;
	}

	public ComponentHolder create() {

		MyCompCore cc = new MyCompCore();
		cc.id = this.id;
		cc.aliases = this.aliases;
		cc.classes = this.classes;
		cc.scope = this.scope;

		if (cc.scope == Scope.Prototype) {
			cc.holder = new MyPrototypeHolder(cc);
		} else {
			cc.holder = new MySingletonHolder(cc);
		}

		cc.fnInject = this.fnInject;
		cc.fnNew = this.fnNew;
		cc.factory = new MyCompFactory(cc);

		return cc.holder;
	}
}
