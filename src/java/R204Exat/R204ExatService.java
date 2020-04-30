/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package R204Exat;

import Connect.Connector;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author chonpisit_klo
 */
public class R204ExatService {

    public ArrayList getData(String startDate, String endDate, String type) throws Exception {
        Connector connector = new Connector();
        ArrayList<Report204Model> r204ModelList = new ArrayList<Report204Model>();
        try {
            connector.connectEta();
            String sqlType = "";
            sqlType = type.equals("all") ? "" : type.equals("etc") ? "AND PAY_TYPE IN ('E', 'Z')\n" : "AND PAY_TYPE NOT IN ('E', 'Z')\n";
            String sqlQuery = "SELECT STT.LINE_CODE, STT.LINE_DSC, TOLL.STATION_CODE, STATION_DSC\n"
                    + "	, SUM(CASE WHEN PASS_ID = 5 THEN 0 ELSE NUM_TYPE1 END) AS NUM_TYPE1\n"
                    + "	, SUM(CASE WHEN PASS_ID = 5 THEN 0 ELSE NUM_TYPE2 END) AS NUM_TYPE2\n"
                    + "	, SUM(CASE WHEN PASS_ID = 5 THEN 0 ELSE NUM_TYPE3 END) AS NUM_TYPE3\n"
                    + "	, SUM(CASE WHEN COM_CODE = 'DOH' THEN REV_TYPE1 - COM_REV_TYPE1 ELSE REV_TYPE1 END) AS REV_TYPE1\n"
                    + "	, SUM(CASE WHEN COM_CODE = 'DOH' THEN REV_TYPE2 - COM_REV_TYPE2 ELSE REV_TYPE2 END) AS REV_TYPE2\n"
                    + "	, SUM(CASE WHEN COM_CODE = 'DOH' THEN REV_TYPE3 - COM_REV_TYPE3 ELSE REV_TYPE3 END) AS REV_TYPE3\n"
                    + "FROM (\n"
                    + "	SELECT line.LINE_CODE, LINE.LINE_DSC, stt.STATION_CODE, stt.STATION_DSC\n"
                    + "	FROM RVA_MST_LINE line\n"
                    + "	LEFT JOIN RVA_MST_LINE_SCT sct on SCT.LINE_CODE = line.LINE_CODE\n"
                    + "	LEFT JOIN RVA_MST_SCT_STT stt on stt.SECTOR_CODE = sct.SECTOR_CODE\n"
                    + "	WHERE LINE.LINE_CODE = '06'\n"
                    + "	AND stt.ACTIVE_STATUS = 'A'\n"
                    + "	AND STT.STATION_CODE != '696'\n"
                    + "	GROUP BY line.LINE_CODE, LINE.LINE_DSC, STATION_CODE, STATION_DSC\n"
                    + ") STT\n"
                    + "LEFT JOIN RVA_TRX_TOLL TOLL ON TOLL.STATION_CODE = STT.STATION_CODE\n"
                    + "LEFT JOIN RVA_TRX_TOLL_DTL DTL ON DTL.TOLL_ID = TOLL.TOLL_ID\n"
                    + "WHERE TRX_DATE BETWEEN TO_DATE('" + startDate + "', 'dd/MM/yyyy') AND TO_DATE('" + endDate + "', 'dd/MM/yyyy')\n"
                    + "AND AUDIT_STATUS = 'Y'\n"
                    + sqlType
                    + "GROUP BY STT.LINE_CODE, STT.LINE_DSC, TOLL.STATION_CODE, STATION_DSC\n"
                    + "ORDER BY TOLL.STATION_CODE ASC";
            ResultSet resultSet = connector.executeQuery(sqlQuery);
            ResultSet resultETC  = null, resultMTC = null;
            while (resultSet.next()) {
                Report204Model reportModel = new Report204Model();
                reportModel.setLINE_CODE(resultSet.getString("LINE_CODE"));
                reportModel.setLINE_DSC(resultSet.getString("LINE_DSC"));
                reportModel.setSTATION_CODE(resultSet.getString("STATION_CODE"));
                reportModel.setSTATION_DSC(resultSet.getString("STATION_DSC"));
                reportModel.setTOT_NUM_TYPE1(resultSet.getInt("NUM_TYPE1"));
                reportModel.setTOT_NUM_TYPE2(resultSet.getInt("NUM_TYPE2"));
                reportModel.setTOT_NUM_TYPE3(resultSet.getInt("NUM_TYPE3"));
                reportModel.setTOT_REV_TYPE1(resultSet.getDouble("REV_TYPE1"));
                reportModel.setTOT_REV_TYPE2(resultSet.getDouble("REV_TYPE2"));
                reportModel.setTOT_REV_TYPE3(resultSet.getDouble("REV_TYPE3"));
                r204ModelList.add(reportModel);
            }
            if (type.equals("all")) {
                resultETC = getTrfDohOnlyETC(startDate, endDate, connector);
                resultMTC = getTrfDohOnlyMTC(startDate, endDate, connector);
            }
            if (type.equals("etc")) {
                resultETC = getTrfDohOnlyETC(startDate, endDate, connector);
            }
            if (type.equals("mtc")) {
                resultMTC = getTrfDohOnlyMTC(startDate, endDate, connector);
            }
//            if (resultMTC.next()) {
//                System.out.println(resultMTC.getString("station_code"));
//            }
                
//            while (resultETC.next()) {
//                for (Report204Model reportModel : r204ModelList) {
//                    if (resultETC.getString("station_cde").equals(reportModel.getSTATION_CODE())) {
//                        reportModel.setTOT_NUM_TYPE1(reportModel.getTOT_NUM_TYPE1() - resultETC.getInt("NUM_TYPE1"));
//                        reportModel.setTOT_NUM_TYPE2(reportModel.getTOT_NUM_TYPE2() - resultETC.getInt("NUM_TYPE2"));
//                        reportModel.setTOT_NUM_TYPE3(reportModel.getTOT_NUM_TYPE3() - resultETC.getInt("NUM_TYPE3"));
//                    }
//                }
//            }
//            while (resultMTC.next()) {
//                for (Report204Model reportModel : r204ModelList) {
//                    if (resultMTC.getString("station_cde").equals(reportModel.getSTATION_CODE())) {
//                        reportModel.setTOT_NUM_TYPE1(reportModel.getTOT_NUM_TYPE1() - resultMTC.getInt("NUM_TYPE1"));
//                        reportModel.setTOT_NUM_TYPE2(reportModel.getTOT_NUM_TYPE2() - resultMTC.getInt("NUM_TYPE2"));
//                        reportModel.setTOT_NUM_TYPE3(reportModel.getTOT_NUM_TYPE3() - resultMTC.getInt("NUM_TYPE3"));
//                    }
//                }
//            }
            return r204ModelList;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception(ex);
        } finally {
            connector.close();
        }
    }

