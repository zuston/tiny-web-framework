package io.github.zuston.framework.entity;

import java.lang.reflect.Method;

/**
 * Created by zuston on 16/11/14.
 */
public class handlerEntity {
    public Class<?> handlerClass;
    public Method handlerMethod;

    public Class<?> getHandlerClass() {
        return handlerClass;
    }

    public void setHandlerClass(Class<?> handlerClass) {
        this.handlerClass = handlerClass;
    }

    public Method getHandlerMethod() {
        return handlerMethod;
    }

    public handlerEntity(Class<?> handlerClass, Method handlerMethod) {

        this.handlerClass = handlerClass;
        this.handlerMethod = handlerMethod;

    }

    public handlerEntity(Class<?> handlerClass) {

        this.handlerClass = handlerClass;
    }
}
