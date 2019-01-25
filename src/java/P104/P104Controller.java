package P104;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class P104Controller extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String[] d = request.getParameter("d").split("/");
        String date = d[1] + d[0] + d[2];

        try {
            P104Service.empty(date);
            P104Service.create(P104Service.read(date, "4"));
            P104Service.create(P104Service.read(date, "5"));
            P104Service.create(P104Service.read(date, "6"));

            out.println("<b>นำเข้าเรียบร้อย วันที่ที่นำเข้าแล้วในเดือนนี้<br><br>" + P104Service.getDate(d[0], d[2]) + "</b>");
        } catch (Exception ex) {
            out.println("ระบบมีปัญหา ssssไม่สามารถนำเข้าได้" + ex.getMessage());
        }

    }

    public static void main(String[] args) {
        String[] d = "01/26/2012".split("/");
        String date = d[1] + d[0] + d[2];
        System.out.println(date);
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
