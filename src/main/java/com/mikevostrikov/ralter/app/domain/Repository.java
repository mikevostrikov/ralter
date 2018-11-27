/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mikevostrikov.ralter.app.domain;

import com.mikevostrikov.ralter.app.domain.model.workers.User;
import java.util.List;

/**
 *
 * @author m.vostrikov
 */
public interface Repository {

    <T> void delete(T entity);

    <T> void deleteById(Class<T> clazz, long entityId);

    <T> List<T> findAll(Class<T> clazz);

    <T> T findById(Class<T> clazz, long id);
    
    <T> void persist(T entity);
    
    <T> T createEmpty(Class<T> clazz);

    <T> T update(T entity);
    
    User findUserByCredentials(String username, String passwordHash);
    
    void beginTransaction();
    
    void commitTransaction();
    
    void rollbackTransaction();
    
    boolean isActiveTransaction();

    <T> void refresh(T entity);
    
    void flush();
    
    void closeSession();
    
}
