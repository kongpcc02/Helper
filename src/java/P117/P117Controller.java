/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P117;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author chonpisit_klo
 */
public class P117Controller extends HttpServlet {

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
            String startDate = request.getParameter("dateFrom");
            String endDate = request.getParameter("dateTo");
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            SimpleDateFormat sdfFile = new SimpleDateFormat("yyyyMMdd");
            P117Service p117Service = new P117Service();
            Date start = sdf.parse(startDate);
            Date end = sdf.parse(endDate);
            String version = request.getParameter("version");
            if (!p117Service.validateDate(start, end)) {
                out.println("<h2><font color='red'>กรุณาระบุช่วงวันที่ให้ถูกต้อง</font></h2>");
                return;
            }
            String pathName = request.getRealPath("/export/SAP/");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(start);
            p117Service.checkPath(pathName);
            String fileDate = sdfFile.format(start);
            if (start.equals(end)) {
                out.println(p117Service.createFile(pathName, fileDate, version));
            } else {
                while (start.before(end) || start.equals(end)) {
                    fileDate = sdfFile.format(start);
                    out.println(p117Service.createFile(pathName, fileDate, version));
                    calendar.add(Calendar.DATE, 1);
                    start = calendar.getTime();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            out.println(ex);
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
