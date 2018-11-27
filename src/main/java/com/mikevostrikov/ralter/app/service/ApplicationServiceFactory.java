/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mikevostrikov.ralter.app.service;

import com.mikevostrikov.ralter.app.domain.Repository;
import java.util.logging.Logger;

/**
 *
 * @author m.vostrikov
 */
public class ApplicationServiceFactory {
    
    private static final Logger LOG = Logger.getLogger(ApplicationServiceFactory.class.getName());
    
    private final Repository repository;
    
    public ApplicationServiceFactory(Repository repository) {
        this.repository = repository;
    }
   
    private WorkersApplicationService workersApplicationService;

    public WorkersApplicationService getWorkersApplicationService() {
        if (workersApplicationService == null) {
            workersApplicationService = new WorkersApplicationService(repository);
        }
        return workersApplicationService;
    }
    
}
