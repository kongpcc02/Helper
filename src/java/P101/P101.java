/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package P101;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import th.co.exat.helper.System.EtaConnectionFactory;

/**
 *
 * @author Siridet
 */
public class P101 {

    private String fileForDownload;

    public static void reciveData() throws Exception {

        Connection con = Connect.ConnectionFactory.getConnection();
        //File dir = new File("D://pluginRVA//Portable//csv");
        File dir = new File("D://t");
        File[] files = dir.listFiles(new WildCardFileFilter("*.*"));

        Double c = 0.0;
        int count = 0;
        int cFile = 0;
        List list = PTFile.getFileNames();

        for (File file : files) {
            cFile++;

            if (list.lastIndexOf(file.getName()) == -1) {
                PTFile.create(file.getName());
                System.out.println(file.getName());

                String sql = "INSERT INTO PORTABLE_TOLL VALUES(?,?,to_date(?,'dd/mm/yyyy'),?,?,?,?,?,?,?,?,?)";
                PreparedStatement pstmt = con.prepareStatement(sql);

                /**
                 * **********************************************
                 */
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                DataInputStream dis = null;

                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                dis = new DataInputStream(bis);

                while (dis.available() != 0) {
                    String strBuff = dis.readLine();

                    if (!strBuff.equals("")) {
                        if (strBuff.substring(1, 2).equals("K")) {
                            String[] sa = strBuff.split(",");
                            count++;
                            int ss;

                            try {
                                ss = Integer.parseInt(sa[8].split(" ")[1].split(":")[2].toString());
                            } catch (Exception e) {
                                ss = 0;
                            }
                            c += Double.valueOf(sa[18]);
                            System.out.println(count);

                            pstmt.setString(1, sa[1]);
                            pstmt.setString(2, "6" + sa[11]);
                            pstmt.setString(3, sa[8].split(" ")[0]);
                            //pstmt.setString(4, PortableTrx.getEmpCode(sa[7]));
                            pstmt.setString(4, sa[7]);
                            pstmt.setString(5, getShift(Integer.parseInt(sa[8].split(" ")[1].split(":")[0].toString()), Integer.parseInt(sa[8].split(" ")[1].split(":")[1].toString()), ss));
                            pstmt.setString(12, sa[8].split(" ")[1]);
                            pstmt.setString(10, "");//เวลา

                            if (sa[15].equals("1")) {
                                pstmt.setString(6, sa[18]);
                                pstmt.setString(7, "");
                                pstmt.setString(8, "");
                                pstmt.setString(9, "");

                            } else if (sa[15].equals("2")) {
                                pstmt.setString(7, sa[18]);
                                pstmt.setString(6, "");
                                pstmt.setString(8, "");
                                pstmt.setString(9, "");
                            } else if (sa[15].equals("3")) {
                                pstmt.setString(8, sa[18]);
                                pstmt.setString(7, "");
                                pstmt.setString(6, "");
                                pstmt.setString(9, "");
                            } else if (sa[15].equals("9")) {
                                pstmt.setString(9, sa[18]);
                                pstmt.setString(7, "");
                                pstmt.setString(8, "");
                                pstmt.setString(6, "");
                            } else {
                                pstmt.setString(9, "");
                                pstmt.setString(7, "");
                                pstmt.setString(8, "");
                                pstmt.setString(6, "");
                            }
                            //pstmt.setString(9, file.getName());
                            pstmt.setString(11, "");
                            pstmt.addBatch();
                        }
                    }
                }
                pstmt.executeBatch();
                pstmt.close();

                fis.close();
                bis.close();
                dis.close();

                /**
                 * **********************************************
                 */
            }
            // System.out.println("recorc is " + count + "  Amount is " + c);
        }
        con.close();
    }

    public static String getEmpCode(String hrEmpCode) throws Exception {
        String empCode = "";
        Connection con = EtaConnectionFactory.getConnection();
        String sql = "select emp_code from rva_mst_emp where hr_emp_code = substr('" + hrEmpCode + "',2)";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        if (rs.next()) {
            empCode = rs.getString("emp_code");
        } else {
            empCode = "none";
        }

        stmt.close();
        con.close();
        rs.close();
        return empCode;
    }

