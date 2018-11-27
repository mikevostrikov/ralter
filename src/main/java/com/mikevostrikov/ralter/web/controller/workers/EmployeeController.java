/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mikevostrikov.ralter.web.controller.workers;

import com.mikevostrikov.ralter.app.domain.Repository;
import com.mikevostrikov.ralter.app.domain.model.workers.Employee;
import com.mikevostrikov.ralter.app.service.ApplicationServiceFactory;
import com.mikevostrikov.ralter.infra.jpa.EntityManagerUtil;
import com.mikevostrikov.ralter.web.controller.HttpRequestProcessor;
import com.mikevostrikov.ralter.web.controller.Jsp;
import com.mikevostrikov.ralter.web.controller.RequestAttribute;
import com.mikevostrikov.ralter.web.controller.SessionAttribute;
import com.mikevostrikov.ralter.web.viewmodel.BasicCompositeViewModel;
import java.math.BigDecimal;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mikevostrikov.ralter.web.viewmodel.HeaderViewModel;
import com.mikevostrikov.ralter.web.viewmodel.NavigationViewModel;
import com.mikevostrikov.ralter.web.viewmodel.workers.EmployeeEditExistingActionViewModel;
import com.mikevostrikov.ralter.web.viewmodel.workers.EmployeeEditNewActionViewModel;
import com.mikevostrikov.ralter.web.viewmodel.workers.EmployeeMainActionViewModel;
import com.mikevostrikov.ralter.web.viewmodel.workers.EmployeeShowActionViewModel;

/**
 *
 * @author m.vostrikov
 */
public class EmployeeController extends HttpRequestProcessor {
    
    private final EmployeeHttpRequestModel requestModel;

    public EmployeeController(HttpServletRequest request, HttpServletResponse response, ApplicationServiceFactory applicationServiceFactory, Repository repository) {
        super(request, response, applicationServiceFactory, repository);
        this.requestModel = new EmployeeHttpRequestModel(request, repository);
    }
    
    @Override
    public void process() {        
        switch (requestModel.getAction()) {
            case NULL:
            case MAIN:
                showEmployeeListAction(); break;
            case VIEW:
                showRequestedEmployeeAction(); break;
            case EDIT:
                editRequestedEmployeeAction(); break;
            case UPDATE:
                updateEmployeeAction(); break;       
            case DELETE:
                deleteRequestedEmployeeAction(); break;
            case ADD:
                addEmployeeAction(); break;
        }
        
        // Reset ERROR_MSG in session, because we are going to show it to the user and we want to do it only once.
        if (requestModel.getErrorMsg() != null) {
            session.setAttribute(SessionAttribute.ERROR_MSG.name(), null);
        }
        
    }
    
    // actions methods
    
    private void deleteRequestedEmployeeAction(){
        // delete Requested Employee
        applicationServiceFactory.getWorkersApplicationService().deleteById(Employee.class, requestModel.getEmployee().getEmId());
        
        // go to show all action
        showEmployeeListAction();
    }
    
    private void updateEmployeeAction(){
        // retrieve employee from request
        Employee employee = requestModel.getEmployee();
        
        // save employee
        if (employee.getEmId() == null) {
            applicationServiceFactory.getWorkersApplicationService().persist(employee);
        } else {
            Employee existingEmployee = repository.findById(Employee.class, employee.getEmId());
            existingEmployee.setEmAddress(employee.getEmAddress());
            existingEmployee.setEmComment(employee.getEmComment());
            existingEmployee.setEmEmail(employee.getEmEmail());
            existingEmployee.setEmName(employee.getEmName());
            existingEmployee.setEmPhone(employee.getEmPhone());
            existingEmployee.setEmPosition(employee.getEmPosition());
            existingEmployee.setEmSalary(employee.getEmSalary());
            applicationServiceFactory.getWorkersApplicationService().update(existingEmployee);
        }
        
        // prepare ViewModel
        BasicCompositeViewModel<EmployeeShowActionViewModel> viewModel = 
            new BasicCompositeViewModel<>(
                new HeaderViewModel(requestModel.getUser(), requestModel.getErrorMsg()),
                new NavigationViewModel(requestModel.getUser()),
                new EmployeeShowActionViewModel(employee)
            );
        
        // render view
        renderEmployeeView(viewModel);
    }
    
    private void addEmployeeAction(){
        Employee employee = getNewEmployee();
        BasicCompositeViewModel<EmployeeEditNewActionViewModel> viewModel = 
            new BasicCompositeViewModel<>(
                new HeaderViewModel(requestModel.getUser(), requestModel.getErrorMsg()),
                new NavigationViewModel(requestModel.getUser()),
                new EmployeeEditNewActionViewModel(employee)
            );
        renderEditView(viewModel);
    }
    
    private void showEmployeeListAction(){
        Collection<Employee> employees = getEmployees();
        BasicCompositeViewModel<EmployeeMainActionViewModel> viewModel = 
            new BasicCompositeViewModel<>(
                new HeaderViewModel(requestModel.getUser(), requestModel.getErrorMsg()),
                new NavigationViewModel(requestModel.getUser()),
                new EmployeeMainActionViewModel(employees)
            );
        renderEmployeeMainView(viewModel);
    }
    
    private void editRequestedEmployeeAction(){
        Employee employee = getRequestedEmployee();
        BasicCompositeViewModel<EmployeeEditExistingActionViewModel> viewModel = 
            new BasicCompositeViewModel<>(
                new HeaderViewModel(requestModel.getUser(), requestModel.getErrorMsg()),
                new NavigationViewModel(requestModel.getUser()),
                new EmployeeEditExistingActionViewModel(employee)
            );
        renderEditView(viewModel);
    }

    private void showRequestedEmployeeAction(){
        Employee employee = getRequestedEmployee();
        BasicCompositeViewModel<EmployeeShowActionViewModel> viewModel = 
            new BasicCompositeViewModel<>(
                new HeaderViewModel(requestModel.getUser(), requestModel.getErrorMsg()),
                new NavigationViewModel(requestModel.getUser()),
                new EmployeeShowActionViewModel(employee)
            );
        renderEmployeeView(viewModel);
    }

    private Employee getNewEmployee() {
        Employee employee = repository.createEmpty(Employee.class);
        employee.setEmName("New Employee");
        employee.setEmSalary(new BigDecimal(0));
        return employee;
    }
    
    private Collection<Employee> getEmployees(){
        return repository.findAll(Employee.class);
    }
    
    private Employee getRequestedEmployee(){
        return repository.findById(Employee.class, requestModel.getEmployee().getEmId());
    }
    
    // view rendering methods
    
    private void renderEmployeeMainView(BasicCompositeViewModel<EmployeeMainActionViewModel> viewModel){
        request.setAttribute(RequestAttribute.VIEW_DATA.name(), viewModel);
        requestRouter.forwardHttpRequest(Jsp.EMPLOYEES_MAIN_JSP);
    }
    
    private void renderEditView(BasicCompositeViewModel viewModel){
        request.setAttribute(RequestAttribute.VIEW_DATA.name(), viewModel);
        requestRouter.forwardHttpRequest(Jsp.EMPLOYEES_EDIT_JSP);
    }
    
    private void renderEmployeeView(BasicCompositeViewModel<EmployeeShowActionViewModel> viewModel){
        request.setAttribute(RequestAttribute.VIEW_DATA.name(), viewModel);
        requestRouter.forwardHttpRequest(Jsp.EMPLOYEES_SHOW_JSP);
    }
    
}
