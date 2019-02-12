/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P114;

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
public class P114Controller extends HttpServlet {

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
            String d1 = request.getParameter("dt");
            String line = request.getParameter("line");

            String dateArr[] = d1.split("/");
            String yy = dateArr[2];
            String mm = dateArr[0];
            String dd = dateArr[1];
            P114Service s = new P114Service();
            if (line.equals("9")) {
                out.println("<br>==============REV file===============<br>");
                out.println(s.convertData("TL_09_ETC_OPN_REV_TOLL_" + yy + mm + dd + ".gw", line, dd + mm + yy));
                out.println("<br>==============TRF file===============<br>");
                out.println(s.convertData("TL_09_ETC_OPN_TRF_TOLL_" + yy + mm + dd + ".gw", line, dd + mm + yy));
            } else {
                out.println("<br>==============REV file===============<br>");
                out.println(s.convertData("TL_07_ETC_CLS_REV_TOLL_" + yy + mm + dd + ".gw", line, dd + mm + yy));
                out.println("<br>==============TRF file===============<br>");
                out.println(s.convertData("TL_07_ETC_CLS_TRF_TOLL_" + yy + mm + dd + ".gw", line, dd + mm + yy));
            }
        } catch (Exception e) {
            System.out.println("=============" + e);
        } finally {
            out.print("=========== end of process =========");
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
