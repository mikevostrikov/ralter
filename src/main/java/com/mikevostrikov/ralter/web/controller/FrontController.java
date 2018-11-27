/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mikevostrikov.ralter.web.controller;

import com.mikevostrikov.ralter.app.domain.Repository;
import com.mikevostrikov.ralter.app.service.ApplicationServiceFactory;
import com.mikevostrikov.ralter.infra.jpa.EntityManagerUtil;
import com.mikevostrikov.ralter.infra.jpa.JpaRepository;
import com.mikevostrikov.ralter.web.controller.workers.EmployeeController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mikevostrikov.ralter.web.controller.security.SecurityController;
import com.mikevostrikov.ralter.web.controller.security.SignOutController;

/**
 *
 * @author m.vostrikov
 */
@WebServlet(
    urlPatterns = {
        Controller.Constants.EMPLOYEES_SERVLET_PATH
       ,Controller.Constants.SECURITY_MOD_SERVLET_PATH
       ,Controller.Constants.HOMEPAGE_SERVLET_PATH
       ,Controller.Constants.SIGNOUT_SERVLET_PATH
       ,"/app/*"
})
public class FrontController extends HttpServlet {
    
    private static final Logger LOG = Logger.getLogger(FrontController.class.getName());
    
    protected final void doGetPost(HttpServletRequest request, HttpServletResponse response) {
        
        LOG.log(Level.INFO, "Path requested: {0}", request.getServletPath());
        
        Repository repository = new JpaRepository(EntityManagerUtil.getEntityManager());
        ApplicationServiceFactory applicationServiceFactory = new ApplicationServiceFactory(repository);
        // Dispatch to controller based on the servlet path
        switch (request.getServletPath()) {
            case Controller.Constants.EMPLOYEES_SERVLET_PATH: new EmployeeController(request, response, applicationServiceFactory, repository).process(); break;
            case Controller.Constants.SECURITY_MOD_SERVLET_PATH: new SecurityController(request, response, applicationServiceFactory, repository).process(); break;
            case Controller.Constants.HOMEPAGE_SERVLET_PATH: new HomePageController(request, response, applicationServiceFactory, repository).process(); break;
            case Controller.Constants.SIGNOUT_SERVLET_PATH: new SignOutController(request, response, applicationServiceFactory, repository).process(); break;
            default: new HomePageController(request, response, applicationServiceFactory, repository).process();
        }
        repository.closeSession();
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
