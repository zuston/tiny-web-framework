package io.github.zuston.framework.core;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zuston on 16/11/14.
 */

/**
 * 封装request的所有参数
 */
public class container {
    public static HashMap<String,Object> getAllParam(HttpServletRequest request){
        HashMap<String,Object> hs = new HashMap<String, Object>();
        Enumeration<String> names  = request.getParameterNames();
        while (names.hasMoreElements()){
            String name = names.nextElement();
            String value = request.getParameter(name);
            hs.put(name,value);
        }
        return hs;
    }

    public static HttpServletRequest putHM2Request(HttpServletRequest req, HashMap<String,Object> hashMap){
        for(Map.Entry<String,Object> m:hashMap.entrySet()){
            req.setAttribute(m.getKey(),m.getValue());
        }
        return req;
    }


}
