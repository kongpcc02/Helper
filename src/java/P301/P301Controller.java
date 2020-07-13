/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P301;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author EXAT
 */
public class P301Controller extends HttpServlet {

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
            P301Service p301Service = new P301Service();

            String trxType = request.getParameter("trxType");
            if ("GET_MST_ETC".equals(trxType)) {
                String mstEtcType = p301Service.getEtcServiceType();
                out.print(mstEtcType);
            }
            if ("GET_MST_LINE".equals(trxType)) {
                String mstLine = p301Service.getLine();
                out.print(mstLine);
            }
            if ("EXECUTE".equals(trxType)) {
                String lineCode = request.getParameter("lineCode");
                double minMoney = Double.parseDouble(request.getParameter("minMoney"));
                String serviceId = request.getParameter("serviceId");
                String dateFrom = request.getParameter("dateFrom");
                String dateTo = request.getParameter("dateTo");
                String userId = p301Service.getUserId((String) request.getSession().getAttribute("ssion_userid"));
                if ("null".equals(userId) || userId == null) {
                    throw new Exception("กรุณาเข้าสู่ระบบใหม่ด้วยครับ");
                }
                p301Service.executeUpdateVat(lineCode, dateFrom, dateTo, serviceId, minMoney, userId);
                out.println("ดำเนินการแก้ไขเรียบร้อยแล้ว");
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
