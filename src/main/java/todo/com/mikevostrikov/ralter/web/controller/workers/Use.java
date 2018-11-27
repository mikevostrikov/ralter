/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package todo.com.mikevostrikov.ralter.web.controller.workers;

import com.mikevostrikov.ralter.app.domain.model.workers.Role;
import com.mikevostrikov.ralter.app.domain.model.workers.Employee;
import todo.com.mikevostrikov.ralter.app.domain.model.HibernateSessionFactoryFactory;
import com.mikevostrikov.ralter.app.domain.model.workers.User;
import java.io.IOException;
import java.io.PrintWriter;

import com.mikevostrikov.ralter.controller.security.UserAuth;
import javax.servlet.*;
import javax.servlet.http.*;

import org.hibernate.Session;

import java.util.*;

/**
 *
 * @author Mike V
 */

public class Use extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // Возвращает идентификатор сервлета
    private Integer getServletID(){
        return new Integer(getServletConfig().getInitParameter("ServletId"));
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            // Проверка авторизации
            HttpSession session = request.getSession();
            UserAuth UserAuth1 = (UserAuth)session.getAttribute("UserAuth");
            if ((UserAuth1 != null) && (UserAuth1.isResourceAllowed(getServletID()))){
                // Авторизация пройдена
                request.setCharacterEncoding("UTF8");

                Session mySession = HibernateSessionFactoryFactory.getSessionFactory().openSession();

                try{
                    if (request.getParameter("act") == null) {
                        // act = null
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Workers/Users?act=main");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("main"))) {
                        // act = main
                        List usList = mySession.createQuery("from Users").list();
                        for (int i = 0; i < usList.size(); i++){
                            ((User)usList.get(i)).cutNulls();
                        }
                        session.setAttribute("usList", usList);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Workers/Users/main.jsp");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("show"))) {
                        // act = show
                        User theUs = (User)mySession.get(User.class, new Long(request.getParameter("usId")));
                        theUs.cutNulls();
                        theUs.trimStrings();
                        session.setAttribute("theUs", theUs);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Workers/Users/show.jsp");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("edit"))){
                        // act = edit
                        List empList = mySession.createQuery("from Employees").list();
                        for (int i = 0; i< empList.size(); i++){
                            // ((Employee)empList.get(i)).cutNulls();
                        }
                        session.setAttribute("empList", empList);
                        List roList = mySession.createQuery("from Roles").list();
                        for (int i = 0; i< roList.size(); i++){
                            ((Role)roList.get(i)).cutNulls();
                        }
                        session.setAttribute("roList", roList);
                        if (empList.size()>0){
                            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Workers/Users/edit.jsp");
                            rd.forward(request, response);
                        }else{
                            session.setAttribute("errorMsg", "You cannot create users without assigning them to employees. That is why you should create employees first.");
                            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Workers/Users/error.jsp");
                            rd.forward(request, response);
                        }
                    } else if (request.getParameter("act").equals(new String("update"))){
                        // act = update
                        User theUs = (User)session.getAttribute("theUs");
                        List empList = (List)session.getAttribute("empList");
                        List roList = (List)session.getAttribute("roList");
                        theUs.setUsLogin(request.getParameter("UsLogin"));
                        theUs.setUsPassword(request.getParameter("UsPassword"));
                        theUs.setRoles((Role)roList.get((new Long(request.getParameter("Index2"))).intValue()));
                        if(request.getParameter("Index") != null){
                            theUs.setEmployee((Employee)empList.get((new Long(request.getParameter("Index"))).intValue()));
                        }else{
                            theUs.setEmployee((Employee)empList.get((new Long(0)).intValue()));                            
                        }
                        mySession.getTransaction().begin();
                        mySession.saveOrUpdate(theUs);
                        mySession.getTransaction().commit();
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Workers/Users?act=show&usId="+theUs.getUsId());
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("delete"))){
                        // act = delete
                        User theUs = (User)session.getAttribute("theUs");
                        session.setAttribute("theUs", null);
                        mySession.getTransaction().begin();
                        mySession.delete(theUs);
                        mySession.getTransaction().commit();
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Workers/Users?act=main");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("add"))){
                        // act = add
                        User theUs = new User();
                        theUs.cutNulls();
                        session.setAttribute("theUs", theUs);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Workers/Users?act=edit");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("existedit"))){
                        // act = existedit
                        User theUs = (User)mySession.get(User.class, new Long(request.getParameter("usId")));
                        theUs.trimStrings();
                        theUs.cutNulls();
                        session.setAttribute("theUs", theUs);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Workers/Users?act=edit");
                        rd.forward(request, response);
                    } else {
                        // act = unknown
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Workers/Users?act=main");
                        rd.forward(request, response);
                    }
                } catch(Exception e) {
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/Workers/Users?act=main");
                    rd.forward(request, response);
                    System.out.println("Exception arised: "+e.toString());
                }

                System.out.println("Us Fullfilled");

                mySession.close();

            }else if (UserAuth1 != null){
                UserAuth1.StatusMsg = "Access Denied. Please, contact your administrator.";
                UserAuth1.isLoggedIn = false;
                response.sendRedirect(response.encodeURL("/Ralter"));
            }else{
                response.sendRedirect(response.encodeURL("/Ralter"));
            }

        } finally {
            out.close();
        }
    }
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
