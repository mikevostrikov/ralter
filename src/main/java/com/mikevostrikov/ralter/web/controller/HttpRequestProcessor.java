/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mikevostrikov.ralter.web.controller;

import com.mikevostrikov.ralter.app.domain.Repository;
import com.mikevostrikov.ralter.app.service.ApplicationServiceFactory;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author m.vostrikov
 */
public abstract class HttpRequestProcessor {
    
    private static final Logger LOG = Logger.getLogger(HttpRequestProcessor.class.getName());
    
    protected final HttpServletRequest request;
    protected final HttpServletResponse response;
    protected final ApplicationServiceFactory applicationServiceFactory;
    protected final Repository repository;
    protected final RequestRouter requestRouter;
    protected final HttpSession session;

    public HttpRequestProcessor(HttpServletRequest request, HttpServletResponse response, ApplicationServiceFactory applicationServiceFactory, Repository repository) {
        this.request = request;
        this.response = response;
        this.applicationServiceFactory = applicationServiceFactory;
        this.repository = repository;
        this.requestRouter = new RequestRouter(request, response);
        this.session = request.getSession();
    }

    public abstract void process();
    
}
