/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P111;

import Connect.Connector;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author siridet_suk
 */
public class P111Controller extends HttpServlet {

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
            String fDate = request.getParameter("dt");
            String line = request.getParameter("line");
            if (fDate.equals("") || line.equals("")) {
                out.print("ใส่ข้อมูลไม่ครบ !!");
            } else {
                P111Service p111Serv = new P111Service();
                out.print(p111Serv.importCyber(fDate, line));
                if (line.equals("1") || line.equals("2") || line.equals("3") || line.equals("4") || line.equals("5") || line.equals("6") || line.equals("8")) {
                    out.print(p111Serv.importGateWayEXAT(fDate, line));
                    List<TRXModel> d = p111Serv.retriveDataExat(fDate, line);
                    out.print("<br>-- retrive data");
                    out.print("<br><hr><table width=50% ><tr><td><b>STT</b></td><td><b>CYBER</b></td><td><b>GW</b></td></tr>");

                    for (TRXModel d1 : d) {
                        out.print("<tr><td>" + d1.getStt() + " </td><td>" + d1.getRevCyber() + "</td><td>" + d1.getRevGateway() + "</td></tr>");
                    }
                    out.print("</table>");
                } else {
                    out.print(p111Serv.importGateWayDOH(fDate, line));
                    List<TRXModel> d = p111Serv.retriveDataDoh(fDate, line);
                    out.print("<br>-- retrive data");
                    out.print("<br><hr><table width=50% ><tr><td><b>STT</b></td><td><b>CYBER</b></td><td><b>GW</b></td></tr>");

                    for (TRXModel d1 : d) {
                        out.print("<tr><td>" + d1.getStt() + " </td><td>" + d1.getRevCyber() + "</td><td>" + d1.getRevGateway() + "</td></tr>");
                    }
                    out.print("</table>");
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    public static void main(String[] args) {
        Connector c = new Connect.Connector();
        try {
            c.connect();
            c.close();
        } catch (Exception ex) {
            System.out.println(ex);
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
        // System.out.println("sdsds");
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
