package framework;

import io.github.zuston.framework.entity.handlerEntity;
import io.github.zuston.framework.entity.requestEntity;
import io.github.zuston.framework.helper.classHelper;
import io.github.zuston.framework.helper.configHelper;
import io.github.zuston.framework.helper.coreHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by zuston on 16/11/14.
 */
public class helperTest {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Class [] all = {classHelper.class,
                        configHelper.class,
                        coreHelper.class,
        };

        for (Class oneclass:all){
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            try {
                cl.loadClass(oneclass.getName());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        HashMap<requestEntity,handlerEntity> hs = new HashMap<requestEntity, handlerEntity>();
        hs = coreHelper.getCoreMap();
        Iterator i = hs.entrySet().iterator();
        while (i.hasNext()){
            Map.Entry mp = (Map.Entry) i.next();
            requestEntity req = (requestEntity) mp.getKey();
            System.out.println(req.hashCode());
            System.out.println(req.getRequestMethod()+":"+req.getRequestUrl());
            handlerEntity handler = hs.get(req);

            Class handlerClass = handler.getHandlerClass();
            Method handleMethod = handler.getHandlerMethod();
            Object instance = handlerClass.newInstance();
            handleMethod.invoke(instance);
        }

        requestEntity r = new requestEntity("get","/pull");
        System.out.println(r.hashCode());

    }
}
