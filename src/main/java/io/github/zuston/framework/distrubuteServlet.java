package io.github.zuston.framework;

import io.github.zuston.framework.entity.handlerEntity;
import io.github.zuston.framework.entity.requestEntity;
import io.github.zuston.framework.helper.classHelper;
import io.github.zuston.framework.helper.configHelper;
import io.github.zuston.framework.helper.coreHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by zuston on 16/11/13.
 */
@WebServlet("/*")
public class distrubuteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestMethod = req.getMethod().toLowerCase();
        String requestUrlPattern = req.getRequestURI().toLowerCase();
        requestEntity r = new requestEntity(requestMethod,requestUrlPattern);
        handlerEntity handler = coreHelper.getHandler(new requestEntity(requestMethod,requestUrlPattern));
        if(handler!=null){
            
        }else{
            // TODO: 16-11-14 重新跳转至另外一个页面
            PrintWriter out = resp.getWriter();
            out.print("can't find the resource");
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void init() throws ServletException {
        Class [] allInitClass = {
                classHelper.class,
                configHelper.class,
                coreHelper.class
        };
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        for (Class oneClass:allInitClass){
            try {
                cl.loadClass(oneClass.getName());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
