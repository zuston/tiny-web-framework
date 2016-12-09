package app.controller;

import app.orm.user;
import io.github.zuston.framework.annotation.action;
import io.github.zuston.framework.annotation.controller;
import io.github.zuston.framework.entity.viewEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zuston on 16-12-8.
 */
@controller
public class manageCtl {
    @action("get:/userlist")
    public viewEntity userList(HashMap<String,Object> hs) throws Exception {
        HttpServletRequest req = (HttpServletRequest) hs.get("request");
        HttpSession session = req.getSession();
        if (!(Boolean) session.getAttribute("isLogin")||session.getAttribute("isLogin")==null){
            return new viewEntity("login.jsp");
        }
        user currentUser = (user) hs.get("currentUser");
        user res = new user();
        List<Object> personList = res.find().all();

        return new viewEntity("user.jsp");
    }
}
