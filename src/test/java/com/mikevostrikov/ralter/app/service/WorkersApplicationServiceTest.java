/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mikevostrikov.ralter.app.service;

import com.mikevostrikov.ralter.app.domain.Repository;
import com.mikevostrikov.ralter.app.domain.model.workers.User;
import com.mikevostrikov.ralter.infra.jpa.EntityManagerUtil;
import com.mikevostrikov.ralter.infra.jpa.JpaRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 *
 * @author m.vostrikov
 */
public class WorkersApplicationServiceTest {
    
    /**
     * Test of Employee, User, Resources relationship and correctness of the resource list extraction.
     */
    public void testAuthenticationService() {
        System.out.println("testAuthenticationService");
        Repository repo = new JpaRepository(EntityManagerUtil.getEntityManager());
        WorkersApplicationService instance = new ApplicationServiceFactory(repo).getWorkersApplicationService();
        User user = instance.authenticate("admin", "admin");
        System.out.println(user);
        assertEquals(1, (long) user.getUsId());
        user = instance.authenticate("admin", "");
        System.out.println(user);
        assertNull(user);
    }
    
}
