package javaLearning.threading;

import app.bean.postBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by zuston on 16-12-6.
 */
public class classloader {

    public static void main(String[] args) {
        Gson gson = new Gson();
        String a = "[{\"id\":\"1\",\"value\":\"\"},{\"id\":\"2\",\"value\":\"\"}]";
        List<postBean> l = gson.fromJson(a, new TypeToken<List<postBean>>(){}.getType());
        System.out.println(l.get(0).getId());
    }
}
