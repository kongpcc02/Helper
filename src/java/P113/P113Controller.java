/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P113;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author siridet_suk
 */
public class P113Controller extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        try {
            out.println("<br>==============start===============<br>");
            String d1 = request.getParameter("d1");
            String d2 = request.getParameter("d2");
            String line = request.getParameter("line");
            String dateArr1[] = d1.split("/");
            String dateArr2[] = d2.split("/");
            String yy1 = dateArr1[2];
            String mm1 = dateArr1[0];
            String dd1 = dateArr1[1];

            String yy2 = dateArr2[2];
            String mm2 = dateArr2[0];
            String dd2 = dateArr2[1];

            P113Service p = new P113Service();
            if (line.equals("1") || line.equals("2") || line.equals("3") || line.equals("5") || line.equals("9") || line.equals("8")) {
                out.println(p.updateOpn(dd1 + mm1 + yy1, dd2 + mm2 + yy2, line));
                out.print("==== update opn system===");
            } else {
                out.print("==== update cls system===");
                out.println(p.updateCls(dd1 + mm1 + yy1, dd2 + mm2 + yy2, line));
            }
            out.println("<br>==============success end.===============");
        } catch (Exception e) {
            out.println("<br> Error ==> " + e);
        } finally {
            out.close();
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

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
