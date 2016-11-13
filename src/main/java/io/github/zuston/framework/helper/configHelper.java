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
}