    public static String getShift(int h, int m, int s) {
        String shift = "0";

        Date shift1 = new Date();
        shift1.setHours(6);
        shift1.setMinutes(0);
        shift1.setSeconds(0);

        Date shift2 = new Date();
        shift2.setHours(14);
        shift2.setMinutes(0);
        shift2.setSeconds(0);

        Date shift3 = new Date();
        shift3.setHours(22);
        shift3.setMinutes(0);
        shift3.setSeconds(0);

        Date d = new Date();
        d.setHours(h);
        d.setMinutes(m);
        d.setSeconds(s);

        if (d.after(shift1) && d.before(shift2) || d.equals(shift1)) {
            shift = "1";
        } else if (d.after(shift2) && d.before(shift3) || d.equals(shift2)) {
            shift = "2";
        } else if (d.after(shift3) || d.before(shift1)) {
            shift = "3";
        }

        return shift;
    }

    public static String getStation(String station) {
        String stt = "";

        if (station.equals("KBT2_X1")) {
            stt = "1011";
        } else {
            stt = "5555";
        }
        return stt;
    }

    /**
     * ลบข้อมูลซ้ำในตาราง
     *
     * @throws Exception
     */
    public static void clearDupData() throws Exception {
        Connection con = Connect.ConnectionFactory.getConnection();

        StringBuffer sql = new StringBuffer("DELETE FROM portable_toll ");
        sql.append("WHERE rowid not in ( SELECT MIN(rowid) ");
        sql.append(" FROM portable_toll ");
        sql.append("GROUP BY pt_ext,pt_ent,pt_date,pt_emp,pt_shift,pt_t1,pt_t3,pt_t4,pt_em,pt_hp,pt_time)");

        Statement stmt = con.createStatement();
        stmt.executeQuery(sql.toString());
        stmt.close();
        con.close();
    }

    /**
     * สร้าง text file สำหรับเรียกดูข้อมูล portable กรุ๊ปตามด่านเข้า ด่านออก
     * วันที่ รหัสพนักงาน
     *
     * @param d
     * @throws Exception
     */
    public static void createTxtFile(String d) throws Exception {
        String dd[] = d.split("/");
        String d1 = dd[1] + "/" + dd[0] + "/" + dd[2];
        String d2 = dd[1] + "/" + dd[0] + "/" + String.valueOf(Integer.parseInt(dd[2]) + 543);

        Connection con = Connect.ConnectionFactory.getConnection();

        StringBuffer sql = new StringBuffer("SELECT   pt_ext, pt_ent, pt_emp, pt_shift ");
        sql.append(" , COUNT (pt_t1), COUNT (pt_t3), COUNT (pt_t4), COUNT (pt_t9), COUNT (pt_em), COUNT (pt_hp) ");
        sql.append(" , nvl(SUM (pt_t1),0), nvl(SUM (pt_t3),0),nvl(SUM (pt_t4),0),nvl(SUM (pt_t9),0),nvl(SUM (pt_em),0),nvl(SUM (pt_hp),0) ");
        sql.append(" FROM portable_toll WHERE pt_date = to_date('" + d1 + "','dd/mm/yyyy')");
        sql.append(" or pt_date = to_date('" + d2 + "','dd/mm/yyyy')");
        sql.append(" GROUP BY pt_ext, pt_ent, pt_emp, pt_shift order by pt_ext, pt_ent,pt_shift");

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql.toString());

        FileWriter fstream = new FileWriter("d://" + dd[1] + "" + dd[0] + "" + dd[2] + ".csv");
        BufferedWriter out = new BufferedWriter(fstream);

        out.write("DAILY PORTABLE SUMMARY, DATE : " + d + "\n");
        out.write(",,,,Class of Traffic,,,,,,Class of Volumn\n");
        out.write("EXT_STT,ENT_STT,EMPLOYEE,SHIFT,4,'6-10,>10,9,EM,HP,4,'6-10,>10,9,EM,HP\n");

        while (rs.next()) {
            out.write(rs.getString(1) + "," + rs.getString(2) + "," + rs.getString(3) + "," + rs.getString(4) + "," + rs.getString(5) + "," + rs.getString(6) + "," + rs.getString(7) + "," + rs.getString(8) + "," + rs.getString(9) + "," + rs.getString(10) + "," + rs.getString(11) + "," + rs.getString(12) + "," + rs.getString(13) + "," + rs.getString(14) + "," + rs.getString(15) + "," + rs.getString(16) + "\n");
        }
        out.close();
        rs.close();
        stmt.close();
        con.close();
    }

    public String getFileForDownload() {
        return fileForDownload;
    }

    public void setFileForDownload(String fileForDownload) {
        this.fileForDownload = fileForDownload;
    }

    public static void main(String[] args) throws Exception {
        /*
         * String d = ""; P101.reciveData(); P101.clearDupData();
         */
        P101.createTxtFile("06/26/2011");
    }
}
