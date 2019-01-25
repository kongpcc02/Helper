
import java.sql.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import th.co.exat.helper.System.EtaConnectionFactory;

/**
 * สำหรับไฟล์ E3
 *
 * @author siridet_suk
 */
public class E3 {

    private static String date;
    private static String dd;
    private static String mm = "06";
    private static String yyyy = "2012";
    private static String[] lineArr = {"4"};
    private static String line;
    private static String profitCenter;
    private static int days;

    public static void main(String[] args) throws Exception {
        for (int l = 0; l < lineArr.length; l++) {
            line = lineArr[l];

            if (line.equals("1")) {
                profitCenter = "0010100000";
            }
            if (line.equals("2")) {
                profitCenter = "0020100000";
            }
            if (line.equals("3")) {
                profitCenter = "0010200000";
            }
            if (line.equals("4")) {
                profitCenter = "0010300000";
            }
            if (line.equals("6")) {
                profitCenter = "0010600000";
            }
            if (line.equals("99")) {
                profitCenter = "0050100000";
            }
            Calendar cal = new GregorianCalendar(Integer.parseInt(yyyy), Integer.parseInt(mm) - 1, 1);
            days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);     // 29

            for (int i = 1; i <= days; i++) {
                //if (i == 25) {
                Connection conn = EtaConnectionFactory.getConnection();
                Statement stmt = conn.createStatement();
                dd = String.valueOf(i);

                if (i < 10) {
                    dd = "0" + dd;
                }
                date = dd + mm + yyyy;

                java.sql.ResultSet rs = stmt.executeQuery(getSQL());

                FileWriter fstream = new FileWriter("d://" + line + "/SAP_" + ((line.length() == 1) ? "0" + line : line) + "_GL_E3_" + yyyy + "" + mm + "" + dd + ".txt");
                BufferedWriter out = new BufferedWriter(fstream);

                while (rs.next()) {
                    if (!rs.getString("g").equals("0")) {
                        out.write(rs.getString("a") + "\t" + date + "\t" + rs.getString("c") + "\t" + rs.getString("d") + "\t" + rs.getString("e") + "\t" + rs.getString("f") + "\t" + rs.getString("g") + "\t" + rs.getString("h") + "\t" + rs.getString("i") + "\t" + "" + "\t" + rs.getString("k") + "\t" + rs.getString("l") + "\n");
                    }
                }
                out.close();
                fstream.close();
                rs.close();
                stmt.close();
                conn.close();
                //  System.out.println(date);
                // System.out.println(getSQL());
                //}
            }
        }
    }

    public static String getSQL() {
        // อื่นๆ รหัส บช เป็น OTH
        String sql = "select '0010' as a," + date + " as b,'OTH" + date + ((line.length() == 1) ? line + "0" : line) + "' as c,'I' as d,'D' as e,'9200230' as f,sum(rmt.COST) as g,'Z4' as h,'0010' as i,'' as j,'" + profitCenter + "' as k ,'OTH" + date + ((line.length() == 1) ? line + "0" : line) + "' as l from rva_trx_etc_trx  rmt ";
        sql += " inner join rva_trx_etc_master m on rmt.SHIFT_ID = m.shift_id ";
        sql += " inner join rva_mst_etc_pos pos on m.POS_ID = pos.pos_id ";
        sql += " where trx_date = to_date('" + date + "','ddmmyyyy')  ";
        sql += " and service_id in (3,5) ";
        sql += " and pos.line_code = " + line + " ";
        sql += " group by  pos.line_code union all ";

        sql += "select '0010'," + date + ",'OTH" + date + ((line.length() == 1) ? line + "0" : line) + "','I','C','4299990',sum(rmt.EXVAT),'Z4','0010','',sap_pos_code ,'OTH" + date + ((line.length() == 1) ? line + "0" : line) + "' from rva_trx_etc_trx  rmt ";
        sql += " inner join rva_trx_etc_master m on rmt.SHIFT_ID = m.shift_id ";
        sql += " inner join rva_mst_etc_pos pos on m.POS_ID = pos.pos_id ";
        sql += " where trx_date = to_date('" + date + "','ddmmyyyy')  ";
        sql += " and service_id in (3,5) ";
        sql += " and pos.line_code = " + line + " ";
        sql += " group by  sap_pos_code   union all ";

        sql += "select '0010'," + date + ",'OTH" + date + ((line.length() == 1) ? line + "0" : line) + "','I','C','2199020',sum(rmt.VAT),'Z4','0010','','" + profitCenter + "','OTH" + date + ((line.length() == 1) ? line + "0" : line) + "' from rva_trx_etc_trx  rmt ";
        sql += " inner join rva_trx_etc_master m on rmt.SHIFT_ID = m.shift_id ";
        sql += " inner join rva_mst_etc_pos pos on m.POS_ID = pos.pos_id ";
        sql += " where trx_date = to_date('" + date + "','ddmmyyyy')  ";
        sql += " and service_id in (3,5) ";
        sql += " and pos.line_code = " + line + " ";
        sql += " group by  pos.line_code union all ";

        // ขายบัตรสมาร์ตการ์ด รหัส บช เป็น SMC
        sql += "select '0010' as a," + date + " as b,'SMC" + date + ((line.length() == 1) ? line + "0" : line) + "' as c,'I' as d,'D' as e,'9200230' as f,sum(rmt.COST) as g,'Z4' as h,'0010' as i,'' as j,'" + profitCenter + "' as k ,'SMC" + date + ((line.length() == 1) ? line + "0" : line) + "' as l from rva_trx_etc_trx  rmt ";
        sql += " inner join rva_trx_etc_master m on rmt.SHIFT_ID = m.shift_id ";
        sql += " inner join rva_mst_etc_pos pos on m.POS_ID = pos.pos_id ";
        sql += " where trx_date = to_date('" + date + "','ddmmyyyy')  ";
        sql += " and service_id =4 ";
        sql += " and pos.line_code = " + line + " ";
        sql += " group by  pos.line_code union all ";

        sql += "select '0010'," + date + ",'SMC" + date + ((line.length() == 1) ? line + "0" : line) + "','I','C','4299990',sum(rmt.EXVAT),'Z4','0010','',sap_pos_code ,'SMC" + date + ((line.length() == 1) ? line + "0" : line) + "' from rva_trx_etc_trx  rmt ";
        sql += " inner join rva_trx_etc_master m on rmt.SHIFT_ID = m.shift_id ";
        sql += " inner join rva_mst_etc_pos pos on m.POS_ID = pos.pos_id ";
        sql += " where trx_date = to_date('" + date + "','ddmmyyyy')  ";
        sql += " and service_id =4 ";
        sql += " and pos.line_code = " + line + " ";
        sql += " group by  sap_pos_code   union all ";

        sql += "select '0010'," + date + ",'SMC" + date + ((line.length() == 1) ? line + "0" : line) + "','I','C','2199020',sum(rmt.VAT),'Z4','0010','','" + profitCenter + "','SMC" + date + ((line.length() == 1) ? line + "0" : line) + "' from rva_trx_etc_trx  rmt ";
        sql += " inner join rva_trx_etc_master m on rmt.SHIFT_ID = m.shift_id ";
        sql += " inner join rva_mst_etc_pos pos on m.POS_ID = pos.pos_id ";
        sql += " where trx_date = to_date('" + date + "','ddmmyyyy')  ";
        sql += " and service_id =4 ";
        sql += " and pos.line_code = " + line + " ";
        sql += " group by  pos.line_code union all ";


        // ค่าทดแทนเปลี่ยนอุปกรณ์ รหัสเป็น OBU และเปลี่ยนรหัส EXVAT เป็น 4299990
        sql += "select '0010' as a," + date + " as b,'OBU" + date + ((line.length() == 1) ? line + "0" : line) + "' as c,'I' as d,'D' as e,'9200230' as f,sum(rmt.COST) as g,'Z4' as h,'0010' as i,'' as j,'" + profitCenter + "' as k ,'OBU" + date + ((line.length() == 1) ? line + "0" : line) + "' as l from rva_trx_etc_trx  rmt ";
        sql += " inner join rva_trx_etc_master m on rmt.SHIFT_ID = m.shift_id ";
        sql += " inner join rva_mst_etc_pos pos on m.POS_ID = pos.pos_id ";
        sql += " where trx_date = to_date('" + date + "','ddmmyyyy')  ";
        sql += " and service_id =6 ";
        sql += " and pos.line_code = " + line + " ";
        sql += " group by  pos.line_code union all ";

        sql += "select '0010'," + date + ",'OBU" + date + ((line.length() == 1) ? line + "0" : line) + "','I','C','2192000',sum(rmt.EXVAT),'Z4','0010','',sap_pos_code ,'OBU" + date + ((line.length() == 1) ? line + "0" : line) + "' from rva_trx_etc_trx  rmt ";
        sql += " inner join rva_trx_etc_master m on rmt.SHIFT_ID = m.shift_id ";
        sql += " inner join rva_mst_etc_pos pos on m.POS_ID = pos.pos_id ";
        sql += " where trx_date = to_date('" + date + "','ddmmyyyy')  ";
        sql += " and service_id =6 ";
        sql += " and pos.line_code = " + line + " ";
        sql += " group by  sap_pos_code   union all ";

        sql += "select '0010'," + date + ",'OBU" + date + ((line.length() == 1) ? line + "0" : line) + "','I','C','2199020',sum(rmt.VAT),'Z4','0010','','" + profitCenter + "','OBU" + date + ((line.length() == 1) ? line + "0" : line) + "' from rva_trx_etc_trx  rmt ";
        sql += " inner join rva_trx_etc_master m on rmt.SHIFT_ID = m.shift_id ";
        sql += " inner join rva_mst_etc_pos pos on m.POS_ID = pos.pos_id ";
        sql += " where trx_date = to_date('" + date + "','ddmmyyyy')  ";
        sql += " and service_id =6 ";
        sql += " and pos.line_code = " + line + " ";
        sql += " group by  pos.line_code  ";

        return sql;
    }
}
