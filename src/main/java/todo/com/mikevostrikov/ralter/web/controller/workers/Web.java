/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package todo.com.mikevostrikov.ralter.web.controller.workers;

import todo.com.mikevostrikov.ralter.app.domain.model.Webres;
import todo.com.mikevostrikov.ralter.app.domain.model.HibernateSessionFactoryFactory;
import java.io.*;

import com.mikevostrikov.ralter.controller.security.UserAuth;
import javax.servlet.*;
import javax.servlet.http.*;

import org.hibernate.Session;

import java.util.*;

import java.net.*;
import javax.servlet.annotation.WebServlet;
import org.w3c.dom.*;
import org.w3c.dom.Node;
import oracle.xml.parser.v2.DOMParser;
import oracle.xml.parser.v2.XMLElement;
import oracle.xml.parser.v2.XMLDocument;


/**
 *
 * @author Mike V
 */
@WebServlet("/Workers/Webres/")
public class Web extends HttpServlet {
   
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
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Workers/Webres?act=main");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("main"))) {
                    // act = main
                        List weList = mySession.createQuery("from Webres we order by we.weId asc").list();
                        for (int i = 0; i < weList.size(); i++){
                            ((Webres)weList.get(i)).cutNulls();
                        }
                        session.setAttribute("weList", weList);

                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Workers/Webres/main.jsp");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("add"))) {
                    // act = add
                        Webres theWeb = new Webres();
                        theWeb.setWeId(new Long(request.getParameter("weId").trim()));
                        theWeb.setWeName(request.getParameter("weName"));
                        theWeb.setWeLocation(request.getParameter("weLocation"));
                        mySession.getTransaction().begin();
                        mySession.saveOrUpdate(theWeb);
                        mySession.getTransaction().commit();
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Workers/Webres?act=main");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("synchronize"))) {
                    // act = synchronize
                        List webresList = new ArrayList();
                        // parsing web.xml
                        DOMParser parser = new DOMParser();
                        URL url = createURL(this.getServletContext().getRealPath("WEB-INF/web.xml"));
                        System.out.println(url.getPath());
                        parser.setErrorStream(System.err);
                        parser.showWarnings(true);
                        parser.parse(url);
                        XMLDocument doc = parser.getDocument();
                        XMLElement e = (XMLElement)doc.getDocumentElement();
                        NodeList nl = e.selectNodes("servlet");
                        System.out.println("nl Length = "+nl.getLength());
                        for (int i = 0; i < nl.getLength(); i++) {
                            System.out.println(nl.item(i).getNodeName());
                            XMLElement e1 = (XMLElement)nl.item(i);
                            Node desc = e1.selectSingleNode("description");
                            Node servname = e1.selectSingleNode("servlet-name");
                            if (desc != null) {
                                System.out.println(desc.getNodeName() + " = " + desc.getTextContent());
                                NodeList nl2 = e1.selectNodes("init-param");
                                for (int j = 0; j < nl2.getLength(); j++) {
                                    XMLElement e2 = (XMLElement) nl2.item(j);
                                    Node paramName = e2.selectSingleNode("param-name");
                                    Node paramValue = e2.selectSingleNode("param-value");
                                    if (paramName != null && paramValue != null && paramName.getTextContent().equals("ServletId")) {
                                        System.out.println("ServletId = " + paramValue.getTextContent());
                                        Webres theWeb = new Webres();
                                        theWeb.setWeName(desc.getTextContent());
                                        theWeb.setWeId(new Long(paramValue.getTextContent()));
                                        theWeb.setWeServName(servname.getTextContent());
                                        webresList.add(theWeb);
                                    }
                                }
                            }
                            System.out.println();
                        }
                        List servnames = new ArrayList();
                        List locations = new ArrayList();
                        NodeList mappings = e.selectNodes("servlet-mapping");
                        for(int i = 0; i < mappings.getLength(); i++){
                            XMLElement e1 = (XMLElement)mappings.item(i);
                            Node servname = e1.selectSingleNode("servlet-name");
                            Node location = e1.selectSingleNode("url-pattern");
                            servnames.add(servname.getTextContent());
                            locations.add(location.getTextContent());
                        }
                        Iterator it1 = webresList.iterator();
                        mySession.getTransaction().begin();
                        while (it1.hasNext()){
                            Webres theWeb = (Webres)it1.next();
                            theWeb.setWeLocation((String)locations.get(servnames.indexOf(theWeb.getWeServName())));
                            Webres theWeb1 = (Webres)mySession.get(Webres.class, theWeb.getWeId());
                            if (theWeb1 != null){
                                theWeb1.setWeLocation(theWeb.getWeLocation());
                                theWeb1.setWeName(theWeb.getWeName());
                                theWeb1.setWeServName(theWeb.getWeServName());
                                mySession.saveOrUpdate(theWeb1);
                            }else{
                                mySession.saveOrUpdate(theWeb);
                            }
                        }
                        mySession.getTransaction().commit();
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Workers/Webres?act=main");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("edit"))) {
                    // act = edit
                        Webres theWeb = (Webres)mySession.get(Webres.class, new Long(request.getParameter("weId")));
                        theWeb.trimStrings();
                        theWeb.cutNulls();
                        session.setAttribute("theWeb", theWeb);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Workers/Webres/edit.jsp");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("update"))) {
                    // act = update
                        Webres theWeb = (Webres)session.getAttribute("theWeb");
                        theWeb.setWeName(request.getParameter("weName"));
                        theWeb.setWeLocation(request.getParameter("weLocation"));
                        mySession.getTransaction().begin();
                        mySession.saveOrUpdate(theWeb);
                        mySession.getTransaction().commit();
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Workers/Webres?act=main");
                        rd.forward(request, response);
                    } else if (request.getParameter("act").equals(new String("delete"))) {
                    // act = delete
                        Webres theWeb = (Webres)mySession.get(Webres.class, new Long(request.getParameter("weId")));
                        Set roweSet = theWeb.getRowes();
                        mySession.getTransaction().begin();
                        Iterator it1 = roweSet.iterator();
                        for (int i=0; i< roweSet.size(); i++){
                            mySession.delete(it1.next());
                        }
                        mySession.delete(theWeb);
                        mySession.getTransaction().commit();
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Workers/Webres?act=main");
                        rd.forward(request, response);
                    } else {
                    // act = unknown
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Workers/Webres?act=main");
                        rd.forward(request, response);
                    }
                } catch(Exception e) {
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/Workers/Webres?act=main");
                    rd.forward(request, response);
                    System.out.println("Exception arised: "+e.toString());
                }

                System.out.println("We Fullfilled");

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

       static URL createURL(String fileName)
   {
      URL url = null;
      try
      {
         url = new URL(fileName);
      }
      catch (MalformedURLException ex)
      {
         File f = new File(fileName);
         try
         {
            String path = f.getAbsolutePath();
            String fs = System.getProperty("file.separator");
            if (fs.length() == 1)
            {
               char sep = fs.charAt(0);
               if (sep != '/')
                  path = path.replace(sep, '/');
               if (path.charAt(0) != '/')
                  path = '/' + path;
            }
            path = "file://" + path;
            url = new URL(path);
         }
         catch (MalformedURLException e)
         {
            System.out.println("Cannot create url for: " + fileName);
            System.exit(0);
         }
      }
      return url;
   }

   static void printElements(Document doc)
   {
      NodeList nl = doc.getElementsByTagName("*");
      Node n;

      for (int i=0; i<nl.getLength(); i++)
      {
         n = nl.item(i);
         System.out.print(n.getNodeName() + " ");
      }

      System.out.println();
   }

   static void printElementAttributes(Document doc)
   {
      NodeList nl = doc.getElementsByTagName("*");
      Element e;
      Node n;
      NamedNodeMap nnm;

      String attrname;
      String attrval;
      int i, len;

      len = nl.getLength();
      for (int j=0; j < len; j++)
      {
         e = (Element)nl.item(j);
         System.out.println(e.getTagName() + ":" + e.getTextContent());
         nnm = e.getAttributes();
         if (nnm != null)
         {
            for (i=0; i<nnm.getLength(); i++)
            {
               n = nnm.item(i);
               attrname = n.getNodeName();
               attrval = n.getNodeValue();
               System.out.print(" " + attrname + " = " + attrval);
            }
         }
         System.out.println();
      }
      System.out.println("Len = "+len);

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
