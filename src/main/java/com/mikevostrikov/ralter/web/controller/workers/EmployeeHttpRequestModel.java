/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mikevostrikov.ralter.web.controller.workers;

import com.mikevostrikov.ralter.app.domain.Repository;
import com.mikevostrikov.ralter.app.domain.model.workers.Employee;
import com.mikevostrikov.ralter.app.domain.model.workers.User;
import com.mikevostrikov.ralter.web.controller.SessionAttribute;
import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author m.vostrikov
 */
public class EmployeeHttpRequestModel {
    
    public enum Param {
        ACTION("act"),
        EMPLOYEE_ID("empId"),
        EMPLOYEE_ADDRESS("empAddress"),
        EMPLOYEE_COMMENT("empComment"),
        EMPLOYEE_EMAIL("empEmail"),
        EMPLOYEE_NAME("empName"),
        EMPLOYEE_PHONE("empPhone"),
        EMPLOYEE_POSITION("empPosition"),
        EMPLOYEE_SALARY("empSalary");
        
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
        MAIN("main"),
        VIEW("show"),
        EDIT("edit"),
        UPDATE("update"),
        DELETE("delete"),
        ADD("add");
        
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
    
    private final Employee employee;
    private final ActionParamValue action;
    private final User user;
    private final String errorMsg;
    
    public Employee getEmployee(){
        return employee;
    }
    
    public ActionParamValue getAction(){
        return action;
    }
    
    public EmployeeHttpRequestModel(HttpServletRequest request, Repository repository) {
        action = ActionParamValue.getActionByParamValue(request.getParameter(Param.ACTION.getParamName()));
        
        if (action.equals(ActionParamValue.EDIT)
                || action.equals(ActionParamValue.DELETE)
                || action.equals(ActionParamValue.VIEW)
                || action.equals(ActionParamValue.UPDATE)
                ){
            employee = repository.createEmpty(Employee.class);
            if (request.getParameter(Param.EMPLOYEE_ID.getParamName()) != null) {
                employee.setEmId(new Long(request.getParameter(Param.EMPLOYEE_ID.getParamName())));
            }
        } else {
            employee = null;
        }
        
        if (action.equals(ActionParamValue.UPDATE)) {
            employee.setEmAddress(request.getParameter(Param.EMPLOYEE_ADDRESS.getParamName()));
            employee.setEmComment(request.getParameter(Param.EMPLOYEE_COMMENT.getParamName()));
            employee.setEmEmail(request.getParameter(Param.EMPLOYEE_EMAIL.getParamName()));
            employee.setEmName(request.getParameter(Param.EMPLOYEE_NAME.getParamName()));
            employee.setEmPhone(request.getParameter(Param.EMPLOYEE_PHONE.getParamName()));
            employee.setEmPosition(request.getParameter(Param.EMPLOYEE_POSITION.getParamName()));
            employee.setEmSalary(new BigDecimal(request.getParameter(Param.EMPLOYEE_SALARY.getParamName())));        
        }
        
        user = (User) request.getSession().getAttribute(SessionAttribute.USER.name());
        errorMsg = (String) request.getSession().getAttribute(SessionAttribute.ERROR_MSG.name());
    }

    public User getUser() {
        return user;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
    
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE, true, true);
    }    

}
