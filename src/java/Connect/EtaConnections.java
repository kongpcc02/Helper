/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Connect;

import java.sql.*;

/**
 *
 * @author Administrator
 */
public class EtaConnections {

    public Connection cnn;
    String gs_error;
    Statement stmt;
    ResultSet Oj_rs;
    public static final String DATABASE_SERVER_IP = "172.20.1.9";
    public static final String DATABASE_SERVER_PORT = "1521";
    public static final String DATABASE_SID = "etanetdb";
    public static final String DATABASE_USER = "rvauser";
    public static final String DATABASE_PASS = "userrva_";

    public void ConnectDB() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            this.cnn = DriverManager.getConnection("jdbc:oracle:thin:@" + DATABASE_SERVER_IP + ":" + DATABASE_SERVER_PORT + ":" + DATABASE_SID, DATABASE_USER, DATABASE_PASS);

        } catch (Exception e) {
            gs_error = e.getMessage();
        }
    }

    public ResultSet doQuery(String ls_sql) {


        try {
            this.stmt = cnn.createStatement();
            Oj_rs = stmt.executeQuery(ls_sql);

            return Oj_rs;
        } catch (Exception e) {
            gs_error = e.getMessage();
        }

        return null;
    }

    public boolean doExecute(String ls_sql) {
        try {
            Statement Oj_sm;
            Oj_sm = cnn.createStatement();
            Oj_sm.execute(ls_sql);
            gs_error = "error";
            Oj_sm.close();
            cnn.close();

            return true;
        } catch (Exception e) {
            gs_error = e.getMessage();
        }
        return false;
    }

    public void CloseDB() throws SQLException {
        this.cnn.close();
    }

    /**
     * ปิดการเชื่อมคต่อ connect,statement
     *
     * @param none
     * @return void
     */
    public void doClose() throws SQLException {
        this.Oj_rs.close();
        this.stmt.close();
        this.cnn.close();
    }
}
