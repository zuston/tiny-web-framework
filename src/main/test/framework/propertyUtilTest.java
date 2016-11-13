package framework;

import io.github.zuston.framework.util.propertyUtil;

import java.util.Properties;

/**
 * Created by zuston on 16/11/13.
 */
public class propertyUtilTest {

    public static void main(String[] args) {
        Properties prop = propertyUtil.loadProperty("test.properties");
        String name = propertyUtil.getString(prop,"name");
        int age = propertyUtil.getInt(prop,"age");
        boolean love = propertyUtil.getBoolean(prop,"love");
        System.out.println(name);
        System.out.println(age);
        System.out.println(love);
    }
}
