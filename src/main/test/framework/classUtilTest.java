package framework;

import io.github.zuston.framework.util.classUtil;

import java.util.Set;

/**
 * Created by zuston on 16/11/13.
 */
public class classUtilTest {

    public static void main(String[] args) {
        Set<Class<?>> allSet = classUtil.getAllClass("app");
        for (Class one:allSet){
            System.out.println(one.getSimpleName());
        }
    }
}
