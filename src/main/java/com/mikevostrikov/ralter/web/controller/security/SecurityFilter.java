/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mikevostrikov.ralter.web.controller.security;

import com.mikevostrikov.ralter.app.domain.model.workers.User;
import com.mikevostrikov.ralter.web.controller.Controller;
import com.mikevostrikov.ralter.web.controller.RequestRouter;
import com.mikevostrikov.ralter.web.controller.SessionAttribute;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author m.vostrikov
 */
@WebFilter(urlPatterns = "/app/*")
public class SecurityFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(SecurityFilter.class.getName());
    
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        RequestRouter requestRouter = new RequestRouter(request, response);
        User user = (session != null) ? (User) session.getAttribute(SessionAttribute.USER.name()) : null;
        boolean isUserLoggedIn = user != null;
        boolean isUserAuthorized = false;
        boolean isRequestToAuthenticationServlet = false;
        boolean isResourceAllowed = false;        
        Controller requestedController = Controller.getByPath(request.getServletPath());
        boolean isRequestToUnknownResource = requestedController == null;
        if (!isRequestToUnknownResource) {
            isRequestToAuthenticationServlet = Controller.AUTHENTICATION_SERVLET.equals(requestedController);
            isResourceAllowed = isUserLoggedIn && user.isAuthorizedForResourceId(requestedController.getId());
        }
        
        isUserAuthorized = 
                1==0
            ||  isRequestToAuthenticationServlet
            ||  isResourceAllowed
        ;
        LOG.log(Level.INFO, "user: {0}", user);
        LOG.log(Level.INFO, "isUserLoggedIn: {0}", isUserLoggedIn);
        LOG.log(Level.INFO, "isRequestToUnknownResource: {0}", isRequestToUnknownResource);
        LOG.log(Level.INFO, "isRequestToAuthenticationServlet: {0}", isRequestToAuthenticationServlet);
        LOG.log(Level.INFO, "isResourceAllowed: {0}", isResourceAllowed);
        LOG.log(Level.INFO, "isUserAuthorized: {0}", isUserAuthorized);
        
        if (isUserLoggedIn) {
            if (!isRequestToUnknownResource) {
                if (isUserAuthorized) {
                    chain.doFilter(request, response);
                } else {
                    session.setAttribute(SessionAttribute.ERROR_MSG.name(), "You are not authorized to use the requested functionality. Please refer to the administrator.");
                    requestRouter.redirectClient(Controller.HOMEPAGE_SERVLET);
                }
            } else {
                session.setAttribute(SessionAttribute.ERROR_MSG.name(), "The requested web-page does not exist. Please refer to the administrator.");
                requestRouter.redirectClient(Controller.HOMEPAGE_SERVLET);            
            }
        } else {
            if (isRequestToAuthenticationServlet) {
                chain.doFilter(request, response);
            } else {
                requestRouter.redirectClient(Controller.AUTHENTICATION_SERVLET);
            }
        }

    }

    @Override
    public void init(FilterConfig fc) throws ServletException {}

    @Override
    public void destroy() {}
    
}