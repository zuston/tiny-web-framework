package io.github.zuston.framework.helper;

/**
 * Created by zuston on 16/11/13.
 */

import io.github.zuston.framework.annotation.controller;
import io.github.zuston.framework.util.classUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * 获取所有类，
 */
public class classHelper {
    private static Set<Class<?>> allClassSet = null;
    static{
        allClassSet = classUtil.getAllClass(configHelper.getPackageName());
    }
    public static Set<Class<?>> getAllClassSet() {
        return allClassSet;
    }


    public static Set<Class<?>> getAllControllerClass(){
        Set<Class<?>> controllerClass = new HashSet<Class<?>>();
        for (Class oneclass:allClassSet){
            if(oneclass.isAnnotationPresent(controller.class)){
                controllerClass.add(oneclass);
            }
        }
        return controllerClass;
    }

    // TODO: 16/11/14 剩下的service类还有一些变量需要加载 

}
