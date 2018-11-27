/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package todo.com.mikevostrikov.ralter.web.controller.process;

import todo.com.mikevostrikov.ralter.app.domain.model.Operations;
import todo.com.mikevostrikov.ralter.app.domain.model.HibernateSessionFactoryFactory;
import java.io.IOException;
import java.io.PrintWriter;

import com.mikevostrikov.ralter.controller.security.UserAuth;
import javax.servlet.*;
import javax.servlet.http.*;

import org.hibernate.Session;
import org.hibernate.Query;

import java.util.*;

/**
 *
 * @author Mike V
 */
public class Pro extends HttpServlet {

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
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Process/Process?act=main");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("main"))) {
                    // act = main
                        // Получим список операций, у которых имеются следующие операции
                        List tpOpList1 = mySession.createQuery("from Operations op where (op.nextOperation<>null)").list();
                        // Найдем среди них операцию без предыдущей (т.е. первую)
                        if (!tpOpList1.isEmpty()){
                            Operations startOp = null;
                            Iterator it1 = tpOpList1.iterator();
                            while (it1.hasNext()){
                                Operations op = (Operations)it1.next();
                                if(op.getPrevOperation().isEmpty()){
                                    System.out.println("Первая операция: "+op.getOpName());
                                    startOp = op;
                                }
                            }
                            // Построим упорядоченный список операций
                            List tpOpList = new ArrayList();
                            Operations op = startOp;
                            while(op.getNextOperation() != null){
                                tpOpList.add(op);
                                op = op.getNextOperation();
                            }
                            tpOpList.add(op);
                            session.setAttribute("tpOpList", tpOpList);
                        }else{
                            List tpOpList = new ArrayList();
                            session.setAttribute("tpOpList", tpOpList);
                        }
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Process/Process/main.jsp");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("edit"))){
                    // act = edit

                        // Проверим, есть ли недоделанная продукция
                        List nedProds = (List)mySession.createQuery("from Products pr where pr.prDate=null").list();

                        if (nedProds.size()>0){
                            session.setAttribute("errorMsg", "It is not allowed to change production process, when some items are not completed yet, i.e. in production.");
                            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Process/Process/error.jsp");
                            rd.forward(request, response);
                        }else{

                            // Сформируем список всех операций
                            List opList = mySession.createQuery("from Operations").list();

                            // Получим список операций, у которых имеются следующие операции
                            List tpOpList1 = mySession.createQuery("from Operations op where (op.nextOperation<>null)").list();
                            // Найдем среди них операцию без предыдущей (т.е. первую)
                            if (!tpOpList1.isEmpty()){
                                Operations startOp = null;
                                Iterator it1 = tpOpList1.iterator();
                                while (it1.hasNext()){
                                    Operations op = (Operations)it1.next();
                                    if(op.getPrevOperation().isEmpty()){
                                        System.out.println("Первая операция: "+op.getOpName());
                                        startOp = op;
                                    }
                                }
                                // Построим упорядоченный список операций
                                List tpOpList = new ArrayList();
                                Operations op = startOp;
                                while(op.getNextOperation() != null){
                                    tpOpList.add(op);
                                    opList.remove(op);
                                    op = op.getNextOperation();
                                }
                                tpOpList.add(op);
                                opList.remove(op);
                                session.setAttribute("tpOpList", tpOpList);
                                session.setAttribute("opList", opList);
                            }else{
                                List tpOpList = new ArrayList();
                                session.setAttribute("tpOpList", tpOpList);
                                session.setAttribute("opList", opList);
                            }

                            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Process/Process/edit.jsp");
                            rd.forward(request, response);

                        }
                    } else if (request.getParameter("act").equals(new String("update"))){
                    // act = update

                        mySession.getTransaction().begin();

                        // Получить список Ids выбранных компонентов
                        // если не пустой, получить список соотв. компонентов
                        String qPart = request.getParameter("sel").trim();

                        mySession.createQuery("update from Operations op set op.nextOperation=null").executeUpdate();

                        if(!qPart.isEmpty()){
                            // Получить Ids всех операций ТП
                            List newIds = new ArrayList();
                            StringTokenizer st1 = new StringTokenizer(qPart, ",");
                            while(st1.hasMoreTokens()){
                                newIds.add(new Long(st1.nextToken()));
                            }
                            Iterator it1 = newIds.iterator();
                            Long curId = null;
                            if (it1.hasNext()) curId = (Long)it1.next();
                            while(it1.hasNext()){
                                Query query = mySession.createQuery("update from Operations op1 set op1.nextOperation = (from Operations op2 where op2.opId=:opId2) where op1.opId=:opId1").setLong("opId1", curId);
                                curId = (Long)it1.next();
                                query.setLong("opId2", curId).executeUpdate();
                            }
                        }

                        mySession.getTransaction().commit();

                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Process/Process?act=main");
                        rd.forward(request, response);
                    } else {
                    // act = unknown
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Process/Process?act=main");
                        rd.forward(request, response);
                    }
                } catch(Exception e) {
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/Process/Process?act=main");
                    rd.forward(request, response);
                    System.out.println("Exception arised: "+e.toString());
                }

                System.out.println("Pr Fullfilled");

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