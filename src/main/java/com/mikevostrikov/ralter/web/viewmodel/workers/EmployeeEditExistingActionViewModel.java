/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mikevostrikov.ralter.web.viewmodel.workers;

import com.mikevostrikov.ralter.web.controller.Controller;
import com.mikevostrikov.ralter.web.controller.UriParameter;
import com.mikevostrikov.ralter.web.controller.workers.EmployeeHttpRequestModel;
import com.mikevostrikov.ralter.web.controller.workers.EmployeeHttpRequestModel.ActionParamValue;
import com.mikevostrikov.ralter.web.controller.workers.EmployeeHttpRequestModel.Param;
import com.mikevostrikov.ralter.app.domain.model.workers.Employee;
import com.mikevostrikov.ralter.web.viewmodel.ViewModel;

/**
 *
 * @author m.vostrikov
 */
public class EmployeeEditExistingActionViewModel extends ViewModel {

    private final Employee employee;
    private final String deleteEmployeeActionLink;
    private final String saveEmployeeActionLink;
    private final String backActionLink;
    private final String emIdParam = EmployeeHttpRequestModel.Param.EMPLOYEE_ID.getParamName();
    private final String emNameParam = EmployeeHttpRequestModel.Param.EMPLOYEE_NAME.getParamName();
    private final String emPhoneParam = EmployeeHttpRequestModel.Param.EMPLOYEE_PHONE.getParamName();
    private final String emPositionParam = EmployeeHttpRequestModel.Param.EMPLOYEE_POSITION.getParamName();
    private final String emSalaryParam = EmployeeHttpRequestModel.Param.EMPLOYEE_SALARY.getParamName();
    private final String emAddressParam = EmployeeHttpRequestModel.Param.EMPLOYEE_ADDRESS.getParamName();
    private final String emCommentParam = EmployeeHttpRequestModel.Param.EMPLOYEE_COMMENT.getParamName();
    private final String emEmailParam = EmployeeHttpRequestModel.Param.EMPLOYEE_EMAIL.getParamName();

    
    public EmployeeEditExistingActionViewModel(Employee employee) {
        this.employee = employee;
        deleteEmployeeActionLink = getEmployeeActionLink(ActionParamValue.DELETE);
        saveEmployeeActionLink = getEmployeeActionLink(ActionParamValue.UPDATE);
        backActionLink = getEmployeeActionLink(ActionParamValue.MAIN);
    }
    
    // getters
    
    public Employee getEmployee() {
        return employee;
    }

    public String getDeleteEmployeeActionLink() {
        return deleteEmployeeActionLink;
    }

    public String getSaveEmployeeActionLink() {
        return saveEmployeeActionLink;
    }

    public String getBackActionLink() {
        return backActionLink;
    }

    public String getEmIdParam() {
        return emIdParam;
    }

    public String getEmNameParam() {
        return emNameParam;
    }

    public String getEmPhoneParam() {
        return emPhoneParam;
    }

    public String getEmPositionParam() {
        return emPositionParam;
    }

    public String getEmSalaryParam() {
        return emSalaryParam;
    }

    public String getEmAddressParam() {
        return emAddressParam;
    }

    public String getEmCommentParam() {
        return emCommentParam;
    }

    public String getEmEmailParam() {
        return emEmailParam;
    }

    // init helpers
        
    private String getEmployeeActionLink(ActionParamValue act){
        return uriFactory.getUri(Controller.EMPLOYEES_SERVLET,
            new UriParameter(Param.ACTION.getParamName(),
                act.getParamValue()
            ),
            new UriParameter(Param.EMPLOYEE_ID.getParamName(),
                Long.toString(employee.getEmId())
            )
        );
    }
}
