
import java.sql.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import th.co.exat.helper.System.EtaConnectionFactory;

/**
 * สำหรับไฟล์ E2
 *
 * @author siridet_suk
 */
public class Accounting {

    public static String date;
    public static String dd;
    public static String mm = "06";
    public static String yyyy = "2012";
    public static String[] lineArr = {"1", "2", "3"};
    public static String line;
    public static String profitCenter;
    public static int days;

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
                Connection conn = EtaConnectionFactory.getConnection();
                Statement stmt = conn.createStatement();
                dd = String.valueOf(i);

                if (i < 10) {
                    dd = "0" + dd;
                }
                date = dd + mm + yyyy;

                java.sql.ResultSet rs = stmt.executeQuery(getSQL());

                FileWriter fstream = new FileWriter("d://" + line + "/SAP_" + ((line.length() == 1) ? "0" + line : line) + "_GL_E2_" + yyyy + "" + mm + "" + dd + ".txt");
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
            }
        }
    }

    public static String getSQL() {
        String sql = "select '0010' as a," + date + " as b,'ESPR" + date + ((line.length() == 1) ? line + "0" : line) + "' as c,'I' as d,'D' as e,'9200230' as f,sum(rmt.COST) as g,'Z4' as h,'0010' as i,'' as j,'" + profitCenter + "' as k ,'ESPR" + date + ((line.length() == 1) ? line + "0" : line) + "' as l from rva_trx_etc_trx  rmt ";
        sql += " inner join rva_trx_etc_master m on rmt.SHIFT_ID = m.shift_id ";
        sql += " inner join rva_mst_etc_pos pos on m.POS_ID = pos.pos_id ";
        sql += " where trx_date = to_date('" + date + "','ddmmyyyy')  ";
        sql += " and service_id = 2 ";
        sql += " and pos.line_code = " + line + " ";
        sql += " group by  pos.line_code union all ";

        sql += "select '0010'," + date + ",'ESPR" + date + ((line.length() == 1) ? line + "0" : line) + "','I','C','2156000',sum(rmt.EXVAT),'Z4','0010','',sap_pos_code ,'ESPR" + date + ((line.length() == 1) ? line + "0" : line) + "' from rva_trx_etc_trx  rmt ";
        sql += " inner join rva_trx_etc_master m on rmt.SHIFT_ID = m.shift_id ";
        sql += " inner join rva_mst_etc_pos pos on m.POS_ID = pos.pos_id ";
        sql += " where trx_date = to_date('" + date + "','ddmmyyyy')  ";
        sql += " and service_id = 2 ";
        sql += " and pos.line_code = " + line + " ";
        sql += " group by  sap_pos_code   union all ";

        sql += "select '0010'," + date + ",'ESPR" + date + ((line.length() == 1) ? line + "0" : line) + "','I','C','2199020',sum(rmt.VAT),'Z4','0010','','" + profitCenter + "','ESPR" + date + ((line.length() == 1) ? line + "0" : line) + "' from rva_trx_etc_trx  rmt ";
        sql += " inner join rva_trx_etc_master m on rmt.SHIFT_ID = m.shift_id ";
        sql += " inner join rva_mst_etc_pos pos on m.POS_ID = pos.pos_id ";
        sql += " where trx_date = to_date('" + date + "','ddmmyyyy')  ";
        sql += " and service_id = 2 ";
        sql += " and pos.line_code = " + line + " ";
        sql += " group by  pos.line_code union all ";

        sql += "select '0010'," + date + ",'SESPR" + date + ((line.length() == 1) ? line + "0" : line) + "','I','D','4315000',sum(rmt.DISCOUNT),'Z4','0010','','" + profitCenter + "','SESPR" + date + ((line.length() == 1) ? line + "0" : line) + "' from rva_trx_etc_trx  rmt ";
        sql += " inner join rva_trx_etc_master m on rmt.SHIFT_ID = m.shift_id ";
        sql += " inner join rva_mst_etc_pos pos on m.POS_ID = pos.pos_id ";
        sql += " where trx_date = to_date('" + date + "','ddmmyyyy')  ";
        sql += " and service_id = 2 ";
        sql += " and pos.line_code = " + line + " ";
        sql += " group by  pos.line_code union all ";

        sql += "select '0010'," + date + ",'SESPR" + date + ((line.length() == 1) ? line + "0" : line) + "','I','C','2156000',sum(rmt.DISCOUNT),'Z4','0010','',sap_pos_code ,'SESPR" + date + ((line.length() == 1) ? line + "0" : line) + "' from rva_trx_etc_trx  rmt ";
        sql += " inner join rva_trx_etc_master m on rmt.SHIFT_ID = m.shift_id ";
        sql += " inner join rva_mst_etc_pos pos on m.POS_ID = pos.pos_id ";
        sql += " where trx_date = to_date('" + date + "','ddmmyyyy')  ";
        sql += " and service_id = 2 ";
        sql += " and pos.line_code = " + line + " ";
        sql += " group by  sap_pos_code  union all ";
        /*
         * ======================== ส่วนของเงินเกิน มีทั้ง creadit and
         * debit===========================
         */
        sql += "SELECT '0010'," + date + ", 'OESPR" + date + ((line.length() == 1) ? line + "0" : line) + "', 'I', 'D', '9200230',SUM (rmt.exc_amt), 'Z4', '0010', '', '" + profitCenter + "', 'OESPR" + date + ((line.length() == 1) ? line + "0" : line) + "' from rva_trx_etc_rmt rmt ";
        sql += "INNER JOIN rva_trx_etc_master m ON rmt.shift_id = m.shift_id ";
        sql += "  INNER JOIN rva_mst_etc_pos pos ON m.pos_id = pos.pos_id ";
        sql += " WHERE trx_date = TO_DATE ('" + date + "', 'ddmmyyyy')";
        sql += " AND service_id = 2 AND pos.line_code =" + line + " AND exc_amt > 0";
        sql += " GROUP BY sap_pos_code UNION ALL ";

        sql += "SELECT '0010'," + date + ", 'OESPR" + date + ((line.length() == 1) ? line + "0" : line) + "', 'I', 'C', '9200280',SUM (rmt.exc_amt), 'Z4', '0010', '', sap_pos_code, 'OESPR" + date + ((line.length() == 1) ? line + "0" : line) + "' from rva_trx_etc_rmt rmt ";
        sql += "INNER JOIN rva_trx_etc_master m ON rmt.shift_id = m.shift_id ";
        sql += "  INNER JOIN rva_mst_etc_pos pos ON m.pos_id = pos.pos_id ";
        sql += " WHERE trx_date = TO_DATE ('" + date + "', 'ddmmyyyy')";
        sql += " AND service_id = 2 AND pos.line_code = " + line + " AND exc_amt > 0";
        sql += " GROUP BY sap_pos_code ";
        return sql;
    }
}
