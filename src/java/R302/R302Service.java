/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package R302;

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
public class R302Service {

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
        File fileReport = new File(this.p + "/รายงานสรุปรายได้ค่าผ่านทาง EMV " + fd + ".xls");
        WritableWorkbook workbook = Workbook.createWorkbook(fileReport);
        WritableSheet shtSummary = workbook.createSheet("รายงานสรุปรายได้ค่าผ่านทาง EMV.", 0);
        writeCoverHead(shtSummary, "รายงานสรุปรายได้ค่าผ่านทาง EMV. ทางพิเศษกาญจนาภิเษก (บางพลี - สุขสวัสดิ์)");
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
                    + "	, NVL(rv4w_T1, 0) + NVL(rv6w_T1, 0) + NVL(rv8w_T1, 0) + NVL(rv10w_T1, 0) as rev_cash_t1\n"
                    + "	, NVL(com_rv4w_T1, 0) + NVL(com_rv6w_T1, 0) + NVL(com_rv8w_T1, 0) + NVL(com_rv10w_T1, 0) as com_rev_cash_t1\n"
                    + "	, NVL(rv4w_T2, 0) + NVL(rv6w_T2, 0) + NVL(rv8w_T2, 0) + NVL(rv10w_T2, 0) as rev_cash_t2\n"
                    + "	, NVL(com_rv4w_T2, 0) + NVL(com_rv6w_T2, 0) + NVL(com_rv8w_T2, 0) + NVL(com_rv10w_T2, 0) as com_rev_cash_t2\n"
                    + "from RVA_MST_SCT_STT stt \n"
                    + "LEFT JOIN (\n"
                    + "	select EXT_STT_CODE as station_code\n"
                    + "		, SUM(NVL((case  when com_code is not null then (rv4w - (trf4w * com_rev_4W)) ELSE rv4w end ), 0)) as rv4w_T1\n"
                    + "		,	SUM(NVL((case  when com_code is not null then (rv6w - (trf6w * com_rev_6W)) ELSE rv6w end ), 0)) as rv6w_T1\n"
                    + "		,	SUM(NVL((case  when com_code is not null then (rv8w - (trf8w * COM_REV_10WX)) ELSE rv8w end ), 0)) as rv8w_T1\n"
                    + "		,	SUM(NVL((case  when com_code is not null then (rv10w - (trf10w * com_rev_10W)) ELSE rv10w end ), 0)) as rv10w_T1\n"
                    + "		,	SUM(NVL((case  when com_code is not null then (trf4w * com_rev_4W) ELSE 0 end ), 0)) as com_rv4w_T1\n"
                    + "		,	SUM(NVL((case  when com_code is not null then (trf6w * com_rev_6W) ELSE 0 end ), 0)) as com_rv6w_T1\n"
                    + "		,	SUM(NVL((case  when com_code is not null then (trf8w * COM_REV_10WX) ELSE 0 end ), 0)) as com_rv8w_T1\n"
                    + "		,	SUM(NVL((case  when com_code is not null then (trf10w * com_rev_10W) ELSE 0 end ), 0)) as com_rv10w_T1\n"
                    + "		, 0 as rv4w_T2\n"
                    + "		,	0 as rv6w_T2\n"
                    + "		,	0 as rv8w_T2\n"
                    + "		,	0 as rv10w_T2\n"
                    + "		,	0 as com_rv4w_T2\n"
                    + "		,	0 as com_rv6w_T2\n"
                    + "		,	0 as com_rv8w_T2\n"
                    + "		,	0 as com_rv10w_T2\n"
                    + "	from (\n"
                    + "		select toll.*, CHRG.COM_CODE, CHRG.COM_REV_4W, CHRG.COM_REV_6W, CHRG.COM_REV_10WX, CHRG.COM_REV_10W\n"
                    + "		from (\n"
                    + "			select EXT_STT_CODE, ENT_STT_CODE, TRX_DATE, EMP_CODE, SHF_CODE\n"
                    + "				,	sum(trf4w) as trf4w\n"
                    + "				,	sum(trf6w) as trf6w\n"
                    + "				,	sum(trf8w) as trf8w\n"
                    + "				,	sum(trf10w) as trf10w\n"
                    + "				,	sum(rv4w) as rv4w\n"
                    + "				,	sum(rv6w) as rv6w\n"
                    + "				,	sum(rv8w) as rv8w\n"
                    + "				,	sum(rv10w) as rv10w\n"
                    + "			from (\n"
                    + "				select \n"
                    + "					EXT_STT_CODE, ENT_STT_CODE, TRX_DATE, EMP_CODE, SHF_CODE\n"
                    + "					, TRF_TYPE1 + TRF_TYPE2 as trf4w\n"
                    + "					, TRF_TYPE3 + TRF_TYPE7 + TRF_TYPE8 as trf6w\n"
                    + "					, TRF_TYPE6 as trf8w\n"
                    + "					, TRF_TYPE4 as trf10w\n"
                    + "					,	0 as rv4w\n"
                    + "					, 0 as rv6w\n"
                    + "					,	0 as rv8w\n"
                    + "					,	0 as rv10w\n"
                    + "				from RVA_TRX_TRF_CLS\n"
                    + "				where TRX_DATE = to_date('" + date + "', 'dd/MM/yyyy')\n"
                    + "				and EMP_CODE = 'EMP0000130'\n"
                    + "				UNION ALL\n"
                    + "				select \n"
                    + "					EXT_STT_CODE, ENT_STT_CODE, TRX_DATE, EMP_CODE, SHF_CODE\n"
                    + "					, 0 as trf4w\n"
                    + "					, 0 as trf6w\n"
                    + "					, 0 as trf8w\n"
                    + "					, 0 as trf10w\n"
                    + "					,	RV_TYPE1 + RV_TYPE2 as rv4w\n"
                    + "					,	RV_TYPE3 + RV_TYPE7 + RV_TYPE8 as rv6w\n"
                    + "					,	RV_TYPE6 as rv8w\n"
                    + "					,	RV_TYPE4 as rv10w\n"
                    + "				from RVA_TRX_REV_CLS\n"
                    + "				where TRX_DATE = to_date('" + date + "', 'dd/MM/yyyy')\n"
                    + "				and EMP_CODE = 'EMP0000130'\n"
                    + "			)\n"
                    + "			group by EXT_STT_CODE, ENT_STT_CODE, TRX_DATE, EMP_CODE, SHF_CODE\n"
                    + "		) toll\n"
                    + "		LEFT JOIN (\n"
                    + "				select EXT_STT_CODE, ENT_STT_CODE, COM_CODE, COM_REV_4W, COM_REV_6W, COM_REV_10WX, COM_REV_10W\n"
                    + "				from RVA_MST_CHRG_CLS\n"
                    + "				where EXT_STT_CODE like '6%'\n"
                    + "				and END_DATE > sysdate\n"
                    + "				group by EXT_STT_CODE, ENT_STT_CODE, COM_CODE, COM_REV_4W, COM_REV_6W, COM_REV_10WX, COM_REV_10W\n"
                    + "		) chrg on chrg.EXT_STT_CODE = TOLL.EXT_STT_CODE and CHRG.ENT_STT_CODE = toll.ENT_STT_CODE\n"
                    + "	)\n"
                    + "	group by EXT_STT_CODE\n"
                    + "	UNION ALL\n"
                    + "	select EXT_STT_CODE as station_code\n"
                    + "		, 0 as rv4w_T1\n"
                    + "		,	0 as rv6w_T1\n"
                    + "		,	0 as rv8w_T1\n"
                    + "		,	0 as rv10w_T1\n"
                    + "		,	0 as com_rv4w_T1\n"
                    + "		,	0 as com_rv6w_T1\n"
                    + "		,	0 as com_rv8w_T1\n"
                    + "		,	0 as com_rv10w_T1\n"
                    + "		, SUM(NVL((case  when com_code is not null then (rv4w - (trf4w * com_rev_4W)) ELSE rv4w end ), 0)) as rv4w_T2\n"
                    + "		,	SUM(NVL((case  when com_code is not null then (rv6w - (trf6w * com_rev_6W)) ELSE rv6w end ), 0)) as rv6w_T2\n"
                    + "		,	SUM(NVL((case  when com_code is not null then (rv8w - (trf8w * COM_REV_10WX)) ELSE rv8w end ), 0)) as rv8w_T2\n"
                    + "		,	SUM(NVL((case  when com_code is not null then (rv10w - (trf10w * com_rev_10W)) ELSE rv10w end ), 0)) as rv10w_T2\n"
                    + "		,	SUM(NVL((case  when com_code is not null then (trf4w * com_rev_4W) ELSE 0 end ), 0)) as com_rv4w_T2\n"
                    + "		,	SUM(NVL((case  when com_code is not null then (trf6w * com_rev_6W) ELSE 0 end ), 0)) as com_rv6w_T2\n"
                    + "		,	SUM(NVL((case  when com_code is not null then (trf8w * COM_REV_10WX) ELSE 0 end ), 0)) as com_rv8w_T2\n"
                    + "		,	SUM(NVL((case  when com_code is not null then (trf10w * com_rev_10W) ELSE 0 end ), 0)) as com_rv10w_T2\n"
                    + "	from (\n"
                    + "		select toll.*, CHRG.COM_CODE, CHRG.COM_REV_4W, CHRG.COM_REV_6W, CHRG.COM_REV_10WX, CHRG.COM_REV_10W\n"
                    + "		from (\n"
                    + "			select EXT_STT_CODE, ENT_STT_CODE, TRX_DATE, EMP_CODE, SHF_CODE\n"
                    + "				,	sum(trf4w) as trf4w\n"
                    + "				,	sum(trf6w) as trf6w\n"
                    + "				,	sum(trf8w) as trf8w\n"
                    + "				,	sum(trf10w) as trf10w\n"
                    + "				,	sum(rv4w) as rv4w\n"
                    + "				,	sum(rv6w) as rv6w\n"
                    + "				,	sum(rv8w) as rv8w\n"
                    + "				,	sum(rv10w) as rv10w\n"
                    + "			from (\n"
                    + "				select \n"
                    + "					EXT_STT_CODE, ENT_STT_CODE, TRX_DATE, EMP_CODE, SHF_CODE\n"
                    + "					, TRF_TYPE1 + TRF_TYPE2 as trf4w\n"
                    + "					, TRF_TYPE3 + TRF_TYPE7 + TRF_TYPE8 as trf6w\n"
                    + "					, TRF_TYPE6 as trf8w\n"
                    + "					, TRF_TYPE4 as trf10w\n"
                    + "					,	0 as rv4w\n"
                    + "					, 0 as rv6w\n"
                    + "					,	0 as rv8w\n"
                    + "					,	0 as rv10w\n"
                    + "				from RVA_TRX_TRF_CLS\n"
                    + "				where TRX_DATE = to_date('" + date + "', 'dd/MM/yyyy')\n"
                    + "				and EMP_CODE = 'EMP0000131'\n"
                    + "				UNION ALL\n"
                    + "				select \n"
                    + "					EXT_STT_CODE, ENT_STT_CODE, TRX_DATE, EMP_CODE, SHF_CODE\n"
                    + "					, 0 as trf4w\n"
                    + "					, 0 as trf6w\n"
                    + "					, 0 as trf8w\n"
                    + "					, 0 as trf10w\n"
                    + "					,	RV_TYPE1 + RV_TYPE2 as rv4w\n"
                    + "					,	RV_TYPE3 + RV_TYPE7 + RV_TYPE8 as rv6w\n"
                    + "					,	RV_TYPE6 as rv8w\n"
                    + "					,	RV_TYPE4 as rv10w\n"
                    + "				from RVA_TRX_REV_CLS\n"
                    + "				where TRX_DATE = to_date('" + date + "', 'dd/MM/yyyy')\n"
                    + "				and EMP_CODE = 'EMP0000131'\n"
                    + "			)\n"
                    + "			group by EXT_STT_CODE, ENT_STT_CODE, TRX_DATE, EMP_CODE, SHF_CODE\n"
                    + "		) toll\n"
                    + "		LEFT JOIN (\n"
                    + "				select EXT_STT_CODE, ENT_STT_CODE, COM_CODE, COM_REV_4W, COM_REV_6W, COM_REV_10WX, COM_REV_10W\n"
                    + "				from RVA_MST_CHRG_CLS\n"
                    + "				where EXT_STT_CODE like '6%'\n"
                    + "				and END_DATE > sysdate\n"
                    + "				group by EXT_STT_CODE, ENT_STT_CODE, COM_CODE, COM_REV_4W, COM_REV_6W, COM_REV_10WX, COM_REV_10W\n"
                    + "		) chrg on chrg.EXT_STT_CODE = TOLL.EXT_STT_CODE and CHRG.ENT_STT_CODE = toll.ENT_STT_CODE\n"
                    + "	)\n"
                    + "	group by EXT_STT_CODE\n"
                    + ") dtl on dtl.station_code = stt.station_code\n"
                    + "where stt.STATION_CODE in (\n"
                    + "		select EXT_STT_CODE\n"
                    + "		from RVA_MST_CHRG_CLS\n"
                    + "		where EXT_STT_CODE like '6%'\n"
                    + "		and END_DATE > sysdate\n"
                    + "		group by EXT_STT_CODE\n"
                    + ")\n"
                    + "and STATION_DSC not like '%โปรโมชั่น%'\n"
                    + "order by SAP_STT_CODE";
            s.addCell(new Label(1, 5, "รหัสด่าน", txtHeader5));
            s.mergeCells(1, 5, 1, 6);
            s.addCell(new Label(2, 5, "ชื่อด่าน", txtHeader5));
            s.mergeCells(2, 5, 2, 6);
            s.addCell(new Label(3, 5, "รายได้ค่าผ่านทาง EMV.", txtHeader5));
            s.mergeCells(3, 5, 5, 5);
            s.addCell(new Label(3, 6, "00:00-21:00 น.", txtCentreDetail));
            s.addCell(new Label(4, 6, "21:01-23:59 น.", txtCentreDetail));
            s.addCell(new Label(5, 6, "รวม", txtCentreDetail));
            s.addCell(new Label(6, 5, "รายได้กรมทางหลวง EMV.", txtHeader5));
            s.mergeCells(6, 5, 8, 5);
            s.addCell(new Label(6, 6, "00:00-21:00 น.", txtCentreDetail));
            s.addCell(new Label(7, 6, "21:01-23:59 น.", txtCentreDetail));
            s.addCell(new Label(8, 6, "รวม", txtCentreDetail));
            s.addCell(new Label(9, 5, "รวม", txtHeader5));
            s.mergeCells(9, 5, 9, 6);
            ResultSet resultData = connector.executeQuery(sqlQueryData);
            int rowNum = 7;
            while (resultData.next()) {
                s.addCell(new Label(1, rowNum, resultData.getString(3), txtCentreDetail));
                s.addCell(new Label(2, rowNum, resultData.getString(2), txtCentreDetail));
                s.addCell(new jxl.write.Number(3, rowNum, resultData.getInt("rev_cash_t1"), tDouble));
                s.addCell(new jxl.write.Number(4, rowNum, resultData.getInt("rev_cash_t2"), tDouble));
                s.addCell(new jxl.write.Formula(5, rowNum, "SUM(D" + (rowNum + 1) + ", E" + (rowNum + 1) + ")", tDouble));
                s.addCell(new jxl.write.Number(6, rowNum, resultData.getInt("com_rev_cash_t1"), tDouble));
                s.addCell(new jxl.write.Number(7, rowNum, resultData.getInt("com_rev_cash_t2"), tDouble));
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
