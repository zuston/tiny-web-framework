package io.github.zuston.framework;

import io.github.zuston.framework.helper.classHelper;
import io.github.zuston.framework.helper.configHelper;
import io.github.zuston.framework.helper.coreHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by zuston on 16/11/14.
 */
public class bootstrap {
    public static void init(){
        Class [] allInitClass = {
                classHelper.class,
                configHelper.class,
                coreHelper.class
        };
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        for (Class oneClass:allInitClass){
            try {
                cl.loadClass(oneClass.getName());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static Object reflection(Class oneClass, Method method, HashMap<String,Object> hs){
        Object result = null;
        try {
            Object instance = oneClass.newInstance();
            result = method.invoke(instance,hs);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }
}
