/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package todo.com.mikevostrikov.ralter.web.controller.workers;

import todo.com.mikevostrikov.ralter.app.domain.model.Webres;
import com.mikevostrikov.ralter.app.domain.model.workers.Role;
import todo.com.mikevostrikov.ralter.app.domain.model.HibernateSessionFactoryFactory;
import todo.com.mikevostrikov.ralter.app.domain.model.Rowe;
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
public class Rol extends HttpServlet {
   
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
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Workers/Roles?act=main");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("main"))) {
                    // act = main
                        List roList = mySession.createQuery("from Roles").list();
                        for (int i = 0; i < roList.size(); i++){
                            ((Role)roList.get(i)).cutNulls();
                        }
                        session.setAttribute("roList", roList);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Workers/Roles/main.jsp");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("show"))) {
                    // act = show
                        Role theRole = (Role)mySession.get(Role.class, new Long(request.getParameter("roId")));
                        theRole.cutNulls();
                        theRole.trimStrings();
                        session.setAttribute("theRole", theRole);
                        String qs = "select rowe.webres from Rowe rowe where rowe.roles.roId="+request.getParameter("roId");
                        List weList = mySession.createQuery(qs).list();
                        System.out.println(weList.size());
                        session.setAttribute("weList", weList);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Workers/Roles/show.jsp");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("edit"))){
                    // act = edit
                        List weList = mySession.createQuery("from Webres").list();
                        for (int i = 0; i< weList.size(); i++){
                            ((Webres)weList.get(i)).cutNulls();
                        }
                        session.setAttribute("weList", weList);
                        if (weList.size()>0){
                            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Workers/Roles/edit.jsp");
                            rd.forward(request, response);
                        }else{
                            session.setAttribute("errorMsg", "To edit you should create at least one web-resource. Please refer to the administrator.");
                            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Workers/Roles/error.jsp");
                            rd.forward(request, response);
                        }
                    } else if (request.getParameter("act").equals(new String("update"))){
                    // act = update
                        mySession.getTransaction().begin();
                        Role theRole = (Role)session.getAttribute("theRole");
                        theRole.setRoName(request.getParameter("RoName"));
                        mySession.saveOrUpdate(theRole);
                        List rowesList = mySession.createQuery("from Rowe rowe where rowe.roles.roId="+theRole.getRoId()).list();
                        // Создаем список идентификаторов ресурсов роли
                        Set weIdsSet = new HashSet();
                        String[] all_of_them=request.getParameterValues("weIds[]");
                        if (all_of_them != null){
                          for (int i = 0; i < all_of_them.length; i++)
                           {
                                String testParam = all_of_them[i];
                                weIdsSet.add(new Long(testParam));
                           }
                        }
                        // Удаляем из базы лишние записи и составляем множество идент. рес-в, к-е нужно связать
                        for (int i = 0; i < rowesList.size(); i++){
                            if (weIdsSet.contains( ((Rowe)rowesList.get(i)).getWebres().getWeId() )) {
                                weIdsSet.remove(((Rowe)rowesList.get(i)).getWebres().getWeId());
                            } else {
                                mySession.delete(rowesList.get(i));
                            }
                        }
                        // Связываем все оставшиеся ресурсы
                        Iterator it = weIdsSet.iterator();
                        for (int i = 0; i < weIdsSet.size(); i++){
                             Webres theWeb = (Webres)mySession.get(Webres.class, (Long)it.next());
                             Rowe theRowe = new Rowe();
                             theRowe.setWebres(theWeb);
                             theRowe.setRoles(theRole);
                             mySession.saveOrUpdate(theRowe);
                        }
                        mySession.getTransaction().commit();
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Workers/Roles?act=show&roId="+theRole.getRoId());
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("delete"))){
                    // act = delete
                        Role theRole = (Role)session.getAttribute("theRole");
                        session.setAttribute("theRole", null);
                        Set rowesSet = theRole.getRowes();
                        Iterator it1 = rowesSet.iterator();
                        mySession.getTransaction().begin();
                        for (int i = 0; i<rowesSet.size(); i++){
                            mySession.delete(it1.next());
                        }
                        mySession.delete(theRole);
                        mySession.getTransaction().commit();
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Workers/Roles?act=main");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("add"))){
                    // act = add
                        Role theRole = new Role();
                        theRole.cutNulls();
                        session.setAttribute("theRole", theRole);
                        List weList = mySession.createQuery("from Webres").list();
                        session.setAttribute("weList", weList);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Workers/Roles?act=edit");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("existedit"))){
                    // act = existedit
                        Role theRole = (Role)mySession.get(Role.class, new Long(request.getParameter("roId")));
                        theRole.trimStrings();
                        theRole.cutNulls();
                        session.setAttribute("theRole", theRole);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Workers/Roles?act=edit");
                        rd.forward(request, response);
                    } else {
                    // act = unknown
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Workers/Roles?act=main");
                        rd.forward(request, response);
                    }
                } catch(Exception e) {
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/Workers/Roles?act=main");
                    rd.forward(request, response);
                    System.out.println("Exception arised: "+e.toString());
                }

                System.out.println("Ro Fullfilled");

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
