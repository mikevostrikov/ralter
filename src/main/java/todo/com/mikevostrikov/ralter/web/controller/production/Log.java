/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package todo.com.mikevostrikov.ralter.web.controller.production;

import todo.com.mikevostrikov.ralter.app.domain.model.Products;
import todo.com.mikevostrikov.ralter.app.domain.model.Operations;
import todo.com.mikevostrikov.ralter.app.domain.model.HibernateSessionFactoryFactory;
import java.io.IOException;
import java.io.PrintWriter;

import com.mikevostrikov.ralter.controller.security.UserAuth;
import javax.servlet.*;
import javax.servlet.http.*;

import java.lang.Math;

import java.sql.Timestamp;

import org.hibernate.Session;
import org.hibernate.Query;

import java.util.*;

/**
 *
 * @author Mike V
 */

public class Log extends HttpServlet {

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
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Production/Log?act=main");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("main"))) {
                    // act = main
                        // Загрузить первую операцию
                        List lFirstOper = mySession.createQuery("from Operations op where op.nextOperation<>null and op not in" +
                                "(select op2.nextOperation from Operations op2)").list();
                        // Загрузить последнюю операцию
                        List lLastOper = mySession.createQuery("select op.nextOperation from Operations op where (op.nextOperation).nextOperation=null").list();
                        if (!lFirstOper.isEmpty() && !lLastOper.isEmpty()) {
                            Operations firstOper = (Operations) lFirstOper.get(0);
                            Operations lastOper = (Operations) lLastOper.get(0);



                            // Список проблемной продукции
                            // последняя выполненная операция для таких изделий выполнена
                            // с ошибкой и не установлена даты (т.е. изделие не в изоляторе)
                            // Загрузить из лога все записи с изделиями с пометкой годности F (недоделаны)
                            List logProblProds = mySession.createQuery("from Log lo1 where lo1.products.prGoden='F'" +
                                    // не установлено даты
                                    " and lo1.products.prDate=null" +
                                    // выполненные операции принадлежат ТП
                                    " and (lo1.operations in (from Operations op where op.nextOperation<>null)" +
                                    " or lo1.operations = :lastOper) " +
                                    // выполненные операции с ошибками
                                    " and lo1.loErr='T'" +
                                    // выполненная операция - последняя из выполненных для данного изделия
                                    " and lo1.loTimedate=" +
                                    " (select max(lo2.loTimedate) from Log lo2 where lo1.products=lo2.products)").setEntity("lastOper", lastOper).list();
                            // Поместить список в сессию
                            session.setAttribute("logProblProds", logProblProds);

                            // Загрузить список продукции, находящейся в процессе производства
                            // Загрузить из лога все записи с изделиями с пометкой годности F (недоделаны)
                            List logLastOps = mySession.createQuery("from Log lo1 where lo1.products.prGoden='F'" +
                                    // выполненные операции принадлежат ТП или пусты(т.е. изделие новое)
                                    " and (lo1.operations in (from Operations op where op.nextOperation<>null)" +
                                    " or lo1.operations = :lastOper " +
                                    " or lo1.operations = null)" +
                                    // выполненные операции без ошибок
                                    " and lo1.loErr='F'" +
                                    // выполненная операция - последняя из выполненных для данного изделия
                                    " and lo1.loTimedate=" +
                                    " (select max(lo2.loTimedate) from Log lo2 where lo1.products=lo2.products)").setEntity("lastOper", lastOper).list();
                            session.setAttribute("logLastOps", logLastOps);
                            System.out.println(logLastOps.size());

                            // Построим упорядоченный список операций
                            List tpOpList = new ArrayList();
                            Operations op = firstOper;
                            while (op.getNextOperation() != null) {
                                tpOpList.add(op);
                                op = op.getNextOperation();
                            }
                            tpOpList.add(op);

                            // Построим список процентов выполнения
                            List percents = new ArrayList();
                            Iterator it1 = logLastOps.iterator();
                            todo.com.mikevostrikov.ralter.app.domain.model.Log log = null;
                            while (it1.hasNext()) {
                                log = (todo.com.mikevostrikov.ralter.app.domain.model.Log) it1.next();
                                if (log.getOperations() != null) {
                                    percents.add(new Long(Math.round(((new Double(tpOpList.indexOf(log.getOperations()) + 1)) / tpOpList.size()) * 100)));
                                } else {
                                    percents.add(new Long(0));
                                }
                            }
                            System.out.println(percents.size());
                            session.setAttribute("percents", percents);

                            // Загрузить список готовой продукции
                            // считается, что изделие годно к использованию, если установлена соотв. метка
                            List readyProds = mySession.createQuery("from Products pr where pr.prGoden='T'").list();
                            session.setAttribute("readyProds", readyProds);

                            // Загрузить список продукции в изоляторе брака
                            // считается, что работа над изделием завершена, если установлена дата его выпуска
                            List isolProds = mySession.createQuery("from Products pr where pr.prGoden='F' and pr.prDate<>null").list();
                            session.setAttribute("isolProds", isolProds);
                            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Production/Log/main.jsp");
                            rd.forward(request, response);
                        }else{
                            session.setAttribute("errorMsg", "Production process is not defined. Nothing in production.");
                            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Production/Log/error.jsp");
                            rd.forward(request, response);
                        }
                    } else if (request.getParameter("act").equals(new String("add"))){
                    // act = add
                        mySession.getTransaction().begin();

                        Products theProd = null;
                        todo.com.mikevostrikov.ralter.app.domain.model.Log lo = null;
                        for(int i=0; i<5; i++){
                            theProd = new Products();
                            theProd.setPrGoden("F");
                            mySession.saveOrUpdate(theProd);
                            theProd.setPrPassport("АБВГ-ПС-"+theProd.getPrId());
                            mySession.saveOrUpdate(theProd);
                            lo = new todo.com.mikevostrikov.ralter.app.domain.model.Log();
                            lo.setEmployee(UserAuth1.emp);
                            lo.setProducts(theProd);
                            lo.setLoComment("New");
                            lo.setLoErr("F");
                            lo.setLoTimedate(new Timestamp((new Date()).getTime()));
                            mySession.saveOrUpdate(lo);
                        }

                        mySession.getTransaction().commit();

                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Production/Log?act=main");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("edit"))){
                    // act = edit
                        
                        // Загрузить первую операцию
                        List lFirstOper = mySession.createQuery("from Operations op where op.nextOperation<>null and op not in" +
                                "(select op2.nextOperation from Operations op2)").list();
                        Operations firstOper = (Operations)lFirstOper.get(0);
                        
                        // Построим упорядоченный список операций
                        List tpOpList = new ArrayList();
                        Operations op = firstOper;
                        while(op.getNextOperation() != null){
                            tpOpList.add(op);
                            op = op.getNextOperation();
                        }
                        tpOpList.add(op);

                        todo.com.mikevostrikov.ralter.app.domain.model.Log theLog = (todo.com.mikevostrikov.ralter.app.domain.model.Log)mySession.get(todo.com.mikevostrikov.ralter.app.domain.model.Log.class, new Long(request.getParameter("loId")));
                        // В сессию
                        session.setAttribute("theLog", theLog);
                        session.setAttribute("tpOpList", tpOpList);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Production/Log/edit.jsp");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("update"))){
                    // act = update
                        todo.com.mikevostrikov.ralter.app.domain.model.Log theLog = (todo.com.mikevostrikov.ralter.app.domain.model.Log)session.getAttribute("theLog");
                        Operations oper = (Operations)mySession.get(Operations.class , new Long(request.getParameter("opId")));
                        todo.com.mikevostrikov.ralter.app.domain.model.Log theNewLog = null;
                        if (oper.getPrevOperation().isEmpty()){
                            theNewLog = new todo.com.mikevostrikov.ralter.app.domain.model.Log();
                            theNewLog.setLoComment("Renewed");
                            theNewLog.setLoTimedate(new Timestamp((new Date()).getTime()));
                            theNewLog.setProducts(theLog.getProducts());
                            theNewLog.setEmployee(UserAuth1.emp);
                            theNewLog.setLoErr("F");
                        }else{
                            theNewLog = new todo.com.mikevostrikov.ralter.app.domain.model.Log();
                            theNewLog.setLoComment("Renewed");
                            theNewLog.setLoTimedate(new Timestamp((new Date()).getTime()));
                            theNewLog.setProducts(theLog.getProducts());
                            theNewLog.setLoErr("F");
                            theNewLog.setEmployee(UserAuth1.emp);
                            theNewLog.setOperations((Operations)oper.getPrevOperation().iterator().next());
                        }
                        mySession.getTransaction().begin();
                        mySession.saveOrUpdate(theNewLog);
                        mySession.getTransaction().commit();
                        System.out.println(theNewLog.getLoId());
                        
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Production/Log?act=main");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("isolate"))){
                    // act = isolate
                        todo.com.mikevostrikov.ralter.app.domain.model.Log theLog = (todo.com.mikevostrikov.ralter.app.domain.model.Log)mySession.get(todo.com.mikevostrikov.ralter.app.domain.model.Log.class, new Long(request.getParameter("loId")));
                        Products theProd = theLog.getProducts();
                        theProd.setPrDate(new Timestamp((new Date()).getTime()));
                        todo.com.mikevostrikov.ralter.app.domain.model.Log theNewLog = new todo.com.mikevostrikov.ralter.app.domain.model.Log();
                        theNewLog.setLoComment("Isolated");
                        theNewLog.setLoTimedate(new Timestamp((new Date()).getTime()));
                        theNewLog.setProducts(theProd);
                        theNewLog.setEmployee(UserAuth1.emp);
                        mySession.getTransaction().begin();
                        mySession.saveOrUpdate(theProd);
                        mySession.saveOrUpdate(theNewLog);
                        mySession.getTransaction().commit();
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Production/Log?act=main");
                        rd.forward(request, response);
                    } else {
                    // act = unknown
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Production/Log?act=main");
                        rd.forward(request, response);
                    }
                } catch(Exception e) {
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/Production/Log?act=main");
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