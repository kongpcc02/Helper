/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P301;

import Connect.Connector;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *
 * @author EXAT
 */
public class P301Service {

    protected String getEtcServiceType() throws Exception {
        Connector connector = new Connector();
        StringBuilder strBuilder = new StringBuilder();
        try {
            connector.connectEta();
            String sqlQuery = "SELECT SERVICE_ID, SERVICE_CODE, SERVICE_DESC\n"
                    + "FROM RVA_MST_ETC_SERVICE_TYPE\n"
                    + "WHERE ACTIVE_STATUS = 'A'\n"
                    + "ORDER BY SERVICE_CODE";
            ResultSet queryResult = connector.executeQuery(sqlQuery);
            while (queryResult.next()) {
                strBuilder.append("<option value='" + queryResult.getString("SERVICE_ID") + "'> " + queryResult.getString("SERVICE_DESC") + "</option> /n");
            }
            return strBuilder.toString();
        } finally {
            connector.close();
        }
    }

    protected String getLine() throws Exception {
        Connector connector = new Connector();
        StringBuilder strBuilder = new StringBuilder();
        try {
            connector.connectEta();
            String sqlQuery = "SELECT LINE_CODE, LINE_DSC\n"
                    + "FROM RVA_MST_LINE\n"
                    + "WHERE ACTIVE_STATUS = 'A'\n"
                    + "AND LINE_CODE != '00'\n"
                    + "ORDER BY LINE_CODE";
            ResultSet queryResult = connector.executeQuery(sqlQuery);
            while (queryResult.next()) {
                strBuilder.append("<option value='" + queryResult.getString("LINE_CODE") + "'> " + queryResult.getString("LINE_DSC") + "</option> /n");
            }
            return strBuilder.toString();
        } finally {
            connector.close();
        }
    }

    protected String getUserId(String userId) throws Exception {
        Connector connector = new Connector();
        try {
            connector.connectEta();
            String sqlQuery = "SELECT USER_ID\n"
                    + "FROM SEC_USER\n"
                    + "WHERE USER_NAME = '" + userId + "'\n"
                    + "AND ACTIVE_STATUS = 'A'";
            ResultSet queryResult = connector.executeQuery(sqlQuery);
            if (!queryResult.next()) {
                throw new Exception("ไม่พบช้อมูลผู้ใช้งาน");
            }
            return queryResult.getString("USER_ID");
        } finally {
            connector.close();
        }
    }
    
    protected void executeUpdateVat(String lineCode, String dateFrom, String dateTo, String serviceId, double minMoney, String userId) throws Exception {
        Connector connector = new Connector();
        StringBuilder strBuilder = new StringBuilder();
        EtcTrxModel etcTrxModel = null;
        ArrayList<EtcTrxModel> etcTrxList = new ArrayList<EtcTrxModel>();
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String vatOfMinMoney = decimalFormat.format(minMoney * 7.0 / 107.0);
        try {
            connector.connectEta();
            String sqlQuery = "SELECT SHIFT_ID, POS.POS_CODE, POS.POS_DESC, TRX.LINE_CODE\n"
                    + ", SUM(TRX.COST) AS COST\n"
                    + ", SUM(TRX.INVAT) AS INVAT\n"
                    + ", SUM(TRX.VAT) AS VAT\n"
                    + ", SUM(TRX.EXVAT) AS EXVAT\n"
                    + "FROM RVA_MST_ETC_POS POS \n"
                    + "INNER JOIN RVA_TRX_ETC_MASTER MST ON POS.POS_ID = MST.POS_ID\n"
                    + "INNER JOIN RVA_TRX_ETC_TRX TRX ON TRX.SHIFT_ID = MST.SHIFT_ID\n"
                    + "WHERE TRX_DATE BETWEEN TO_DATE('" + dateFrom + "', 'dd/MM/yyyy') AND TO_DATE('" + dateTo + "', 'dd/MM/yyyy')\n"
                    + "AND POS.LINE_CODE = '" + lineCode + "'\n"
                    + "AND TRX.SERVICE_ID = '" + serviceId + "'\n"
                    + "GROUP BY SHIFT_ID, POS.POS_CODE, POS.POS_DESC, TRX.LINE_CODE\n"
                    + "ORDER BY POS_CODE";
            ResultSet queryResult = connector.executeQuery(sqlQuery);
            while (queryResult.next()) {
                etcTrxModel = new EtcTrxModel();
                etcTrxModel.setShiftId(queryResult.getString("SHIFT_ID"));
                etcTrxModel.setCost(queryResult.getDouble("COST"));
                etcTrxModel.setLineCode(queryResult.getString("LINE_CODE"));
                etcTrxModel.setPosCode(queryResult.getString("POS_CODE"));
                etcTrxModel.setPosDesc(queryResult.getString("POS_DESC"));
                etcTrxList.add(etcTrxModel);
            }
            double cost, vatOfCost, excludeVatOfCost;
            int costDivideMin;
            for (EtcTrxModel etcTrx : etcTrxList) {
                cost = etcTrx.getCost();
                if (cost == minMoney) {
                    continue;
                }
                costDivideMin = (int) (cost / minMoney);
                vatOfCost = Double.parseDouble(vatOfMinMoney) * costDivideMin;
                excludeVatOfCost = cost - vatOfCost;
                String sqlUpdate = "UPDATE RVA_TRX_ETC_TRX\n"
                        + "SET EXVAT = '" + excludeVatOfCost + "', VAT = '" + vatOfCost + "', UPDATED_BY = '" + userId + "', UPDATED_DATE = SYSDATE\n"
                        + "WHERE SHIFT_ID = '" + etcTrx.getShiftId() + "'\n"
                        + "AND SERVICE_ID = '" + serviceId + "'\n "
                        + "AND LINE_CODE = '" + etcTrx.getLineCode() + "'";
                connector.executeUpdate(sqlUpdate);
            }
        } catch (Exception ex) {
            connector.rollback();
            throw new Exception(ex);
        } finally {
            connector.close();
        }
    }

}
