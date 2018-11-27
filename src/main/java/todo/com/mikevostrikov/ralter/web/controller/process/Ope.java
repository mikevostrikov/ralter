/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package todo.com.mikevostrikov.ralter.web.controller.process;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import todo.com.mikevostrikov.ralter.app.domain.model.Coop;
import todo.com.mikevostrikov.ralter.app.domain.model.Operations;
import todo.com.mikevostrikov.ralter.app.domain.model.Suop;
import todo.com.mikevostrikov.ralter.app.domain.model.Supplies;
import todo.com.mikevostrikov.ralter.app.domain.model.Components;
import todo.com.mikevostrikov.ralter.app.domain.model.Doop;
import todo.com.mikevostrikov.ralter.app.domain.model.HibernateSessionFactoryFactory;
import todo.com.mikevostrikov.ralter.app.domain.model.Documents;
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
public class Ope extends HttpServlet {


    // Возвращает идентификатор сервлета
    private Integer getServletID(){
        return new Integer(getServletConfig().getInitParameter("ServletId"));
    }
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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

                /*List results = mySession.createQuery("select coop.components.coId, coop.components.coName, coop.coopQuantity from Coop coop").list();
                for(int i=0; i<results.size(); i++){
                    Object[] obj = (Object[])results.get(i);
                    CoQu coQu = new CoQu((Long)obj[0], ((String)obj[1]).trim(), (Long)obj[2]);
                    System.out.println(coQu.getCoId()+" "+coQu.getcoName()+" "+coQu.getCoopQuantity());
                }*/

