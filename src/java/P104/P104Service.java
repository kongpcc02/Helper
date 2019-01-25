/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package P104;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import th.co.exat.helper.System.EtaConnectionFactory;
import th.co.exat.helper.System.RESConnection;

public class P104Service {

    public static void create(List<RES_TRX_TRX> trx) throws Exception {
        Connection c = EtaConnectionFactory.getConnection();
        Statement stmt = c.createStatement();

        for (RES_TRX_TRX res : trx) {
            stmt.addBatch("insert into rva_trx_keyin_summary (station_code,trx_date,num_type1,num_type2,num_type3,rev_type1,rev_type2,rev_type3,"
                    + " COM_REV_TYPE1, COM_REV_TYPE2,  COM_REV_TYPE3, VAT_OWNER) "
                    + "values(" + res.getSecId()
                    + ",to_date('"
                    + res.getDate()
                    + "','dd/mm/yyyy'),"
                    + res.getTrf1() + ","
                    + res.getTrf2() + ","
                    + res.getTrf3() + ","
                    + res.getRev1() + ","
                    + res.getRev2() + ","
                    + res.getRev3() + ","
                    + res.getRev1() + ","
                    + res.getRev2() + ","
                    + res.getRev3() + ","
                    + "'C'"
                    + ")");
        }

        stmt.executeBatch();
        stmt.close();
        c.close();
    }

    static void empty(String d) throws Exception {
        Connection c = EtaConnectionFactory.getConnection();
        StringBuffer sql = new StringBuffer("delete  rva_trx_keyin_summary where trx_date = to_date('" + d + "','ddmmyyyy')");

        Statement s = c.createStatement();
        s.execute(sql.toString());
        c.close();
        s.close();
    }

    static List<RES_TRX_TRX> read(String ddmmyyyy, String sec) throws Exception {
        List<RES_TRX_TRX> z = new ArrayList<RES_TRX_TRX>();
        String date = (new StringBuilder()).append(ddmmyyyy.substring(4, 8)).append("-").append(ddmmyyyy.substring(2, 4)).append("-").append(ddmmyyyy.substring(0, 2)).toString();

        Connection c = RESConnection.getConnection();
        Statement s = c.createStatement();
        StringBuilder sql = new StringBuilder("SELECT  trx.station_id AS stt, SUM( trx_trf_type1 ) AS trx1, SUM( trx_trf_type2 ) AS trx2, SUM( trx_trf_type3 ) AS trx3, SUM( (");
        sql.append(" trx_rev_type1 * trx_res_becl /100) ) AS rev1, SUM( (trx_rev_type2 * trx_res_becl /100) ) AS rev2, SUM( (trx_rev_type3 * trx_res_becl /100) ) AS rev3");
        sql.append(" FROM  `res_trx_trx` trx INNER JOIN res_mst_station stt ON stt.station_id = trx.station_id");
        sql.append(" WHERE sec_id =" + sec + "");
        sql.append(" AND trx_date =  '" + date + "' AND trx_system =  'MTC' GROUP BY trx_Date, trx.station_id");

        ResultSet r = s.executeQuery(sql.toString());

        while (r.next()) {
            RES_TRX_TRX trx = new RES_TRX_TRX();

            /* if (r.getString("stt").equals("402")) {
             trx.setSecId("262");
             } else if (r.getString("stt").equals("403")) {
             trx.setSecId("263");
             } else if (r.getString("stt").equals("404")) {
             trx.setSecId("264");
             } else if (r.getString("stt").equals("405")) {
             trx.setSecId("251");
             } else if (r.getString("stt").equals("406")) {
             trx.setSecId("265");
             } else if (r.getString("stt").equals("408")) {
             trx.setSecId("266");
             } else {
             trx.setSecId(r.getString("stt"));
             }*/
            if (r.getString("stt").equals("402")) {
                trx.setSecId("242");
            } else if (r.getString("stt").equals("403")) {
                trx.setSecId("243");
            } else if (r.getString("stt").equals("404")) {
                trx.setSecId("244");
            } else if (r.getString("stt").equals("405")) {
                trx.setSecId("245");
            } else if (r.getString("stt").equals("406")) {
                trx.setSecId("246");
            } else if (r.getString("stt").equals("408")) {
                trx.setSecId("247");
            } else {
                trx.setSecId(r.getString("stt"));
            }
            trx.setDate(ddmmyyyy);
            trx.setTrf1(r.getInt("trx1"));
            trx.setTrf2(r.getInt("trx2"));
            trx.setTrf3(r.getInt("trx3"));
            trx.setRev1(r.getInt("rev1"));
            trx.setRev2(r.getInt("rev2"));
            trx.setRev3(r.getInt("rev3"));
            z.add(trx);
        }
        c.close();
        r.close();
        s.close();

        return z;
    }

    static String getDate(String m, String y) throws Exception {
        StringBuilder returnStr = new StringBuilder();
        Connection c = EtaConnectionFactory.getConnection();
        Statement stmt = c.createStatement();
        String sql = "SELECT TO_CHAR (trx_date, 'dd-mm-yyyy') AS A, wm_concat( DISTINCT  SUBSTR( station_code, 0, 1)) AS B  from rva_trx_keyin_summary   where  to_char(trx_date,'mm')='" + m + "' and to_char(trx_date,'yyyy')='" + y + "'  group by TO_CHAR (trx_date, 'dd-mm-yyyy')  ";

        stmt.executeQuery(sql);
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            returnStr.append(rs.getString("a") + " | " + rs.getString("b") + "<br>");
        }
        c.close();
        stmt.close();

        return returnStr.toString();
    }

    public static void main(String[] args) throws Exception {
        empty("01072013");
        List<RES_TRX_TRX> d = read("01072013", "4");

        create(d);
        List<RES_TRX_TRX> ds = read("01072013", "5");

        create(ds);
    }
}
