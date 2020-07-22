/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package R303;

/**
 *
 * @author chonpisit_klo
 */
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

public class R303Service {

    public String fd = "";
    public String fdLocalFmt = "";
    public String p = "";
    public String pImg = "";
    public String lineType = "";
    public String lineDsc = "";
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
        txtHeader5 = new WritableCellFormat(fontBold2);
        txtHeader4.setBorder(Border.ALL, BorderLineStyle.THIN);
        txtHeader5.setBorder(Border.ALL, BorderLineStyle.THIN);
        txtHeader1.setAlignment(Alignment.CENTRE);
        txtHeader3.setAlignment(Alignment.CENTRE);
        txtHeader5.setAlignment(Alignment.CENTRE);
//        txtHeader5.setWrap(true);
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
        String fromDate = request.getParameter("date");
        String toDate = request.getParameter("toDate");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Date trxFromDate = sdf.parse(fromDate);
        Date trxToDate = sdf.parse(toDate);
        Calendar calendarDate = Calendar.getInstance();
        calendarDate.setTime(trxFromDate);

        this.fd = util.DateUtil.convertFormat(fromDate, "yyyy-MM-dd");
        this.fdLocalFmt = fromDate;
        if (!(new File(this.p)).exists()) {
            (new File(this.p)).mkdirs();
        }
        String lineCode = request.getParameter("lineCode");
        this.lineDsc = getLineDsc(lineCode);
        this.lineType = getLineType(lineCode);
        File fileReport = new File(this.p + "/รายงานสรุปรายได้ค่าผ่านทาง EMV ทางพิเศษ" + this.lineDsc + " " + fd + " ถึง " + util.DateUtil.convertFormat(toDate, "yyyy-MM-dd") + ".xls");
        WritableWorkbook workbook = Workbook.createWorkbook(fileReport);
        int index = 0;
        String trxDate = "";
        WritableSheet shtSummary = null;
        while (calendarDate.getTime().equals(trxToDate) || calendarDate.getTime().before(trxToDate)) {
            trxDate = sdf.format(calendarDate.getTime());
            shtSummary = workbook.createSheet("วันที่ " + util.DateUtil.convertFormat(trxDate, "dd-MM-yyyy"), index);
            writeCoverHead(shtSummary, "รายงานสรุปรายได้ค่าผ่านทาง EMV. ทางพิเศษ" + this.lineDsc, trxDate);
            writeDataSummary(shtSummary, trxDate, lineCode);
            calendarDate.add(Calendar.DATE, 1);
            index++;
        }
        workbook.write();
        workbook.close();
        return fileReport.getName();
    }

    public void writeCoverHead(WritableSheet s, String headName, String date) throws WriteException, Exception {
        CellView c = new CellView();
        c.setSize(30 * 30);
        s.setColumnView(0, c);
        c.setSize(30 * 80);
        s.setColumnView(1, c);
        s.setColumnView(9, c);
        c.setSize(30 * 200);
        s.setColumnView(2, c);
        c.setSize(30 * 110);
        s.setColumnView(3, c);
        s.setColumnView(4, c);
        s.setColumnView(5, c);
        s.setColumnView(6, c);
        s.setColumnView(7, c);
        s.setColumnView(8, c);
        s.setPageSetup(PageOrientation.LANDSCAPE);
        s.addCell(new Label(1, 0, util.DateUtil.getDateTimeExportReport(), txtRightDetail));
        s.mergeCells(1, 0, 9, 0);
        s.addCell(new Label(1, 1, "การทางพิเศษแห่งประเทศไทย", txtHeader1));
        s.mergeCells(1, 1, 9, 1);
        s.addCell(new Label(1, 2, "" + headName, txtHeader3));
        s.mergeCells(1, 2, 9, 2);
        s.addCell(new Label(1, 3, "ประจำวันที่ "
                + util.DateUtil.convertFormat(date, "dd") + " " + util.DateUtil.getMonthTh(Integer.parseInt(util.DateUtil.convertFormat(date, "MM")))
                + " " + util.DateUtil.convertFormatYear(date, "yyyy"), txtHeader3));
        s.mergeCells(1, 3, 8, 3);
    }

    public void writeDataSummary(WritableSheet s, String date, String lineCode) throws Exception {
        // date format dd/MM/yyyy
        Connector connector = new Connector();
        Connector connectorHelper = new Connector();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date presentDate = sdf.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(presentDate);
            calendar.add(Calendar.DATE, -1);
            String pastDate = sdf.format(calendar.getTime());
            connector.connectEta();
            connectorHelper.connect();
            String empCodeT1, empCodeT2;
            String sqlQueryEmpCode = "SELECT *\n"
                    + "FROM RVA_MST_EMP_EMV\n"
                    + "WHERE LINE_CODE = '" + lineCode + "'";
            ResultSet resultEmp = connectorHelper.executeQuery(sqlQueryEmpCode);
            if (!resultEmp.next()) {
                throw new Exception("ไม่พบข้อมูลพนักงานจัดเก็บค่าผ่านทาง EMV");
            }
            empCodeT1 = resultEmp.getString("EMP_CODE_T1");
            empCodeT2 = resultEmp.getString("EMP_CODE_T2");
            String sqlLineType = this.lineType.equals("O") ? "RVA_MST_CHRG_OPN CHRG ON CHRG.STATION_CODE " : " RVA_MST_CHRG_CLS CHRG ON CHRG.EXT_STT_CODE ";
            String sqlIgnoreSectorD = "02".equals(lineCode) ? "AND SCT.SECTOR_CODE <> 26 \n" : "";
            String sqlQueryData = "SELECT STT.STATION_CODE, STT.STATION_DSC, STT.SAP_STT_CODE \n"
                    + ", SUM(NVL(TRX.BANK_RMT_AMOUNT_T1, 0)) AS BANK_RMT_AMOUNT_T1, SUM(NVL(TRX.BANK_RMT_AMOUNT_T2, 0)) AS BANK_RMT_AMOUNT_T2\n"
                    + ", SUM(NVL(TRX.DELIVER_RMT_AMOUNT_T1, 0)) AS DELIVER_RMT_AMOUNT_T1, SUM(NVL(TRX.DELIVER_RMT_AMOUNT_T2, 0)) AS DELIVER_RMT_AMOUNT_T2\n"
                    + ", SUM(NVL(TRX.ADJ_AMOUNT, 0)) AS ADJ_AMOUNT\n"
                    + "FROM (\n"
                    + "	SELECT DISTINCT STT.STATION_CODE, STT.SAP_STT_CODE, STT.STATION_DSC\n"
                    + "	FROM RVA_MST_LINE LINE\n"
                    + "	INNER JOIN RVA_MST_LINE_SCT SCT ON SCT.LINE_CODE = LINE.LINE_CODE\n"
                    + "	INNER JOIN RVA_MST_SCT_STT STT ON STT.SECTOR_CODE = SCT.SECTOR_CODE\n"
                    + "	INNER JOIN " + sqlLineType + " = STT.STATION_CODE\n"
                    + "	WHERE SCT.ACTIVE_STATUS = 'A'\n"
                    + "	AND STT.ACTIVE_STATUS = 'A'\n"
                    + "	AND LINE.LINE_CODE = '" + lineCode + "'\n"
                    + "	AND  STT.STATION_DSC NOT LIKE '%โปรโมชั่น%' \n"
                    + " AND STT.STATION_DSC NOT LIKE '%โปรโมชัน%' \n"
                    + sqlIgnoreSectorD
                    + "	ORDER BY STT.STATION_CODE\n"
                    + ") STT\n"
                    + "LEFT JOIN (\n"
                    + "	SELECT BANK.STATION_CODE, BANK.RMT_AMOUNT AS BANK_RMT_AMOUNT_T1, 0 AS BANK_RMT_AMOUNT_T2, 0 AS DELIVER_RMT_AMOUNT_T1, 0 AS DELIVER_RMT_AMOUNT_T2, 0 AS ADJ_AMOUNT\n"
                    + "	FROM RVA_TRX_BANK_COUNT BANK\n"
                    + "	WHERE TRX_DATE = TO_DATE('" + date + "', 'dd/MM/yyyy')\n"
                    + "	AND EMP_CODE = '" + empCodeT1 +"'\n"
                    + "	AND REV_TYPE = 'TOLL'\n"
                    + "	AND AUDIT_STATUS = 'Y'\n"
                    + "	GROUP BY BANK.STATION_CODE, BANK.RMT_AMOUNT\n"
                    + "	UNION ALL\n"
                    + "	SELECT BANK.STATION_CODE, 0 AS BANK_RMT_AMOUNT_T1, BANK.RMT_AMOUNT AS BANK_RMT_AMOUNT_T2, 0 AS DELIVER_RMT_AMOUNT_T1, 0 AS DELIVER_RMT_AMOUNT_T2, 0 AS ADJ_AMOUNT\n"
                    + "	FROM RVA_TRX_BANK_COUNT BANK\n"
                    + "	WHERE TRX_DATE = TO_DATE('" + pastDate + "', 'dd/MM/yyyy')\n"
                    + "	AND EMP_CODE = '" + empCodeT2 +"'\n"
                    + "	AND REV_TYPE = 'TOLL'\n"
                    + "	AND AUDIT_STATUS = 'Y'\n"
                    + "	GROUP BY BANK.STATION_CODE, BANK.RMT_AMOUNT\n"
                    + "	UNION ALL\n"
                    + "	SELECT BANK.STATION_CODE, 0 AS BANK_RMT_AMOUNT_T1, 0 AS BANK_RMT_AMOUNT_T2, BANK.RMT_AMOUNT AS DELIVER_RMT_AMOUNT_T1, 0 AS DELIVER_RMT_AMOUNT_T2, BANK.ADJ_AMOUNT AS ADJ_AMOUNT\n"
                    + "	FROM RVA_TRX_BANK_COUNT BANK\n"
                    + "	WHERE TRX_DATE = TO_DATE('" + date + "', 'dd/MM/yyyy')\n"
                    + "	AND EMP_CODE = '" + empCodeT1 +"'\n"
                    + "	AND REV_TYPE = 'TOLL'\n"
                    + "	AND AUDIT_STATUS = 'Y'\n"
                    + "	GROUP BY BANK.STATION_CODE, BANK.RMT_AMOUNT, BANK.ADJ_AMOUNT\n"
                    + "	UNION ALL\n"
                    + "	SELECT BANK.STATION_CODE, 0 AS BANK_RMT_AMOUNT_T1, 0 AS BANK_RMT_AMOUNT_T2, 0 AS DELIVER_RMT_AMOUNT_T1, BANK.RMT_AMOUNT AS DELIVER_RMT_AMOUNT_T2, BANK.ADJ_AMOUNT AS ADJ_AMOUNT\n"
                    + "	FROM RVA_TRX_BANK_COUNT BANK\n"
                    + "	WHERE TRX_DATE = TO_DATE('" + date + "', 'dd/MM/yyyy')\n"
                    + "	AND EMP_CODE = '" + empCodeT2 +"'\n"
                    + "	AND REV_TYPE = 'TOLL'\n"
                    + "	AND AUDIT_STATUS = 'Y'\n"
                    + "	GROUP BY BANK.STATION_CODE, BANK.RMT_AMOUNT, BANK.ADJ_AMOUNT\n"
                    + ") TRX ON STT.STATION_CODE = TRX.STATION_CODE\n"
                    + "GROUP BY STT.STATION_CODE, STT.SAP_STT_CODE, STT.STATION_DSC\n"
                    + "ORDER BY STT.SAP_STT_CODE";
            s.addCell(new Label(1, 5, "รหัสด่าน", txtHeader5));
            s.mergeCells(1, 5, 1, 6);
            s.addCell(new Label(2, 5, "ชื่อด่าน", txtHeader5));
            s.mergeCells(2, 5, 2, 6);
            s.addCell(new Label(3, 5, "รายได้ค่าผ่านทาง EMV. (ธนาคาร)", txtHeader5));
            s.mergeCells(3, 5, 5, 5);
            s.addCell(new Label(3, 6, "21:01-23:59 น.", txtCentreDetail));
            s.addCell(new Label(4, 6, "00:00-21:00 น.", txtCentreDetail));
            s.addCell(new Label(5, 6, "รวม", txtCentreDetail));

            s.addCell(new Label(6, 5, "รายได้ค่าผ่านทาง EMV. (กทพ.)", txtHeader5));
            s.mergeCells(6, 5, 9, 5);
            s.addCell(new Label(6, 6, "00:00-21:00 น.", txtCentreDetail));
            s.addCell(new Label(7, 6, "21:01-23:59 น.", txtCentreDetail));
            s.addCell(new Label(8, 6, "รวม", txtCentreDetail));
            s.addCell(new Label(9, 6, "ปรับปรุง", txtCentreDetail));
//            s.addCell(new Label(6, 5, "รวม", txtHeader5));
//            s.mergeCells(6, 5, 6, 6);

            ResultSet resultData = connector.executeQuery(sqlQueryData);
            int rowNum = 7;
            while (resultData.next()) {
                s.addCell(new Label(1, rowNum, resultData.getString(3), txtCentreDetail));
                s.addCell(new Label(2, rowNum, resultData.getString(2), txtCentreDetail));
                s.addCell(new jxl.write.Number(3, rowNum, resultData.getInt("BANK_RMT_AMOUNT_T2"), tDouble));
                s.addCell(new jxl.write.Number(4, rowNum, resultData.getInt("BANK_RMT_AMOUNT_T1"), tDouble));
                s.addCell(new jxl.write.Formula(5, rowNum, "SUM(D" + (rowNum + 1) + ", E" + (rowNum + 1) + ")", tDouble));
                s.addCell(new jxl.write.Number(6, rowNum, resultData.getInt("DELIVER_RMT_AMOUNT_T1"), tDouble));
                s.addCell(new jxl.write.Number(7, rowNum, resultData.getInt("DELIVER_RMT_AMOUNT_T2"), tDouble));
                s.addCell(new jxl.write.Formula(8, rowNum, "SUM(G" + (rowNum + 1) + ", H" + (rowNum + 1) + ")", tDouble));
                s.addCell(new jxl.write.Number(9, rowNum, resultData.getInt("ADJ_AMOUNT"), tDouble));
//                s.addCell(new jxl.write.Formula(6, rowNum, "SUM(F" + (rowNum + 1) + ")", tDouble));
                rowNum++;
            }
            s.addCell(new Label(1, rowNum, "รวม", txtCentreDetail));
            s.mergeCells(1, rowNum, 2, rowNum);
            s.addCell(new jxl.write.Formula(3, rowNum, "SUM(D8: D" + rowNum + ")", tDouble));
            s.addCell(new jxl.write.Formula(4, rowNum, "SUM(E8: E" + rowNum + ")", tDouble));
            s.addCell(new jxl.write.Formula(5, rowNum, "SUM(F8: F" + rowNum + ")", tDouble));
            s.addCell(new jxl.write.Formula(6, rowNum, "SUM(G8: G" + rowNum + ")", tDouble));
            s.addCell(new jxl.write.Formula(7, rowNum, "SUM(H8: H" + rowNum + ")", tDouble));
            s.addCell(new jxl.write.Formula(8, rowNum, "SUM(I8: I" + rowNum + ")", tDouble));
            s.addCell(new jxl.write.Formula(9, rowNum, "SUM(J8: J" + rowNum + ")", tDouble));
//            s.addCell(new jxl.write.Formula(6, rowNum, "SUM(G8: G" + rowNum + ")", tDouble));
        } finally {
            connector.close();
            connectorHelper.close();
        }
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

    private String getLineType(String lineCode) throws Exception {
        Connector connector = new Connector();
        try {
            connector.connectEta();
            String sqlQuery = "SELECT LINE_TYPE FROM RVA_MST_LINE WHERE LINE_CODE = '" + lineCode + "'";
            ResultSet result = connector.executeQuery(sqlQuery);
            if (!result.next()) {
                throw new Exception("ไม่พบข้อมูลสายทางพิเศษ");
            }
            return result.getString("LINE_TYPE");
        } finally {
            connector.close();
        }
    }
}
