/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package P109;

import java.sql.Connection;
import java.sql.Statement;
import th.co.exat.helper.System.EtaConnectionFactory;

/**
 *
 * @author siridet_suk
 */
public class P109Model {

    public static void clearError(String d1, String d2) throws Exception {
        Connection con = EtaConnectionFactory.getConnection();
        Statement stmt = con.createStatement();
        StringBuilder query = new StringBuilder("update rva_trx_etc_cls_rev set pass_rev0=0 where  pass_id<>1 and pass_rev0>0 and cls_id in( ");
        query.append(" select  rev.cls_id  from rva_trx_etc_cls_rev rev inner join rva_trx_etc_cls cls on rev.cls_id=cls.cls_id ");
        query.append(" where trx_date between to_Date('").append(d1).append("','ddmmyyyy') and to_Date('").append(d2).append("','ddmmyyyy')");
        query.append(" and pass_id<>1 and ext_stt_code='638' and pass_rev0>0 group by rev.cls_id )");

        stmt.execute(query.toString());

        StringBuilder query2 = new StringBuilder("update  rva_trx_etc_cls_rev set pass_rev0=0 where pass_id=1 and pass_rev0>0 and cls_id in ( ");
        query2.append(" select  rev.cls_id  from rva_trx_etc_cls_rev rev inner join rva_trx_etc_cls cls on rev.cls_id=cls.cls_id  ");
        query2.append(" where trx_date between to_Date('").append(d1).append("','ddmmyyyy') and to_Date('").append(d2).append("','ddmmyyyy')");
        query2.append(" and pass_rev0<>0 and ext_stt_code='638' group by rev.cls_id )");

        stmt.execute(query2.toString());

        stmt.close();
        con.close();
    }
}
