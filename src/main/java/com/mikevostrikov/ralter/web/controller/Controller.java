/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mikevostrikov.ralter.web.controller;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author m.vostrikov
 */
public enum Controller {
        
    EMPLOYEES_SERVLET(Constants.EMPLOYEES_SERVLET_ID, Constants.EMPLOYEES_SERVLET_PATH, "Employees"),
    OPERATIONS_SERVLET(Constants.OPERATIONS_SERVLET_ID, Constants.OPERATIONS_SERVLET_PATH, "Operations"),
    PROCESS_SERVLET(Constants.PROCESS_SERVLET_ID, Constants.PROCESS_SERVLET_PATH, "Production"),
    LOG_SERVLET(Constants.LOG_SERVLET_ID, Constants.LOG_SERVLET_PATH, "Log"),
    TODO_SERVLET(Constants.TODO_SERVLET_ID, Constants.TODO_SERVLET_PATH, "Todo"),
    COMPONENTS_SERVLET(Constants.COMPONENTS_SERVLET_ID, Constants.COMPONENTS_SERVLET_PATH, "Components"),
    DOCUMENTS_SERVLET(Constants.DOCUMENTS_SERVLET_ID, Constants.DOCUMENTS_SERVLET_PATH, "Documents"),
    EQUIPMENT_SERVLET(Constants.EQUIPMENT_SERVLET_ID, Constants.EQUIPMENT_SERVLET_PATH, "Equipment"),
    SUPPLIES_SERVLET(Constants.SUPPLIES_SERVLET_ID, Constants.SUPPLIES_SERVLET_PATH, "Supplies"),
    AUTHENTICATION_SERVLET(Constants.SECURITY_MOD_SERVLET_ID, Constants.SECURITY_MOD_SERVLET_PATH, "Security"),
    ROLES_SERVLET(Constants.ROLES_SERVLET_ID, Constants.ROLES_SERVLET_PATH, "Roles"),
    USERS_SERVLET(Constants.USERS_SERVLET_ID, Constants.USERS_SERVLET_PATH, "Users"),
    WEB_RESOURCES_SERVLET(Constants.WEB_RESOURCES_SERVLET_ID, Constants.WEB_RESOURCES_SERVLET_PATH, "Web Resources"),
    HOMEPAGE_SERVLET(Constants.HOMEPAGE_SERVLET_ID, Constants.HOMEPAGE_SERVLET_PATH, "Home"),
    SIGNOUT_SERVLET(Constants.SIGNOUT_SERVLET_ID, Constants.SIGNOUT_SERVLET_PATH, "SignOut");
    
    public class Constants {
        public final static String EMPLOYEES_SERVLET_PATH = "/app/workers/employees";
        public final static String OPERATIONS_SERVLET_PATH = "/app/process/operations";
        public final static String PROCESS_SERVLET_PATH = "/app/process/process";
        public final static String LOG_SERVLET_PATH = "/app/production/log";
        public final static String TODO_SERVLET_PATH = "/app/production/todo";
        public final static String COMPONENTS_SERVLET_PATH = "/app/resources/components";
        public final static String DOCUMENTS_SERVLET_PATH = "/app/resources/documents";
        public final static String EQUIPMENT_SERVLET_PATH = "/app/resources/equipment";
        public final static String SUPPLIES_SERVLET_PATH = "/app/resources/supplies";
        public final static String SECURITY_MOD_SERVLET_PATH = "/app/securitymod";
        public final static String ROLES_SERVLET_PATH = "/app/workers/roles";
        public final static String USERS_SERVLET_PATH = "/app/workers/users";
        public final static String WEB_RESOURCES_SERVLET_PATH = "/app/workers/webres";
        public final static String HOMEPAGE_SERVLET_PATH = "/app/home";
        public final static String SIGNOUT_SERVLET_PATH = "/app/signout";
        
        public final static String EMPLOYEES_SERVLET_ID = "4";
        public final static String OPERATIONS_SERVLET_ID = "9";
        public final static String PROCESS_SERVLET_ID = "10";
        public final static String LOG_SERVLET_ID = "11";
        public final static String TODO_SERVLET_ID = "12";
        public final static String COMPONENTS_SERVLET_ID = "5";
        public final static String DOCUMENTS_SERVLET_ID = "9";
        public final static String EQUIPMENT_SERVLET_ID = "6";
        public final static String SUPPLIES_SERVLET_ID = "7";
        public final static String SECURITY_MOD_SERVLET_ID = "0";
        public final static String ROLES_SERVLET_ID = "2";
        public final static String USERS_SERVLET_ID = "3";
        public final static String WEB_RESOURCES_SERVLET_ID = "1";
        public final static String HOMEPAGE_SERVLET_ID = "13";
        public final static String SIGNOUT_SERVLET_ID = "14";
    }
    
    private final String path;
    private final int id;
    private final String displayName;
    
    private static final Map<Integer, Controller> idControllerMap;
    static {
        idControllerMap = new HashMap<>();
        for (Controller ctr : Controller.values()) {
            idControllerMap.put(ctr.id, ctr);
        }
    }
    
    private static final Map<String, Controller> pathControllerMap;
    static {
        pathControllerMap = new HashMap<>();
        for (Controller ctr : Controller.values()) {
            pathControllerMap.put(ctr.path, ctr);
        }
    }
    
    Controller(String id, String path, String displayName) {
        this.id = Integer.parseInt(id);
        this.path = path;
        this.displayName = displayName;
    }
    
    public String getPath() {
        return path;
    }
    
    public int getId() {
        return id;
    }
    
    public static Controller getById(Integer id) {
        return idControllerMap.get(id);
    }

    public static Controller getByPath(String path) {
        return pathControllerMap.get(path);
    }

    public static Integer getIdByPath(String path) {
        Controller ctr = pathControllerMap.get(path);
        if (ctr != null) {
            return ctr.getId();
        }
        return null;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
}
