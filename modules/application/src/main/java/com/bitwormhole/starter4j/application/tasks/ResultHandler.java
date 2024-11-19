package com.bitwormhole.starter4j.application.tasks;

public interface ResultHandler<T> {

    Result<T> handle(Result<T> res) throws Exception;

}
