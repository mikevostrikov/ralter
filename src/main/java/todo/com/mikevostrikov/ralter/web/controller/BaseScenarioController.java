/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todo.com.mikevostrikov.ralter.web.controller;

import todo.com.mikevostrikov.ralter.web.controller.SecurityWrapper;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;

/**
 *
 * @author m.vostrikov
 */
public abstract class BaseScenarioController extends HttpServlet implements RequestProcessor {
    
    private static final Logger LOG = Logger.getLogger(BaseScenarioController.class.getName());
    
    protected RequestContext requestContext;
    
    public abstract void processRequest();
    
    protected final Integer getServletID(){
        return new Integer(getServletConfig().getInitParameter("ServletId"));
    }
    
    protected final void doGetPost(HttpServletRequest request, HttpServletResponse response) {
        
        response.setContentType("text/html;charset=UTF-8");
        
        requestContext = new RequestContext(this, request, response, null, null, null, ActionController.LOG_SERVLET);
        
        new LoggingRequestWrapper(
            new SecurityWrapper(this, requestContext), requestContext
        ).processRequest();
        
    }
    
    @Override
    protected final void doGet(HttpServletRequest request, HttpServletResponse response) {
        doGetPost(request, response);
    }

    @Override
    protected final void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGetPost(request, response);
    }
    
}
