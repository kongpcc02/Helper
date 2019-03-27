/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P102_1;

import Connect.Connector;
import P102.ETCRev;
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
public class P102_1Schedule extends HttpServlet {

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

        System.out.println("Start P102");
        Connector connect = new Connect.Connector();
        try {
            connect.connect();
            while (true) {
                if (!compareBeforeDate(getLastP102Date(), getYesterday())) {
                    break;
                }
                String dateFind = getNextDate(getLastP102Date());
                ETCRev etcRev = new ETCRev();
                etcRev.setfRMTName("ET_02_ETC_RMT_" + dateFind + ".txt");
                etcRev.setfTRXName("ET_02_ETC_TRX_" + dateFind + ".txt");
                if (etcRev.hasFile(etcRev.getfRMTName()) && etcRev.hasFile(etcRev.getfTRXName())) {
                    System.out.println("Has file " + etcRev.getfRMTName() + " and " + etcRev.getfTRXName());
                    // อ่าน และสร้างไฟล์
                    etcRev.readAndCreateFile(etcRev.getfRMTName());
                    etcRev.readAndCreateFile(etcRev.getfTRXName());
                    // อัพโหลด
//                etcRev.uploadToFTP(etcRev.localPath, etcRev.getfRMTName());
//                etcRev.uploadToFTP(etcRev.localPath, etcRev.getfTRXName());
//                    String txtTrx = "TL_02_ETC_OPN_TRF_" + dateFind + ".txt";
//                    String txtRev = "TL_02_ETC_OPN_REV_" + dateFind + ".txt";
//                    P102_1Service sv = new P102_1Service();
//                    sv.readAndCreateFile(txtTrx);
//                    sv.readAndCreateFile(txtRev);
                    String sql = "INSERT INTO P102_TMP VALUES(to_date(\'" + dateFind + "\',\'yyyyMMdd\'))";
                    connect.addBatch(sql);
                    connect.executeBatch();
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
//            throw e;
        } finally {
            connect.close();
        }
        System.out.println("End P102");
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

    private String getLastP102Date() {
        Connector connector = new Connect.Connector();
        try {
            connector.connect();
            String sql = "SELECT P102_DATE FROM (SELECT DISTINCT P102_DATE FROM P102_TMP ORDER BY P102_DATE DESC) WHERE ROWNUM = 1 ";
            ResultSet res = connector.executeQuery(sql);
            String date = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            while (res.next()) {
                date = sdf.format(res.getDate("P102_DATE"));
                break;
            }
            return date;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        } finally {
            connector.close();
        }
    }

    public static String getYesterday() {
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        //calendar.set(Calendar.YEAR, (Calendar.getInstance().get(Calendar.YEAR) - 543));
        return sdf.format(calendar.getTime());
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
