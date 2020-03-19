/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P119;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author chonpisit_klo
 */
public class P119Controller extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            String reqStartDate = request.getParameter("dateFrom");
            String reqEndDate = request.getParameter("dateTo");
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
            SimpleDateFormat sdfCyber = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
            P119Service p119Service = new P119Service();
            String fileNameTrf = "TL_01_ETC_OPN_TRF_TOLL_";
            String fileNameRev = "TL_01_ETC_OPN_REV_TOLL_";
            if (reqStartDate.equals(reqEndDate)) {
                out.println(p119Service.importCyber(fileNameTrf + "" + sdfCyber.format(sdf.parse(reqStartDate)) + ".pro"));
                out.println(p119Service.importCyber(fileNameRev + "" + sdfCyber.format(sdf.parse(reqStartDate)) + ".pro"));
            }
            if (!reqStartDate.equals(reqEndDate)) {
                Calendar calendar = Calendar.getInstance();
                Date startDate, endDate, currentDate;
                startDate = sdf.parse(reqStartDate);
                endDate = sdf.parse(reqEndDate);
                calendar.setTime(startDate);
                currentDate = calendar.getTime();
                while (currentDate.before(endDate) || currentDate.equals(endDate)) {
//                    System.out.println(currentDate);
                    out.println(p119Service.importCyber(fileNameTrf + "" + sdfCyber.format(currentDate) + ".pro"));
                    out.println(p119Service.importCyber(fileNameRev + "" + sdfCyber.format(currentDate) + ".pro"));
                    calendar.add(Calendar.DATE, 1);
                    currentDate = calendar.getTime();
                }
            }

        } catch (Exception ex) {
            out.println("<h1 style=\"color: red;\">Error : " + ex.getMessage() + "</h1>");
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
