/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mikevostrikov.ralter.web.viewmodel;

import com.mikevostrikov.ralter.web.controller.Controller;

/**
 *
 * @author m.vostrikov
 */
public class SignOutViewModel extends ViewModel {

    private final String signInLink;

    public SignOutViewModel() {
        this.signInLink = signInLink();
    }

    // getters

    public String getSignInLink() {
        return signInLink;
    }
    
    // init helpers
    
    private String signInLink() {
        return uriFactory.getUri(Controller.AUTHENTICATION_SERVLET
        );
    }

}
