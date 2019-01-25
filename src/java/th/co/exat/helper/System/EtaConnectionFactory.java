package th.co.exat.helper.System;
import java.sql.Connection;
import java.sql.DriverManager;

public class EtaConnectionFactory {

    //*/
    public static final String DATABASE_SERVER_IP = "172.20.1.9";
    public static final String DATABASE_SERVER_PORT = "1521";
    public static final String DATABASE_SID = "etanetdb";
    public static final String DATABASE_USER = "rvauser";
    public static final String DATABASE_PASS = "userrva_";
    //*/
    /*//*
    public static final String DATABASE_SERVER_IP = "1.3.5.101";
    public static final String DATABASE_SERVER_PORT = "1521";
    public static final String DATABASE_SID = "ORCL";
    public static final String DATABASE_USER = "rvauser";
    public static final String DATABASE_PASS = "rvauser";
    //*/

    public static Connection getConnection() throws Exception {
        Connection cnn = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            cnn = DriverManager.getConnection("jdbc:oracle:thin:@" + DATABASE_SERVER_IP + ":" + DATABASE_SERVER_PORT + ":" + DATABASE_SID, DATABASE_USER, DATABASE_PASS);

        } catch (Exception e) {
        }

        return cnn;
    }
}
