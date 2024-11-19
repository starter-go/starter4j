package com.bitwormhole.starter4j.application.tasks;

public class Result<T> {

    private T value;
    private Throwable error;

    public Result() {
    }

    public Result(T val) {
        this.value = val;
    }

    public Result(Throwable err) {
        this.error = err;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

}
