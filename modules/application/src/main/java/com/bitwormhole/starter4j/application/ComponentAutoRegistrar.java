package com.bitwormhole.starter4j.application;

import java.util.List;

/**
 * ComponentAutoRegistrar 接口表示实现它的类提供自动注册能力
 */
public interface ComponentAutoRegistrar {

    /**
     * 实现自动注册的类需要把它的 ComponentRegistryFunc 函数添加到传入的 list 中
     */
    void registerSelf(List<ComponentRegistryFunc> list);

}
