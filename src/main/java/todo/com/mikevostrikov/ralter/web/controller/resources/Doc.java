/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package todo.com.mikevostrikov.ralter.web.controller.resources;

import todo.com.mikevostrikov.ralter.app.domain.model.HibernateSessionFactoryFactory;
import todo.com.mikevostrikov.ralter.app.domain.model.Documents;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.File;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.util.Streams;
import com.mikevostrikov.ralter.controller.security.UserAuth;
import javax.servlet.*;
import javax.servlet.http.*;

import org.hibernate.Session;

import java.util.*;

/**
 *
 * @author Mike V
 */

public class Doc extends HttpServlet {

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
                
                // Check that we have a file upload request
                boolean isMultipart = ServletFileUpload.isMultipartContent(request);

                Session mySession = HibernateSessionFactoryFactory.getSessionFactory().openSession();

                try{
                    if (!isMultipart && request.getParameter("act") == null) {
                    // act = null
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Resources/Documents?act=main");
                        rd.forward(request, response);
                    } else if (!isMultipart && request.getParameter("act").equals(new String("main"))) {
                    // act = main
                        // Загрузить List из БД
                        List doList = mySession.createQuery("from Documents").list();
                        for (int i = 0; i < doList.size(); i++){
                            ((Documents)doList.get(i)).cutNulls();
                        }
                        // Поместить в сессию
                        session.setAttribute("doList", doList);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Resources/Documents/main.jsp");
                        rd.forward(request, response);
                    } else if (!isMultipart && request.getParameter("act").equals(new String("show"))){
                    // act = show
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Resources/Documents?act=main");
                        rd.forward(request, response);
                    } else if (!isMultipart && request.getParameter("act").equals(new String("edit"))){
                    // act = edit
                        // Загрузить объект из БД
                        Documents theDoc = (Documents)mySession.get(Documents.class, new Long(request.getParameter("doId")));
                        theDoc.trimStrings();
                        theDoc.cutNulls();
                        // Поместить объект в сессию
                        session.setAttribute("theDoc", theDoc);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Resources/Documents/edit.jsp");
                        rd.forward(request, response);
                    } else if (!isMultipart && request.getParameter("act").equals(new String("update"))){
                    // act = update
                        mySession.getTransaction().begin();
                        // Загрузить объект из сессии
                        Documents theDoc = (Documents)session.getAttribute("theDoc");
                        // Получить новые значения
                        /*theDoc.setEqComment(request.getParameter("EqComment"));
                        theDoc.setEqManufacturer(request.getParameter("EqManufacturer"));
                        theDoc.setEqName(request.getParameter("EqName"));*/
                        // Сохранить объект в БД
                        mySession.saveOrUpdate(theDoc);
                        mySession.getTransaction().commit();

                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Resources/Documents?act=main");
                        rd.forward(request, response);
                    } else if (!isMultipart && request.getParameter("act").equals(new String("delete"))){
                    // act = delete
                        // Загрузить объект из БД
                        Documents theDoc = (Documents)mySession.get(Documents.class, new Long(request.getParameter("doId")));
                        // Удалить объект из сессии
                        session.setAttribute("theDoc", null);
                        // Удалить файл
                        File f1 = new File(this.getServletContext().getRealPath("Resources/Documents/Files/"+theDoc.getDoUrl()));
                        f1.delete();
                        // Удалить объект из БД
                        mySession.getTransaction().begin();
                        mySession.delete(theDoc);
                        mySession.getTransaction().commit();
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Resources/Documents?act=main");
                        rd.forward(request, response);
                    } else if (isMultipart){
                    // act = add
                        // request.getParameter("act").equals(new String("add"))
                        // Create a new file upload handler
                        ServletFileUpload upload = new ServletFileUpload();
                        // Parse the request
                        try{
                            FileItemIterator iter = upload.getItemIterator(request);
                            
                            if (iter.hasNext()) {
                                FileItemStream item1 = iter.next();
                                String param1 = item1.getFieldName();
                                InputStream stream1 = item1.openStream();
                                String value1 = Streams.asString(stream1);
                                if (item1.isFormField()) {
                                    if (param1.equals(new String("act")) && value1.equals(new String("add"))) {
                                        if (iter.hasNext()) {
                                            FileItemStream item2 = iter.next();
                                            String param2 = item2.getFieldName();
                                            InputStream stream2 = item2.openStream();
                                            String value2 = Streams.asString(stream2, "UTF8");
                                            if (item2.isFormField() && param2.equals(new String("DoName"))) {
                                                if (iter.hasNext()) {
                                                    FileItemStream item3 = iter.next();
                                                    String param3 = item3.getFieldName();
                                                    InputStream stream3 = item3.openStream();
                                                    if (!item3.isFormField() && param3.equals(new String("File"))) {
                                                        // Создать объект
                                                        Documents theDoc = new Documents();
                                                        theDoc.cutNulls();
                                                        // Поместить новые значения в объект
                                                        theDoc.setDoName(value2);
                                                        String uniFileName = new String(item3.getName());
                                                        // Сохранить файл
                                                        int size = 10*1024*1024;
                                                        BufferedInputStream bf = new BufferedInputStream((InputStream)stream3, size);
                                                        byte[] all_data = new byte[size];
                                                        int b, j = 0;
                                                        while ((b = bf.read()) != -1) {
                                                            all_data[j] = (byte) b;
                                                            j++;
                                                        }
                                                        System.out.println("Have been read "+j+" bytes.");
                                                        byte[] data = new byte[j];
                                                        for (int i = 0; i < j; i++) {
                                                            data[i] = all_data[i];
                                                        }
                                                        all_data = null;
                                                        // Если файл был послан сохраним его
                                                        if (j > 0){
                                                            this.SaveFile(data, getFileName(uniFileName));
                                                            theDoc.setDoUrl(getFileName(uniFileName).toLowerCase());
                                                        }
                                                        // Сохранить объект в БД
                                                        mySession.getTransaction().begin();
                                                        mySession.saveOrUpdate(theDoc);
                                                        mySession.getTransaction().commit();
                                                    }
                                                }                                              
                                            }
                                        }                                               
                                    } else if (param1.equals(new String("act")) && value1.equals(new String("update"))) {
                                        if (iter.hasNext()) {
                                            FileItemStream item2 = iter.next();
                                            String param2 = item2.getFieldName();
                                            InputStream stream2 = item2.openStream();
                                            String value2 = Streams.asString(stream2, "UTF8");
                                            if (item2.isFormField() && param2.equals(new String("DoName"))) {
                                                if (iter.hasNext()) {
                                                    FileItemStream item3 = iter.next();
                                                    String param3 = item3.getFieldName();
                                                    InputStream stream3 = item3.openStream();
                                                    if (!item3.isFormField() && param3.equals(new String("File"))) {
                                                        System.out.println("We are HERE!!!");
                                                        // Загрузить объект из сессии
                                                        Documents theDoc = (Documents)session.getAttribute("theDoc");
                                                        theDoc.cutNulls();
                                                        // Поместить новые значения в объект
                                                        theDoc.setDoName(value2);
                                                        String uniFileName = new String(item3.getName());
                                                        // Сохранить файл
                                                        int size = 10*1024*1024;
                                                        BufferedInputStream bf = new BufferedInputStream((InputStream)stream3, size);
                                                        byte[] all_data = new byte[size];
                                                        int b, j = 0;
                                                        while ((b = bf.read()) != -1) {
                                                            all_data[j] = (byte) b;
                                                            j++;
                                                        }
                                                        System.out.println("Have been read "+j+" bytes.");
                                                        byte[] data = new byte[j];
                                                        for (int i = 0; i < j; i++) {
                                                            data[i] = all_data[i];
                                                        }
                                                        all_data = null;
                                                        // Если файл был послан
                                                        if (j > 0){
                                                            // Удалить старый файл
                                                            File f1 = new File(this.getServletContext().getRealPath("Resources/Documents/Files/"+theDoc.getDoUrl()));
                                                            f1.delete();
                                                            // Сохранить новый
                                                            this.SaveFile(data, getFileName(uniFileName));
                                                            theDoc.setDoUrl(getFileName(uniFileName).toLowerCase());
                                                        }
                                                        // Сохранить объект в БД
                                                        mySession.getTransaction().begin();
                                                        mySession.saveOrUpdate(theDoc);
                                                        mySession.getTransaction().commit();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            while (iter.hasNext()) {
                                FileItemStream item = iter.next();
                                String name = item.getFieldName();
                                InputStream stream = item.openStream();
                                if (item.isFormField()) {
                                    System.out.println("Form field " + name + " with value "
                                        + Streams.asString(stream)+ " detected.");
                                } else {
                                    String uniFileName = new String(item.getName());
                                    System.out.println("File field " + name + " with file name "
                                        + uniFileName + " detected.");
                                    // Process the input stream
                                    int size = 5*1024*1024;
                                    BufferedInputStream bf = new BufferedInputStream((InputStream)stream, size);
                                    byte[] all_data = new byte[size];
                                    int b, j = 0;
                                    while ((b = bf.read()) != -1) {
                                        all_data[j] = (byte) b;
                                        j++;
                                    }
                                    System.out.println("Have been read "+j+" bytes.");
                                    byte[] data = new byte[j];
                                    for (int i = 0; i < j; i++) {
                                        data[i] = all_data[i];
                                    }
                                    all_data = null;
                                    this.SaveFile(data, getFileName(uniFileName));
                                    System.out.println("Length of "+getFileName(item.getName())+" = "+data.length);
                                }
                            }
                        }catch(Exception exc){
                            System.out.println("Exception raised in add:"+exc.toString());
                        }
                        // Создать объект
                        /*Documents theDoc = new Documents();
                        theDoc.cutNulls();
                        // Поместить новые значения в объект
                        // theDoc.setDoName(request.getParameter("DoName"));
                        // Сохранить объект в БД
                        mySession.getTransaction().begin();
                        mySession.saveOrUpdate(theDoc);
                        mySession.getTransaction().commit();*/
                        response.sendRedirect("/Ralter/Resources/Documents?act=main");
                    } else {
                    // act = unknown
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Resources/Documents?act=main");
                        rd.forward(request, response);
                    }
                } catch(Exception e) {
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/Resources/Documents?act=main");
                    rd.forward(request, response);
                    System.out.println("Exception arised: "+e.toString());
                }

                System.out.println("Do Fullfilled");

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
// <editor-fold defaultstate="collapsed" desc="Методы для загрузки файла">
      // получаем имя файла, если файла нет, то пишем NO_FILE
        private String getFileName(String fullPath)
        {
           String filename  = "";
           fullPath.toLowerCase();
           int index, up_index;
           if (fullPath != null)
           {
               // ищем закрывающую кавычку после filename="....
               index = fullPath.lastIndexOf((int)'/');
               // символ "file.separator"
               up_index = fullPath.lastIndexOf((int)'\\');
               filename = fullPath.substring(Math.max(index, up_index) + 1);
           }else
               filename = "NO_FILE";
           return filename;
        }
        public void SaveFile (byte[] data, String fileName)
        {
           String fullPath = new String(this.getServletContext().getRealPath("Resources/Documents/Files") + System.getProperty("file.separator")+fileName.toLowerCase());
           if (fullPath != null)
           {
              File f = new File(fullPath);
              try
              {
               FileOutputStream fos = new FileOutputStream(f);
               fos.write(data);
               fos.close();
              }
              catch(Exception ex)
              {
               System.out.println(ex.toString());
              }
           }
        }
    // </editor-fold>

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