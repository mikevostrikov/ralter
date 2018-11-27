/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mikevostrikov.ralter.web.viewmodel;

import com.mikevostrikov.ralter.web.controller.Controller;
import com.mikevostrikov.ralter.web.controller.UriParameter;
import com.mikevostrikov.ralter.web.controller.security.SecurityControllerRequestModel;

/**
 *
 * @author m.vostrikov
 */
public class SignInViewModel extends ViewModel {

    private final String authenticateLink;
    private final boolean authenticationFailed;
    private final String usernameParam = SecurityControllerRequestModel.Param.USERNAME.getParamName();
    private final String passwordParam = SecurityControllerRequestModel.Param.PASSWORD.getParamName();

    public SignInViewModel(boolean authenticationFailed) {
        this.authenticateLink = calcAuthPostLink();
        this.authenticationFailed = authenticationFailed;
    }

    // getters

    public String getAuthenticateLink() {
        return authenticateLink;
    }

    public boolean isAuthenticationFailed() {
        return authenticationFailed;
    }

    public String getUsernameParam() {
        return usernameParam;
    }

    public String getPasswordParam() {
        return passwordParam;
    }

    // init helpers
    
    private String calcAuthPostLink() {
        return uriFactory.getUri(Controller.AUTHENTICATION_SERVLET,
            new UriParameter(SecurityControllerRequestModel.Param.ACTION.getParamName(), SecurityControllerRequestModel.ActionParamValue.AUTH.getParamValue())
        );
    }

}
