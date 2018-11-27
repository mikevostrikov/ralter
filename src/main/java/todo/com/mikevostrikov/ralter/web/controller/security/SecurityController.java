package todo.com.mikevostrikov.ralter.web.controller.security;

import com.mikevostrikov.ralter.app.domain.Repository;
import todo.com.mikevostrikov.ralter.web.controller.BaseScenarioController;
import com.mikevostrikov.ralter.web.controller.todo.Jsp;
import com.mikevostrikov.ralter.web.controller.todo.RequestAttribute;
import com.mikevostrikov.ralter.web.controller.todo.ActionController;
import com.mikevostrikov.ralter.web.controller.todo.SessionAttribute;
import com.mikevostrikov.ralter.app.domain.model.workers.User;
import com.mikevostrikov.ralter.app.service.ApplicationServiceFactory;
import todo.com.mikevostrikov.ralter.app.domain.model.Webres;
import com.mikevostrikov.ralter.web.viewmodel.SignInViewModel;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ActionController implementation class SecurityController
 */
public class SecurityController extends BaseScenarioController {
    
    private static final Logger LOG = Logger.getLogger(SecurityController.class.getName());
    
    SecurityControllerRequestModel requestModel;

    public SecurityController(HttpServletRequest request, HttpServletResponse response, ApplicationServiceFactory applicationServiceFactory, Repository repository) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void processRequest() {
        
        httpSession.setAttribute(SessionAttribute.USER.name(), null);
        requestModel = new SecurityControllerRequestModel(request);
        LOG.log(Level.INFO, "Request model parsed: ", requestModel.toString());
        
        switch (requestModel.getAction()) {
            case NULL:
                emptyAction(); break;
            case AUTH:
                authenticateAction(); break;                     
        }
        
    }
    
    // actions    
    private void emptyAction() {
        SignInViewModel viewModel = new SignInViewModel(false);
        request.setAttribute(RequestAttribute.VIEW_DATA.name(), viewModel);
        renderSignInView(viewModel);
    }

    private void authenticateAction() {
        
        // try to authenticate against the superuser credentials
        if (requestModel.getUsername().equals("superuser")
                && requestModel.getPassword().equals("")) {
            Collection<Webres> webres = getAllWebres();
            proceedWhenAuthenticated(new User(null, null, null, "superuser", "", null) {
                @Override
                public Collection<Webres> getAllowedResources() {
                    return webres;
                }
                @Override
                public ActionController getDefaultController() {
                    return ActionController.EMPLOYEES_SERVLET;
                }
            });
            return;
        }
        
        // try to authenticate against other credentials from the database
        Optional<User> u = getUserByCredentials(requestModel.getUsername(), requestModel.getPassword());        
        if (u.isPresent()) {
            proceedWhenAuthenticated(u.get());
            return;
        }
        
        // report authentication failed
        LOG.log(Level.INFO, "{0} Authentication failed. Login: {1}, Password: {2}", new Object[]{httpSession.getId(), requestModel.getUsername(), requestModel.getPassword()});
        SignInViewModel viewModel = new SignInViewModel(true);
        renderSignInView(viewModel);
    }
    
    private void proceedWhenAuthenticated(User user) {
        httpSession.setAttribute(SessionAttribute.USER.name(), user);
        LOG.log(Level.INFO, "{0} Successfull authentication. Login: {1}", new Object[]{httpSession.getId(), user.getUsLogin().trim()});
        sendRedirect(ActionController.HOMEPAGE_SERVLET);
    }
    
    // domain interaction methods

    private List<Webres> getAllWebres() {
        return hibernateSession.createQuery("from Webres").list();
    }
    
    private Optional<User> getUserByCredentials(String username, String password) {
        List<User> usersRetrieved = hibernateSession.createQuery("from User users where users.usLogin='" + username + "' and users.usPassword='" + password + "'").list();
        if (usersRetrieved.size() == 1) {
            return Optional.of(usersRetrieved.get(0));
        } else if (usersRetrieved.size() > 1) {
            throw new RuntimeException("More than one user exist with the specified credentials");
        } else {
            return Optional.empty();
        }
    }
    
    // renderers methods
    
    private void renderSignInView(SignInViewModel viewModel) {
        request.setAttribute(RequestAttribute.VIEW_DATA.name(), viewModel);
        forwardHttpRequest(Jsp.SIGNIN_JSP);
    }
}
