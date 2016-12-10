package io.github.zuston.framework.proxy;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by zuston on 16-12-9.
 */
public abstract class AspectProxy implements Proxy{

    public Object doProxy(ProxyChain proxyChain) throws Throwable {

        Class<?> targetClass = proxyChain.getTargetClass();
        Object targetObject = proxyChain.getTargetObject();
        Method targetMethod = proxyChain.getTargetMethod();
        MethodProxy methodProxy = proxyChain.getMethodProxy();
        Object[] methodParams = proxyChain.getMethodParams();

        before(targetClass,targetMethod,methodParams);
        Object result = proxyChain.doProxyChain();
        after(targetClass,targetMethod,methodParams);
        return result;
    }

    public void before(Class<?> targetClass,Method targetMethod,Object[] methodParams){
    }

    public void after(Class<?> targetClass,Method targetMethod,Object[] methodParams){
    }
}
