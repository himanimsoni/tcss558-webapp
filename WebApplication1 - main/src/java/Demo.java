/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Himani Soni
 */
public class Demo extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //String name;
        //name = request.getParameter("name");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Demo</title>");            
            out.println("</head>");
            out.println("<body>");
            //out.println("<h1>Servlet Demo at " + name + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
                try (PrintWriter out = response.getWriter()) {
                    Class.forName("com.mysql.jdbc.Driver");
                    String connectionURL;
                    connectionURL = "jdbc:mysql://node207144-env-0889003.j.layershift.co.uk/demoDBJelastic";
                    
                    try {
                        Connection conn;
                        conn = DriverManager.getConnection(connectionURL, "tcss558","ORamb85zNe");
                        //out.print("Connect successfully ! ");
                
            //processRequest(request, response);
                        if (request.getParameter("Register") != null){
                            System.out.println("register called");
                            String name = request.getParameter("name");
                            String email = request.getParameter("email");
                            String phone =  request.getParameter("phone");
                            String sln = request.getParameter("sln");
                            
                            PreparedStatement ps = conn.prepareStatement("INSERT INTO TABLEDEMO(name, email, phone, sln) VALUES(?,?,?,?)");
                            ps.setString(1, name);
                            ps.setString(2, email);
                            ps.setString(3, phone);
                            ps.setString(4, sln);
                            System.out.println(ps);
                            synchronized (this)
                            {
                                int i = ps.executeUpdate();
                                if (i <= 0) {
                                    out.print("You are not registered...");
                                } else {
                                    out.print("You are successfully registered...");
                                }
                            }
                        } else if (request.getParameter("Update") != null){
                            System.out.println("update called");
                            String name = request.getParameter("name");
                            String email = request.getParameter("email");
                            String phone =  request.getParameter("phone");
                            
                            PreparedStatement ps = conn.prepareStatement("UPDATE TABLEDEMO SET email=?,phone=? WHERE name=?");
                            ps.setString(1, email);
                            ps.setString(2, phone);
                            ps.setString(3, name);
                            System.out.println(ps);
                            synchronized (this)
                            {
                                int i = ps.executeUpdate();
                                if (i <= 0) {
                                    out.print("Record not updated...");
                                } else {
                                    out.print("Record updated successfully...");
                                }
                            }
                            
                        } else if (request.getParameter("Remove") !=null){
                            System.out.println("remove called");
                            String name = request.getParameter("name");
                            PreparedStatement ps = conn.prepareStatement("DELETE FROM TABLEDEMO WHERE name=?");
                            ps.setString(1, name);
                            
                            synchronized (this)
                            {
                                int i = ps.executeUpdate();
                                if (i <= 0) {
                                    out.print("Not removed...");
                                } else {
                                    out.print("You have successfully been removed...");
                                }
                            }
                        }
                    
                    
                    conn.close();
                
                } catch (SQLException ex) {
                    System.out.println("Connect failed ! ");
                    System.out.println(ex);
            }
            out.close();
        
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
 
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
