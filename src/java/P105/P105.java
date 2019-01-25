/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package P105;

import java.sql.Connection;
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

        StringBuffer sqlTrf = new StringBuffer("delete rva_int_etc_opn_trf ");
        sqlTrf.append(" where trx_date = to_date('" + d + "','dd/mm/yyyy')");
        sqlTrf.append(" and station_code = '" + stt + "'");

        StringBuffer sqlRev = new StringBuffer("delete rva_int_etc_opn_rev ");
        sqlRev.append(" where trx_date = to_date('" + d + "','dd/mm/yyyy')");
        sqlRev.append(" and station_code = '" + stt + "'");

        stmt.executeUpdate(sqlTrf.toString());
        stmt.executeUpdate(sqlRev.toString());

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
