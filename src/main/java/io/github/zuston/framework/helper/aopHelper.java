package io.github.zuston.framework.helper;

import io.github.zuston.framework.annotation.aspect;
import io.github.zuston.framework.proxy.AspectProxy;
import io.github.zuston.framework.proxy.Proxy;
import io.github.zuston.framework.proxy.ProxyManager;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * Created by zuston on 16-12-9.
 */
public class aopHelper {

    static{
        try {
            Map<Class<?>,List<Proxy>> target2Proxy = createTargetMap(createProxyMap());
            for (Map.Entry<Class<?>,List<Proxy>> oneMap:target2Proxy.entrySet()){
                Class<?> key = oneMap.getKey();
                List<Proxy> value = oneMap.getValue();
                for (Proxy v:value){
                    System.out.println(v.getClass().getName());
                }
                System.out.println("======================");
                Object res = ProxyManager.createProxy(key,value);
                beanHelper.setBeanMap(key,res);
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public static Map<Class<?>,List<Proxy>> createTargetMap(Map<Class<?>,Set<Class<?>>> proxyMap) throws IllegalAccessException, InstantiationException {
        Map<Class<?>,List<Proxy>> targetMap = new HashMap<Class<?>, List<Proxy>>();
        for (Map.Entry<Class<?>,Set<Class<?>>> oneMap:proxyMap.entrySet()){
            Class<?> proxyClass = oneMap.getKey();
            for (Class<?> target:oneMap.getValue()){
                Proxy proxyInstance = (Proxy) proxyClass.newInstance();
                if(targetMap.containsKey(target)){
                    targetMap.get(target).add(proxyInstance);
                }else{
                    List<Proxy> proxyList = new ArrayList<Proxy>();
                    proxyList.add(proxyInstance);
                    targetMap.put(target,proxyList);
                }
            }
        }
        return targetMap;
    }

    public static Map<Class<?>,Set<Class<?>>> createProxyMap(){
        Map<Class<?>,Set<Class<?>>> aspectMap = new HashMap<Class<?>, Set<Class<?>>>();
        Set<Class<?>> superClassSet = classHelper.getAllClassBySuper(AspectProxy.class);
        for(Class<?> oneAspect:superClassSet){
            if (oneAspect.isAnnotationPresent(aspect.class)){
                aspect aspectAnnotion = oneAspect.getAnnotation(aspect.class);
                Set<Class<?>> targetClassSet = targetClassSet(aspectAnnotion);
                aspectMap.put(oneAspect,targetClassSet);
            }
        }
        return aspectMap;
    }


    public static Set<Class<?>> targetClassSet(aspect asp){
        Set<Class<?>> targetClsSet = new HashSet<Class<?>>();
        Class<? extends Annotation> annotion = asp.value();
        if(annotion!=null&&!annotion.equals(aspect.class)){
            targetClsSet.addAll(classHelper.getAllClassByAnnotion(annotion));
        }
        return targetClsSet;
    }


}
