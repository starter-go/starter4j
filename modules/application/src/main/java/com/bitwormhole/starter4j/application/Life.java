package com.bitwormhole.starter4j.application;

public class Life {

	/**
	 *  order of starting, low value first
	 */
	public int order;

	public OnCreateFunc onCreate;
	public OnStartPreFunc onStartPre;
	public OnStartFunc onStart;
	public OnStartPostFunc onStartPost;
	public OnLoopFunc onLoop;
	public OnStopPreFunc onStopPre;
	public OnStopFunc onStop;
	public OnStopPostFunc onStopPost;
	public OnDestroyFunc onDestroy;

	public Life() {
	}

	public Life(Life src) {
		if (src == null) {
			return;
		}

		this.onCreate = src.onCreate;
		this.onLoop = src.onLoop;
		this.onDestroy = src.onDestroy;

		this.onStart = src.onStart;
		this.onStartPre = src.onStartPre;
		this.onStartPost = src.onStartPost;

		this.onStop = src.onStop;
		this.onStopPre = src.onStopPre;
		this.onStopPost = src.onStopPost;
	}

	public void normalize() {
		Life nop = getNopLife();

		this.onLoop = normalizeFn(this.onLoop, nop.onLoop);
		this.onCreate = normalizeFn(this.onCreate, nop.onCreate);
		this.onDestroy = normalizeFn(this.onDestroy, nop.onDestroy);

		this.onStart = normalizeFn(this.onStart, nop.onStart);
		this.onStartPre = normalizeFn(this.onStartPre, nop.onStartPre);
		this.onStartPost = normalizeFn(this.onStartPost, nop.onStartPost);

		this.onStop = normalizeFn(this.onStop, nop.onStop);
		this.onStopPre = normalizeFn(this.onStopPre, nop.onStopPre);
		this.onStopPost = normalizeFn(this.onStopPost, nop.onStopPost);
	}

	private static <T> T normalizeFn(T t1, T t2) {
		return (t1 != null) ? t1 : t2;
	}

	private static Life theNopLife;

	private static Life getNopLife() {
		Life l = theNopLife;
		if (l != null) {
			return l;
		}
		l = new Life();

		l.onCreate = () -> {
		};
		l.onLoop = () -> {
		};
		l.onDestroy = () -> {
		};

		l.onStart = () -> {
		};
		l.onStartPre = () -> {
		};
		l.onStartPost = () -> {
		};

		l.onStop = () -> {
		};
		l.onStopPre = () -> {
		};
		l.onStopPost = () -> {
		};

		theNopLife = l;
		return l;
	}

	//////////////////////////////////////////////////////////

	public interface OnCreateFunc {
		void invoke();
	}

	public interface OnStartPreFunc {
		void invoke();
	}

	public interface OnStartFunc {
		void invoke();
	}

	public interface OnStartPostFunc {
		void invoke();
	}

	public interface OnStopPreFunc {
		void invoke();
	}

	public interface OnStopFunc {
		void invoke();
	}

	public interface OnStopPostFunc {
		void invoke();
	}

	public interface OnDestroyFunc {
		void invoke();
	}

	public interface OnLoopFunc {
		void invoke();
	}

}
