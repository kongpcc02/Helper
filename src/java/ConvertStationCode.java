
import Connect.Connector;
import P111.TRXModel;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Arrays;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author siridet_suk
 */
public class ConvertStationCode {

    public static void main(String[] args) throws IOException, Exception {
        Connector c = new Connect.Connector("172.20.1.9", "rvauser", "userrva_", "etanetdb", "1521");
        // Connector c = new Connect.Connector("1.3.4.2", "rvauser", "userrva_", "etanetdb", "1521");
        c.connect();
        c.setAutoCommit(false);
        ResultSet rs = c.executeQuery("SELECT table_name TBL  FROM all_tab_columns "
                + "WHERE table_name LIKE 'RVA_MST_CHRG_OPN'   ");
        //  + "WHERE table_name LIKE 'RVA_%' AND column_name in('POS_ID') and table_name NOT LIKE '%MST%'");
        // ResultSet rs2 = null;
        while (rs.next()) {
            String tbl = rs.getString("TBL");
            System.out.println("-" + tbl);
            /*   c.addBatch("UPDATE " + tbl + " SET POS_ID=163 WHERE POS_ID=56");
             c.addBatch("UPDATE " + tbl + " SET POS_ID=164 WHERE POS_ID=54");
             c.addBatch("UPDATE " + tbl + " SET POS_ID=165 WHERE POS_ID=110");
             c.addBatch("UPDATE " + tbl + " SET POS_ID=166 WHERE POS_ID=111");
             c.addBatch("UPDATE " + tbl + " SET POS_ID=167 WHERE POS_ID=112");
             c.addBatch("UPDATE " + tbl + " SET POS_ID=168 WHERE POS_ID=60");
             c.addBatch("UPDATE " + tbl + " SET POS_ID=55 WHERE POS_ID=113");
             c.addBatch("UPDATE " + tbl + " SET POS_ID=160 WHERE POS_ID=57");
             c.addBatch("UPDATE " + tbl + " SET POS_ID=161 WHERE POS_ID=58");*/
            // String sql = "SELECT COUNT(*) as c FROM " + tbl + " WHERE station_code in('248','249','246','247','230','262','263','264','251','265','266')";
            //rs2 = c.executeQuery(sql);
            // rs2.next();
            //System.out.println("-> " + rs2.getString("c"));
            c.addBatch("UPDATE " + tbl + " SET STATION_CODE='216' WHERE STATION_CODE='246'");
            c.addBatch("UPDATE " + tbl + " SET STATION_CODE='217' WHERE STATION_CODE='247'");
            c.addBatch("UPDATE " + tbl + " SET STATION_CODE='241' WHERE STATION_CODE='230'");
            c.addBatch("UPDATE " + tbl + " SET STATION_CODE='242' WHERE STATION_CODE='262'");
            c.addBatch("UPDATE " + tbl + " SET STATION_CODE='243' WHERE STATION_CODE='263'");
            c.addBatch("UPDATE " + tbl + " SET STATION_CODE='244' WHERE STATION_CODE='264'");
            c.addBatch("UPDATE " + tbl + " SET STATION_CODE='245' WHERE STATION_CODE='251'");
            c.addBatch("UPDATE " + tbl + " SET STATION_CODE='246' WHERE STATION_CODE='265'");
            c.addBatch("UPDATE " + tbl + " SET STATION_CODE='247' WHERE STATION_CODE='266'");
            c.addBatch("UPDATE " + tbl + " SET STATION_CODE='213' WHERE STATION_CODE='248'");
            c.addBatch("UPDATE " + tbl + " SET STATION_CODE='214' WHERE STATION_CODE='249'");
            System.out.println(Arrays.toString(c.executeBatch()));
            System.out.println(tbl + " success==============================");
            c.commit();
        }

        rs.close();
        //  rs2.close();
        c.close();
    }
}
