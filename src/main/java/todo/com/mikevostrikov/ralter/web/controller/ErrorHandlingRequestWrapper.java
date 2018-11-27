/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todo.com.mikevostrikov.ralter.web.controller;

import com.mikevostrikov.ralter.web.controller.Jsp;
import com.mikevostrikov.ralter.web.controller.RequestAttribute;
import com.mikevostrikov.ralter.web.controller.RequestProcessor;
import com.mikevostrikov.ralter.web.controller.SessionAttribute;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author m.vostrikov
 */
class ErrorHandlingRequestWrapper implements RequestProcessor {
    
    private static final Logger LOG = Logger.getLogger(ErrorHandlingRequestWrapper.class.getName());
    
    final RequestProcessor innerRequestProcessor;
    final RequestContext requestContext;

    ErrorHandlingRequestWrapper(RequestProcessor innerRequestProcessor, RequestContext requestContext) {
        this.innerRequestProcessor = innerRequestProcessor;
        this.requestContext = requestContext;
    }

    @Override
    public void processRequest() {
        try {
            requestContext.getRequest().setAttribute(RequestAttribute.ERROR_MSG.name(), requestContext.getHttpSession().getAttribute(SessionAttribute.ERROR_MSG.name()));
            innerRequestProcessor.processRequest();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, requestContext.getHttpSession().getId() + " An exception was thrown within the session: ", e);
            if (requestContext.getHttpSession().getAttribute(SessionAttribute.ERROR_MSG.name()) == null) {
                // This is the first exception in a row
                // Try to redirect the user to his home page and display an error message
                requestContext.getHttpSession().setAttribute(SessionAttribute.ERROR_MSG.name(), "We have technical problems while processing your request. Please, try again later.");            
                requestContext.redirectClient(ActionController.HOMEPAGE_SERVLET);
            } else {
                // Two exception in a row - show the user the simplest error page
                requestContext.forwardHttpRequest(Jsp.SEVERE_ERROR_HTML);
            }
        }
    }
    
}
