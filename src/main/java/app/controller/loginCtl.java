package app.controller;

import app.orm.user;
import com.sun.corba.se.spi.ior.ObjectKey;
import io.github.zuston.framework.annotation.action;
import io.github.zuston.framework.annotation.controller;
import io.github.zuston.framework.entity.viewEntity;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zuston on 16/11/13.
 */
@controller
public class loginCtl {

    @action("get:/login")
    public viewEntity login(HashMap<String,Object> hs) throws Exception {
        HttpServletRequest req = (HttpServletRequest) hs.get("request");
        HttpSession session = req.getSession();
        if (session.getAttribute("isLogin")==null){
            return new viewEntity("login.jsp");
        }
        Boolean loginFlag = (Boolean) session.getAttribute("isLogin");
        if(loginFlag){
            user currentUser = (user) session.getAttribute("currentUser");
            hs.put("currentUser",currentUser);
            return show(hs);
        }
        return new viewEntity("login.jsp");
    }

    @action("post:/login")
    public viewEntity loginPost(HashMap<String,Object> hs) throws Exception {
        HttpServletRequest req = (HttpServletRequest) hs.get("request");
        String username = req.getParameter("username");
        String pwd = req.getParameter("pwd");

        HashMap<String,Object> hq = new HashMap<String, Object>();
        hq.put("nick_name",username);
        user res = new user();
        user person = (user) res.find().where(hq).one();
        if (person!=null&&person.pwd.equals(pwd)){
            HttpSession session = req.getSession();
            session.setAttribute("isLogin",true);
            session.setAttribute("currentUser",person);
            hs.put("currentUser",person);
            return show(hs);
        } else{
            return login(hs);
        }
    }


    @action("get:/logout")
    public viewEntity logout(HashMap<String,Object> hs) throws Exception {
        HttpServletRequest req = (HttpServletRequest) hs.get("request");
        HttpSession session = req.getSession();
        session.setAttribute("isLogin",false);
        return login(hs);
    }


    @action("get:/show")
    public viewEntity show(HashMap<String,Object> hs) throws Exception {
        user currentUser = (user) hs.get("currentUser");
        user res = new user();
        List<Object> personList = res.find().all();
        return new viewEntity("show.jsp").addModel("name",currentUser.name).addModel("personList",personList);
    }


}
