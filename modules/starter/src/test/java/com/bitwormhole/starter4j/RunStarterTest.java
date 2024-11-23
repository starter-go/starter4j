package com.bitwormhole.starter4j;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bitwormhole.starter4j.application.ComponentRegistration;
import com.bitwormhole.starter4j.application.ComponentRegistry;
import com.bitwormhole.starter4j.application.properties.Properties;
import com.bitwormhole.starter4j.application.Life;
import com.bitwormhole.starter4j.application.LifeCycle;
import com.bitwormhole.starter4j.application.Module;
import com.bitwormhole.starter4j.application.ModuleBuilder;
import com.bitwormhole.starter4j.application.components.ComponentSelector;
import com.bitwormhole.starter4j.vlog.simple.VLogSimple;

import com.bitwormhole.starter4j.res.t.StarterSrcTest;

public class RunStarterTest {

	final static Logger logger = LoggerFactory.getLogger(RunStarterTest.class);

	@Test
	public void testRunStarter() {
		String[] args = {
				"apple",
				"banana",
				"cherry",
				"--debug.enabled=1",
				"--debug.log-environment=0",
				"--test.props.by.args=ok",
		};
		Module m = module();
		Initializer i = Starter.init(args);

		Properties props = i.getProperties();
		props.setProperty("debug.enabled", "1");
		props.setProperty("debug.log-properties", "1");

		i.setMainModule(m);
		i.enableToThrowException(true);
		i.run();
	}

	public static Module module() {
		ModuleBuilder mb = new ModuleBuilder();

		mb.setName(RunStarterTest.class.getName());
		mb.setVersion("1.0");
		mb.setRevision(1);

		mb.setResources(StarterSrcTest.res());

		mb.depend(Starter.module());
		mb.depend(VLogSimple.module());

		mb.setComponents((cr) -> {
			com1(cr);
			com2(cr);
		});

		return mb.create();
	}

	private static void com1(ComponentRegistry cr) {

		final ComponentSelector cs = ComponentSelector.getInstance();
		ComponentRegistration r1 = cr.newRegistration();

		r1.id = MyCom1.class.getName();
		r1.functionNew = () -> {
			return new MyCom1();
		};
		r1.functionInject = (ext, i) -> {
			MyCom1 com = (MyCom1) i;
			com.value = ext.getInt(cs.PROPERTY("a.b.com1"));
			com.com2 = (MyCom2) ext.getComponent(cs.ID(MyCom2.class));
		};

		cr.register(r1);
	}

	private static void com2(ComponentRegistry cr) {

		final ComponentSelector cs = ComponentSelector.getInstance();
		ComponentRegistration r1 = cr.newRegistration();

		r1.id = MyCom2.class.getName();
		r1.functionNew = () -> {
			return new MyCom2();
		};
		r1.functionInject = (ext, i) -> {
			MyCom2 com = (MyCom2) i;
			com.value = ext.getString(cs.PROPERTY("a.b.com2"));
			com.com1 = (MyCom1) ext.getComponent(cs.ID(MyCom1.class));
		};

		cr.register(r1);
	}

	private static class MyCom1 {

		int value;
		MyCom2 com2;
	}

	private static class MyCom2 implements LifeCycle {

		String value;
		MyCom1 com1;

		@Override
		public Life life() {
			Life l = new Life();
			l.order = 1;
			l.onCreate = this::on_create;
			l.onStart = this::on_start;
			l.onLoop = this::on_loop;
			l.onStop = this::on_stop;
			l.onDestroy = this::on_destroy;
			return l;
		}

		private void on_create() {
			logger.warn(this + ".on_create");
		}

		private void on_start() {
			logger.warn(this + ".on_start");
		}

		private void on_loop() {
			logger.warn(this + ".on_loop");
		}

		private void on_stop() {
			logger.warn(this + ".on_stop");
		}

		private void on_destroy() {
			logger.warn(this + ".on_destroy");
		}
	}
}
