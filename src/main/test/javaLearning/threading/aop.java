package javaLearning.threading;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by zuston on 16-12-9.
 */
public class aop {

    public static void main(String[] args) {
        //此处输出也会有before和after，怀疑是sout调用了tostring,事实证明是的
        System.out.println(new proxy(new helloImpl()).getProxy());
        hello action = new proxy(new helloImpl()).getProxy();
        action.say();
    }
}


interface hello{
    public void say();
}


class helloImpl implements hello{
    public helloImpl(){
        System.out.println("init the instance");
    }
    public void say(){
        System.out.println("hello!");
    }

    @Override
    public String toString() {
        System.out.println("tostring invoke");
        return super.toString();
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


