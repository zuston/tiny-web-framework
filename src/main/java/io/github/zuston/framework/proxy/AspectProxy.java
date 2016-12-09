package io.github.zuston.framework.proxy;

/**
 * Created by zuston on 16-12-9.
 */
public abstract class AspectProxy implements Proxy{

    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        before();
        Object result = proxyChain.doProxyChain();
        after();
        return result;
    }

    public void before(){
    }

    public void after(){
    }
}
