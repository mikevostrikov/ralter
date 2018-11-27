package com.mikevostrikov.ralter.web.controller.security;

import com.mikevostrikov.ralter.app.domain.Repository;
import com.mikevostrikov.ralter.web.controller.Jsp;
import com.mikevostrikov.ralter.web.controller.RequestAttribute;
import com.mikevostrikov.ralter.web.controller.Controller;
import com.mikevostrikov.ralter.web.controller.SessionAttribute;
import com.mikevostrikov.ralter.app.domain.model.workers.User;
import com.mikevostrikov.ralter.app.service.ApplicationServiceFactory;
import com.mikevostrikov.ralter.web.controller.HttpRequestProcessor;
import com.mikevostrikov.ralter.web.viewmodel.SignInViewModel;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * ActionController implementation class SecurityController
 */
public class SecurityController extends HttpRequestProcessor {
    
    private static final Logger LOG = Logger.getLogger(SecurityController.class.getName());
    
    SecurityControllerRequestModel requestModel;

    public SecurityController(HttpServletRequest request, HttpServletResponse response, ApplicationServiceFactory applicationServiceFactory, Repository repository) {
        super(request, response, applicationServiceFactory, repository);
        this.requestModel = new SecurityControllerRequestModel(request);
        LOG.log(Level.INFO, "Request model parsed: ", requestModel.toString());
    }
    
    @Override
    public void process() {
        switch (requestModel.getAction()) {
            case NULL:
                emptyAction(); break;
            case AUTH:
                authenticateAction(); break;                     
        }
    }
    
    // actions    
    private void emptyAction() {
        LOG.log(Level.INFO, "action begin...");
        SignInViewModel viewModel = new SignInViewModel(false);
        request.setAttribute(RequestAttribute.VIEW_DATA.name(), viewModel);
        renderSignInView(viewModel);
        LOG.log(Level.INFO, "action end");
    }

    private void authenticateAction() {
        LOG.log(Level.INFO, "action begin...");        
        // try to authenticate
        Optional<User> user = Optional.ofNullable(applicationServiceFactory.getWorkersApplicationService().authenticate(requestModel.getUsername(), requestModel.getPassword()));
        
        user.ifPresent((User u) -> {
            session.setAttribute(SessionAttribute.USER.name(), user.get());
            LOG.log(Level.INFO, "Session {0}. Successfull authentication. Login: {1}", new Object[]{session.getId(), u});
            requestRouter.redirectClient(Controller.HOMEPAGE_SERVLET);            
        });
        
        if (!user.isPresent()) {
            // report authentication failed
            LOG.log(Level.INFO, "Session {0}. Authentication failed. Login: {1}, Password: {2}", new Object[]{session, requestModel.getUsername(), requestModel.getPassword()});
            SignInViewModel viewModel = new SignInViewModel(true);
            renderSignInView(viewModel);      
        }
        LOG.log(Level.INFO, "action end");
    }

    // renderers methods
    
    private void renderSignInView(SignInViewModel viewModel) {
        request.setAttribute(RequestAttribute.VIEW_DATA.name(), viewModel);
        requestRouter.forwardHttpRequest(Jsp.SIGNIN_JSP);
    }
}
