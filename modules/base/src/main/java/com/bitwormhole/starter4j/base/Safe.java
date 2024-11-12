package com.bitwormhole.starter4j.base;

public class Safe {

	public static SafeMode normalize(SafeMode mode) {
		if (mode == SafeMode.Fast || mode == SafeMode.Safe) {
			return mode;
		}
		return SafeMode.Fast;
	}

}
