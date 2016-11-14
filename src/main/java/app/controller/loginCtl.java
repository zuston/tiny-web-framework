package app.controller;

import io.github.zuston.framework.annotation.action;
import io.github.zuston.framework.annotation.controller;
import io.github.zuston.framework.entity.viewEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Created by zuston on 16/11/13.
 */
@controller
public class loginCtl {

    @action("get:/favicon.ico")
    public void hello(HashMap<String,Object> hs){
        System.out.println("hello world");
    }

    @action("get:/pull")
    public viewEntity pull(HashMap<String,Object> hs){
        HashMap<String,Object> h = new HashMap<String, Object>();
        return new viewEntity("index.jsp");
    }
}
