/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todo.com.mikevostrikov.ralter.web.controller;

import com.mikevostrikov.ralter.web.controller.RequestProcessor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author m.vostrikov
 */
class LoggingRequestWrapper implements RequestProcessor {
    
    private static final Logger LOG = Logger.getLogger(LoggingRequestWrapper.class.getName());
    
    final RequestProcessor innerRequestProcessor;
    final RequestContext requestContext;

    LoggingRequestWrapper(RequestProcessor innerRequestProcessor, RequestContext requestContext) {
        this.innerRequestProcessor = innerRequestProcessor;
        this.requestContext = requestContext;
    }

    @Override
    public void processRequest() {
        LOG.log(Level.INFO, "{0} Servlet called through address: {1}", new Object[]{requestContext.getHttpSession().getId(), requestContext.getRequest().getServletPath()});
        innerRequestProcessor.processRequest();
    }
    
}
