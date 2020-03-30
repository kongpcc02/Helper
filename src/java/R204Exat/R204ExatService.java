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
import javax.servlet.http.HttpServletRequest;
import net.sf.jasperreports.engine.JRException;
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
            System.out.println(sqlQuery);
            ResultSet resultSet = connector.executeQuery(sqlQuery);
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
            return r204ModelList;
        } catch (Exception ex) {
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
        String uid = request.getSession().getAttribute("ssion_userid").toString();
        String deptName = request.getSession().getAttribute("ssion_deptname").toString();
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
                auditStatus += (resultSet.getInt("count_status") > 0 ? "ยังไม่การตรวจสอบจาก" : "ผ่านการตรวจสอบจาก") + deptName;
            }
        } catch (Exception ex) {
            throw new Exception(ex);
        } finally {
            connector.close();
        }
        return auditStatus;
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
