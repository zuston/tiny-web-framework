package io.github.zuston.framework.proxy;

/**
 * Created by zuston on 16-12-9.
 */
public interface Proxy {
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
