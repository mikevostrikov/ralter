/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todo.com.mikevostrikov.ralter.web.controller;

import com.mikevostrikov.ralter.app.domain.model.workers.User;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.NotImplementedException;

/**
 *
 * @author m.vostrikov
 */
class SecurityWrapper implements RequestProcessor {
    
    private static final Logger LOG = Logger.getLogger(SecurityWrapper.class.getName());
    
    final RequestProcessor innerRequestProcessor;
    final RequestContext requestContext;

    protected SecurityWrapper(RequestProcessor innerRequestProcessor, RequestContext requestContext) {
        this.innerRequestProcessor = innerRequestProcessor;
        this.requestContext = requestContext;
    }

    @Override
    public void processRequest() {
        User user = (User) requestContext.getHttpSession().getAttribute(SessionAttribute.USER.name());
        requestContext.setUser(user);
        
        if ((user != null) && (user.getUsLogin().equals("superuser") || user.isAuthorizedForResourceId(requestContext.getServlet().getId()))) {
            // The user is authenticated and authorized to use the requested resource
            LOG.log(Level.INFO, "{0} User {1} is authorized to use a requested servlet #{2}", new Object[]{requestContext.getHttpSession().getId(), user.getUsLogin(), requestContext.getServlet()});
            innerRequestProcessor.processRequest();
        } else if (requestContext.getServlet().equals(ActionController.AUTHENTICATION_SERVLET)) {
            // The user is accessing authentication module
            LOG.log(Level.INFO, "{0} All users are authorized to use the authentication servlet. Proceeding...", requestContext.getHttpSession().getId());
            innerRequestProcessor.processRequest();
        } else if (user != null) {
            // The user is authenticated, but not authorized to use the requested resource
            throw new NotImplementedException("The user is authenticated, but not authorized to use the requested resource"); //TODO implement
        } else {
            LOG.log(Level.INFO, "{0} User is unknown.", requestContext.getHttpSession().getId());
            requestContext.redirectClient(ActionController.AUTHENTICATION_SERVLET);
        }
    }
    
}
