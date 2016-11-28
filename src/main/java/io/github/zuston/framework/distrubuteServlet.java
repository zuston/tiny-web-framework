package io.github.zuston.framework;

import io.github.zuston.framework.core.container;
import io.github.zuston.framework.entity.handlerEntity;
import io.github.zuston.framework.entity.requestEntity;
import io.github.zuston.framework.entity.viewEntity;
import io.github.zuston.framework.helper.configHelper;
import io.github.zuston.framework.helper.coreHelper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by zuston on 16/11/13.
 */
@WebServlet(urlPatterns = "/*",loadOnStartup = 0)
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
        String viewPath = configHelper.viewPath();
        String defaultJSP = configHelper.defaultJSP();
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
                req = container.putHM2Request(req,pageHM);
                req.getRequestDispatcher(viewPath+viewName).forward(req,resp);
            }



        }else{
            req.getRequestDispatcher(viewPath+defaultJSP).forward(req,resp);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        bootstrap.init();
        ServletContext servletContext = servletConfig.getServletContext();
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(configHelper.viewPath()+"*");
    }
}
