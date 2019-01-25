/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package P101;

import Connect.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Siridet
 */
public class PTFile {

    public static List getFileNames() throws Exception {
        List list = new ArrayList();

        Connection con = ConnectionFactory.getConnection();
        Statement stmt = con.createStatement();
        String sql = "select * from PORTABLE_FILE";
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            list.add(rs.getString("FILE_NAME"));
        }
        stmt.close();
        rs.close();
        con.close();

        return list;
    }

    public static void create(String fname) throws Exception {
        Connection con = ConnectionFactory.getConnection();
        Statement stmt = con.createStatement();
        String sql = "insert into PORTABLE_FILE values('" + fname + "')";
        stmt.executeQuery(sql);
        stmt.close();
        con.close();
    }

    public static void main(String[] args) throws Exception {
        List list = getFileNames();

        System.out.println(list.indexOf("bkt2x1_20110522.csv"));
    }
}
