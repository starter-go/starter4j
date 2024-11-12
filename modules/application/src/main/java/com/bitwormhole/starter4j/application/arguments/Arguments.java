package com.bitwormhole.starter4j.application.arguments;

public class Arguments {

	private String[] args;

	public Arguments() {
		this.args = new String[0];
	}

	public Arguments(String[] a) {
		if (a == null) {
			a = new String[0];
		}
		this.args = a;
	}

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] a) {
		if (a == null) {
			a = new String[0];
		}
		this.args = a;
	}
}
