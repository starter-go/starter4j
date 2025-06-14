package com.bitwormhole.starter4j.swing;

import java.util.Map;

import com.bitwormhole.starter4j.application.Module;

public class SwingApplicationConfig {

    private String[] arguments;
    private Map<String, String> properties;
    private Class<?> mainFrameClass;

    /**
     * the main module
     */
    private Module module;

    public SwingApplicationConfig() {
    }

    public String[] getArguments() {
        return arguments;
    }

    public void setArguments(String[] arguments) {
        this.arguments = arguments;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public Class<?> getMainFrameClass() {
        return mainFrameClass;
    }

    public void setMainFrameClass(Class<?> mfc) {
        this.mainFrameClass = mfc;
    }

}
