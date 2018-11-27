/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mikevostrikov.ralter.web.viewmodel;

import com.mikevostrikov.ralter.web.controller.ClientSideUriFactory;
import com.mikevostrikov.ralter.web.controller.UriFactory;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author m.vostrikov
 */
public class ViewModel {
    
    private final String appBaseUri;
    protected final UriFactory uriFactory = new ClientSideUriFactory();

    public ViewModel() {
        this.appBaseUri = uriFactory.getAppBaseUri();
    }
    
    public String getAppBaseUri() {
        return appBaseUri;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE, true, true);
    }
    
}
