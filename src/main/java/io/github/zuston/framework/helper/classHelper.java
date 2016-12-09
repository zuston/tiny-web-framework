package io.github.zuston.framework.helper;

/**
 * Created by zuston on 16/11/13.
 */

import io.github.zuston.framework.annotation.controller;
import io.github.zuston.framework.util.classUtil;

import java.lang.annotation.Annotation;
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

    //获取所有控制器类
    public static Set<Class<?>> getAllControllerClass(){
        Set<Class<?>> controllerClass = new HashSet<Class<?>>();
        for (Class oneclass:allClassSet){
            if(oneclass.isAnnotationPresent(controller.class)){
                controllerClass.add(oneclass);
            }
        }
        return controllerClass;
    }

    //获取父类下面的所有子类
    public static Set<Class<?>> getAllClassBySuper(Class<?> superClass){
        Set<Class<?>> hs = new HashSet<Class<?>>();
        for(Class<?> oneClass:allClassSet){
            if(superClass.isAssignableFrom(oneClass)&&!superClass.equals(oneClass)){
                hs.add(oneClass);
            }
        }
        return hs;
    }

    //获取某个注解的所有类
    public static Set<Class<?>> getAllClassByAnnotion(Class<? extends Annotation> annotion){
        Set<Class<?>> annotionClassSet = new HashSet<Class<?>>();
        for(Class<?> oneClass:allClassSet){
            if(oneClass.isAnnotationPresent(annotion)){
                annotionClassSet.add(oneClass);
            }
        }
        return annotionClassSet;
    }

    // TODO: 16/11/14 剩下的service类还有一些变量需要加载 

}
