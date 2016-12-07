package app.controller;

import app.bean.postBean;
import app.orm.thing;
import app.orm.user;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.github.zuston.framework.annotation.action;
import io.github.zuston.framework.annotation.controller;
import io.github.zuston.framework.entity.viewEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

//        分组查询todo列表
        String sql = "select * from (select * from thing order by time desc) as a group by userId";
        List<thing> todoThingList = new ArrayList<thing>();
        Map<Integer,String> id2string = new HashMap<Integer, String>();
        thing todos = new thing();
        ResultSet resset = todos.findBySql(sql);
        while (resset.next()){
            thing temp = new thing(resset.getInt("userId"),resset.getString("content"));
            todoThingList.add(temp);
            id2string.put(resset.getInt("userId"),resset.getString("content"));
        }
        return new viewEntity("show.jsp").addModel("name",currentUser.name).addModel("personList",personList).addModel("relation",id2string);
    }


    @action("post:/savetodo")
    public void save(HashMap<String,Object> hs) throws IllegalAccessException {
        HttpServletRequest req = (HttpServletRequest) hs.get("request");
        String data = req.getParameter("data");
        System.out.println(data);
        Gson gson = new Gson();
        List<postBean> l = gson.fromJson(data, new TypeToken<List<postBean>>(){}.getType());
        for (postBean dataOne:l){
            thing t = new thing(dataOne.getId(),dataOne.getValue());
            t.save();
        }
    }
}
