package io.github.zuston.framework.proxy;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zuston on 16-12-9.
 */
public class ProxyChain {

    private  Class<?> targetClass;
    private  Object targetObject;
    private  Method targetMethod;
    private  MethodProxy methodProxy;
    private  Object[] methodParams;

    private List<Proxy> proxyList = new ArrayList<Proxy>();

    private int index = 0;
    public ProxyChain(Class<?> targetClass, Object targetObject, Method targetMethod, MethodProxy methodProxy, Object[] methodParams,List<Proxy> proxyList) {
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.methodParams = methodParams;
        this.proxyList = proxyList;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Object getTargetObject() {
        return targetObject;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public MethodProxy getMethodProxy() {
        return methodProxy;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }

    public List<Proxy> getProxyList() {
        return proxyList;
    }

    public Object doProxyChain() throws Throwable{
        Object methodResult;
        if(index<proxyList.size()){
            System.out.println("index:"+index);
            methodResult = proxyList.get(index++).doProxy(this);
        }else{
            methodResult = methodProxy.invokeSuper(targetObject,methodParams);
        }
        return methodResult;
    }
}

