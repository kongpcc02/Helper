package P201;

import P107.*;
import Connect.Connector;

/**
 *
 * @author siridet_suk
 */
public class P201Service {

    public StringBuffer update(String d1, String d2, int line) {
        StringBuffer str = new StringBuffer();
        Connector c = new Connect.Connector("172.20.1.9", "rvauser", "userrva_", "etanetdb", "1521");
        String sql = null;
        String sql2 = null;
        try {
            c.connect();
            if (line == 1 || line == 2 || line == 3 || line == 5 || line == 9 || line == 8) {
                sql = " delete rva_int_etc_opn_trf where  SUBSTR(STATION_CODE,0,1)='" + line + "' and TRX_DATE BETWEEN  to_Date('" + d1 + "','dd/mm/yyyy') and  to_Date('" + d2 + "','dd/mm/yyyy') ";
                sql2 = " delete rva_int_etc_opn_rev where  SUBSTR(STATION_CODE,0,1)='" + line + "' and TRX_DATE BETWEEN  to_Date('" + d1 + "','dd/mm/yyyy') and  to_Date('" + d2 + "','dd/mm/yyyy') ";
            } else {
                sql = " delete rva_int_etc_cls_trf where  SUBSTR(ext_stt_code,0,1)='" + line + "' and TRX_DATE BETWEEN  to_Date('" + d1 + "','dd/mm/yyyy') and  to_Date('" + d2 + "','dd/mm/yyyy') ";
                sql2 = " delete rva_int_etc_cls_rev where  SUBSTR(ext_stt_code,0,1)='" + line + "' and TRX_DATE BETWEEN  to_Date('" + d1 + "','dd/mm/yyyy') and  to_Date('" + d2 + "','dd/mm/yyyy') ";

            }
            System.out.println(sql);
            System.out.println(sql2);
            c.executeUpdate(sql);
            c.executeUpdate(sql2);
            str.append("=====update complete=====");
        } catch (Exception ex) {
            str.append("ERROR==").append(ex);
        } finally {
            c.close();
            return str;
        }
    }

    public static void main(String[] args) {
        P201Service s = new P201Service();

    }
}