    public void generateReport(HttpServletRequest request) throws FileNotFoundException, JRException, Exception {
        String startDate = request.getParameter("dateFrom");
        String endDate = request.getParameter("dateTo");
        String type = request.getParameter("type");
        String reportParamDsc = startDate.equals(endDate) ? "วันที่ " + startDate : "วันที่ " + startDate + " ถึง " + endDate;
        if (type.equals("all")) {
            reportParamDsc += " (MTC/ETC)";
        }
        if (type.equals("mtc")) {
            reportParamDsc += " (MTC)";
        }
        if (type.equals("etc")) {
            reportParamDsc += " (ETC)";
        }
        String rootPath = request.getServletContext().getRealPath("/");
        rootPath += "/reports/";
        String exportPath = request.getRealPath("/export/report/");
        if (!(new File(exportPath)).exists()) {
            (new File(exportPath)).mkdirs();
        }
        ArrayList<Report204Model> r204ModelList = getData(startDate, endDate, type);
        String fileName = "RR204EXATB";
        HashMap<String, Object> mapParam = new HashMap<String, Object>();
        File reportFile = null;
        File jasperFile = null;
        JasperReport report = null;
//        String uid = request.getSession().getAttribute("ssion_userid").toString();
//        String deptName = request.getSession().getAttribute("ssion_deptname").toString();
        String uid = "sss";
        String deptName = "ssss";
        reportFile = new File(rootPath + fileName + ".jrxml");
        jasperFile = new File(rootPath + fileName + ".jasper");
        try {
            report = (JasperReport) JRLoader.loadObjectFromFile(jasperFile.getPath());
        } catch (Exception e) {
            JasperCompileManager.compileReportToFile(reportFile.getPath(), jasperFile.getPath());
            report = (JasperReport) JRLoader.loadObjectFromFile(jasperFile.getPath());
        }
        mapParam.put("REPORT_PARAM_DSC", reportParamDsc);
        mapParam.put("REPORT_AUDIT_STATUS_FOOTER", getAuditStatus(startDate, endDate, deptName));
        mapParam.put("REPORT_PRINT_BY", "จัดพิมพ์โดย " + uid + " " + deptName);
        mapParam.put(JRParameter.REPORT_LOCALE, new Locale("th", "TH"));
        JasperPrint jasperPrint = JasperFillManager.fillReport(report, mapParam, new JRBeanCollectionDataSource(r204ModelList));
        JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath + "/" + fileName + ".pdf");
    }

    public String getAuditStatus(String startDate, String endDate, String deptName) throws Exception {
        Connector connector = new Connector();
        String auditStatus = "ข้อมูล";
        try {
            connector.connectEta();
            String sqlQuery = "select count(*) as count_status\n"
                    + "from RVA_MST_PERIOD\n"
                    + "where LINE_CODE = '06'\n"
                    + "and (\n"
                    + "	START_DATE BETWEEN to_date('" + startDate + "', 'dd/MM/yyyy') and to_date('" + endDate + "', 'dd/MM/yyyy') \n"
                    + "	or \n"
                    + "	END_DATE BETWEEN to_date('" + startDate + "', 'dd/MM/yyyy') and to_date('" + endDate + "', 'dd/MM/yyyy')\n"
                    + ")\n"
                    + "and PERIOD_STATUS = 'O'";
            ResultSet resultSet = connector.executeQuery(sqlQuery);
            if (resultSet.next()) {
                auditStatus += (resultSet.getInt("count_status") > 0 ? "ยังไม่ผ่านการตรวจสอบจาก" : "ผ่านการตรวจสอบจาก") + deptName;
            }
        } catch (Exception ex) {
            throw new Exception(ex);
        } finally {
            connector.close();
        }
        return auditStatus;
    }

    private ResultSet getTrfDohOnlyMTC(String startDate, String endDate, Connector connector) throws Exception {
        String sqlQuery = "select station_code\n"
                + ", sum(NUM_TYPE1) as NUM_TYPE1\n"
                + ", sum(NUM_TYPE2) as NUM_TYPE2\n"
                + ", sum(NUM_TYPE3) as NUM_TYPE3\n"
                + "from (\n"
                + "	select TRF.EXT_STT_CODE as station_code\n"
                + "	, sum(TRF_TYPE1 + TRF_TYPE2) as NUM_TYPE1\n"
                + "	, sum(TRF_TYPE3 + TRF_TYPE6 + TRF_TYPE7 + TRF_TYPE8) as NUM_TYPE2\n"
                + "	, sum(TRF_TYPE4) as NUM_TYPE3\n"
                + "	from (\n"
                + "		select EXT_STT_CODE, ENT_STT_CODE\n"
                + "		from RVA_MST_CHRG_CLS\n"
                + "		where END_DATE > sysdate\n"
                + "		and EXT_STT_CODE like '6%'\n"
                + "		and COM_CODE = 'DOH'\n"
                + "		and (CHRG_REV_4W  - COM_REV_4W ) = 0\n"
                + "		GROUP BY EXT_STT_CODE, ENT_STT_CODE \n"
                + "	) chrg \n"
                + "	LEFT JOIN RVA_TRX_TRF_CLS trf on CHRG.EXT_STT_CODE = trf.EXT_STT_CODE and CHRG.ENT_STT_CODE = trf.ENT_STT_CODE\n"
                + "	where TRX_DATE BETWEEN TO_DATE('" + startDate + "', 'dd/MM/yyyy') AND TO_DATE('" + endDate + "', 'dd/MM/yyyy')\n"
                + "     and AUDIT_STATUS = 'Y'\n"
                + "	group by TRF.EXT_STT_CODE\n"
                + "\n"
                + "	union ALL\n"
                + "	select UAP.EXT_STT_CODE as station_code\n"
                + "	, sum(NUAP_TYPE1) as NUM_TYPE1\n"
                + "	, sum(NUAP_TYPE2) as NUM_TYPE2\n"
                + "	, sum(NUAP_TYPE3) as NUM_TYPE3\n"
                + "	from (\n"
                + "		select EXT_STT_CODE, ENT_STT_CODE\n"
                + "		from RVA_MST_CHRG_CLS\n"
                + "		where END_DATE > sysdate\n"
                + "		and EXT_STT_CODE like '6%'\n"
                + "		and COM_CODE = 'DOH'\n"
                + "		and (CHRG_REV_4W  - COM_REV_4W ) = 0\n"
                + "		GROUP BY EXT_STT_CODE, ENT_STT_CODE \n"
                + "	) chrg \n"
                + "	LEFT JOIN RVA_TRX_UAP_CLS uap on CHRG.EXT_STT_CODE = uap.EXT_STT_CODE and CHRG.ENT_STT_CODE = uap.ENT_STT_CODE\n"
                + "	where TRX_DATE BETWEEN TO_DATE('" + startDate + "', 'dd/MM/yyyy') AND TO_DATE('" + endDate + "', 'dd/MM/yyyy')\n"
                + "     and AUDIT_STATUS = 'Y'\n"
                + "	group by UAP.EXT_STT_CODE\n"
                + "\n"
                + "	UNION ALL\n"
                + "	select adj.EXT_STT_CODE\n"
                + "	, sum(ADJ_NUM_TYPE1) as NUM_TYPE1\n"
                + "	, sum(ADJ_NUM_TYPE2) as NUM_TYPE2\n"
                + "	, sum(ADJ_NUM_TYPE3) as NUM_TYPE3\n"
                + "	from (\n"
                + "		select EXT_STT_CODE, ENT_STT_CODE\n"
                + "		from RVA_MST_CHRG_CLS\n"
                + "		where END_DATE > sysdate\n"
                + "		and EXT_STT_CODE like '6%'\n"
                + "		and COM_CODE = 'DOH'\n"
                + "		and (CHRG_REV_4W  - COM_REV_4W ) = 0\n"
                + "		GROUP BY EXT_STT_CODE, ENT_STT_CODE \n"
                + "	) chrg \n"
                + "	LEFT JOIN RVA_TRX_REV_ADJ_CLS adj on CHRG.EXT_STT_CODE = adj.EXT_STT_CODE and CHRG.ENT_STT_CODE = adj.ENT_STT_CODE\n"
                + "	where TRX_DATE BETWEEN TO_DATE('" + startDate + "', 'dd/MM/yyyy') AND TO_DATE('" + endDate + "', 'dd/MM/yyyy')\n"
                + "     and AUDIT_STATUS = 'Y'\n"
                + "	group by adj.EXT_STT_CODE\n"
                + ")\n"
                + "group by station_code";
        try {
            connector.connectEta();
            ResultSet resultSet = connector.executeQuery(sqlQuery);
            return resultSet;
        } finally {
//            connector.close();
        }

    }

    private ResultSet getTrfDohOnlyETC(String startDate, String endDate, Connector connector) throws Exception {
        String sqlQuery = "select trf.EXT_STT_CODE as station_code\n"
                + ",	sum(trf.PASS_CNT1) as NUM_TYPE1\n"
                + ", sum(trf.PASS_CNT2) as NUM_TYPE2\n"
                + ", sum(trf.PASS_CNT3) as NUM_TYPE3\n"
                + "from (\n"
                + "		select EXT_STT_CODE, ENT_STT_CODE\n"
                + "		from RVA_MST_CHRG_CLS\n"
                + "		where END_DATE > sysdate\n"
                + "		and EXT_STT_CODE like '6%'\n"
                + "		and COM_CODE = 'DOH'\n"
                + "		and (CHRG_REV_4W  - COM_REV_4W ) = 0\n"
                + "		GROUP BY EXT_STT_CODE, ENT_STT_CODE \n"
                + ") chrg \n"
                + "INNER JOIN (\n"
                + "	select EXT_STT_CODE, ENT_STT_CODE, PASS_CNT1, PASS_CNT2, PASS_CNT3\n"
                + "	from RVA_TRX_ETC_CLS cls\n"
                + "	INNER join RVA_TRX_ETC_CLS_TRF trf on trf.CLS_ID = cls.CLS_ID\n"
                + "	where TRX_DATE BETWEEN TO_DATE('" + startDate + "', 'dd/MM/yyyy') AND TO_DATE('" + endDate + "', 'dd/MM/yyyy')\n"
                + "	and AUDIT_STATUS = 'Y'\n"
                + "\n"
                + "	UNION ALL\n"
                + "	select EXT_STT_CODE, ENT_STT_CODE, PASS_CNT1, PASS_CNT2, PASS_CNT3\n"
                + "	from RVA_TRX_ETC_CLS cls\n"
                + "	INNER join RVA_TRX_ETC_CLS_TRF_ADJ adj on adj.CLS_ID = cls.CLS_ID\n"
                + "	where TRX_DATE BETWEEN TO_DATE('" + startDate + "', 'dd/MM/yyyy') AND TO_DATE('" + endDate + "', 'dd/MM/yyyy')\n"
                + "	and AUDIT_STATUS = 'Y'\n"
                + "\n"
                + ") trf on CHRG.EXT_STT_CODE = trf.EXT_STT_CODE and CHRG.ENT_STT_CODE = trf.ENT_STT_CODE\n"
                + "group by trf.EXT_STT_CODE";
        try {
            connector.connectEta();
            ResultSet resultSet = connector.executeQuery(sqlQuery);
            return resultSet;
        } finally {
//            connector.close();
        }
    }

    public static void main(String[] args) {
        try {
            String type = "etc";
            String sqlType = type.equals("all") ? "" : type.equals("etc") ? "AND PAY_TYPE = 'E'" : "AND PAY_TYPE != 'E'";
            System.out.println(sqlType);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
