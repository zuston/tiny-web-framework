package app.controller;

import app.service.loginService;
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

    @action("get:/login")
    public viewEntity login(HashMap<String,Object> hs){
        viewEntity ve = new viewEntity("index.jsp");
        ve.addModel("name","zuston");
        return new viewEntity("index.jsp").addModel("name","zuston");
    }
}
