package io.github.zuston.framework.util;

/**
 * Created by zuston on 16/11/13.
 */
public class castUtil {
    public static int castInt(String value){
        return value!=null?Integer.parseInt(String.valueOf(value).trim()):0;
    }

    public static boolean castBoolean(String value){

        return value!=null?Boolean.parseBoolean(String.valueOf(value).trim()):false;
    }

}
