/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P115;

import Connect.Connector;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
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
public class P115Schedule extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected String processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            while (true) {
                System.out.println("Last shift | " + getLastShiftDate());
                System.out.println("Yesterday | " + getYesterday());
                if (!compareBeforeDate(getLastShiftDate(), getYesterday())) {
                    break;
                }
                String nextDate = getNextDate(getLastShiftDate());
                P115Service p115Service = new P115Service();
                if (!p115Service.scheduleConvertData(nextDate)) {
                    break;
                }
            }
            return "Success";
        }catch(Exception ex){
            ex.printStackTrace();
            return "Failed : "+ex.getMessage();
        }finally {
            out.close();
        }
    }
    
    private static String getNextDate(String currentDate) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = sdf.parse(currentDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return sdf.format(calendar.getTime());
    }

    private boolean compareBeforeDate(String lastDate, String yesterday) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date lastShiftDate = sdf.parse(lastDate);
        Date yesterday1 = sdf.parse(yesterday);
        if (lastShiftDate.before(yesterday1)) {
            return true;
        } else {
            return false;
        }
    }

    public static String getYesterday() {
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        calendar.set(Calendar.YEAR, (Calendar.getInstance().get(Calendar.YEAR) - 543));
        return sdf.format(calendar.getTime());
    }

    private String getLastShiftDate() {
        Connector connector = new Connect.Connector();
        try {
            connector.connect();
            String sql = "SELECT SHIFT_DATE FROM (";
            sql += "SELECT DISTINCT SHIFT_DATE FROM DMS_TEXT_BECL  ORDER BY SHIFT_DATE DESC )";
            sql += "WHERE ROWNUM = 1";
            ResultSet res = connector.executeQuery(sql);
            String date = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            while (res.next()) {
                date = sdf.format(res.getDate("SHIFT_DATE"));
                break;
            }
//            System.out.println("Last shift date " + date);
            return date;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        } finally {
            connector.close();
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
