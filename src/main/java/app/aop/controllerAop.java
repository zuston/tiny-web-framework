package app.aop;

import io.github.zuston.framework.annotation.aspect;
import io.github.zuston.framework.annotation.controller;
import io.github.zuston.framework.proxy.AspectProxy;

import java.lang.reflect.Method;

/**
 * Created by zuston on 16-12-9.
 */
@aspect(value = controller.class)
public class controllerAop extends AspectProxy {
    public void before(Class<?> targetClass, Method targetMethod, Object[] methodParams){
        System.out.println(targetMethod.getName()+":begin");
    }
    public void after(Class<?> targetClass,Method targetMethod,Object[] methodParams){
        System.out.println(targetMethod.getName()+":end");
    }
}
