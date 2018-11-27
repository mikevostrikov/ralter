/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mikevostrikov.ralter.web.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author m.vostrikov
 */
public class RequestRouter {
    
    private static final Logger LOG = Logger.getLogger(RequestRouter.class.getName());
    
    protected final UriFactory serverSideUriFactory;
    protected final UriFactory clientSideUriFactory;
    protected final HttpServletRequest request;
    protected final HttpServletResponse response;

    public RequestRouter(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.clientSideUriFactory = new ClientSideUriFactory();
        this.serverSideUriFactory = new ServerSideUriFactory();
    }

    public void redirectClient(Controller controller, UriParameter... params) {
        try {
            response.sendRedirect(clientSideUriFactory.getUri(controller, params));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public void forwardHttpRequest(Controller controller, UriParameter... params) {
        forwardHttpRequest(serverSideUriFactory.getUri(controller, params));
    }
    
    public void forwardHttpRequest(Jsp jsp, UriParameter... params) {
        forwardHttpRequest(serverSideUriFactory.getUri(jsp, params));
    }
        
    @SuppressWarnings("UseSpecificCatch")
    private void forwardHttpRequest(String uri) {
        LOG.log(Level.INFO, "Request forwarded to {0}", uri);

        RequestDispatcher rd = request.getServletContext().getRequestDispatcher(uri);
        
        try {
            rd.forward(request, response);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        
    }
    
}
