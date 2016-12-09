package app.aop;

import io.github.zuston.framework.annotation.aspect;
import io.github.zuston.framework.annotation.controller;
import io.github.zuston.framework.proxy.AspectProxy;

/**
 * Created by zuston on 16-12-9.
 */
@aspect(value = controller.class)
public class aspectproxy extends AspectProxy {
    public void before(){
        System.out.println("begin");
    }
    public void after(){
        System.out.println("end");
    }
}
