/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mikevostrikov.ralter.infra.jpa;

import javax.persistence.EntityManager;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 *
 * @author m.vostrikov
 */
public class EntityManagerUtilTest {
    
    public EntityManagerUtilTest() {
    }
    
    /**
     * Test of getEntityManager method, of class EntityManagerUtil.
     */
    public void testGetEntityManager() {
        System.out.println("getEntityManager");
        EntityManager result = EntityManagerUtil.getEntityManager();
        assertNotNull(result);
    }
    
}
