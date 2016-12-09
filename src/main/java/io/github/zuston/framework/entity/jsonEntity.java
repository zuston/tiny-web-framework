package io.github.zuston.framework.entity;

import java.util.HashMap;

/**
 * Created by zuston on 16/11/24.
 */
public class jsonEntity {
    private HashMap<String,Object> hs = new HashMap<String, Object>();
    public jsonEntity(){

    }

    public jsonEntity addModel(String key,Object model){
        hs.put(key,model);
        return this;
    }

    public Boolean isEmptyModel(){
        return hs.isEmpty();
    }

    public HashMap<String,Object> getModel(){
        return hs;
    }
}
