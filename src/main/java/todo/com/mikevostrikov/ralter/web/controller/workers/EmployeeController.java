/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package todo.com.mikevostrikov.ralter.web.controller.workers;

import com.mikevostrikov.ralter.web.controller.workers.EmployeeHttpRequestModel;
import com.mikevostrikov.ralter.app.domain.model.workers.Employee;
import java.util.*;
import java.math.BigDecimal;
import todo.com.mikevostrikov.ralter.web.controller.BaseScenarioController;
import com.mikevostrikov.ralter.web.controller.todo.Jsp;
import com.mikevostrikov.ralter.web.controller.todo.RequestAttribute;
import com.mikevostrikov.ralter.web.controller.todo.ActionController;
import com.mikevostrikov.ralter.web.controller.workers.EmployeeHttpRequestModel.ActionParamValue;
import com.mikevostrikov.ralter.infra.jpa.JpaRepository;
import com.mikevostrikov.ralter.web.viewmodel.todo.BasicCompositeViewModel;
import com.mikevostrikov.ralter.web.viewmodel.HeaderViewModel;
import com.mikevostrikov.ralter.web.viewmodel.todo.NavigationViewModel;
import com.mikevostrikov.ralter.web.viewmodel.workers.EmployeeEditExistingActionViewModel;
import com.mikevostrikov.ralter.web.viewmodel.workers.EmployeeEditNewActionViewModel;
import com.mikevostrikov.ralter.web.viewmodel.workers.EmployeeMainActionViewModel;
import com.mikevostrikov.ralter.web.viewmodel.workers.EmployeeShowActionViewModel;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;


/**
 *
 * @author Mike V
 */


public class EmployeeController {
        
    private EmployeeHttpRequestModel requestData;
    
    @Override
    @SuppressWarnings("UseSpecificCatch")
    public void processRequest() {
        // regular behavior
        requestData = new EmployeeHttpRequestModel(requestContext.getRequest());
        switch (requestData.getAction()) {
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
    }
    
    // actions methods
    
    private void deleteRequestedEmployeeAction(){
        deleteRequestedEmployee();
        showEmployeeListAction();
    }
    
    private void updateEmployeeAction(){
        saveOrUpdateEmployee();
        showRequestedEmployeeAction();
    }
    
    private void addEmployeeAction(){
        Employee employee = getNewEmployee();
        BasicCompositeViewModel<EmployeeEditNewActionViewModel> viewModel = 
                new BasicCompositeViewModel<>(
                        new HeaderViewModel(requestContext.getUser(), (String) requestContext.getRequest().getAttribute(RequestAttribute.ERROR_MSG.name())),
                        new NavigationViewModel(requestContext.getUser()), 
                        new EmployeeEditNewActionViewModel(employee)
                );
        renderEditView(viewModel);
    }
    
    private void showEmployeeListAction(){
        Collection<Employee> employees = getEmployees();
        BasicCompositeViewModel<EmployeeMainActionViewModel> viewModel = 
                new BasicCompositeViewModel<>(
                        new HeaderViewModel(requestContext.getUser(), (String) requestContext.getRequest().getAttribute(RequestAttribute.ERROR_MSG.name())),
                        new NavigationViewModel(requestContext.getUser()), 
                        new EmployeeMainActionViewModel(employees)
                );
        renderEmployeeMainView(viewModel);
    }
    
    private void editRequestedEmployeeAction(){
        Employee employee = getRequestedEmployee();
        BasicCompositeViewModel<EmployeeEditExistingActionViewModel> viewModel = 
                new BasicCompositeViewModel<>(
                        new HeaderViewModel(requestContext.getUser(), (String) requestContext.getRequest().getAttribute(RequestAttribute.ERROR_MSG.name())),
                        new NavigationViewModel(requestContext.getUser()), 
                        new EmployeeEditExistingActionViewModel(employee)
                );
        renderEditView(viewModel);
    }

    private void showRequestedEmployeeAction(){
        Employee employee = getRequestedEmployee();
        BasicCompositeViewModel<EmployeeShowActionViewModel> viewModel = 
                new BasicCompositeViewModel<>(
                        new HeaderViewModel(requestContext.getUser(), (String) requestContext.getRequest().getAttribute(RequestAttribute.ERROR_MSG.name())),
                        new NavigationViewModel(requestContext.getUser()), 
                        new EmployeeShowActionViewModel(employee)
                );
        renderEmployeeView(viewModel);
    }

    // domain interaction methods

    private void deleteRequestedEmployee() {
        new JpaRepository().deleteById(Employee.class, requestData.getEmployee().getEmId());
    }
    
    private void saveOrUpdateEmployee() {
        // retrieve employee from request
        Employee employee = requestData.getEmployee();
        // persist
        hibernateSession.getTransaction().begin();
        hibernateSession.saveOrUpdate(employee);
        hibernateSession.getTransaction().commit();
        hibernateSession.refresh(employee);
    }
        
    private Employee getNewEmployee() {
        Employee employee = new Employee();
        employee.setEmName("New Employee");
        employee.setEmSalary(new BigDecimal(0));
        return employee;
    }
    
    private Collection<Employee> getEmployees(){
        return hibernateSession.createQuery("from Employee").list();
    }
    
    private Employee getRequestedEmployee(){
        return (Employee)hibernateSession.get(Employee.class, requestData.getEmployee().getEmId());
    }
    
    // view rendering methods
    
    private void renderEmployeeMainView(BasicCompositeViewModel<EmployeeMainActionViewModel> viewModel){
        requestContext.getRequest().setAttribute(RequestAttribute.VIEW_DATA.name(), viewModel);
        forwardHttpRequest(Jsp.EMPLOYEES_MAIN_JSP);
    }
    
    private void renderEditView(BasicCompositeViewModel viewModel){
        requestContext.getRequest().setAttribute(RequestAttribute.VIEW_DATA.name(), viewModel);
        forwardHttpRequest(Jsp.EMPLOYEES_EDIT_JSP);
    }
    
    private void renderEmployeeView(BasicCompositeViewModel<EmployeeShowActionViewModel> viewModel){
        requestContext.getRequest().setAttribute(RequestAttribute.VIEW_DATA.name(), viewModel);
        forwardHttpRequest(Jsp.EMPLOYEES_SHOW_JSP);
    }

}
