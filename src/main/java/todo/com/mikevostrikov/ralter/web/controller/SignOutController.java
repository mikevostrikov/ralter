/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todo.com.mikevostrikov.ralter.web.controller;

import com.mikevostrikov.ralter.web.viewmodel.SignOutViewModel;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author m.vostrikov
 */

@WebServlet(
        urlPatterns = ActionController.Constants.SIGNOUT_SERVLET_URI,
        initParams = @WebInitParam(name = "ServletId", value = ActionController.Constants.SIGNOUT_SERVLET_ID)
)
public class SignOutController extends BaseScenarioController {

    @Override
    protected void processRequest() {
        httpSession.setAttribute(SessionAttribute.USER.name(), null);
        SignOutViewModel viewModel = new SignOutViewModel();
        renderSignOutPage(viewModel);
    }
    
    // view rendering methods
    
    private void renderSignOutPage(SignOutViewModel viewModel){
        request.setAttribute(RequestAttribute.VIEW_DATA.name(), viewModel);
        forwardHttpRequest(Jsp.SIGNOUT_JSP);
    }
    
}
