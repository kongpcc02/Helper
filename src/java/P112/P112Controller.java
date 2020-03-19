/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P112;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author siridet_suk
 */
public class P112Controller extends HttpServlet {

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
            out.println("<br>==============start===============");
            String fDate = request.getParameter("fDate");
            String line = request.getParameter("line");
            String dateArr[] = fDate.split("/");
            String dateFind = dateArr[2] + dateArr[0] + dateArr[1];
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            //Easy pass Discount 5 percent
            Date startDate, endDate, currentDate;
            startDate = sdf.parse("20200104");
            endDate = sdf.parse("20200203");
            currentDate = sdf.parse(dateFind);
            boolean isPromotion = (currentDate.after(startDate) && currentDate.before(endDate)) || currentDate.equals(startDate) || currentDate.equals(endDate) ? true : false;

//            String dateArr[] = d1.split("/");
            String yy = dateArr[2];
            String mm = dateArr[0];
            String dd = dateArr[1];

            String txtTrx = "TL_" + line + "_ETC_CLS_TRF_TOLL_" + dateFind + ".gw";
            String txtRev = "TL_" + line + "_ETC_CLS_REV_TOLL_" + dateFind + ".gw";
            String txtTrfPro = "TL_" + line + "_ETC_CLS_TRF_TOLL_" + dateFind + ".pro";
            String txtRevPro = "TL_" + line + "_ETC_CLS_REV_TOLL_" + dateFind + ".pro";
            P112Service p = new P112Service();
            out.println("<br>===create trf file===");
            out.println(p.importCyber(txtTrx, dd + mm + yy));
//            if (isPromotion) {
            out.println(p.importCyber(txtTrfPro, dd + mm + yy, true));
//            }
            out.println("<br>===create rev file===");
            out.println(p.importCyber(txtRev, dd + mm + yy));
//            if (isPromotion) {
            out.println(p.importCyber(txtRevPro, dd + mm + yy, true));
//            }
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
