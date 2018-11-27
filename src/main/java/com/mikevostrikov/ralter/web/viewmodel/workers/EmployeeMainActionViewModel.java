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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author m.vostrikov
 */
public class EmployeeMainActionViewModel extends ViewModel {
    
    private final Collection<Employee> employees;
    private Map<Employee, String> deleteEmployeeActionLinksMap;
    private Map<Employee, String> editEmployeeActionLinksMap;
    private Map<Employee, String> viewEmployeeActionLinksMap;
    private String addEmployeeActionLink;
    
    public EmployeeMainActionViewModel(Collection<Employee> employees) {
        this.employees = employees;
        initEmployeeActionLinksMaps();
        initAddEmployeeActionLink();
    }
    
    // getters

    public Collection<Employee> getEmployees() {
        return employees;
    }

    public Map<Employee, String> getDeleteEmployeeActionLinksMap() {
        return deleteEmployeeActionLinksMap;
    }

    public Map<Employee, String> getEditEmployeeActionLinksMap() {
        return editEmployeeActionLinksMap;
    }

    public Map<Employee, String> getViewEmployeeActionLinksMap() {
        return viewEmployeeActionLinksMap;
    }

    public String getAddEmployeeActionLink() {
        return addEmployeeActionLink;
    }
    
    // init helpers
    
    private void initEmployeeActionLinksMaps() {
        Map<Employee, String> deleteEmpActionLinksMap = new HashMap<>();
        Map<Employee, String> editEmpActionLinksMap = new HashMap<>();
        Map<Employee, String> viewEmpActionLinksMap = new HashMap<>();
        employees.stream().forEach((emp) -> {
            deleteEmpActionLinksMap.put(emp, getEmployeeActionLink(EmployeeHttpRequestModel.ActionParamValue.DELETE, emp));
            editEmpActionLinksMap.put(emp, getEmployeeActionLink(EmployeeHttpRequestModel.ActionParamValue.EDIT, emp));
            viewEmpActionLinksMap.put(emp, getEmployeeActionLink(EmployeeHttpRequestModel.ActionParamValue.VIEW, emp));
        });
        this.deleteEmployeeActionLinksMap = deleteEmpActionLinksMap;
        this.editEmployeeActionLinksMap = editEmpActionLinksMap;
        this.viewEmployeeActionLinksMap = viewEmpActionLinksMap;
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
    
    private void initAddEmployeeActionLink() {
        addEmployeeActionLink = uriFactory.getUri(Controller.EMPLOYEES_SERVLET,
            new UriParameter(EmployeeHttpRequestModel.Param.ACTION.getParamName(),
                EmployeeHttpRequestModel.ActionParamValue.ADD.getParamValue()
            )
        );
    }

}
