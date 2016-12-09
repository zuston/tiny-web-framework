package io.github.zuston.framework;

import io.github.zuston.framework.helper.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by zuston on 16/11/14.
 */
public class bootstrap {
    public static void init(){
        Class [] allInitClass = {
                aopHelper.class,
                classHelper.class,
                configHelper.class,
                coreHelper.class,
                beanHelper.class,
        };
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        for (Class oneClass:allInitClass){
            try {
                //强制实例化static
                Class.forName(oneClass.getName(),true,cl);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static Object reflection(Object obj, Method method, HashMap<String,Object> hs) throws InvocationTargetException, IllegalAccessException {
        Object result = null;

        result = method.invoke(obj,hs);

        return result;
    }
}
