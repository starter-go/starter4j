package com.bitwormhole.starter4j.convertors;

public class ConvertorSelector {

    private Class<?> inputType;
    private Class<?> outputType;
    private String name;

    public ConvertorSelector() {
    }

    public ConvertorSelector(ConvertorSelector src) {
        if (src == null) {
        }
        this.inputType = src.inputType;
        this.outputType = src.outputType;
        this.name = src.name;
    }

    public Class<?> getInputType() {
        return inputType;
    }

    public void setInputType(Class<?> inputType) {
        this.inputType = inputType;
    }

    public Class<?> getOutputType() {
        return outputType;
    }

    public void setOutputType(Class<?> outputType) {
        this.outputType = outputType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
