/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mikevostrikov.ralter.web.viewmodel.workers;

import com.mikevostrikov.ralter.web.controller.Controller;
import com.mikevostrikov.ralter.web.controller.UriParameter;
import com.mikevostrikov.ralter.web.controller.workers.EmployeeHttpRequestModel;
import com.mikevostrikov.ralter.app.domain.model.workers.Employee;
import com.mikevostrikov.ralter.web.viewmodel.ViewModel;

/**
 *
 * @author m.vostrikov
 */
public class EmployeeShowActionViewModel extends ViewModel {
    
    private final Employee employee;
    private final String deleteEmployeeActionLink;
    private final String editEmployeeActionLink;
    private final String backActionLink;
    private String gluedUsernames;

    public EmployeeShowActionViewModel(Employee employee) {
        this.employee = employee;
        deleteEmployeeActionLink = getEmployeeActionLink(EmployeeHttpRequestModel.ActionParamValue.DELETE, employee);
        editEmployeeActionLink = getEmployeeActionLink(EmployeeHttpRequestModel.ActionParamValue.EDIT, employee);
        backActionLink = getEmployeeActionLink(EmployeeHttpRequestModel.ActionParamValue.MAIN, employee);
        gluedUsernames = "";
        if (employee.getUser() != null) gluedUsernames += employee.getUser().getUsLogin();
    }
    
    // getters
    
    public Employee getEmployee() {
        return employee;
    }

    public String getDeleteEmployeeActionLink() {
        return deleteEmployeeActionLink;
    }

    public String getEditEmployeeActionLink() {
        return editEmployeeActionLink;
    }

    public String getBackActionLink() {
        return backActionLink;
    }

    public String getGluedUsernames() {
        return gluedUsernames;
    }
        
    private String getEmployeeActionLink(EmployeeHttpRequestModel.ActionParamValue act, Employee emp){
        return uriFactory.getUri(Controller.EMPLOYEES_SERVLET,
            new UriParameter(EmployeeHttpRequestModel.Param.ACTION.getParamName(),
                act.getParamValue()
            ),
            new UriParameter(EmployeeHttpRequestModel.Param.EMPLOYEE_ID.getParamName(),
                Long.toString(emp.getEmId())
            )
        );
    }
    
}
