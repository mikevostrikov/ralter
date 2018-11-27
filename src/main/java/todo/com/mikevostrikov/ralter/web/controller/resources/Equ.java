/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package todo.com.mikevostrikov.ralter.web.controller.resources;

import todo.com.mikevostrikov.ralter.app.domain.model.Equipment;
import todo.com.mikevostrikov.ralter.app.domain.model.HibernateSessionFactoryFactory;
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
public class Equ extends HttpServlet {

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
                // Установка кодировки
                request.setCharacterEncoding("UTF8");

                Session mySession = HibernateSessionFactoryFactory.getSessionFactory().openSession();

                try{
                    if (request.getParameter("act") == null) {
                    // act = null
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Resources/Equipment?act=main");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("main"))) {
                    // act = main
                        // Загрузить List из БД
                        List eqList = mySession.createQuery("from Equipment").list();
                        for (int i = 0; i < eqList.size(); i++){
                            ((Equipment)eqList.get(i)).cutNulls();
                        }
                        // Поместить в сессию
                        session.setAttribute("eqList", eqList);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Resources/Equipment/main.jsp");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("edit"))){
                    // act = edit
                        // Загрузить объект из БД
                        Equipment theEq = (Equipment)mySession.get(Equipment.class, new Long(request.getParameter("eqId")));
                        theEq.trimStrings();
                        theEq.cutNulls();
                        // Поместить объект в сессию
                        session.setAttribute("theEq", theEq);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Resources/Equipment/edit.jsp");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("update"))){
                    // act = update
                        mySession.getTransaction().begin();
                        // Загрузить объект из сессии
                        Equipment theEq = (Equipment)session.getAttribute("theEq");
                        // Получить новые значения
                        theEq.setEqComment(request.getParameter("EqComment"));
                        theEq.setEqManufacturer(request.getParameter("EqManufacturer"));
                        theEq.setEqName(request.getParameter("EqName"));
                        // Сохранить объект в БД
                        mySession.saveOrUpdate(theEq);
                        mySession.getTransaction().commit();
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Resources/Equipment?act=main");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("delete"))){
                    // act = delete
                        // Загрузить объект из БД
                        Equipment theEq = (Equipment)mySession.get(Equipment.class, new Long(request.getParameter("eqId")));
                        // Удалить объект из сессии
                        session.setAttribute("theEq", null);
                        mySession.getTransaction().begin();
                        // Удалить объект из БД
                        mySession.delete(theEq);
                        mySession.getTransaction().commit();
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Resources/Equipment?act=main");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("add"))){
                    // act = add
                        // Создать объект
                        Equipment theEq = new Equipment();
                        theEq.cutNulls();
                        // Поместить новые значения в объект
                        theEq.setEqComment(request.getParameter("EqComment"));
                        theEq.setEqManufacturer(request.getParameter("EqManufacturer"));
                        theEq.setEqName(request.getParameter("EqName"));
                        // Сохранить объект в БД
                        mySession.getTransaction().begin();
                        mySession.saveOrUpdate(theEq);
                        mySession.getTransaction().commit();
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Resources/Equipment?act=main");
                        rd.forward(request, response);
                    } else {
                    // act = unknown
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Resources/Equipment?act=main");
                        rd.forward(request, response);
                    }
                } catch(Exception e) {
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/Resources/Equipment?act=main");
                    rd.forward(request, response);
                    System.out.println("Exception arised: "+e.toString());
                }

                System.out.println("Eq Fullfilled");

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