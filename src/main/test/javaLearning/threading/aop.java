package javaLearning.threading;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by zuston on 16-12-9.
 */
public class aop {

    public static void main(String[] args) {
        hello action = new proxy(new helloImpl()).getProxy();
        action.say();

        hello cglibAction = (hello) Enhancer.create(helloImpl.class, new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("start");
                Object res = methodProxy.invokeSuper(o,objects);
                System.out.println("end");
                return res;
            }
        });
        cglibAction.say();
    }
}


interface hello{
    public void say();
}


class helloImpl implements hello{
    public void say(){
        System.out.println("hello!");
    }
}


class proxy implements InvocationHandler{

    public Object oj = null;

    public proxy(Object oj){
        this.oj = oj;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before");
        Object res = method.invoke(oj,args);
        System.out.println("after");
        return res;
    }

    public <T> T getProxy(){
        return (T) Proxy.newProxyInstance(
                oj.getClass().getClassLoader(),
                oj.getClass().getInterfaces(),
                this
        );
    }
}


class cglibProxy implements MethodInterceptor{

    public <T> T getProxy(Class<T> cls){
        return (T) Enhancer.create(cls,this);
    }

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("cglib before");
        Object res = methodProxy.invokeSuper(o, objects);
        System.out.println("cglib after");
        return res;
    }
}