                try{
                    if (request.getParameter("act") == null) {
                    // act = null
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Process/Operations?act=main");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("main"))) {
                    // act = main
                        // Загрузить List из БД
                        List opList = mySession.createQuery("from Operations").list();
                        for (int i = 0; i < opList.size(); i++){
                            ((Operations)opList.get(i)).cutNulls();
                        }
                        // Поместить в сессию
                        session.setAttribute("opList", opList);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Process/Operations/main.jsp");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("show"))) {
                    // act = show
                        // Загрузить объект из БД
                        Operations theOp = (Operations)mySession.get(Operations.class, new Long(request.getParameter("opId")));
                        theOp.cutNulls();
                        theOp.trimStrings();
                        // Поместить в сессию
                        session.setAttribute("theOp", theOp);
                        // Загрузить списки связанных объектов
                        List doList = mySession.createQuery("select doop.documents from Doop doop where doop.operations.opId="+request.getParameter("opId")).list();
                        List coList = mySession.createQuery("select coop.components from Coop coop where coop.operations.opId="+request.getParameter("opId")).list();
                        List suList = mySession.createQuery("select suop.supplies from Suop suop where suop.operations.opId="+request.getParameter("opId")).list();
                        List eqList = new ArrayList();
                        eqList.addAll(theOp.getEquipments());
                        // Поместить в сессию
                        session.setAttribute("doList", doList);
                        session.setAttribute("coList", coList);
                        session.setAttribute("suList", suList);
                        session.setAttribute("eqList", eqList);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Process/Operations/show.jsp");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("edit"))){
                    // act = edit
                        // Загрузить список всех документов
                        List doList = mySession.createQuery("from Documents").list();
                        for (int i = 0; i< doList.size(); i++){
                            ((Documents)doList.get(i)).cutNulls();
                        }
                        Operations theOp = (Operations)session.getAttribute("theOp");
                        // Загрузить список связанных документов
                        List doRelList = mySession.createQuery("select doop.documents from Doop doop where doop.operations.opId="+theOp.getOpId()).list();
                        for (int i = 0; i< doRelList.size(); i++){
                            ((Documents)doRelList.get(i)).cutNulls();
                            System.out.println(((Documents)doRelList.get(i)).getDoName());
                        }
                        // Поместить в сессию
                        session.setAttribute("doList", doList);
                        session.setAttribute("doRelList", doRelList);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Process/Operations/edit.jsp");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("update1"))){
                    // act = update1
                        mySession.getTransaction().begin();
                        // Загрузить объект из сессии
                        Operations theOp = (Operations)session.getAttribute("theOp");
                        // Получить новые значения
                        theOp.setOpName(request.getParameter("OpName"));
                        theOp.setOpComment(request.getParameter("OpComment"));
                        theOp.setOpComplexity(request.getParameter("OpComplexity"));
                        theOp.setOpDescription(request.getParameter("OpDescription"));
                        try {
                            theOp.setOpDuration(new Double(request.getParameter("OpDuration")));
                        } catch (Exception eint) {
                            theOp.setOpDuration(new Double(0));
                        }
                        // Сохранить объект в БД
                        mySession.saveOrUpdate(theOp);
                        // <editor-fold defaultstate="collapsed" desc="// Изменение списков связанных объектов">

                        // Создаем список-строку идентификаторов вновь выбранных документов для запроса нового множества объектов
                        // и список идентификаторов вновь выбранных документов
                        String qPart = new String("");
                        String[] sSelectedDoIds=request.getParameterValues("doIds[]");
                        List selectedDoIds = new ArrayList();
                        if (sSelectedDoIds != null){
                          for (int i = 0; i < sSelectedDoIds.length-1; i++)
                           {
                                qPart+=sSelectedDoIds[i]+",";
                                selectedDoIds.add(new Long(sSelectedDoIds[i]));
                           }
                          qPart+=sSelectedDoIds[sSelectedDoIds.length-1];
                          selectedDoIds.add(new Long(sSelectedDoIds[sSelectedDoIds.length-1]));
                          
                        }

                        // Удаляем лишние свобъекты
                        if (qPart.length()>0){
                            mySession.createQuery( "delete from Doop doop where doop.operations.opId="+theOp.getOpId()+" and doop.documents.doId not in ("+qPart+")").executeUpdate();
                        }else{
                            mySession.createQuery( "delete from Doop doop where doop.operations.opId="+theOp.getOpId()).executeUpdate();
                        }
                      
                        // Создаем список идентификаторов документов, оставшихся связанными с текущей операцией
                        List doIdsList = mySession.createQuery( "select doop.documents.doId from Doop doop where doop.operations.opId="+theOp.getOpId() ).list();

                        // Просматрвиаем новый список идентификаторов. Если надо, создаем свобъект.
                        Iterator it1 = selectedDoIds.iterator();
                        Long curId;
                        while(it1.hasNext()){
                            curId = (Long)it1.next();
                            if(!doIdsList.contains(curId)){
                                Documents theDoc = (Documents)mySession.get(Documents.class, curId);
                                Doop theDoop = new Doop();
                                theDoop.setDocuments(theDoc);
                                theDoop.setOperations(theOp);
                                mySession.saveOrUpdate(theDoop);
                            }
                        }

                        // </editor-fold>
                        mySession.getTransaction().commit();
                        
                        // Подготовить объекты для следующего шага
                        session.setAttribute("theOp", theOp);
                        // Загрузить список всех и назначенных компонентов
                        List coList = mySession.createQuery("from Components").list();
                        List selectedCoList = mySession.createQuery("select coop.components from Coop coop where coop.operations.opId="+theOp.getOpId()).list();
                        coList.removeAll(selectedCoList);

                        session.setAttribute("coList", coList);
                        session.setAttribute("selectedCoList", selectedCoList);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Process/Operations/edit2.jsp");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("edit2"))){
                    // act = edit2
                        System.out.println("Were here: edit2");
                        // Загрузить объект из сессии
                        Operations theOp = (Operations)session.getAttribute("theOp");
                        // Получить список Ids выбранных компонентов
                        // если не пустой, получить список соотв. компонентов
                        String qPart = new String("");
                        List selectedCoList = null;
                        if (request.getParameter("sel") != null){
                            qPart = request.getParameter("sel").trim();
                            if (!qPart.isEmpty()){
                                selectedCoList = mySession.createQuery("from Components co where co.coId in ("+qPart+")").list();
                            }else{
                                selectedCoList = null;
                            }
                        }
                        // Поместить список выбранных компонентов в сессию
                        session.setAttribute("selectedCoList", selectedCoList);
                        // Выбрать все компоненты, удовлетворяющие условию фильтрации и отсутствующие в списке уже выбранных
                        List coList = null;
                        if (request.getParameter("fltrPhrase") != null){
                            if (!qPart.isEmpty()){
                                coList = mySession.createQuery("from Components co where lower(co.coName) like lower('%"+request.getParameter("fltrPhrase")+"%') and co.coId not in ("+qPart+")").list();
                            }else{
                                coList = mySession.createQuery("from Components co where lower(co.coName) like lower('%"+request.getParameter("fltrPhrase")+"%')").list();
                            }
                        } else {
                        // Фильтр отсутствует
                            if (!qPart.isEmpty()){
                                coList = mySession.createQuery("from Components co where co.coId not in ("+qPart+")").list();
                            }else{
                                coList = mySession.createQuery("from Components").list();
                            }
                        }
                        // Поместить фильтрованный список в сессию
                        session.setAttribute("coList", coList);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Process/Operations/edit2.jsp");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("edit3"))){
                    // act = edit3
                        System.out.println("Were here: edit3");

                        // Загрузить объект из сессии
                        Operations theOp = (Operations)session.getAttribute("theOp");

                        // Получить список-строку идентификаторов вновь выбранных компонентов
                        String qPart = request.getParameter("sel");

                        // Начнем транзакцию
                        mySession.getTransaction().begin();

                        // Удаляем лишние свобъекты
                        if (qPart.length()>0){
                            mySession.createQuery( "delete from Coop coop where coop.operations.opId="+theOp.getOpId()+" and coop.components.coId not in ("+qPart+")").executeUpdate();
                        }else{
                            mySession.createQuery( "delete from Coop coop where coop.operations.opId="+theOp.getOpId()).executeUpdate();
                        }

                        // Формируем список идентификаторов выбранных компонентов на основе qPart
                        StringTokenizer st = new StringTokenizer(qPart, ",");
                        List selectedCoIds = new ArrayList();
                        while(st.hasMoreTokens()){
                            selectedCoIds.add(new Long(st.nextToken()));
                        }

                        // Создаем список идентификаторов компонентов, оставшихся связанными с текущей операцией
                        List coIdsList = mySession.createQuery( "select coop.components.coId from Coop coop where coop.operations.opId="+theOp.getOpId() ).list();

                        // Просматрвиаем новый список идентификаторов. Если надо, создаем недостающие свобъекты.
                        Iterator it1 = selectedCoIds.iterator();
                        Long curId;
                        while(it1.hasNext()){
                            curId = (Long)it1.next();
                            if(!coIdsList.contains(curId)){
                                Components theComp = (Components)mySession.get(Components.class, curId);
                                Coop theCoop = new Coop(theOp, theComp, new Long(0));
                                mySession.saveOrUpdate(theCoop);
                            }
                        }
                        mySession.getTransaction().commit();
                        // загружаем все коопы операции и помещаем их в сессию
                        List coopList = mySession.createQuery("from Coop coop where coop.operations.opId="+theOp.getOpId()).list();
                        session.setAttribute("coopList", coopList);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Process/Operations/edit3.jsp");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("update2"))){
                    // act = update2
                        Operations theOp = (Operations)session.getAttribute("theOp");
                        // Загрузить список коопов из сессии
                        List coopList = (List)session.getAttribute("coopList");
                        // Получить новые значения, сразу сохраняя в БД
                        Iterator it1 = coopList.iterator();
                        mySession.getTransaction().begin();
                        while(it1.hasNext()){
                            Coop theCoop = (Coop)it1.next();
                            theCoop.setCoopQuantity(new Long(request.getParameter(theCoop.getCoopId().toString())));
                            mySession.saveOrUpdate(theCoop);
                        }
                        mySession.refresh(theOp);
                        mySession.getTransaction().commit();
                        // Подготовить объекты для edit4
                        // Загрузить список всего и назначенного оборудования
                        List eqList = mySession.createQuery("from Equipment eq where eq.operations=null").list();
                        List selectedEqList = new ArrayList(theOp.getEquipments());
                        eqList.removeAll(selectedEqList);
                        
                        session.setAttribute("eqList", eqList);
                        session.setAttribute("selectedEqList", selectedEqList);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Process/Operations/edit4.jsp");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("edit4"))){
                    // act = edit4
                        System.out.println("Were here: edit4");
                        // Загрузить объект из сессии
                        Operations theOp = (Operations)session.getAttribute("theOp");
                        // Получить список Ids выбранного оборудования из сессии
                        // если не пустой, получить список соотв. оборудования
                        String qPart = new String("");
                        List selectedEqList = null;
                        if (request.getParameter("sel") != null){
                            qPart = request.getParameter("sel").trim();
                            if (!qPart.isEmpty()){
                                selectedEqList = mySession.createQuery("from Equipment eq where eq.eqId in ("+qPart+")").list();
                            }else{
                                selectedEqList = null;
                            }
                        }
                        // Поместить список выбранных оборудования в сессию
                        session.setAttribute("selectedEqList", selectedEqList);
                        // Выбрать все компоненты, удовлетворяющие условию фильтрации и отсутствующие в списке уже выбранных и закрепленных за др операциями
                        List eqList = null;
                        if (request.getParameter("fltrPhrase") != null){
                            if (!qPart.isEmpty()){
                                eqList = mySession.createQuery("from Equipment eq where eq.operations=null and lower(eq.eqName) like lower('%"+request.getParameter("fltrPhrase")+"%') and eq.eqId not in ("+qPart+")").list();
                            }else{
                                eqList = mySession.createQuery("from Equipment eq where eq.operations=null and lower(eq.eqName) like lower('%"+request.getParameter("fltrPhrase")+"%')").list();
                            }
                        } else {
                        // Фильтр отсутствует
                            if (!qPart.isEmpty()){
                                eqList = mySession.createQuery("from Equipment eq where eq.operations=null and eq.eqId not in ("+qPart+")").list();
                            }else{
                                eqList = mySession.createQuery("from Equipment eq where eq.operations=null").list();
                            }
                        }
                        // Поместить фильтрованный список в сессию
                        session.setAttribute("eqList", eqList);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Process/Operations/edit4.jsp");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("update3"))){
                    // act = update3
                        System.out.println("Were here: update3");

                        // Загрузить объект из сессии
                        Operations theOp = (Operations)session.getAttribute("theOp");

                        // Получить список-строку идентификаторов вновь выбранных компонентов
                        String qPart = request.getParameter("sel");

                        // Начнем транзакцию
                        mySession.getTransaction().begin();

                        // Получим список нововыбранного оборудования
                        if (qPart.length()>0){
                            mySession.createQuery( "update from Equipment eq set eq.operations=null where eq.operations=:theOp and eq.eqId not in ("+qPart+")").setEntity("theOp", theOp).executeUpdate();
                            mySession.createQuery("update from Equipment eq set eq.operations=:theOp where eq.operations=null and eq.eqId in (" + qPart + ")").setEntity("theOp", theOp).executeUpdate();
                        }else{
                            mySession.createQuery( "update from Equipment eq set eq.operations=null where eq.operations=:theOp").setEntity("theOp", theOp).executeUpdate();
                        }

                        mySession.refresh(theOp);

                        // Завершаем транзакцию
                        mySession.getTransaction().commit();

                       // Подготовка объектов для edit5
                        
                        session.setAttribute("theOp", theOp);
                        // Загрузить список всех и назначенных материалов
                        List suList = mySession.createQuery("from Supplies").list();
                        List selectedSuList = mySession.createQuery("select suop.supplies from Suop suop where suop.operations.opId="+theOp.getOpId()).list();
                        suList.removeAll(selectedSuList);

                        session.setAttribute("suList", suList);
                        session.setAttribute("selectedSuList", selectedSuList);

                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Process/Operations/edit5.jsp");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("edit5"))){
                    // act = edit5
                        System.out.println("Were here: edit5");
                        // Загрузить объект из сессии
                        Operations theOp = (Operations)session.getAttribute("theOp");
                        // Получить список Ids выбранных компонентов
                        // если не пустой, получить список соотв. компонентов
                        String qPart = new String("");
                        List selectedSuList = null;
                        if (request.getParameter("sel") != null){
                            qPart = request.getParameter("sel").trim();
                            if (!qPart.isEmpty()){
                                selectedSuList = mySession.createQuery("from Supplies su where su.suId in ("+qPart+")").list();
                            }else{
                                selectedSuList = null;
                            }
                        }
                        // Поместить список выбранных компонентов в сессию
                        session.setAttribute("selectedSuList", selectedSuList);
                        // Выбрать все компоненты, удовлетворяющие условию фильтрации и отсутствующие в списке уже выбранных
                        List suList = null;
                        if (request.getParameter("fltrPhrase") != null){
                            if (!qPart.isEmpty()){
                                suList = mySession.createQuery("from Supplies su where lower(su.suName) like lower('%"+request.getParameter("fltrPhrase")+"%') and su.suId not in ("+qPart+")").list();
                            }else{
                                suList = mySession.createQuery("from Supplies su where lower(su.suName) like lower('%"+request.getParameter("fltrPhrase")+"%')").list();
                            }
                        } else {// Фильтр отсутствует
                            if (!qPart.isEmpty()){
                                suList = mySession.createQuery("from Supplies su where su.suId not in ("+qPart+")").list();
                            }else{
                                suList = mySession.createQuery("from Supplies").list();
                            }
                        }
                        // Поместить фильтрованный список в сессию
                        session.setAttribute("suList", suList);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Process/Operations/edit5.jsp");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("edit6"))){
                    // act = edit6
                        System.out.println("Were here: edit6");

                        // Загрузить объект из сессии
                        Operations theOp = (Operations)session.getAttribute("theOp");

                        // Получить список-строку идентификаторов вновь выбранных материалов
                        String qPart = request.getParameter("sel");

                        // Начнем транзакцию
                        mySession.getTransaction().begin();

                        // Удаляем лишние свобъекты
                        if (qPart.length()>0){
                            mySession.createQuery( "delete from Suop suop where suop.operations.opId="+theOp.getOpId()+" and suop.supplies.suId not in ("+qPart+")").executeUpdate();
                        }else{
                            mySession.createQuery( "delete from Suop suop where suop.operations.opId="+theOp.getOpId()).executeUpdate();
                        }

                        // Формируем список идентификаторов выбранных материалов на основе qPart
                        StringTokenizer st = new StringTokenizer(qPart, ",");
                        List selectedSuIds = new ArrayList();
                        while(st.hasMoreTokens()){
                            selectedSuIds.add(new Long(st.nextToken()));
                        }

                        // Создаем список идентификаторов компонентов, оставшихся связанными с текущей операцией
                        List suIdsList = mySession.createQuery( "select suop.supplies.suId from Suop suop where suop.operations.opId="+theOp.getOpId() ).list();

                        // Просматрвиаем новый список идентификаторов. Если надо, создаем недостающие свобъекты.
                        Iterator it1 = selectedSuIds.iterator();
                        Long curId;
                        while(it1.hasNext()){
                            curId = (Long)it1.next();
                            if(!suIdsList.contains(curId)){
                                Supplies theSup = (Supplies)mySession.get(Supplies.class, curId);
                                Suop theSuop = new Suop(theOp, theSup, new Double(0));
                                mySession.saveOrUpdate(theSuop);
                            }
                        }
                        mySession.getTransaction().commit();
                        // загружаем все суопы операции и помещаем их в сессию
                        List suopList = mySession.createQuery("from Suop suop where suop.operations.opId="+theOp.getOpId()).list();
                        session.setAttribute("suopList", suopList);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Process/Operations/edit6.jsp");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("update4"))){
                    // act = update4
                        Operations theOp = (Operations)session.getAttribute("theOp");
                        // Загрузить список суопов из сессии
                        List suopList = (List)session.getAttribute("suopList");
                        // Получить новые значения, сразу сохраняя в БД
                        Iterator it1 = suopList.iterator();
                        mySession.getTransaction().begin();
                        while(it1.hasNext()){
                            Suop theSuop = (Suop)it1.next();
                            theSuop.setSuopQuantity(new Double(request.getParameter(theSuop.getSuopId().toString())));
                            mySession.saveOrUpdate(theSuop);
                        }
                        mySession.getTransaction().commit();

                        session.setAttribute("theOp", null);
                    
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Process/Operations?act=show&opId="+theOp.getOpId());
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("add"))){
                    // act = add
                        // Создать объект
                        Operations theOp = new Operations();
                        theOp.cutNulls();
                        // Поместить объект в сессию
                        session.setAttribute("theOp", theOp);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Process/Operations?act=edit");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("editexisting"))){
                    // act = editexisting
                        // Загрузить объект из БД
                        Operations theOp = (Operations)mySession.get(Operations.class, new Long(request.getParameter("opId")));
                        theOp.trimStrings();
                        theOp.cutNulls();
                        // Поместить объект в сессию
                        session.setAttribute("theOp", theOp);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Process/Operations?act=edit");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("delete"))){
                    // act = delete
                        // Загрузить объект из БД
                        Operations theOp = (Operations)mySession.get(Operations.class, new Long(request.getParameter("opId")));

                        // Загрузить первую операцию
                        List lFirstOper = mySession.createQuery("from Operations op where op.nextOperation<>null and op not in" +
                                "(select op2.nextOperation from Operations op2)").list();
                        Operations firstOper = null;
                        if (lFirstOper.size() > 0)
                                firstOper = (Operations)lFirstOper.get(0);

                        // Построим упорядоченный список операций
                        List tpOpList = new ArrayList();
                        Operations op1 = firstOper;
                        while(op1 != null && op1.getNextOperation() != null){
                            tpOpList.add(op1);
                            op1 = op1.getNextOperation();
                        }
                        if (op1 != null) tpOpList.add(op1);

                        // Проверим, есть ли недоделанная продукция, использующая данную операцию
                        List nedProds = (List)mySession.createQuery("from Products pr where pr.prDate=null").list();
                        if (nedProds.size()>0 && tpOpList.contains(theOp)){
                            session.setAttribute("errorMsg", "It is not allowed to change production process, when some items are not completed yet, i.e. in production.");
                            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Process/Process/error.jsp");
                            rd.forward(request, response);
                        }else{
                            // Удалить объект из сессии
                            session.setAttribute("theOp", null);
                            mySession.getTransaction().begin();
                            // Изменить/удалить связанные объекты, если нужно
                            Set childrenSet = theOp.getPrevOperation();
                            Iterator it1 = childrenSet.iterator();
                            for (int i = 0; i<childrenSet.size(); i++){
                                Operations op = (Operations)it1.next();
                                op.setNextOperation(null);
                                mySession.saveOrUpdate(op);
                            }
                            // Удалить объект из БД
                            mySession.delete(theOp);
                            mySession.getTransaction().commit();
                            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Process/Operations?act=main");
                            rd.forward(request, response);
                        }
                    } else {
                    // act = unknown
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Process/Operations?act=main");
                        rd.forward(request, response);
                    }
                } catch(Exception e) {
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/Process/Operations?act=main");
                    rd.forward(request, response);
                    System.out.println("Exception arised: "+e.toString());
                }

                System.out.println("Op Fullfilled");

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