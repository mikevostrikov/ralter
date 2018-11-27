/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todo.com.mikevostrikov.ralter.web.controller;

import static com.mikevostrikov.ralter.web.controller.todo.SessionAttribute.USER;
import com.mikevostrikov.ralter.app.domain.model.workers.User;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author m.vostrikov
 */

public class HomePageController extends BaseScenarioController {

    @Override
    protected void processRequest() {
        User user = (User) httpSession.getAttribute(USER.name());
        forwardHttpRequest(user.getDefaultController());
    }
    
}
