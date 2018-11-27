/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mikevostrikov.ralter.infra.jpa;

import com.mikevostrikov.ralter.app.domain.Repository;
import com.mikevostrikov.ralter.app.domain.model.workers.Employee;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
/**
 *
 * @author m.vostrikov
 */

public class JpaRepositoryTest {

    /**
     * Test of findById method, of class JpaRepository.
     */
    public void testFindById() {
        System.out.println("testFindById");
        Repository instance = new JpaRepository(EntityManagerUtil.getEntityManager());
        int expResult = 2;
        Employee result = instance.findById(Employee.class, 2);
        System.out.println("found: " + result);
        assertEquals(expResult, (long) result.getEmId());
    }

    /**
     * Test of findAll method, of class JpaRepository.
     */
    public void testFindAll() {
        System.out.println("testFindAll");
        Repository instance = new JpaRepository(EntityManagerUtil.getEntityManager());
        int expResult = 4;
        List result = instance.findAll(Employee.class);
        System.out.println("found: " + result);
        assertEquals(expResult, result.size());
    }
    
    public void testPersist() {
        System.out.println("testPersist");
        Repository instance = new JpaRepository(EntityManagerUtil.getEntityManager());
        Employee emp = instance.createEmpty(Employee.class);
        instance.persist(emp);
        System.out.println("created: " + emp);
        assertNotNull(emp.getEmId());
        instance.delete(emp);
    }

    /**
     * Test of update method, of class JpaRepository.
     */
    public void testUpdate() {
        System.out.println("testUpdate");
        Repository instance = new JpaRepository(EntityManagerUtil.getEntityManager());
        Employee emp = instance.createEmpty(Employee.class);
        instance.persist(emp);
        System.out.println("created: " + emp);
        assertNotNull(emp.getEmId());
        emp.setEmName("Test Name");
        emp = instance.update(emp);
        System.out.println("updated: " + emp);
        Employee updatedEmp = instance.findById(Employee.class, emp.getEmId());
        System.out.println("found: " + updatedEmp);
        assertEquals(emp.getEmName(), updatedEmp.getEmName());
        instance.delete(emp);
    }

    /**
     * Test of delete method, of class JpaRepository.
     */
    public void testDelete() {
        System.out.println("testDelete");
        Repository instance = new JpaRepository(EntityManagerUtil.getEntityManager());
        Employee emp = instance.createEmpty(Employee.class);
        instance.persist(emp);
        assertNotNull(emp.getEmId());
        instance.delete(emp);
        System.out.println("deleted: " + emp);
        Employee checkEmp = instance.findById(Employee.class, emp.getEmId());
        System.out.println("found after delete: " + checkEmp);
        assertNull(checkEmp);
    }

    /**
     * Test of deleteById method, of class JpaRepository.
     */
    public void testDeleteById() {
        System.out.println("testDeleteById");
        Repository instance = new JpaRepository(EntityManagerUtil.getEntityManager());
        Employee emp = instance.createEmpty(Employee.class);
        instance.persist(emp);
        System.out.println("created: " + emp);
        assertNotNull(emp.getEmId());
        instance.deleteById(Employee.class, emp.getEmId());
        Employee checkEmp = instance.findById(Employee.class, emp.getEmId());
        System.out.println("found after delete: " + checkEmp);
        assertNull(checkEmp);
    }
    
}
