package io.github.zuston.framework.helper;

import io.github.zuston.framework.annotation.action;
import io.github.zuston.framework.entity.handlerEntity;
import io.github.zuston.framework.entity.requestEntity;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by zuston on 16/11/13.
 */
public class coreHelper {


    public static HashMap<requestEntity,handlerEntity> coreMap = new HashMap<requestEntity, handlerEntity>();
    static{
        Set<Class<?>> controllerClass = classHelper.getAllControllerClass();
        for (Class oneClass:controllerClass){
            for(Method m:oneClass.getDeclaredMethods()){
                if(m.isAnnotationPresent(action.class)){
                    String actionValue = m.getAnnotation(action.class).value();
                    if(actionValue.split(":").length==2){
                        String requestMethod = actionValue.split(":")[0].trim();
                        String requestUrlPattern = actionValue.split(":")[1].trim();
                        requestEntity request = new requestEntity(requestMethod,requestUrlPattern);
                        handlerEntity handler = new handlerEntity(oneClass,m);
                        coreMap.put(request,handler);
                    }
                }
            }
        }
    }

    public static HashMap<requestEntity,handlerEntity> getCoreMap(){
        return coreMap;
    }

    public static handlerEntity getHandler(requestEntity request){
        handlerEntity handler = null;
        if(coreMap.containsKey(request)){
            handler = coreMap.get(request);
        }
        return handler;
    }
}
