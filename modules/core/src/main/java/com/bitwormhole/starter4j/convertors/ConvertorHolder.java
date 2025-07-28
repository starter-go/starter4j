package com.bitwormhole.starter4j.convertors;

public class ConvertorHolder {

    private Convertor convertor;
    private Class<?> inputType;
    private Class<?> outputType;
    private String name; // the name of (wanted) convertor

    public ConvertorHolder(Class<?> in_type, Class<?> out_type) {
        this.inputType = in_type;
        this.outputType = out_type;
    }

    public ConvertorHolder(Class<?> in_type, Class<?> out_type, String _name) {
        this.inputType = in_type;
        this.outputType = out_type;
        this.name = _name;
    }

    public Convertor getConvertor() {
        Convertor c = this.convertor;
        if (c == null) {
            c = this.loadConvertor();
            this.convertor = c;
        }
        return c;
    }

    public void setConvertor(Convertor convertor) {
        this.convertor = convertor;
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

    private Convertor loadConvertor() {
        ConvertorManager cm = ConvertorManager.getInstance();
        ConvertorSelector sel = new ConvertorSelector();
        sel.setInputType(this.inputType);
        sel.setOutputType(this.outputType);
        sel.setName(this.name);
        return cm.findConvertor(sel);
    }

}
