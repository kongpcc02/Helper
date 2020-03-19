/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package R301;

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
public class R301Service {

    public String fd = "";
    public String fdLocalFmt = "";
    public String p = "";
    public String pImg = "";
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
        File fileReport = new File(this.p + "/รายงานสรุปรายได้ค่าผ่านทาง EMV ตามยอดนำส่งธนาคาร " + fd + ".xls");
        WritableWorkbook workbook = Workbook.createWorkbook(fileReport);
        WritableSheet shtSummary = workbook.createSheet("รายงานสรุปรายได้ค่าผ่านทาง EMV.", 0);
        writeCoverHead(shtSummary, "รายงานสรุปรายได้ค่าผ่านทาง EMV. ตามยอดนำส่งธนาคาร ทางพิเศษกาญจนาภิเษก (บางพลี - สุขสวัสดิ์)");
        writeDataSummary(shtSummary, request.getParameter("date"));
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

    public void writeDataSummary(WritableSheet s, String date) throws Exception {
        // date format dd/MM/yyyy
        Connector connector = new Connector();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date presentDate = sdf.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(presentDate);
            calendar.add(Calendar.DATE, -1);
            String pastDate = sdf.format(calendar.getTime());
            connector.connectEta();
            String sqlQueryData = "select stt.STATION_CODE, STATION_DSC, SAP_STT_CODE\n"
                    + ", NVL(SUM(dtl.REV_CASH_T1) - SUM(dtl.com_rev_cash_T1), 0) as rev_cash_t1, NVL(SUM(dtl.com_rev_cash_T1), 0) as com_rev_cash_t1\n"
                    + ", NVL(SUM(dtl.REV_CASH_T2) - SUM(dtl.com_rev_cash_T2), 0) as rev_cash_t2, NVL(SUM(dtl.com_rev_cash_T2), 0) as com_rev_cash_t2\n"
                    + "from RVA_MST_SCT_STT stt \n"
                    + "LEFT JOIN (\n"
                    + "	select STATION_CODE, sum(rev_cash_t1) as REV_CASH_T1, sum(com_rev_t1) as com_rev_cash_T1, sum(rev_cash_t2) as REV_CASH_T2, sum(com_rev_t2) as com_rev_cash_T2\n"
                    + "	from (\n"
                    + "	--T1\n"
                    + "		select STATION_CODE\n"
                    + "		, REV_CASH as rev_cash_t1\n"
                    + "		, (NVL(COM_REV_TYPE1, 0) + NVL(COM_REV_TYPE2, 0) + NVL(COM_REV_TYPE3, 0) + NVL(COM_REV_10WX, 0)) as com_rev_t1\n"
                    + "		, 0 as  rev_cash_t2\n"
                    + "		, 0 as com_rev_t2\n"
                    + "		from RVA_TRX_TOLL toll\n"
                    + "		LEFT JOIN RVA_TRX_TOLL_DTL dtl on DTL.TOLL_ID = TOLL.TOLL_ID\n"
                    + "		where TRX_DATE = to_date('" + date + "', 'dd/MM/yyyy') \n"
                    + "		and EMP_CODE in (\n"
                    + "			select EMP_CODE\n"
                    + "			from RVA_MST_EMP\n"
                    + "			where hr_emp_code = '1666666661'\n"
                    + "		)\n"
                    + "		and toll.audit_status = 'Y'\n"
                    + "		union all \n"
                    + "		--T2\n"
                    + "		select STATION_CODE\n"
                    + "		, 0 as rev_cash_t1\n"
                    + "		, 0 as com_rev_t1\n"
                    + "		, REV_CASH as  rev_cash_t2\n"
                    + "		, (NVL(COM_REV_TYPE1, 0) + NVL(COM_REV_TYPE2, 0) + NVL(COM_REV_TYPE3, 0) + NVL(COM_REV_10WX, 0)) as com_rev_t2\n"
                    + "		from RVA_TRX_TOLL toll\n"
                    + "		LEFT JOIN RVA_TRX_TOLL_DTL dtl on DTL.TOLL_ID = TOLL.TOLL_ID\n"
                    + "		where TRX_DATE = to_date('" + pastDate + "', 'dd/MM/yyyy')\n"
                    + "		and EMP_CODE in (\n"
                    + "			select EMP_CODE\n"
                    + "			from RVA_MST_EMP\n"
                    + "			where hr_emp_code = '1666666662'\n"
                    + "		)\n"
                    + "		and toll.audit_status = 'Y'\n"
                    + "	)\n"
                    + "	GROUP BY STATION_CODE\n"
                    + ") dtl on dtl.station_code = stt.station_code\n"
                    + "where stt.STATION_CODE in (\n"
                    + "		select EXT_STT_CODE\n"
                    + "		from RVA_MST_CHRG_CLS\n"
                    + "		where EXT_STT_CODE like '6%'\n"
                    + "		and END_DATE > sysdate\n"
                    + "		group by EXT_STT_CODE\n"
                    + ")\n"
                    + "and STATION_DSC not like '%โปรโมชั่น%'\n"
                    + "group by stt.STATION_CODE, STATION_DSC, SAP_STT_CODE\n"
                    + "order by SAP_STT_CODE";
            s.addCell(new Label(1, 5, "รหัสด่าน", txtHeader5));
            s.mergeCells(1, 5, 1, 6);
            s.addCell(new Label(2, 5, "ชื่อด่าน", txtHeader5));
            s.mergeCells(2, 5, 2, 6);
            s.addCell(new Label(3, 5, "รายได้ค่าผ่านทาง EMV.", txtHeader5));
            s.mergeCells(3, 5, 5, 5);
            s.addCell(new Label(3, 6, "21:01-23:59 น.", txtCentreDetail));
            s.addCell(new Label(4, 6, "00:00-21:00 น.", txtCentreDetail));
            s.addCell(new Label(5, 6, "รวม", txtCentreDetail));
            s.addCell(new Label(6, 5, "รายได้กรมทางหลวง EMV.", txtHeader5));
            s.mergeCells(6, 5, 8, 5);
            s.addCell(new Label(6, 6, "21:01-23:59 น.", txtCentreDetail));
            s.addCell(new Label(7, 6, "00:00-21:00 น.", txtCentreDetail));
            s.addCell(new Label(8, 6, "รวม", txtCentreDetail));
            s.addCell(new Label(9, 5, "รวม", txtHeader5));
            s.mergeCells(9, 5, 9, 6);
            ResultSet resultData = connector.executeQuery(sqlQueryData);
            int rowNum = 7;
            while (resultData.next()) {
                s.addCell(new Label(1, rowNum, resultData.getString(3), txtCentreDetail));
                s.addCell(new Label(2, rowNum, resultData.getString(2), txtCentreDetail));
                s.addCell(new jxl.write.Number(3, rowNum, resultData.getInt("rev_cash_t2"), tDouble));
                s.addCell(new jxl.write.Number(4, rowNum, resultData.getInt("rev_cash_t1"), tDouble));
                s.addCell(new jxl.write.Formula(5, rowNum, "SUM(D" + (rowNum + 1) + ", E" + (rowNum + 1) + ")", tDouble));
                s.addCell(new jxl.write.Number(6, rowNum, resultData.getInt("com_rev_cash_t2"), tDouble));
                s.addCell(new jxl.write.Number(7, rowNum, resultData.getInt("com_rev_cash_t1"), tDouble));
                s.addCell(new jxl.write.Formula(8, rowNum, "SUM(G" + (rowNum + 1) + ", H" + (rowNum + 1) + ")", tDouble));
                s.addCell(new jxl.write.Formula(9, rowNum, "SUM(F" + (rowNum + 1) + ", I" + (rowNum + 1) + ")", tDouble));
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
            s.addCell(new jxl.write.Formula(9, rowNum, "SUM(F" + (rowNum + 1) + ", I" + (rowNum + 1) + ")", tDouble));
        } finally {
            connector.close();
        }
    }
}
