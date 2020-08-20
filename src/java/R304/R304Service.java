/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package R304;

import Connect.Connector;
import java.io.File;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import jxl.CellView;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.PageOrientation;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 *
 * @author chonpisit_klo
 */
public class R304Service {

    public String fd = "";
    public String fdLocalFmt = "";
    public String p = "";
    public String pImg = "";
    public String lineType = "";
    public String lineDsc = "";
    public String dateHeader = "";
    NumberFormat fInt;
    WritableCellFormat cInteger;
    WritableCellFormat cDouble;
    NumberFormat fDou;
    WritableFont font;
    WritableFont fontBold;
    WritableFont fontBold2;
    WritableFont fontBold3;
    WritableCellFormat txtHeader1;
    WritableCellFormat txtHeader2;
    WritableCellFormat txtHeader3;
    WritableCellFormat txtHeader4;
    WritableCellFormat txtHeader5;
    WritableCellFormat txtCol;
    WritableCellFormat txtDetail;
    WritableCellFormat txtRightDetail;
    WritableCellFormat txtCentreDetail;
    WritableCellFormat tInteger;
    WritableCellFormat tDouble;
    WritableCellFormat tDoubleBold;
    WritableCellFormat txtChecker;
    WritableCellFormat fmtCenter;
    WritableCellFormat tSummary;

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
            strBuilder.append("<option value='all'> ทั้งหมด </option> /n");
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

    public String generateReport(HttpServletRequest request) throws Exception {
        fInt = new NumberFormat("#,##0");
        cInteger = new WritableCellFormat(fInt);
        fDou = new NumberFormat("#,##0.00_);(#,##0.00)");
        cDouble = new WritableCellFormat(fDou);
        font = new WritableFont(WritableFont.createFont("THSarabunPSK"), 14, WritableFont.NO_BOLD);
        fontBold = new WritableFont(WritableFont.createFont("THSarabunPSK"), 22, WritableFont.BOLD);
        fontBold2 = new WritableFont(WritableFont.createFont("THSarabunPSK"), 18, WritableFont.BOLD);
        fontBold3 = new WritableFont(WritableFont.createFont("THSarabunPSK"), 16, WritableFont.BOLD);
        txtHeader1 = new WritableCellFormat(fontBold);
        txtHeader2 = new WritableCellFormat(font);
        txtHeader3 = new WritableCellFormat(fontBold2);
        txtHeader4 = new WritableCellFormat(fontBold3);
        txtHeader5 = new WritableCellFormat(fontBold3);
        txtHeader4.setBorder(Border.ALL, BorderLineStyle.THIN);
        txtHeader5.setBorder(Border.ALL, BorderLineStyle.THIN);
        txtHeader1.setAlignment(Alignment.CENTRE);
        txtHeader3.setAlignment(Alignment.CENTRE);
        txtHeader5.setAlignment(Alignment.CENTRE);
        txtCol = new WritableCellFormat(font);
        txtCol.setBorder(Border.ALL, BorderLineStyle.THIN);
        txtCol.setAlignment(Alignment.CENTRE);
        txtDetail = new WritableCellFormat(font);
        txtDetail.setAlignment(Alignment.LEFT);
        txtDetail.setBorder(Border.ALL, BorderLineStyle.THIN);
        txtCentreDetail = new WritableCellFormat(font);
        txtCentreDetail.setAlignment(Alignment.CENTRE);
        txtCentreDetail.setBorder(Border.ALL, BorderLineStyle.THIN);
        txtRightDetail = new WritableCellFormat(font);
        txtRightDetail.setAlignment(Alignment.RIGHT);
        tInteger = new WritableCellFormat(fInt);
        tInteger.setFont(font);
        tInteger.setBorder(Border.ALL, BorderLineStyle.THIN);
        tDouble = new WritableCellFormat(fDou);
        tDouble.setFont(font);
        tDouble.setBorder(Border.ALL, BorderLineStyle.THIN);
        txtChecker = new WritableCellFormat(font);
        txtChecker.setAlignment(Alignment.CENTRE);
        fmtCenter = new WritableCellFormat();
        fmtCenter.setAlignment(Alignment.CENTRE);
        fmtCenter.setFont(font);
        this.p = request.getRealPath("/export/report/");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        String lineCode = request.getParameter("lineCode");
        String serviceId = request.getParameter("serviceId");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        SimpleDateFormat sdfThai = new SimpleDateFormat("dd MMMM yyyy", new Locale("th", "TH"));
        Date trxFromDate = sdf.parse(fromDate);
        Date trxToDate = sdf.parse(toDate);
        this.dateHeader = sdfThai.format(trxFromDate);
        if (!trxFromDate.equals(trxToDate)) {
            this.dateHeader = sdfThai.format(trxFromDate) + " ถึง " + sdfThai.format(trxToDate);
        }
        Calendar calendarDate = Calendar.getInstance();
        calendarDate.setTime(trxFromDate);
        this.fd = util.DateUtil.convertFormat(fromDate, "yyyy-MM-dd");
        this.fdLocalFmt = fromDate;
        if (!(new File(this.p)).exists()) {
            (new File(this.p)).mkdirs();
        }
        this.lineDsc = "ทางพิเศษ" + getLineDsc(lineCode);
        File fileReport = new File(this.p + "/รายงานการให้บริการ Easy Pass (EDC) " + this.lineDsc + " " + dateHeader + ".xls");
        WritableWorkbook workbook = Workbook.createWorkbook(fileReport);
        WritableSheet shtSummary = workbook.createSheet(this.lineDsc, 0);
        writeCoverHead(shtSummary, "รายงานการให้บริการ Easy Pass (เฉพาะประเภทการชำระจากเครื่อง EDC) " + this.lineDsc, serviceId);
        writeDataSummary(shtSummary, fromDate, toDate, lineCode, serviceId);
        workbook.write();
        workbook.close();
        return fileReport.getName();
    }

