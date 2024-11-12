package com.bitwormhole.starter4j.application.boot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.bitwormhole.starter4j.application.Life;
import com.bitwormhole.starter4j.application.LifeManager;
import com.bitwormhole.starter4j.base.SafeMode;

public class LifeManagerImpl implements LifeManager {

	private final MyMaster mMaster;
	private final List<Life> mList;
	private Life[] mListCache; // sorted items of list

	public LifeManagerImpl(SafeMode mode) {
		List<Life> list = new ArrayList<>();
		if (mode == SafeMode.Safe) {
			list = Collections.synchronizedList(list);
		}
		this.mMaster = new MyMaster();
		this.mList = list;
	}

	private interface LifeHandler {
		void handle(Life l);
	}

	private Life[] loadCacheList() {
		Life[] all = this.mList.toArray(new Life[0]);
		Arrays.sort(all, (i1, i2) -> {
			return i1.order - i2.order;
		});
		return all;
	}

	private void forEach(LifeHandler h, boolean reverse) {
		Life[] all = this.mListCache;
		if (all == null) {
			all = this.loadCacheList();
			this.mListCache = all;
		}
		if (reverse) {
			for (int i = all.length - 1; i >= 0; i--) {
				h.handle(all[i]);
			}
		} else {
			for (Life l : all) {
				h.handle(l);
			}
		}
	}

	private void forEach(LifeHandler h) {
		forEach(h, false);
	}

	private void forEachReverse(LifeHandler h) {
		forEach(h, true);
	}

	private class MyMaster extends Life {

		MyMaster() {
			this.onCreate = () -> {
				forEach((life) -> {
					life.onCreate.invoke();
				});
			};
			this.onDestroy = () -> {
				forEachReverse((life) -> {
					life.onDestroy.invoke();
				});
			};
			this.onLoop = () -> {
				forEach((life) -> {
					life.onLoop.invoke();
				});
			};

			this.onStart = () -> {
				forEach((life) -> {
					life.onStart.invoke();
				});
			};
			this.onStartPre = () -> {
				forEach((life) -> {
					life.onStartPre.invoke();
				});
			};
			this.onStartPost = () -> {
				forEach((life) -> {
					life.onStartPost.invoke();
				});
			};

			this.onStop = () -> {
				forEachReverse((life) -> {
					life.onStop.invoke();
				});
			};
			this.onStopPre = () -> {
				forEachReverse((life) -> {
					life.onStopPre.invoke();
				});
			};
			this.onStopPost = () -> {
				forEachReverse((life) -> {
					life.onStopPost.invoke();
				});
			};
		}
	}

	@Override
	public void add(Life l) {
		if (l == null) {
			return;
		}
		l.normalize();
		this.mList.add(l);
		this.mListCache = null;
	}

	@Override
	public Life getMaster() {
		return this.mMaster;
	}
}
