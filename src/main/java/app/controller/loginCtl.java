package app.controller;

import io.github.zuston.framework.annotation.action;
import io.github.zuston.framework.annotation.controller;

/**
 * Created by zuston on 16/11/13.
 */
@controller
public class loginCtl {

    @action("post:/hello")
    public void hello(){
        System.out.println("hello world");
    }
}