    private void writeDataSummary(WritableSheet shtSummary, String fromDate, String toDate, String lineCode, String serviceId) throws Exception {
        Connector connector = new Connector();
        try {
            connector.connectEta();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            SimpleDateFormat sdfSlash = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            SimpleDateFormat sdfThai = new SimpleDateFormat("dd MMMM yyyy", new Locale("th", "TH"));
            String sqlServiceId = "all".equals(serviceId) ? "" : "AND TRX.SERVICE_ID = '" + serviceId + "'\n";
            String sqlQuery = "SELECT MST.TRX_DATE, POS.POS_CODE, POS.POS_DESC\n"
                    + ", SUM( NVL( (CASE TRX.PT_ID WHEN 11 THEN TRX.COST END), 0)) AS COST_QR\n"
                    + ", SUM( NVL( (CASE TRX.PT_ID WHEN 13 THEN TRX.COST END), 0)) AS COST_CREDIT\n"
                    + ", SUM( NVL( (CASE TRX.PT_ID WHEN 14 THEN TRX.COST END), 0)) AS COST_DEBIT\n"
                    + ", SUM( NVL(TRX.COST, 0)) AS COST_ALL\n"
                    + "FROM RVA_MST_ETC_POS POS\n"
                    + "INNER JOIN RVA_TRX_ETC_MASTER MST ON POS.POS_ID = MST.POS_ID\n"
                    + "INNER JOIN RVA_TRX_ETC_TRX TRX ON TRX.SHIFT_ID = MST.SHIFT_ID\n"
                    + "WHERE TRX_DATE BETWEEN TO_DATE('" + fromDate + "', 'dd/MM/yyyy') AND TO_DATE('" + toDate + "', 'dd/MM/yyyy')\n"
                    + "AND MST.AUDIT_STATUS = 'Y'\n"
                    + "AND POS.LINE_CODE = '" + lineCode + "'\n"
                    + sqlServiceId
                    + "AND TRX.PT_ID IN ('11', '13', '14')\n"
                    + "GROUP BY MST.TRX_DATE, POS.POS_CODE, POS.POS_DESC\n"
                    + "ORDER BY MST.TRX_DATE, POS.POS_CODE";
            ResultSet result = connector.executeQuery(sqlQuery);
            shtSummary.addCell(new Label(1, 6, "วันที่", txtHeader5));
            shtSummary.addCell(new Label(2, 6, "รหัส POS", txtHeader5));
            shtSummary.addCell(new Label(3, 6, "ชื่อ POS", txtHeader5));
            shtSummary.addCell(new Label(4, 6, "QR PAYMENT", txtHeader5));
            shtSummary.addCell(new Label(5, 6, "CREDIT", txtHeader5));
            shtSummary.addCell(new Label(6, 6, "DEBIT", txtHeader5));
            shtSummary.addCell(new Label(7, 6, "รวม", txtHeader5));
            int rowNum = 7;
            Date currentResultDate, previousResultDate = sdfSlash.parse(fromDate);
            while (result.next()) {
                currentResultDate = result.getDate("trx_date");
                if (!currentResultDate.equals(previousResultDate) && rowNum != 7) {
                    shtSummary.addCell(new Label(1, rowNum, "", txtCentreDetail));
                    shtSummary.mergeCells(1, rowNum, 7, rowNum);
                    rowNum++;
                }
                shtSummary.addCell(new Label(1, rowNum, sdfThai.format(sdf.parse(result.getString("TRX_DATE"))), txtCentreDetail));
                shtSummary.addCell(new Label(2, rowNum, result.getString("pos_code"), txtCentreDetail));
                shtSummary.addCell(new Label(3, rowNum, result.getString("pos_desc"), txtCentreDetail));
                shtSummary.addCell(new jxl.write.Number(4, rowNum, result.getDouble("COST_QR"), tDouble));
                shtSummary.addCell(new jxl.write.Number(5, rowNum, result.getDouble("COST_CREDIT"), tDouble));
                shtSummary.addCell(new jxl.write.Number(6, rowNum, result.getDouble("COST_DEBIT"), tDouble));
                shtSummary.addCell(new jxl.write.Number(7, rowNum, result.getDouble("COST_ALL"), tDouble));
                previousResultDate = result.getDate("trx_date");
                rowNum++;
            }
        } finally {
            connector.close();
        }

    }

