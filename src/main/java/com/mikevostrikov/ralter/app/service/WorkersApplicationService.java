/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mikevostrikov.ralter.app.service;

import com.mikevostrikov.ralter.app.domain.Repository;
import com.mikevostrikov.ralter.app.domain.model.workers.User;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author m.vostrikov
 */
public class WorkersApplicationService {
    
    private static final Logger LOG = Logger.getLogger(WorkersApplicationService.class.getName());
    
    private final Repository repository;

    WorkersApplicationService(Repository repository) {
        this.repository = repository;
    }
    
    public User authenticate(String username, String password) {
        return repository.findUserByCredentials(username, password);
    }
    
    public <T> void persist(T entity) {
        repository.beginTransaction();
        repository.persist(entity);
        repository.commitTransaction();
        repository.refresh(entity);
        LOG.log(Level.INFO, entity.toString());
    }
    
    public <T> T update(T entity) {
        LOG.log(Level.INFO, entity.toString());
        repository.beginTransaction();
        T updatedEntity = repository.update(entity);
        repository.commitTransaction();
        return updatedEntity;
    }

    public <T> void deleteById(Class<T> clazz, Long id) {
        repository.beginTransaction();
        repository.deleteById(clazz, id);
        repository.commitTransaction();
    }
    
    /*    
    public <T> void saveOrUpdate(T entity) {
        repository.beginTransaction();
        try {
            repository.update(entity);
        } catch (Exception ex) {
            repository.persist(entity);
        }
        repository.flush();
        repository.refresh(entity);
        repository.commitTransaction();
        LOG.log(Level.INFO, entity.toString());
    }
    */
    
}
