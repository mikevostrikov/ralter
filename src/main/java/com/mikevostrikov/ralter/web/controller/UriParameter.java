/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mikevostrikov.ralter.web.controller;

/**
 *
 * @author m.vostrikov
 */
public class UriParameter {
    
    private final String name;
    private final String value;
    
    public UriParameter(String name, String value) {
        this.name = name;
        this.value = value;
    }
    
    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
    
}
