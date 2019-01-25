/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package P105;

import P102.EasyPassUsing;
import com.Helper.Helper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author phumipat_suk
 */
public class P105ResultDisplay extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
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
            //MM/DD/YYYY
            String fDate = request.getParameter("fDate");
            String ln = request.getParameter("ln");
            String stt = request.getParameter("stt");

            //YYYYMMDD
            String[] fDateSplit = fDate.split("/");

            //YYYYMMDD
            String fDateFtp = fDateSplit[2] + fDateSplit[0] + fDateSplit[1];

            //DD/MM/YYYY
            String fDateDisplay = fDateSplit[1] + "/" + fDateSplit[0] + "/" + fDateSplit[2];

            String fname = "TL_0" + ln + "_ETC_OPN_TRF_" + fDateFtp + ".txt";

            EasyPassUsing easyPassUsing = new EasyPassUsing();

            BufferedReader br = new BufferedReader(new InputStreamReader(easyPassUsing.retrieveFromFTP("import/DMS", fname)));

            String strLine = "";
            String[] strLineSplit;

            int trfqty = 0;

            while ((strLine = br.readLine()) != null) {
                System.out.println(strLine);
                strLineSplit = strLine.split("\t");
                if (strLineSplit[0].equals(stt) && strLineSplit[4].equals("01")) {
                    trfqty += Integer.parseInt(strLineSplit[9]);
                }
            }

            out.print("ด่าน " + stt + " มีปริมาณรถ ณ วันที่ " + fDateDisplay + " จำนวน " + trfqty + " คัน");
            out.close();

        } catch (Exception e) {
            out.print("เกิดข้อผิดพลาด : " + e);
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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
