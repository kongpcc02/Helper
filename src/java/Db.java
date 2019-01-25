/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class Db {

    private String host;
    private String sid;
    private String username;
    private String password;

    public Db(String host, String sid, String username, String password) {
        this.host = host;
        this.sid = sid;
        this.username = username;
        this.password = password;
    }

    public void insert(List<String> datalist, String tableName) {
        try {
            Connection conn;
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@" + host
                    + ":1521:" + sid, username, password);

            String sql = "INSERT INTO " + tableName + " "
                    + "VALUES "
                    + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            for (int i = 0; i < datalist.size(); i++) {
                String strList = datalist.get(i);
                String arrList[] = strList.split(",");

                if (arrList.length == 14) {
                    pstmt.setString(1, arrList[0]);             //c1
                    pstmt.setString(2, arrList[1]);             //c2
                    pstmt.setString(3, arrList[2]);             //c2
                    pstmt.setString(4, arrList[3]);             //c2
                    pstmt.setString(5, arrList[4]);             //c2
                    pstmt.setString(6, arrList[5]);             //c2
                    pstmt.setString(7, arrList[6]);             //c2
                    pstmt.setString(8, arrList[7]);             //c2
                    pstmt.setString(9, arrList[8]);             //c2
                    pstmt.setString(10, arrList[9]);             //c2
                    pstmt.setString(11, arrList[10]);             //c2
                    pstmt.setString(12, arrList[11]);             //c2
                    pstmt.setString(13, arrList[12]);             //c2
                    pstmt.setString(14, arrList[13]);             //c2

                    pstmt.addBatch();
                }
                //System.out.println(arrList[0]);
            }

            pstmt.executeBatch();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.print(e);
        }
    }
}
