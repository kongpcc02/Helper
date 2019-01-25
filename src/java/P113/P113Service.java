/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P113;

import Connect.Connector;

/**
 *
 * @author siridet_suk
 */
public class P113Service {

    public StringBuffer updateOpn(String d1, String d2, String line) {
        StringBuffer str = new StringBuffer();
        Connector c = new Connect.Connector("172.20.1.9", "rvauser", "userrva_", "etanetdb", "1521");
        try {
            c.connect();
            String sql = "UPDATE RVA_TRX_ETC_OPN_rev trx_rev SET trx_rev.PASS_REV1 = (SELECT int_REV.PASS_REV1 FROM RVA_TRX_ETC_OPN mst, RVA_INT_ETC_OPN_rev int_rev  WHERE  	trx_rev.OPN_ID = mst.OPN_ID "
                    + "	AND mst.TRX_DATE BETWEEN TO_DATE('" + d1 + "', 'ddmmyyyy') "
                    + "	AND TO_DATE('" + d2 + "', 'ddmmyyyy') "
                    + "	AND SUBSTR(mst.STATION_CODE, 0, 1) = '" + line + "'  "
                    + " AND mst.STATION_CODE <>  '324'"
                    + "	AND mst.EMP_CODE = INT_REV.EMP_CODE "
                    + "	AND mst.SHF_CODE = INT_REV.SHF_CODE "
                    + "	AND mst.STATION_CODE = INT_REV.STATION_CODE "
                    + "	AND mst.TRX_DATE = INT_REV.TRX_DATE "
                    + "	AND TRX_REV.ISSUER_ID = INT_REV.ISSUER_ID "
                    + "	AND TRX_REV.PASS_ID = INT_REV.PASS_ID "
                    + "	AND TRX_REV.LINE_CODE = INT_REV.LINE_CODE  ) "
                    + "WHERE  EXISTS ( "
                    + "		SELECT  int_REV.PASS_REV1  FROM "
                    + "			RVA_TRX_ETC_OPN mst, "
                    + "			RVA_INT_ETC_OPN_rev int_rev "
                    + "		WHERE trx_rev.OPN_ID = mst.OPN_ID "
                    + "		AND mst.TRX_DATE BETWEEN TO_DATE('" + d1 + "', 'ddmmyyyy') "
                    + "		AND TO_DATE('" + d2 + "', 'ddmmyyyy') "
                    + "		AND SUBSTR(mst.STATION_CODE, 0, 1) = '" + line + "' "
                    + " AND mst.STATION_CODE <>  '324'"
                    + "		AND mst.EMP_CODE = INT_REV.EMP_CODE "
                    + "		AND mst.SHF_CODE = INT_REV.SHF_CODE "
                    + "		AND mst.STATION_CODE = INT_REV.STATION_CODE "
                    + "		AND mst.TRX_DATE = INT_REV.TRX_DATE "
                    + "		AND TRX_REV.ISSUER_ID = INT_REV.ISSUER_ID "
                    + "		AND TRX_REV.PASS_ID = INT_REV.PASS_ID "
                    + "		AND TRX_REV.LINE_CODE = INT_REV.LINE_CODE )";

            c.executeUpdate(sql);
        } catch (Exception ex) {
            str.append("ERROR==").append(ex);
        } finally {
            c.close();
            return str;
        }
    }

    public StringBuffer updateCls(String d1, String d2, String line) {
        StringBuffer str = new StringBuffer();
        Connector c = new Connect.Connector("172.20.1.9", "rvauser", "userrva_", "etanetdb", "1521");
        try {
            c.connect();
            String sql = " UPDATE RVA_TRX_ETC_CLS_rev trx_rev  "
                    + "SET trx_rev.PASS_REV1 = ( SELECT  int_REV.PASS_REV1  "
                    + "	FROM  "
                    + "		RVA_TRX_ETC_CLS mst,  "
                    + "		RVA_INT_ETC_CLS_rev int_rev  "
                    + "	WHERE  "
                    + "		trx_rev.CLS_ID = mst.CLS_ID  "
                    + "	AND mst.TRX_DATE BETWEEN \"TO_DATE\" ('" + d1 + "', 'ddmmyyyy')  "
                    + "	AND \"TO_DATE\" ('" + d2 + "', 'ddmmyyyy')  "
                    + "	AND \"SUBSTR\" (mst.EXT_STT_CODE, 0, 1) = '" + line + "'  "
                    + "	AND mst.EMP_CODE = INT_REV.EMP_CODE  "
                    + "	AND mst.SHF_CODE = INT_REV.SHF_CODE  "
                    + "	AND mst.EXT_STT_CODE = INT_REV.EXT_STT_CODE  "
                    + "	AND TRX_REV.ENT_STT_CODE = INT_REV.ENT_STT_CODE  "
                    + "	AND mst.TRX_DATE = INT_REV.TRX_DATE  "
                    + "	AND TRX_REV.ISSUER_ID = INT_REV.ISSUER_ID  "
                    + "	AND TRX_REV.PASS_ID = INT_REV.PASS_ID  "
                    + "	AND TRX_REV.LINE_CODE = INT_REV.LINE_CODE  "
                    + ")  "
                    + "WHERE  "
                    + "	EXISTS (  "
                    + "		SELECT  "
                    + "			int_REV.PASS_REV1  "
                    + "		FROM  "
                    + "			RVA_TRX_ETC_CLS mst,  "
                    + "			RVA_INT_ETC_CLS_rev int_rev  "
                    + "		WHERE  "
                    + "			trx_rev.CLS_ID = mst.CLS_ID  "
                    + "		AND mst.TRX_DATE BETWEEN \"TO_DATE\" ('" + d1 + "', 'ddmmyyyy')  "
                    + "		AND \"TO_DATE\" ('" + d2 + "', 'ddmmyyyy')  "
                    + "		AND \"SUBSTR\" (mst.EXT_STT_CODE, 0, 1) = '" + line + "'  "
                    + "		AND mst.EMP_CODE = INT_REV.EMP_CODE  "
                    + "		AND mst.SHF_CODE = INT_REV.SHF_CODE  "
                    + "		AND mst.EXT_STT_CODE = INT_REV.EXT_STT_CODE  "
                    + "		AND TRX_REV.ENT_STT_CODE = INT_REV.ENT_STT_CODE  "
                    + "		AND mst.TRX_DATE = INT_REV.TRX_DATE  "
                    + "		AND TRX_REV.ISSUER_ID = INT_REV.ISSUER_ID  "
                    + "		AND TRX_REV.PASS_ID = INT_REV.PASS_ID  "
                    + "		AND TRX_REV.LINE_CODE = INT_REV.LINE_CODE  "
                    + "	)";

            c.executeUpdate(sql);
        } catch (Exception ex) {
            str.append("ERROR==").append(ex.getMessage());
        } finally {
            c.close();
            return str;
        }
    }

    public static void main(String[] args) {
        P113Service s = new P113Service();
        System.out.println(s.updateCls("06012017", "06012017", "1"));

    }
}
