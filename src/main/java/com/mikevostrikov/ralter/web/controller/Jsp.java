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
public enum Jsp {
    SIGNIN_JSP(Constants.SIGNIN_JSP_URI),
    SIGNOUT_JSP(Constants.SIGNOUT_JSP_URI),
    EMPLOYEES_MAIN_JSP(Constants.EMPLOYEES_MAIN_JSP_URI),
    EMPLOYEES_SHOW_JSP(Constants.EMPLOYEES_SHOW_JSP_URI),
    EMPLOYEES_EDIT_JSP(Constants.EMPLOYEES_EDIT_JSP_URI),
    SEVERE_ERROR_HTML(Constants.SEVERE_ERROR_HTML_URI);
    
    public class Constants {
        public final static String SIGNIN_JSP_URI = "/WEB-INF/jsp/login.jsp";
        public final static String SIGNOUT_JSP_URI = "/WEB-INF/jsp/logout.jsp";
        public final static String EMPLOYEES_MAIN_JSP_URI = "/WEB-INF/jsp/workers/employees/main.jsp";
        public final static String EMPLOYEES_SHOW_JSP_URI = "/WEB-INF/jsp/workers/employees/show.jsp";
        public final static String EMPLOYEES_EDIT_JSP_URI = "/WEB-INF/jsp/workers/employees/edit.jsp";
        public final static String SEVERE_ERROR_HTML_URI = "/WEB-INF/jsp/severe_error.html";
    }
    
    private final String uri;
    
    Jsp(String uri) {
        this.uri = uri;
    }
    
    public String getUri() {
        return uri;
    }
    
}
