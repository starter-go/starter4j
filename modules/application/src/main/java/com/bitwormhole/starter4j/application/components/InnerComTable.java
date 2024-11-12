package com.bitwormhole.starter4j.application.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bitwormhole.starter4j.base.Safe;
import com.bitwormhole.starter4j.base.SafeMode;
import com.bitwormhole.starter4j.base.StarterException;

final class InnerComTable implements Components {

	private final SafeMode mode;
	private final Map<String, IdentityNode> tableById;
	private final Map<String, ClassNode> tableByClass;

	public InnerComTable(SafeMode m) {
		m = Safe.normalize(m);
		Map<String, IdentityNode> t1 = new HashMap<>();
		Map<String, ClassNode> t2 = new HashMap<>();
		if (m == SafeMode.Safe) {
			t1 = Collections.synchronizedMap(t1);
			t2 = Collections.synchronizedMap(t2);
		}
		this.tableById = t1;
		this.tableByClass = t2;
		this.mode = m;
	}

	private static class IdentityNode {

		final String mId;
		final String mAlias;
		final ComponentHolder mHolder;

		IdentityNode(String name, ComponentHolder h, boolean as_alias) {
			// id & alias 二选一，另外一个必须为 null
			String id = null;
			String alias = null;
			if (as_alias) {
				alias = name;
			} else {
				id = name;
			}
			this.mHolder = h;
			this.mId = id;
			this.mAlias = alias;
		}
	}

	private static class ClassNode {

		final ComponentHolder mHolder;
		final ClassNode mPrev;
		final String mClass;

		ClassNode(ComponentHolder h, String clazz) {
			this.mHolder = h;
			this.mPrev = null;
			this.mClass = clazz;
		}

		ClassNode(ComponentHolder h, ClassNode prev) {
			this.mHolder = h;
			this.mPrev = prev;
			this.mClass = prev.mClass;
		}
	}

	private void innerPutWithId(String name, ComponentHolder holder, boolean as_alias) {
		if (name == null || holder == null) {
			return;
		}
		IdentityNode older = tableById.get(name);
		if (older != null) {
			throw new StarterException("components are duplicated, with id(or alias):" + name);
		}
		IdentityNode node = new IdentityNode(name, holder, as_alias);
		tableById.put(name, node);
	}

	private void innerPutWithClass(String clazz, ComponentHolder holder) {
		if (clazz == null || holder == null) {
			return;
		}
		ClassNode older = tableByClass.get(clazz);
		ClassNode next;
		if (older == null) {
			next = new ClassNode(holder, clazz);
		} else {
			next = new ClassNode(holder, older);
		}
		tableByClass.put(next.mClass, next);
	}

	private ComponentHolder innerFindById(String id, boolean required) throws StarterException {
		IdentityNode node = this.tableById.get(id);
		if (node == null && required) {
			throw new StarterException("no component with id(or alias) : " + id);
		}
		return node.mHolder;
	}

	private void innerListByClass(String clazz, List<ComponentHolder> dst) {
		if (dst == null) {
			return;
		}
		ClassNode node = this.tableByClass.get(clazz);
		for (; node != null; node = node.mPrev) {
			dst.add(node.mHolder);
		}
	}

	@Override
	public void put(ComponentHolder holder) throws StarterException {
		ComponentInfo info = holder.info();
		// id
		String id = info.id();
		this.innerPutWithId(id, holder, false);
		// aliases
		String[] aliases = info.aliases();
		if (aliases != null) {
			for (String alias : aliases) {
				this.innerPutWithId(alias, holder, true);
			}
		}
		// classes
		String[] clist = info.classes();
		if (clist != null) {
			for (String cl : clist) {
				this.innerPutWithClass(cl, holder);
			}
		}
	}

	@Override
	public ComponentHolder get(String id) throws StarterException {
		return this.innerFindById(id, true);
	}

	@Override
	public List<ComponentHolder> select(String selector, List<ComponentHolder> dst) throws StarterException {
		if (dst == null) {
			dst = new ArrayList<>();
		}
		if (selector == null) {
			return dst;
		} else if (selector.startsWith("#")) {
			// as id selector
			String id = selector.substring(1);
			ComponentHolder h = this.innerFindById(id, true);
			dst.add(h);
		} else if (selector.startsWith(".")) {
			// as class selector
			String clazz = selector.substring(1);
			this.innerListByClass(clazz, dst);
		}
		return dst;
	}

	@Override
	public String[] ids() {
		Collection<IdentityNode> src = this.tableById.values();
		List<String> dst = new ArrayList<>();
		for (IdentityNode node : src) {
			String id = node.mId;
			if (id != null) {
				dst.add(id);
			}
		}
		return dst.toArray(new String[0]);
	}

	@Override
	public Map<String, ComponentHolder> exportTo(Map<String, ComponentHolder> dst) {
		if (dst == null) {
			dst = new HashMap<>();
		}
		Collection<IdentityNode> src = this.tableById.values();
		for (IdentityNode in : src) {
			String id = in.mId;
			ComponentHolder holder = in.mHolder;
			if (id == null || holder == null) {
				continue;
			}
			dst.put(id, holder);
		}
		return null;
	}

	@Override
	public void importFrom(Map<String, ComponentHolder> src) {
		if (src == null) {
			return;
		}
		for (ComponentHolder h : src.values()) {
			this.put(h);
		}
	}

	@Override
	public SafeMode mode() {
		return this.mode;
	}
}
