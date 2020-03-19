/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package P105;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import th.co.exat.helper.System.EtaConnectionFactory;

/**
 *
 * @author Siridet
 */
public class P105 {

    public static void delete(String d, String stt) throws Exception {
        Connection c = EtaConnectionFactory.getConnection();
        Statement stmt = c.createStatement();

        String sqlSttType = "select DISTINCT LINE_TYPE\n"
                + "from rva_mst_line line\n"
                + "LEFT JOIN RVA_MST_LINE_SCT sct on SCT.line_code = line.LINE_CODE\n"
                + "LEFT JOIN RVA_MST_SCT_STT stt on stt.sector_code = sct.sector_code\n"
                + "where stt.active_status = 'A'\n"
                + "and sct.ACTIVE_STATUS = 'A'"
                + "and stt.station_code  = '" + stt + "'";
        ResultSet resultSttType = stmt.executeQuery(sqlSttType);
        if (!resultSttType.next()) {
            return;
        }
//        StringBuffer sqlTrf = new StringBuffer(""), sqlRev = new StringBuffer("");
        if (resultSttType.getString("LINE_TYPE").equals("O")) {
            StringBuffer sqlTrf = new StringBuffer("delete rva_int_etc_opn_trf ");
            sqlTrf.append(" where trx_date = to_date('" + d + "','dd/mm/yyyy')");
            sqlTrf.append(" and station_code = '" + stt + "'");

            StringBuffer sqlRev = new StringBuffer("delete rva_int_etc_opn_rev ");
            sqlRev.append(" where trx_date = to_date('" + d + "','dd/mm/yyyy')");
            sqlRev.append(" and station_code = '" + stt + "'");
            stmt.executeUpdate(sqlTrf.toString());
            stmt.executeUpdate(sqlRev.toString());
        } else if (resultSttType.getString("LINE_TYPE").equals("C")) {
            StringBuffer sqlEtcClsTrf = new StringBuffer("delete RVA_INT_ETC_CLS_TRF ");
            sqlEtcClsTrf.append(" where trx_date = to_date('" + d + "','dd/mm/yyyy')");
            sqlEtcClsTrf.append(" and EXT_STT_CODE = '" + stt + "'");

            StringBuffer sqlEtcClsRev = new StringBuffer("delete RVA_INT_ETC_CLS_REV ");
            sqlEtcClsRev.append(" where trx_date = to_date('" + d + "','dd/mm/yyyy')");
            sqlEtcClsRev.append(" and EXT_STT_CODE = '" + stt + "'");
//            System.out.println(sqlEtcClsTrf);
//            System.out.println(sqlEtcClsRev);
            stmt.executeUpdate(sqlEtcClsTrf.toString());
            stmt.executeUpdate(sqlEtcClsRev.toString());
        }

        stmt.close();
        c.close();
    }

    public static String convertDateFormat(String d) {
        String[] darr = d.split("/");
        return darr[1] + "/" + darr[0] + "/" + darr[2];

    }

    public static void main(String[] args) throws Exception {
        //  delete("26/12/2010", "206");
        //   System.out.println(convertDateFormat("02/09/2011"));
    }
}
