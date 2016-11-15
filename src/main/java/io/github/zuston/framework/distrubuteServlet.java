package io.github.zuston.framework;

import io.github.zuston.framework.core.container;
import io.github.zuston.framework.entity.handlerEntity;
import io.github.zuston.framework.entity.requestEntity;
import io.github.zuston.framework.entity.viewEntity;
import io.github.zuston.framework.helper.coreHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by zuston on 16/11/13.
 */
@WebServlet(urlPatterns = "/*",loadOnStartup = 1)
public class distrubuteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("hello zuston");
        String requestMethod = req.getMethod().toLowerCase();
        String requestUrlPattern = req.getRequestURI().toLowerCase();
        handlerEntity handler = coreHelper.getHandler(new requestEntity(requestMethod,requestUrlPattern));
        if(handler!=null){
            Class handlerClass = handler.getHandlerClass();
            Method handlerMethod = handler.getHandlerMethod();
            Object res = bootstrap.reflection(handlerClass,handlerMethod, container.getAllParam(req));
            if(res.getClass()==viewEntity.class){
                viewEntity view = (viewEntity)res;
                HashMap<String,Object> pageHM = view.getModel();
                String viewName = view.getView();
                System.out.println(viewName);
//                req.getRequestDispatcher("/index.jsp").forward(req,resp);
            }else{
                // TODO: 16/11/14
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void init() throws ServletException {
        bootstrap.init();
    }
}
