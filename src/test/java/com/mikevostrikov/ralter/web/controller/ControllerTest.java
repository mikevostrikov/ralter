/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mikevostrikov.ralter.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author m.vostrikov
 */
public class ControllerTest {
    
    public void testGetById() {
        assertEquals(Controller.EMPLOYEES_SERVLET.getId(), Controller.getById(Controller.EMPLOYEES_SERVLET.getId()).getId());
        assertEquals(Controller.SIGNOUT_SERVLET.getId(), Controller.getById(Controller.SIGNOUT_SERVLET.getId()).getId());
        assertEquals(Controller.AUTHENTICATION_SERVLET, Controller.getByPath(Controller.AUTHENTICATION_SERVLET.getPath()));
    }

    public void testGetByUri() {
        assertEquals(Controller.AUTHENTICATION_SERVLET, Controller.getByPath(Controller.Constants.SECURITY_MOD_SERVLET_PATH));
    }
    
}
