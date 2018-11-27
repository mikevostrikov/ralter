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
public class ClientSideUriFactory extends UriFactory {
    
    @Override
    public String getUri(Controller servlet, UriParameter... params) {
        return toUri(Application.RALTER_APP, servlet, null, params);
    }

    @Override
    public String getUri(Jsp jsp, UriParameter... params) {
        return toUri(Application.RALTER_APP, null, jsp, params);
    }
    
}
