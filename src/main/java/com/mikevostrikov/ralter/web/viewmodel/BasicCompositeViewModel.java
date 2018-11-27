/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mikevostrikov.ralter.web.viewmodel;

import com.mikevostrikov.ralter.web.viewmodel.HeaderViewModel;
import com.mikevostrikov.ralter.web.viewmodel.ViewModel;

/**
 *
 * @author m.vostrikov
 * @param <T>
 */
public class BasicCompositeViewModel<T extends ViewModel> extends ViewModel {

    private final HeaderViewModel header;
    private final NavigationViewModel navigation;
    private final T content;

    public BasicCompositeViewModel(HeaderViewModel header, NavigationViewModel navigation, T content) {
        this.header = header;
        this.navigation = navigation;
        this.content = content;
    }

    public HeaderViewModel getHeader() {
        return header;
    }

    public NavigationViewModel getNavigation() {
        return navigation;
    }

    public T getContent() {
        return content;
    }
    
}
