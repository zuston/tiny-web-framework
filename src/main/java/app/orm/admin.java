package app.orm;

import io.github.zuston.framework.orm.baseOrm;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zuston on 16-11-28.
 */
public class admin extends baseOrm {
    static {
        System.out.println("init the admin model");
    }
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
//        hs.put("name","shacha");
//        hs.put("permission","root");
//        admin ad = (admin) am.find().where(hs).one();
//        System.out.println(ad.getPassword());

        hs.put("1","1");
        List<Object> adList = am.find().where(hs).all();
        for (Object a:adList){
            admin p = (admin)a;
            System.out.println(p.getName());
        }
    }
}
