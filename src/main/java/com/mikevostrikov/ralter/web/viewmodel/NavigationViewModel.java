/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mikevostrikov.ralter.web.viewmodel;

import com.mikevostrikov.ralter.app.domain.model.workers.User;
import com.mikevostrikov.ralter.web.controller.Controller;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author m.vostrikov
 */
public class NavigationViewModel extends ViewModel {
        
    private final User user;
    private final Map<String, String> controllersLinks = new HashMap<>();

    public NavigationViewModel(User user) {
        this.user = user;
        initControllersLinks();
    }

    // getters
    
    public User getUser() {
        return user;
    }

    public Map<String, String> getControllersLinks() {
        return controllersLinks;
    }
    
    // init helpers
    
    private void initControllersLinks() {
        if (user != null) {
            user.getResourceIds()
                .stream()
                .filter(id -> Controller.HOMEPAGE_SERVLET.getId() != id && Controller.SIGNOUT_SERVLET.getId() != id)
                .forEach((Long resource) -> {
                    if (Controller.getById(resource.intValue()) != null) {
                        controllersLinks.put(
                            Controller.getById(resource.intValue()).getDisplayName(),
                            uriFactory.getUri(Controller.getById(resource.intValue()))
                        );
                    }
            });
        }
    }
        
}
