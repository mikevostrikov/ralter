/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mikevostrikov.ralter.web.controller.security;

import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author m.vostrikov
 */
public class SecurityControllerRequestModel {
    
    private static final Logger LOG = Logger.getLogger(SecurityControllerRequestModel.class.getName());
    
    public enum Param {
        ACTION("act"),
        USERNAME("username"),
        PASSWORD("pwd");
        
        private final String paramName;
        
        private Param(final String uriName) {
            this.paramName = uriName;
        }
        
        public String getParamName() {
            return paramName;
        }
    }
    
    public enum ActionParamValue {
        NULL("null"),
        AUTH("auth");
        
        private final String paramValue;
        
        private ActionParamValue(final String uriName) {
            this.paramValue = uriName;
        }
        
        public String getParamValue() {
            return paramValue;
        }
        
        public static ActionParamValue getActionByParamValue(String paramValue) {
            if (paramValue == null) {
                return NULL;
            }
            for (ActionParamValue val : ActionParamValue.values()) {
                if (val.getParamValue().equals(paramValue)) {
                    return val;
                }
            }
            throw new IllegalArgumentException("Illegal action requested");
        }
    }
    
    private final ActionParamValue action;
    private final String username;
    private final String password;
        
    public SecurityControllerRequestModel(HttpServletRequest request) {
        action = ActionParamValue.getActionByParamValue(request.getParameter(Param.ACTION.getParamName()));
        username = (String) request.getParameter(Param.USERNAME.getParamName());
        password = (String) request.getParameter(Param.PASSWORD.getParamName());
    }

    public ActionParamValue getAction() {
        return action;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE, true, true);
    }    
    
}
