/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Connect;

/**
 *
 * @author Exat
 */
import java.sql.*;

public class ConNection {

    public Connection con;
    public String gs_error;
    public Statement stmt;

    public int Con_Oracle() {
        String url = "jdbc:oracle:thin:@1.3.5.101:1521:ORCL";
        String userName = "EXAT_P";
        String password = "EXAT_P";

        int result = 0;
        try {
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            con = DriverManager.getConnection(url, userName, password);
        } catch (Exception e) {
            result = 1;
            gs_error = e.getMessage();
        }
        return result;
    }

    public ResultSet doQuery(String ls_sql) {
        //Statement Oj_sm;
        ResultSet Oj_rs;

        try {
            this.stmt = con.createStatement();
            Oj_rs = stmt.executeQuery(ls_sql);

            return Oj_rs;
        } catch (Exception e) {
            gs_error = e.getMessage();
            //System.out.println("--> " + e.getMessage());
        }

        return null;
    }

    public boolean doExecute(String ls_sql) {
        try {
            Statement Oj_sm;
            Oj_sm = con.createStatement();
            Oj_sm.execute(ls_sql);
            gs_error = "error";
            Oj_sm.close();
            con.close();

            return true;
        } catch (Exception e) {
            gs_error = e.getMessage();
        }
        return false;
    }

    /*
     * เลือกเงื่อนไขด้วย where true = มีค่า false = ไม่มีค่า
     */
    public boolean selectOneVal(String cmd) throws SQLException {
        boolean returnResult = true;
        ResultSet rs = this.doQuery(cmd);

        if (rs.next()) {
            returnResult = true;
        } else {
            returnResult = false;
        }

        this.doClose();
        rs.close();

        return returnResult;
    }

    /**
     * ปิดการเชื่อมคต่อ connect,statement
     *
     * @param none
     * @return void
     */
    public void doClose() throws SQLException {
        this.stmt.close();
        this.con.close();
    }
}
