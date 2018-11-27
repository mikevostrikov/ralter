package com.mikevostrikov.ralter.web.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.mikevostrikov.ralter.app.domain.Repository;
import com.mikevostrikov.ralter.app.domain.model.workers.User;
import com.mikevostrikov.ralter.app.service.ApplicationServiceFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author m.vostrikov
 */

public class HomePageController extends HttpRequestProcessor {

    public HomePageController(HttpServletRequest request, HttpServletResponse response, ApplicationServiceFactory applicationServiceFactory, Repository repository) {
        super(request, response, applicationServiceFactory, repository);
    }

    @Override
    public void process() {
        User user = (User) session.getAttribute(SessionAttribute.USER.name());
        new RequestRouter(request, response).redirectClient(Controller.getById(user.getDefaultControllerId()));
    }
    
}
