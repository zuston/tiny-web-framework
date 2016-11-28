package io.github.zuston.framework.helper;

import io.github.zuston.framework.util.propertyUtil;

import java.util.Properties;

/**
 * Created by zuston on 16/11/13.
 */
public class configHelper {

    public static String getPackageName(){
        Properties prop = propertyUtil.loadProperty("Config.properties");
        return propertyUtil.getString(prop,"packageName");
    }

    public static String packageOrm(){
        Properties prop = propertyUtil.loadProperty("Config.properties");
        return propertyUtil.getString(prop,"packageOrm");
    }

    public static String viewPath(){
        return "/WEB-INF/view/";
    }

    public static String defaultJSP() {
        return "default.jsp";
    }


    public static String dbName(){
        Properties prop = propertyUtil.loadProperty("dbConfig.properties");
        return propertyUtil.getString(prop,"dbname");
    }

    public static String dbUsername(){
        Properties prop = propertyUtil.loadProperty("dbConfig.properties");
        return propertyUtil.getString(prop,"user");
    }

    public static String dbPassword(){
        Properties prop = propertyUtil.loadProperty("dbConfig.properties");
        return propertyUtil.getString(prop,"pwd");
    }
}
