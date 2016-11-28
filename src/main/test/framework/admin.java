package framework;

import io.github.zuston.framework.orm.baseOrm;

import java.util.HashMap;

/**
 * Created by zuston on 16-11-28.
 */
public class admin extends baseOrm {
    public String name;
    public String password;
    public String permission;

    public admin(String name, String password, String permission) {
        this.name = name;
        this.password = password;
        this.permission = permission;
    }

    public admin(){

    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getPermission() {
        return permission;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public static void main(String[] args) throws Exception {
//        admin am = new admin("ann","ad25f4dc0e6833dc383b2c4f5a80011e","root");
//        am.save();
        admin am = new admin();
        HashMap<String,Object> hs = new HashMap<String, Object>();
        hs.put("cname","ac大");
        hs.put("ctag","0");
        am.find().where(hs).one();
    }
}