    public void writeCoverHead(WritableSheet s, String headName, String serviceId) throws WriteException, Exception {
        CellView c = new CellView();
        c.setSize(30 * 30);
        s.setColumnView(0, c);
        c.setSize(30 * 175);
        s.setColumnView(1, c);
        s.setColumnView(3, c);
        c.setSize(30 * 100);
        s.setColumnView(2, c);
        c.setSize(30 * 150);
        s.setColumnView(4, c);
        s.setColumnView(5, c);
        s.setColumnView(6, c);
        s.setColumnView(7, c);
        s.setPageSetup(PageOrientation.LANDSCAPE);
        s.addCell(new Label(1, 0, util.DateUtil.getDateTimeExportReport(), txtRightDetail));
        s.mergeCells(1, 0, 7, 0);
        s.addCell(new Label(1, 1, "การทางพิเศษแห่งประเทศไทย", txtHeader1));
        s.mergeCells(1, 1, 7, 1);
        s.addCell(new Label(1, 2, "" + headName, txtHeader3));
        s.mergeCells(1, 2, 7, 2);
        s.addCell(new Label(1, 3, "ประเภทการให้บริการ" + getServiceDsc(serviceId), txtHeader3));
        s.mergeCells(1, 3, 7, 3);
        s.addCell(new Label(1, 4, "ประจำวันที่ " + dateHeader, txtHeader3));
        s.mergeCells(1, 4, 7, 4);
    }

    private String getLineDsc(String lineCode) throws Exception {
        Connector connector = new Connector();
        try {
            connector.connectEta();
            String sqlQuery = "SELECT LINE_DSC FROM RVA_MST_LINE WHERE LINE_CODE = '" + lineCode + "'";
            ResultSet result = connector.executeQuery(sqlQuery);
            if (!result.next()) {
                throw new Exception("ไม่พบข้อมูลสายทางพิเศษ");
            }
            return result.getString("LINE_DSC");
        } finally {
            connector.close();
        }
    }

    private String getServiceDsc(String serviceId) throws Exception {
        Connector connector = new Connector();
        try {
            if ("all".equals(serviceId)) {
                return "ทั้งหมด";
            }
            connector.connectEta();
            String sqlQuery = "SELECT SERVICE_DESC\n"
                    + "FROM RVA_MST_ETC_SERVICE_TYPE\n"
                    + "WHERE SERVICE_ID = '" + serviceId + "'\n"
                    + "AND ACTIVE_STATUS = 'A'";
            ResultSet result = connector.executeQuery(sqlQuery);
            if (!result.next()) {
                throw new Exception("ไม่พบข้อมูลประเภทการให้บริการ");
            }
            return result.getString("SERVICE_DESC");
        } finally {
            connector.close();
        }
    }

}
