
import java.sql.*;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import th.co.exat.helper.System.RESConnection;

public class DOC {

    public static String date;
    public static String dd;
    public static String mm = "05";
    public static String yyyy = "2013";
    public static int days;

    public static void main(String[] args) throws Exception {
        java.text.DecimalFormat dfm = new java.text.DecimalFormat("0.00");
        Calendar cal = new GregorianCalendar(Integer.parseInt(yyyy), Integer.parseInt(mm) - 1, 1);
        days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);     // 29
        int totalRev;
        Double total;
        StringBuffer str;

        for (int i = 1; i <= days; i++) {
            str = new StringBuffer();
            total = 0.0;
            totalRev = 0;
            Connection conn = RESConnection.getConnection();
            Statement stmt = conn.createStatement();
            dd = String.valueOf(i);

            if (i < 10) {
                dd = "0" + dd;
            }
            date = yyyy + "-" + mm + "-" + dd;

            java.sql.ResultSet rs = stmt.executeQuery(getSQL(date));

            FileWriter fstream = new FileWriter("d://DOC/DOC_REV_" + yyyy + "" + mm + "" + dd + ".txt");

            BufferedWriter out = new BufferedWriter(fstream);


            while (rs.next()) {

                totalRev += rs.getInt("revT1") + rs.getInt("revT2") + rs.getInt("revT3");

                if (rs.getInt("trfT1") != 0) {
                    str.append("ปริมาณการจราจร\t");
                    str.append(rs.getString("d") + "\t");
                    str.append(rs.getString("stt") + "\t");
                    str.append(rs.getString("sec") + "\t");
                    str.append(rs.getString("sys") + "\tรถ 4 ล้อ\t");
                    str.append(rs.getString("trfT1") + "\tข้อมูลตรวจสอบแล้ว\t\n");
                    str.append("ปริมาณการจราจร\t");
                    str.append(rs.getString("d") + "\t");
                    str.append(rs.getString("stt") + "\t");
                    str.append("รวม" + "\t");
                    str.append(rs.getString("sys") + "\tรถ 4 ล้อ\t");
                    str.append(rs.getString("trfT1") + "\tข้อมูลตรวจสอบแล้ว\t\n");
                }
                if (rs.getInt("trfT2") != 0) {
                    str.append("ปริมาณการจราจร\t");
                    str.append(rs.getString("d") + "\t");
                    str.append(rs.getString("stt") + "\t");
                    str.append(rs.getString("sec") + "\t");
                    str.append(rs.getString("sys") + "\tรถ 6-10 ล้อ\t");
                    str.append(rs.getString("trfT2") + "\tข้อมูลตรวจสอบแล้ว\t\n");
                    str.append("ปริมาณการจราจร\t");
                    str.append(rs.getString("d") + "\t");
                    str.append(rs.getString("stt") + "\t");
                    str.append("รวม" + "\t");
                    str.append(rs.getString("sys") + "\tรถ 6-10 ล้อ\t");
                    str.append(rs.getString("trfT2") + "\tข้อมูลตรวจสอบแล้ว\t\n");
                }
                if (rs.getInt("trfT3") != 0) {
                    str.append("ปริมาณการจราจร\t");
                    str.append(rs.getString("d") + "\t");
                    str.append(rs.getString("stt") + "\t");
                    str.append(rs.getString("sec") + "\t");
                    str.append(rs.getString("sys") + "\tรถมากกว่า 10 ล้อ\t");
                    str.append(rs.getString("trfT3") + "\tข้อมูลตรวจสอบแล้ว\t\n");
                    str.append("ปริมาณการจราจร\t");
                    str.append(rs.getString("d") + "\t");
                    str.append(rs.getString("stt") + "\t");
                    str.append("รวม" + "\t");
                    str.append(rs.getString("sys") + "\tรถมากกว่า 10 ล้อ\t");
                    str.append(rs.getString("trfT3") + "\tข้อมูลตรวจสอบแล้ว\t\n");
                }

                //***********************************************************************************************

                if (rs.getString("sys").equals("เงินสด")) {
                    if (rs.getInt("revT1") != 0) {
                        str.append("รายได้รวมภาษีมูลค่าเพิ่ม\t");
                        str.append(rs.getString("d") + "\t");
                        str.append(rs.getString("stt") + "\t");
                        str.append(rs.getString("sec") + "\t");
                        str.append(rs.getString("sys") + "\tรถ 4 ล้อ\t");
                        str.append(rs.getString("revT1") + "\tข้อมูลตรวจสอบแล้ว\t\n");
                        str.append("รายได้รวมภาษีมูลค่าเพิ่ม\t");
                        str.append(rs.getString("d") + "\t");
                        str.append(rs.getString("stt") + "\t");
                        str.append("รวม" + "\t");
                        str.append(rs.getString("sys") + "\tรถ 4 ล้อ\t");
                        str.append(rs.getString("revT1") + "\tข้อมูลตรวจสอบแล้ว\t\n");
                        str.append("รายได้หักภาษีมูลค่าเพิ่ม\t");
                        str.append(rs.getString("d") + "\t");
                        str.append(rs.getString("stt") + "\t");
                        str.append(rs.getString("sec") + "\t");
                        str.append(rs.getString("sys") + "\tรถ 4 ล้อ\t");
                        str.append(dfm.format(rs.getDouble("revT1") - (rs.getDouble("revT1") * 7 / 107)) + "\tข้อมูลตรวจสอบแล้ว\t\n");

                        str.append("จำนวนภาษีมูลค่าเพิ่ม\t");
                        str.append(rs.getString("d") + "\t");
                        str.append(rs.getString("stt") + "\t");
                        str.append("รวม" + "\t");
                        str.append(rs.getString("sys") + "\tรถ 4 ล้อ\t");
                        str.append(dfm.format((rs.getDouble("revT1") * 7 / 107)) + "\tข้อมูลตรวจสอบแล้ว\t\n");
                        str.append("จำนวนภาษีมูลค่าเพิ่ม\t");
                        str.append(rs.getString("d") + "\t");
                        str.append(rs.getString("stt") + "\t");
                        str.append("กทพ" + "\t");
                        str.append(rs.getString("sys") + "\tรถ 4 ล้อ\t");
                        str.append(dfm.format((rs.getDouble("revT1") * 7 / 107)) + "\tข้อมูลตรวจสอบแล้ว\t\n");


                    }
                    if (rs.getInt("revT2") != 0) {
                        str.append("รายได้รวมภาษีมูลค่าเพิ่ม\t");
                        str.append(rs.getString("d") + "\t");
                        str.append(rs.getString("stt") + "\t");
                        str.append(rs.getString("sec") + "\t");
                        str.append(rs.getString("sys") + "\tรถ 6-10 ล้อ\t");
                        str.append(rs.getString("revT2") + "\tข้อมูลตรวจสอบแล้ว\t\n");
                        str.append("รายได้รวมภาษีมูลค่าเพิ่ม\t");
                        str.append(rs.getString("d") + "\t");
                        str.append(rs.getString("stt") + "\t");
                        str.append("รวม" + "\t");
                        str.append(rs.getString("sys") + "\tรถ 6-10 ล้อ\t");
                        str.append(rs.getString("revT2") + "\tข้อมูลตรวจสอบแล้ว\t\n");
                        str.append("รายได้หักภาษีมูลค่าเพิ่ม\t");
                        str.append(rs.getString("d") + "\t");
                        str.append(rs.getString("stt") + "\t");
                        str.append(rs.getString("sec") + "\t");
                        str.append(rs.getString("sys") + "\tรถ 6-10 ล้อ\t");
                        str.append(dfm.format(rs.getDouble("revT2") - (rs.getDouble("revT2") * 7 / 107)) + "\tข้อมูลตรวจสอบแล้ว\t\n");

                        str.append("จำนวนภาษีมูลค่าเพิ่ม\t");
                        str.append(rs.getString("d") + "\t");
                        str.append(rs.getString("stt") + "\t");
                        str.append("รวม" + "\t");
                        str.append(rs.getString("sys") + "\tรถ 6-10 ล้อ\t");
                        str.append(dfm.format((rs.getDouble("revT2") * 7 / 107)) + "\tข้อมูลตรวจสอบแล้ว\t\n");
                        str.append("จำนวนภาษีมูลค่าเพิ่ม\t");
                        str.append(rs.getString("d") + "\t");
                        str.append(rs.getString("stt") + "\t");
                        str.append("กทพ" + "\t");
                        str.append(rs.getString("sys") + "\tรถ 6-10 ล้อ\t");
                        str.append(dfm.format((rs.getDouble("revT2") * 7 / 107)) + "\tข้อมูลตรวจสอบแล้ว\t\n");
                    }
                    if (rs.getInt("revT3") != 0) {
                        str.append("รายได้รวมภาษีมูลค่าเพิ่ม\t");
                        str.append(rs.getString("d") + "\t");
                        str.append(rs.getString("stt") + "\t");
                        str.append(rs.getString("sec") + "\t");
                        str.append(rs.getString("sys") + "\tรถมากกว่า 10 ล้อ\t");
                        str.append(rs.getString("revT3") + "\tข้อมูลตรวจสอบแล้ว\t\n");
                        str.append("รายได้รวมภาษีมูลค่าเพิ่ม\t");
                        str.append(rs.getString("d") + "\t");
                        str.append(rs.getString("stt") + "\t");
                        str.append("รวม" + "\t");
                        str.append(rs.getString("sys") + "\tรถมากกว่า 10 ล้อ\t");
                        str.append(rs.getString("revT3") + "\tข้อมูลตรวจสอบแล้ว\t\n");
                        str.append("รายได้หักภาษีมูลค่าเพิ่ม\t");
                        str.append(rs.getString("d") + "\t");
                        str.append(rs.getString("stt") + "\t");
                        str.append(rs.getString("sec") + "\t");
                        str.append(rs.getString("sys") + "\tรถมากกว่า 10 ล้อ\t");
                        str.append(dfm.format(rs.getDouble("revT3") - (rs.getDouble("revT3") * 7 / 107)) + "\tข้อมูลตรวจสอบแล้ว\t\n");


                        str.append("จำนวนภาษีมูลค่าเพิ่ม\t");
                        str.append(rs.getString("d") + "\t");
                        str.append(rs.getString("stt") + "\t");
                        str.append("รวม" + "\t");
                        str.append(rs.getString("sys") + "\tรถมากกว่า 10 ล้อ\t");
                        str.append(dfm.format((rs.getDouble("revT3") * 7 / 107)) + "\tข้อมูลตรวจสอบแล้ว\t\n");
                        str.append("จำนวนภาษีมูลค่าเพิ่ม\t");
                        str.append(rs.getString("d") + "\t");
                        str.append(rs.getString("stt") + "\t");
                        str.append("กทพ" + "\t");
                        str.append(rs.getString("sys") + "\tรถมากกว่า 10 ล้อ\t");
                        str.append(dfm.format((rs.getDouble("revT3") * 7 / 107)) + "\tข้อมูลตรวจสอบแล้ว\t\n");
                    }
                } else {
                    //ETC
                    str.append("รายได้รวมภาษีมูลค่าเพิ่ม\t");
                    str.append(rs.getString("d") + "\t");
                    str.append(rs.getString("stt") + "\t");
                    str.append(rs.getString("sec") + "\t");
                    str.append(rs.getString("sys") + "\tรถ 4 ล้อ\t");
                    str.append(rs.getString("revT1") + "\tข้อมูลตรวจสอบแล้ว\t\n");
                    str.append("รายได้รวมภาษีมูลค่าเพิ่ม\t");
                    str.append(rs.getString("d") + "\t");
                    str.append(rs.getString("stt") + "\t");
                    str.append("รวม" + "\t");
                    str.append(rs.getString("sys") + "\tรถ 4 ล้อ\t");
                    str.append(rs.getString("revT1") + "\tข้อมูลตรวจสอบแล้ว\t\n");
                    str.append("รายได้หักภาษีมูลค่าเพิ่ม\t");
                    str.append(rs.getString("d") + "\t");
                    str.append(rs.getString("stt") + "\t");
                    str.append(rs.getString("sec") + "\t");
                    str.append(rs.getString("sys") + "\tรถ 4 ล้อ\t");
                    str.append(dfm.format(rs.getDouble("revT1") - (rs.getDouble("revT1") * 7 / 107)) + "\tข้อมูลตรวจสอบแล้ว\t\n");


                    str.append("จำนวนภาษีมูลค่าเพิ่ม\t");
                    str.append(rs.getString("d") + "\t");
                    str.append(rs.getString("stt") + "\t");
                    str.append("รวม" + "\t");
                    str.append(rs.getString("sys") + "\tรถ 4 ล้อ\t");
                    str.append(dfm.format((rs.getDouble("revT1") * 7 / 107)) + "\tข้อมูลตรวจสอบแล้ว\t\n");
                    str.append("จำนวนภาษีมูลค่าเพิ่ม\t");
                    str.append(rs.getString("d") + "\t");
                    str.append(rs.getString("stt") + "\t");
                    str.append("กทพ" + "\t");
                    str.append(rs.getString("sys") + "\tรถ 4 ล้อ\t");
                    str.append(dfm.format((rs.getDouble("revT1") * 7 / 107)) + "\tข้อมูลตรวจสอบแล้ว\t\n");
                }
                /*
                 * if (rs.getInt("trfT1") != 0) {
                 * str.append("รายได้รวมภาษีมูลค่าเพิ่ม\t");
                 * str.append(rs.getString("d") + "\t");
                 * str.append(rs.getString("stt") + "\t");
                 * str.append(rs.getString("sec") + "\t");
                 * str.append(rs.getString("sys") + "\tรถ 4 ล้อ\t");
                 * str.append(rs.getString("revT1") +
                 * "\tข้อมูลตรวจสอบแล้ว\t\n");
                 * str.append("จำนวนภาษีมูลค่าเพิ่ม\t");
                 * str.append(rs.getString("d") + "\t");
                 * str.append(rs.getString("stt") + "\t");
                 * str.append(rs.getString("sec") + "\t");
                 * str.append(rs.getString("sys") + "\tรถ 4 ล้อ\t");
                 * str.append(dfm.format(rs.getDouble("revT1") * 7 / 107) +
                 * "\tข้อมูลตรวจสอบแล้ว\t\n");
                 * str.append("รายได้หักภาษีมูลค่าเพิ่ม\t");
                 * str.append(rs.getString("d") + "\t");
                 * str.append(rs.getString("stt") + "\t");
                 * str.append(rs.getString("sec") + "\t");
                 * str.append(rs.getString("sys") + "\tรถ 4 ล้อ\t");
                 * str.append(dfm.format(rs.getDouble("revT1") -
                 * (rs.getDouble("revT1") * 7 / 107)) +
                 * "\tข้อมูลตรวจสอบแล้ว\t\n"); } if (rs.getInt("trfT2") != 0) {
                 * str.append("รายได้รวมภาษีมูลค่าเพิ่ม\t");
                 * str.append(rs.getString("d") + "\t");
                 * str.append(rs.getString("stt") + "\t");
                 * str.append(rs.getString("sec") + "\t");
                 * str.append(rs.getString("sys") + "\tรถ 6-10 ล้อ\t");
                 * str.append(rs.getString("revT2") +
                 * "\tข้อมูลตรวจสอบแล้ว\t\n");
                 * str.append("จำนวนภาษีมูลค่าเพิ่ม\t");
                 * str.append(rs.getString("d") + "\t");
                 * str.append(rs.getString("stt") + "\t");
                 * str.append(rs.getString("sec") + "\t");
                 * str.append(rs.getString("sys") + "\tรถ 6-10 ล้อ\t");
                 * str.append(dfm.format(rs.getDouble("revT2") * 7 / 107) +
                 * "\tข้อมูลตรวจสอบแล้ว\t\n");
                 * str.append("รายได้หักภาษีมูลค่าเพิ่ม\t");
                 * str.append(rs.getString("d") + "\t");
                 * str.append(rs.getString("stt") + "\t");
                 * str.append(rs.getString("sec") + "\t");
                 * str.append(rs.getString("sys") + "\tรถ 6-10 ล้อ\t");
                 * str.append(dfm.format(rs.getDouble("revT2") -
                 * ((rs.getDouble("revT2")) * 7 / 107)) +
                 * "\tข้อมูลตรวจสอบแล้ว\t\n"); } if (rs.getInt("trfT3") != 0) {
                 * str.append("รายได้รวมภาษีมูลค่าเพิ่ม\t");
                 * str.append(rs.getString("d") + "\t");
                 * str.append(rs.getString("stt") + "\t");
                 * str.append(rs.getString("sec") + "\t");
                 * str.append(rs.getString("sys") + "\tรถมากกว่า 10 ล้อ\t");
                 * str.append(rs.getString("revT3") +
                 * "\tข้อมูลตรวจสอบแล้ว\t\n");
                 * str.append("จำนวนภาษีมูลค่าเพิ่ม\t");
                 * str.append(rs.getString("d") + "\t");
                 * str.append(rs.getString("stt") + "\t");
                 * str.append(rs.getString("sec") + "\t");
                 * str.append(rs.getString("sys") + "\tรถมากกว่า 10 ล้อ\t");
                 * str.append(dfm.format(rs.getDouble("revT3") * 7 / 107) +
                 * "\tข้อมูลตรวจสอบแล้ว\t\n");
                 * str.append("รายได้หักภาษีมูลค่าเพิ่ม\t");
                 * str.append(rs.getString("d") + "\t");
                 * str.append(rs.getString("stt") + "\t");
                 * str.append(rs.getString("sec") + "\t");
                 * str.append(rs.getString("sys") + "\tรถมากกว่า 10 ล้อ\t");
                 * str.append(dfm.format(rs.getDouble("revT3") -
                 * (rs.getDouble("revT3") * 7 / 107)) +
                 * "\tข้อมูลตรวจสอบแล้ว\t\n"); }
                 */
            }
            String print = str.toString();
            byte[] b = print.getBytes("tis620");
            FileOutputStream fo = new FileOutputStream("d://DOC/DOC_REV_" + yyyy + "" + mm + "" + dd + ".txt");
            fo.write(b);
            fo.close();
            //out.write(str.toString());
            //out.close();
            fstream.close();
            rs.close();
            stmt.close();
            conn.close();
        }
    }

    static String getSQL(String d) {
        StringBuffer sql = new StringBuffer("SELECT DATE_FORMAT( trx_date,  '%Y%m%d' ) AS  \"d\", station_name AS  \"stt\", IF( sec_id =4,  \"BECL\",  \"NECL\" ) AS  \"sec\", IF( trx_system =  \"MTC\",  \"เงินสด\",  \"Easy Pass\" ) AS  \"sys\", trx_trf_type1 AS  \"trfT1\", trx_trf_type2 AS  \"trfT2\", trx_trf_type3 AS trfT3, trx_rev_type1 AS revT1, trx_rev_type2 AS revT2, trx_rev_type3 AS revT3");
        sql.append(" FROM res_trx_trx trx INNER JOIN res_mst_station stt ON trx.station_id = stt.station_id");
        sql.append(" WHERE trx_date =  '" + d + "' AND sec_id IN ( 4, 5 ) ");
        return sql.toString();
    }
}
