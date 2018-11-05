package com.laxtech.connector.ctparameter.internal;

import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;

public class Options {

    @Parameter
    public String color;

    @Parameter
    @Optional
    public String mode;

    public String getColor(){
        return color;
    }

    public void setColor(String color){
        this.color = color;
    }

    public String getMode(){
        return mode;
    }

    public void setMode(String mode){
        this.mode = mode;
    }
}