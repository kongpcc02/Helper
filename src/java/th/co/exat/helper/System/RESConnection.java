/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.exat.helper.System;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Siridet
 */
public class RESConnection {

    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        String url = "jdbc:mysql://172.20.1.109/res";
        Connection conn = DriverManager.getConnection(url, "cas1", "P@ssw0rd123");

        return conn;

    }
}
