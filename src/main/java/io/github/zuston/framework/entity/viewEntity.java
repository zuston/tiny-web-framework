package io.github.zuston.framework.entity;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by zuston on 16/11/14.
 */
public class viewEntity {

    public String viewName;
    public HashMap<String,Object> hs;
    public viewEntity(String viewName) {
        this.viewName=viewName;
    }

    public viewEntity addModel(String key,Object value){
        hs.put(key,value);
        return this;
    }

    public HashMap<String,Object> getModel(){
        return hs;
    }

    public String getView(){
        return viewName;
    }

}
