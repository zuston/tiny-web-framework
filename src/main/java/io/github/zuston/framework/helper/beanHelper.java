package io.github.zuston.framework.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by zuston on 16-12-9.
 */
public class beanHelper {
    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<Class<?>, Object>();

    static{
        Set<Class<?>> set = classHelper.getAllControllerClass();
        for(Class<?> one:set){
            try {
                Object obj = one.newInstance();
                BEAN_MAP.put(one,obj);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static Map<Class<?>,Object> getBean(){
        return BEAN_MAP;
    }

    public static <T> T get(Class<T> cls){
        return (T) BEAN_MAP.get(cls);
    }


    public static void setBeanMap(Class<?> cls,Object obj){
        BEAN_MAP.put(cls,obj);
    }
}
