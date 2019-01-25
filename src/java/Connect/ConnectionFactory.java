package Connect;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

    public static Connection getConnection() throws Exception {
        Connection con = null;
        String url = "jdbc:oracle:thin:@1.3.5.101:1521:ORCL";
        String userName = "EXAT_P";
        String password = "EXAT_P";

        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        con = DriverManager.getConnection(url, userName, password);


        return con;
    }
}
