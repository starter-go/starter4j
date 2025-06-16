package com.bitwormhole.starter4j.swing;

import java.net.URI;
import java.util.Map;

import java.awt.Component;

import com.bitwormhole.starter4j.application.ApplicationContext;

public class Goal {

    private String action;
    private Map<String, Object> attributes;
    private String contentType;
    private ApplicationContext context;
    private Class<?> frameClass;
    private String frameName;
    private URI location;
    private Map<String, String> params;
    private Component parent;
    private FrameRegistrationFilter filter;

    public Goal() {
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public URI getLocation() {
        return location;
    }

    public void setLocation(URI location) {
        this.location = location;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public ApplicationContext getContext() {
        return context;
    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    public Class<?> getFrameClass() {
        return frameClass;
    }

    public void setFrameClass(Class<?> frameClass) {
        this.frameClass = frameClass;
    }

    public String getFrameName() {
        return frameName;
    }

    public void setFrameName(String frameName) {
        this.frameName = frameName;
    }

    public Component getParent() {
        return parent;
    }

    public void setParent(Component parent) {
        this.parent = parent;
    }

    public FrameRegistrationFilter getFilter() {
        return filter;
    }

    public void setFilter(FrameRegistrationFilter filter) {
        this.filter = filter;
    }

}
