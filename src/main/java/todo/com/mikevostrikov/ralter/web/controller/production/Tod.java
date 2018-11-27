/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package todo.com.mikevostrikov.ralter.web.controller.production;

import todo.com.mikevostrikov.ralter.app.domain.model.Products;
import todo.com.mikevostrikov.ralter.app.domain.model.Coop;
import todo.com.mikevostrikov.ralter.app.domain.model.Operations;
import todo.com.mikevostrikov.ralter.app.domain.model.Supplies;
import todo.com.mikevostrikov.ralter.app.domain.model.Suop;
import todo.com.mikevostrikov.ralter.app.domain.model.Components;
import todo.com.mikevostrikov.ralter.app.domain.model.HibernateSessionFactoryFactory;
import java.io.IOException;
import java.io.PrintWriter;

import com.mikevostrikov.ralter.controller.security.UserAuth;
import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.Timestamp;

import org.hibernate.Session;
import org.hibernate.Query;

import java.util.*;

/**
 *
 * @author Mike V
 */

public class Tod extends HttpServlet {

    /**
     * Productiones requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // Возвращает идентификатор сервлета
    private Integer getServletID(){
        return new Integer(getServletConfig().getInitParameter("ServletId"));
    }

    protected void ProductionRequest(HttpServletRequest request, HttpServletResponse response)
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
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Production/Todo?act=main");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("main"))) {
                    // act = main
                        // Загрузить первую операцию
                        List lFirstOper = mySession.createQuery("from Operations op where op.nextOperation<>null and op not in" +
                                "(select op2.nextOperation from Operations op2)").list();
                        // Загрузить последнюю операцию
                        List lLastOper = mySession.createQuery("select op.nextOperation from Operations op where (op.nextOperation).nextOperation=null").list();
                        if (!lFirstOper.isEmpty() && !lLastOper.isEmpty()) {
                            Operations firstOper = (Operations)lFirstOper.get(0);
                            Operations lastOper = (Operations)lLastOper.get(0);

                            // Загрузить список недоделанных изделий, для которых отсутствуют записи в логе
                            List newProducts = mySession.createQuery("from Products pr where pr.prGoden='F' and (select count(lo) from Log lo where lo.products=pr)=0").list();

                            // Создать для них записи в логе
                            mySession.getTransaction().begin();
                            Iterator it1 = newProducts.iterator();
                            while (it1.hasNext()){
                                Products pr = (Products)it1.next();
                                todo.com.mikevostrikov.ralter.app.domain.model.Log lo = new todo.com.mikevostrikov.ralter.app.domain.model.Log();
                                lo.setProducts(pr);
                                lo.setLoComment("New");
                                lo.setLoErr("F");
                                lo.setLoTimedate(new Timestamp((new Date()).getTime()));
                                mySession.saveOrUpdate(lo);
                            }
                            mySession.getTransaction().commit();



                            // Загрузить из лога: все записи с изделиями с пометкой годности F (недоделаны)
                            List lastOps = mySession.createQuery("from Log lo1 where lo1.products.prGoden='F'"+
                                    // выполненные операции принадлежат ТП или пусты(т.е. изделие новое)
                                    " and (lo1.operations in (from Operations op where op.nextOperation<>null)" +
                                        " or lo1.operations = :lastOper " +
                                        " or lo1.operations = null)" +
                                    // выполненные операции без ошибок
                                    " and lo1.loErr='F'" +
                                    // выполненная операция - последняя из выполненных для данного изделия
                                    " and lo1.loTimedate=" +
                                        " (select max(lo2.loTimedate) from Log lo2 where lo1.products=lo2.products)"
                                        ).setEntity("lastOper", lastOper).list();

                            // Поместить списки в сессию
                            session.setAttribute("firstOper", firstOper);
                            session.setAttribute("lastOper", lastOper);
                            session.setAttribute("lastOps", lastOps);
                            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Production/Todo/main.jsp");
                            rd.forward(request, response);
                        }else{
                            session.setAttribute("errorMsg", "Production process is not set up. No work available.");
                            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Production/Todo/error.jsp");
                            rd.forward(request, response);
                        }

                    } else if (request.getParameter("act").equals(new String("edit"))){
                    // act = edit
                        // Загрузить объект лога
                        todo.com.mikevostrikov.ralter.app.domain.model.Log theLog = (todo.com.mikevostrikov.ralter.app.domain.model.Log)mySession.get(todo.com.mikevostrikov.ralter.app.domain.model.Log.class, new Long(request.getParameter("loId")));
                        session.setAttribute("theLog", theLog);
                        Operations firstOper = (Operations)session.getAttribute("firstOper");
                        Operations oper = null;
                        if (theLog.getOperations() != null){
                            oper = theLog.getOperations().getNextOperation();
                        }else{
                            oper = firstOper;
                        }
                        session.setAttribute("oper", oper);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Production/Todo/edit.jsp");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("update"))){
                    // act = update
                        mySession.getTransaction().begin();
                        todo.com.mikevostrikov.ralter.app.domain.model.Log theLog = (todo.com.mikevostrikov.ralter.app.domain.model.Log)session.getAttribute("theLog");
                        Operations oper = (Operations)session.getAttribute("oper");
                        mySession.refresh(oper);
                        Operations lastOper = (Operations)session.getAttribute("lastOper");
                        todo.com.mikevostrikov.ralter.app.domain.model.Log newLog = new todo.com.mikevostrikov.ralter.app.domain.model.Log();
                        newLog.setEmployee(UserAuth1.emp);
                        newLog.setLoComment(request.getParameter("LoComment"));
                        newLog.setLoErr(request.getParameter("LoErr"));
                        newLog.setOperations(oper);
                        newLog.setProducts(theLog.getProducts());
                        newLog.setLoTimedate(new Timestamp((new Date()).getTime()));
                        if (oper.getOpId().equals(lastOper.getOpId()) && newLog.getLoErr().equals("F")){
                            newLog.getProducts().setPrGoden("T");
                            newLog.getProducts().setPrDate(newLog.getLoTimedate());
                        }
                        Set coopList = newLog.getOperations().getCoops();
                        Set suopList = newLog.getOperations().getSuops();
                        Iterator it1 = coopList.iterator();
                        while(it1.hasNext()){
                            Coop coop = (Coop)it1.next();
                            Components comp = coop.getComponents();
                            comp.setCoQuantity(comp.getCoQuantity()-coop.getCoopQuantity());
                            mySession.saveOrUpdate(comp);
                        }
                         it1 = suopList.iterator();
                        while(it1.hasNext()){
                            Suop suop = (Suop)it1.next();
                            Supplies supl = suop.getSupplies();
                            supl.setSuQuantity(supl.getSuQuantity()-suop.getSuopQuantity());
                            mySession.saveOrUpdate(supl);
                        }
                        mySession.saveOrUpdate(newLog.getProducts());
                        mySession.saveOrUpdate(newLog);
                        mySession.getTransaction().commit();
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Production/Todo?act=main");
                        rd.forward(request, response);
                    } else {
                    // act = unknown
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Production/Todo?act=main");
                        rd.forward(request, response);
                    }
                } catch(Exception e) {
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/Production/Todo?act=main");
                    rd.forward(request, response);
                    System.out.println("Exception arised: "+e.toString());
                }

                System.out.println("To Fullfilled");

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
        ProductionRequest(request, response);
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
        ProductionRequest(request, response);
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