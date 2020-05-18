/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package R302A;

import Connect.Connector;
import java.io.File;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
public class R302AService {

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
        fDou = new NumberFormat("#,##0.00");
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
        this.fd = util.DateUtil.convertFormat(request.getParameter("date"), "yyyy-MM-dd");
        this.fdLocalFmt = request.getParameter("date");
        if (!(new File(this.p)).exists()) {
            (new File(this.p)).mkdirs();
        }
        String lineCode = request.getParameter("lineCode");
        this.lineDsc = getLineDsc(lineCode);
        this.lineType = getLineType(lineCode);
        File fileReport = new File(this.p + "/รายงานสรุปรายได้ค่าผ่านทาง EMV " + fd + ".xls");
        WritableWorkbook workbook = Workbook.createWorkbook(fileReport);
        WritableSheet shtSummary = workbook.createSheet("รายงานสรุปรายได้ค่าผ่านทาง EMV.", 0);
        writeCoverHead(shtSummary, "รายงานสรุปรายได้ค่าผ่านทาง EMV. ตามยอดนำส่งธนาคาร ทางพิเศษ" + this.lineDsc);
        writeDataSummary(shtSummary, request.getParameter("date"), lineCode);
        workbook.write();
        workbook.close();
        return fileReport.getName();
    }

    public void writeCoverHead(WritableSheet s, String headName) throws WriteException, Exception {
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
                + util.DateUtil.convertFormat(fdLocalFmt, "dd") + " " + util.DateUtil.getMonthTh(Integer.parseInt(util.DateUtil.convertFormat(fdLocalFmt, "MM")))
                + " " + util.DateUtil.convertFormatYear(fdLocalFmt, "yyyy"), txtHeader3));
        s.mergeCells(1, 3, 9, 3);
    }

    public void writeDataSummary(WritableSheet s, String date, String lineCode) throws Exception {
        // date format dd/MM/yyyy
        Connector connector = new Connector();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date presentDate = sdf.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(presentDate);
            calendar.add(Calendar.DATE, -1);
            connector.connectEta();
            String sqlLineType = this.lineType.equals("O") ? "RVA_MST_CHRG_OPN CHRG ON CHRG.STATION_CODE " : " RVA_MST_CHRG_CLS CHRG ON CHRG.EXT_STT_CODE ";
            String sqlQueryData = "SELECT STT.STATION_CODE, STT.STATION_DSC, STT.SAP_STT_CODE, SUM(NVL(TRX.RMT_AMOUNT_T1, 0)) AS RMT_AMOUNT_T1, SUM(NVL(TRX.RMT_AMOUNT_T2, 0)) AS RMT_AMOUNT_T2\n"
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
                    + "	ORDER BY STT.STATION_CODE\n"
                    + ") STT\n"
                    + "LEFT JOIN (\n"
                    + "	SELECT BANK.STATION_CODE, BANK.RMT_AMOUNT AS RMT_AMOUNT_T1, 0 AS RMT_AMOUNT_T2\n"
                    + "	FROM RVA_TRX_BANK_COUNT BANK\n"
                    + "\n"
                    + "	WHERE TRX_DATE = TO_DATE('" + date + "', 'dd/MM/yyyy')\n"
                    + "	AND EMP_CODE = 'EMP0000130'\n"
                    + "	AND REV_TYPE = 'TOLL'\n"
                    + "	AND AUDIT_STATUS = 'Y'\n"
                    + "	GROUP BY BANK.STATION_CODE, BANK.RMT_AMOUNT\n"
                    + "	UNION ALL\n"
                    + "	SELECT BANK.STATION_CODE, 0 AS RMT_AMOUNT_T1, BANK.RMT_AMOUNT AS RMT_AMOUNT_T2\n"
                    + "	FROM RVA_TRX_BANK_COUNT BANK\n"
                    + "	WHERE TRX_DATE = TO_DATE('" + date + "', 'dd/MM/yyyy')\n"
                    + "	AND EMP_CODE = 'EMP0000131'\n"
                    + "	AND REV_TYPE = 'TOLL'\n"
                    + "	AND AUDIT_STATUS = 'Y'\n"
                    + "	GROUP BY BANK.STATION_CODE, BANK.RMT_AMOUNT\n"
                    + ") TRX ON STT.STATION_CODE = TRX.STATION_CODE\n"
                    + "GROUP BY STT.STATION_CODE, STT.SAP_STT_CODE, STT.STATION_DSC\n"
                    + "ORDER BY STT.SAP_STT_CODE";
            s.addCell(new Label(1, 5, "รหัสด่าน", txtHeader5));
            s.mergeCells(1, 5, 1, 6);
            s.addCell(new Label(2, 5, "ชื่อด่าน", txtHeader5));
            s.mergeCells(2, 5, 2, 6);
            s.addCell(new Label(3, 5, "รายได้ค่าผ่านทาง EMV.", txtHeader5));
            s.mergeCells(3, 5, 5, 5);
            s.addCell(new Label(3, 6, "21:01-23:59 น.", txtCentreDetail));
            s.addCell(new Label(4, 6, "00:00-21:00 น.", txtCentreDetail));
            s.addCell(new Label(5, 6, "รวม", txtCentreDetail));
            s.addCell(new Label(6, 5, "รวม", txtHeader5));
            s.mergeCells(6, 5, 6, 6);
            ResultSet resultData = connector.executeQuery(sqlQueryData);
            int rowNum = 7;
            while (resultData.next()) {
                s.addCell(new Label(1, rowNum, resultData.getString(3), txtCentreDetail));
                s.addCell(new Label(2, rowNum, resultData.getString(2), txtCentreDetail));
                s.addCell(new jxl.write.Number(3, rowNum, resultData.getInt("RMT_AMOUNT_T2"), tDouble));
                s.addCell(new jxl.write.Number(4, rowNum, resultData.getInt("RMT_AMOUNT_T1"), tDouble));
                s.addCell(new jxl.write.Formula(5, rowNum, "SUM(D" + (rowNum + 1) + ", E" + (rowNum + 1) + ")", tDouble));
                s.addCell(new jxl.write.Formula(6, rowNum, "SUM(F" + (rowNum + 1) + ")", tDouble));
                rowNum++;
            }
            s.addCell(new Label(1, rowNum, "รวม", txtCentreDetail));
            s.mergeCells(1, rowNum, 2, rowNum);
            s.addCell(new jxl.write.Formula(3, rowNum, "SUM(D8: D" + rowNum + ")", tDouble));
            s.addCell(new jxl.write.Formula(4, rowNum, "SUM(E8: E" + rowNum + ")", tDouble));
            s.addCell(new jxl.write.Formula(5, rowNum, "SUM(F8: F" + rowNum + ")", tDouble));
            s.addCell(new jxl.write.Formula(6, rowNum, "SUM(G8: G" + rowNum + ")", tDouble));
        } finally {
            connector.close();
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
