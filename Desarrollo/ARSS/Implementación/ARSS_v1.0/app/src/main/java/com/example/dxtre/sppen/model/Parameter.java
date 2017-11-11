package com.example.dxtre.sppen.model;

/**
 * Created by DXtre on 22/06/16.
 */

public class Parameter {

    private String parameter;
    private String value;

    public Parameter(String parameter, String value) {
        this.parameter = parameter;
        this.value = value;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
