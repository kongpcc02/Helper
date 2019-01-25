package P101;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class P101Controller extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String fDate = request.getParameter("d");
            String stt = request.getParameter("stt");
            System.out.println("stt=>" + stt);
            String dateArr[] = fDate.split("/");
            String dateFind = dateArr[2] + dateArr[0] + dateArr[1];

            System.out.println("=====================");

            P101Service serv = new P101Service();
            serv.convertData("TL_06_TRF_CLS_" + dateFind, stt);
            serv.convertData("TL_06_REV_CLS_" + dateFind, stt);

//            serv.copyFile("TL_06_RMT_" + dateFind + ".mnl");
//            serv.copyFile("TL_06_UAP_CLS_" + dateFind + ".mnl");
//            serv.copyFile("TL_06_SUM_CLS_" + dateFind + ".mnl");
            out.print("============นำเข้าเรียบร้อย==============");
        } catch (Exception e) {
            System.out.println("=======================================");
            out.println(e);
            System.out.println("=======================================");

        } finally {
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
