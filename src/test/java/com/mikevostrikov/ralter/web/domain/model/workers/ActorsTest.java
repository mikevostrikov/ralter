/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mikevostrikov.ralter.web.domain.model.workers;

import com.mikevostrikov.ralter.app.domain.Repository;
import com.mikevostrikov.ralter.app.domain.model.workers.Employee;
import com.mikevostrikov.ralter.app.domain.model.workers.User;
import com.mikevostrikov.ralter.infra.jpa.EntityManagerUtil;
import com.mikevostrikov.ralter.infra.jpa.JpaRepository;
import com.mikevostrikov.ralter.util.java.lang.util.SetUtil;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author m.vostrikov
 */
public class ActorsTest {
    
    /**
     * Test of Employee, User, Resources relationship and correctness of the resource list extraction.
     */
    public void testEmployeeUserResources() {
        System.out.println("testEmployeeUserResources");
        Repository instance = new JpaRepository(EntityManagerUtil.getEntityManager());
        Employee employee = instance.findById(Employee.class, 2);
        System.out.println(employee);
        assertEquals(2, (long) employee.getEmId());
        assertEquals(4, (long) employee.getUser().getUsId());
        assertEquals(3, (long) employee.getUser().getRole().getRoId());
        assertEquals("CEO", employee.getUser().getRole().getRoName());
        assertTrue(SetUtil.equals(
                Arrays.asList(4L, 5L, 6L, 7L, 8L, 11L, 13L, 14L),
                employee.getUser().getRole().getResourceIds()
        ));
        assertTrue(employee.getUser().isAuthorizedForResourceId(4L));
        assertEquals(4, employee.getUser().getDefaultControllerId());
    }

    /**
     * Test of User by credentials retrieval.
     */
    public void testFindUserByCredentials() {
        System.out.println("testFindUserByCredentials");
        Repository instance = new JpaRepository(EntityManagerUtil.getEntityManager());
        User user = instance.findUserByCredentials("admin", "admin");
        System.out.println(user);
        assertEquals(1, (long) user.getUsId());
        user = instance.findUserByCredentials("admin", "");
        System.out.println(user);
        assertNull(user);
    }
    
}
