/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mikevostrikov.ralter.web.controller.security;

import com.mikevostrikov.ralter.app.domain.Repository;
import com.mikevostrikov.ralter.app.service.ApplicationServiceFactory;
import com.mikevostrikov.ralter.web.controller.HttpRequestProcessor;
import com.mikevostrikov.ralter.web.controller.Jsp;
import com.mikevostrikov.ralter.web.controller.RequestAttribute;
import com.mikevostrikov.ralter.web.controller.RequestRouter;
import com.mikevostrikov.ralter.web.controller.SessionAttribute;
import com.mikevostrikov.ralter.web.viewmodel.SignOutViewModel;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author m.vostrikov
 */

public class SignOutController extends HttpRequestProcessor {

    public SignOutController(HttpServletRequest request, HttpServletResponse response, ApplicationServiceFactory applicationServiceFactory, Repository repository) {
        super(request, response, applicationServiceFactory, repository);
    }
    
    @Override
    public void process() {
        session.setAttribute(SessionAttribute.USER.name(), null);
        SignOutViewModel viewModel = new SignOutViewModel();
        renderSignOutPage(viewModel);
    }    
    // view rendering methods
    
    private void renderSignOutPage(SignOutViewModel viewModel){
        request.setAttribute(RequestAttribute.VIEW_DATA.name(), viewModel);
        new RequestRouter(request, response).forwardHttpRequest(Jsp.SIGNOUT_JSP);
    }
    
}
