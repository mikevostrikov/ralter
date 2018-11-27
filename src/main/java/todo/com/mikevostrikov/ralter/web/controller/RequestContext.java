/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mikevostrikov.ralter.web.controller.todo;

import com.mikevostrikov.ralter.app.domain.model.workers.User;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;

/**
 *
 * @author m.vostrikov
 */
public class RequestContext {
    
    private static final Logger LOG = Logger.getLogger(RequestContext.class.getName());
    
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession httpSession;
    private Session hibernateSession;
    private User user;
    private ActionController actionController;
    private HttpServlet httpServlet;
    private final UriFactory serverSideUriFactory = new ServerSideUriFactory();
    private final UriFactory clientSideUriFactory = new ClientSideUriFactory();

    public RequestContext(HttpServlet httpServlet, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession, Session hibernateSession, User user, ActionController actionController) {
        this.request = request;
        this.response = response;
        this.httpSession = httpSession;
        this.hibernateSession = hibernateSession;
        this.user = user;
        this.actionController = actionController;
        this.httpServlet = httpServlet;
    }
    
    /**
     * @return the request
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * @param request the request to set
     */
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * @return the response
     */
    public HttpServletResponse getResponse() {
        return response;
    }

    /**
     * @param response the response to set
     */
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    /**
     * @return the httpSession
     */
    public HttpSession getHttpSession() {
        return httpSession;
    }

    /**
     * @param httpSession the httpSession to set
     */
    public void setHttpSession(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    /**
     * @return the hibernateSession
     */
    public Session getHibernateSession() {
        return hibernateSession;
    }

    /**
     * @param hibernateSession the hibernateSession to set
     */
    public void setHibernateSession(Session hibernateSession) {
        this.hibernateSession = hibernateSession;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the serverSideUriFactory
     */
    public UriFactory getServerSideUriFactory() {
        return serverSideUriFactory;
    }

    /**
     * @return the clientSideUriFactory
     */
    public UriFactory getClientSideUriFactory() {
        return clientSideUriFactory;
    }

    public ActionController getActionController() {
        return actionController;
    }

    public void setActionController(ActionController actionController) {
        this.actionController = actionController;
    }

    protected void redirectClient(ActionController controller, UriParameter... params) {
        try {
            response.sendRedirect(clientSideUriFactory.getUri(controller, params));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    protected void forwardHttpRequest(ActionController controller, UriParameter... params) {
        forwardHttpRequest(serverSideUriFactory.getUri(controller, params));
    }
    
    protected void forwardHttpRequest(Jsp jsp, UriParameter... params) {
        forwardHttpRequest(serverSideUriFactory.getUri(jsp, params));
    }
        
    @SuppressWarnings("UseSpecificCatch")
    private void forwardHttpRequest(String uri) {
        LOG.log(Level.INFO, "{0} Request forwarded to {1}", new Object[]{httpSession.getId(), uri});

        RequestDispatcher rd = httpServlet.getServletContext().getRequestDispatcher(uri);
        
        try {
            rd.forward(request, response);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
}
