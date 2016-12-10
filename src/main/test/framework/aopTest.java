package framework;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zuston on 16-12-9.
 */

/**
 * 实现多重aop的调用，因为cglib不支持
 */
public class aopTest {

    public static void main(String[] args) {

        fuckImpl fl = new fuckImpl();
        final List<baseProxy> listProxy = new ArrayList<>();
        listProxy.add(new aProxy());
        listProxy.add(new bProxy());

        hello sHello = (hello) Enhancer.create(fl.getClass(), new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                Object res = new proxyChain(listProxy,o,method,objects,methodProxy).doChain();
                return res;
            }
        });
        sHello.say();
    }
}

class proxyChain{
    List<baseProxy> listProxy = new ArrayList<>();
    Object o;
    Method method;
    Object[] objects;
    MethodProxy methodProxy;

    int index = 0;

    public proxyChain(List<baseProxy> listProxy, Object o, Method method, Object[] objects, MethodProxy methodProxy) {
        this.listProxy = listProxy;
        this.o = o;
        this.method = method;
        this.objects = objects;
        this.methodProxy = methodProxy;
    }

    public Object doChain() throws Throwable {
        Object res = null;
        if(index<listProxy.size()){
            res = listProxy.get(index++).doProxy(this);
        }else{
            res = methodProxy.invokeSuper(o,objects);
        }
        return res;
    }
}


interface hello{
    void say();
}

class helloImpl implements hello{

    @Override
    public void say() {
        System.out.println("hello world");
    }
}


class fuckImpl implements hello{

    @Override
    public void say() {
        System.out.println("fuck world");
    }
}

class baseProxy{
    public Object doProxy(proxyChain chain) throws Throwable {
        before();
        //递归调用
        Object res = chain.doChain();
        after();
        return res;
    }

    public void before(){

    }

    public void after(){

    }
}


class aProxy extends baseProxy{
    public void before(){
        System.out.println("a.before");
    }

    public void after(){
        System.out.println("a.after");
    }
}


class bProxy extends baseProxy{
    public void before(){
        System.out.println("b.before");
    }

    public void after(){
        System.out.println("b.after");
    }
}
