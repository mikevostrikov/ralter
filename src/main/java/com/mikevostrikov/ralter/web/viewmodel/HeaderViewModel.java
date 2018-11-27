/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mikevostrikov.ralter.web.viewmodel;

import com.mikevostrikov.ralter.web.controller.Controller;
import com.mikevostrikov.ralter.app.domain.model.workers.User;

/**
 *
 * @author m.vostrikov
 */
public class HeaderViewModel extends ViewModel {
    
    private final User user;
    private final String signOutLink;
    private final String errorMsg;

    public HeaderViewModel(User user, String errorMsg) {
        this.user = user;
        this.errorMsg = errorMsg;
        this.signOutLink = initSignOutLink();
    }
    
    // getters
    
    public User getUser() {
        return user;
    }

    public String getSignOutLink() {
        return signOutLink;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
    
    // init helpers

    private String initSignOutLink() {
        return uriFactory.getUri(Controller.SIGNOUT_SERVLET);
    }
    
}
