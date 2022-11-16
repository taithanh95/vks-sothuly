package com.bitsco.vks.sothuly.model;
/**
 *
 * @author phucnv
 */
public class ParamBO {
    private String name;
    private Object value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
    public ParamBO(String name, Object value) {
        this.name = name;
        this.value = value;
    }
}
