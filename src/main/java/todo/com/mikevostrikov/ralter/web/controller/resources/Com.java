/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package todo.com.mikevostrikov.ralter.web.controller.resources;

import todo.com.mikevostrikov.ralter.app.domain.model.Components;
import todo.com.mikevostrikov.ralter.app.domain.model.HibernateSessionFactoryFactory;
import java.io.IOException;
import java.io.PrintWriter;

import com.mikevostrikov.ralter.controller.security.UserAuth;
import javax.servlet.*;
import javax.servlet.http.*;
import java.text.DateFormat;

import org.hibernate.Session;

import java.util.*;

/**
 *
 * @author Mike V
 */
public class Com extends HttpServlet {

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
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Resources/Components?act=main");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("main"))) {
                    // act = main
                        // Загрузить List из БД
                        List coList = mySession.createQuery("from Components").list();
                        for (int i = 0; i < coList.size(); i++){
                            ((Components)coList.get(i)).cutNulls();
                        }
                        // Поместить в сессию
                        session.setAttribute("coList", coList);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Resources/Components/main.jsp");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("show"))) {
                    // act = show
                        // Загрузить объект из БД
                        Components theComp = (Components)mySession.get(Components.class, new Long(request.getParameter("coId")));
                        theComp.cutNulls();
                        theComp.trimStrings();
                        // Поместить в сессию
                        session.setAttribute("theComp", theComp);
                        // Загрузить списки связанных объектов
                        /*String qs = "select rowe.webres from Rowe rowe where rowe.Components.coId="+request.getParameter("coId");
                        List weList = mySession.createQuery(qs).list();*/
                        // Поместить в сессию
                        //session.setAttribute("weList", weList);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Resources/Components/show.jsp");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("edit"))){
                    // act = edit
                        // Загрузить списки связанных объектов
                        /*List weList = mySession.createQuery("from Webres").list();
                        for (int i = 0; i< weList.size(); i++){
                            ((Webres)weList.get(i)).cutNulls();
                        }*/
                        // Поместить в сессию
                        //session.setAttribute("weList", weList);
                        //if (weList.size()>0){
                            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Resources/Components/edit.jsp");
                            rd.forward(request, response);
                        /*}else{
                            session.setAttribute("errorMsg", "Для редактирования роли должен существовать хотя бы один ресурс.");
                            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Resources/Components/error.jsp");
                            rd.forward(request, response);
                        }*/
                    } else if (request.getParameter("act").equals(new String("update"))){
                    // act = update
                        mySession.getTransaction().begin();
                        // Загрузить объект из сессии
                        Components theComp = (Components)session.getAttribute("theComp");
                        // Получить новые значения
                        theComp.setCoName(request.getParameter("CoName"));
                        theComp.setCoManufacturer(request.getParameter("CoManufacturer"));
                        try {
                            theComp.setCoQuantity(new Long(request.getParameter("CoQuantity")));
                        } catch (Exception eint) {
                            theComp.setCoQuantity(new Long(0));
                        }
                        try {
                            DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.CANADA);
                            theComp.setCoDate(df.parse(request.getParameter("CoDate")));
                        } catch (Exception e) {
                            theComp.setCoDate(new Date());
                            System.out.println("Exception arised: "+e.toString());
                        }

                        // Сохранить объект в БД
                        mySession.saveOrUpdate(theComp);

                        // <editor-fold defaultstate="collapsed" desc="// Изменение списков связанных объектов">
                        /*
                        List rowesList = mySession.createQuery("from Rowe rowe where rowe.Components.coId="+theComp.getcoId()).list();
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
                             theRowe.setComponents(theComp);
                             mySession.saveOrUpdate(theRowe);
                        }
                         */
                        // </editor-fold>

                        mySession.getTransaction().commit();
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Resources/Components?act=show&coId="+theComp.getCoId());
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("delete"))){
                    // act = delete
                        // Загрузить объект из БД
                        Components theComp = (Components)mySession.get(Components.class, new Long(request.getParameter("coId")));
                        // Удалить объект из сессии
                        session.setAttribute("theComp", null);
                        mySession.getTransaction().begin();
                        // Удалить связанные объекты, если нужно
                        /*Set rowesSet = theComp.getRowes();
                        Iterator it1 = rowesSet.iterator();
                        for (int i = 0; i<rowesSet.size(); i++){
                            mySession.delete(it1.next());
                        }
                         */
                        // Удалить объект из БД
                        mySession.delete(theComp);
                        mySession.getTransaction().commit();
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Resources/Components?act=main");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("add"))){
                    // act = add
                        // Создать объект
                        Components theComp = new Components();
                        theComp.cutNulls();
                        // Поместить объект в сессию
                        session.setAttribute("theComp", theComp);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Resources/Components?act=edit");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("editexisting"))){
                    // act = editexisting
                        // Загрузить объект из БД
                        Components theComp = (Components)mySession.get(Components.class, new Long(request.getParameter("coId")));
                        theComp.trimStrings();
                        theComp.cutNulls();
                        // Поместить объект в сессию
                        session.setAttribute("theComp", theComp);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Resources/Components?act=edit");
                        rd.forward(request, response);
                    } else {
                    // act = unknown
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Resources/Components?act=main");
                        rd.forward(request, response);
                    }
                } catch(Exception e) {
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/Resources/Components?act=main");
                    rd.forward(request, response);
                    System.out.println("Exception arised: "+e.toString());
                }

                System.out.println("Co Fullfilled");

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