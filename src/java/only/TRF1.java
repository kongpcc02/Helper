package only;

import Connect.ConnectionFactory;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class TRF1 {

    private static String dd2Char;//01
    private static String mm2Char;//01
    private static String dd;
    //------------ VVV เริ่มต้น Parameter สำคัญ VVV ------------
    // 1 = ประมวลผลข้อมูล
    // 2 = อัพโหลดข้อมูลที่อยู่ใน ftpREVTRFDir ไปยัง FTP เซิร์ฟเวอร์
    private static int choice = 2;
    //วันที่เริ่มต้นที่ต้องการ
    private static int dateBegin = 1;
    //วันที่สิ้นสุดที่ต้องการ
    private static int dateEnd = 23;
    //เดือนที่เป็นตัวเลข ตัวอย่าง เดือน มิ.ย. = 6 / ธ.ค. = 12
    private static int mm = 6;
    //ชื่อไฟล์ที่ต่อหลังจากวันที่ เช่น ไฟล์ชื่อ 1 พ.ค. 56 ก็เติม<วรรค>พ.ค. 56 เข้าไป
    private static String monthInString = " มิ.ย. 57";
    //ปี ค.ศ.
    private static String yyyy = "2014";
    // ----- ส่วน Directory -----
    //Directory ที่เก็บไฟล์นำเข้า <<<
    private static String impDir = "D:/TRF/";
    //Directory ที่เก็บไฟล์ส่งออก REV >>>
    private static String expREVDir = "D:/TRF/Export/";
    //Directory ที่เก็บไฟล์ส่งออก TRF >>>
    private static String expTRFDir = "D:/TRF/Export/";
    //Directory ที่เป็นที่พักของไฟล์ทั้ง REV และ TRF ก่อนจะอัพโหลดเข้า FTP >>>
    private static String ftpREVTRFDir = "D:/TRF/Export/";

    //------------ ^^^ สิ้นสุด Parameter สำคัญ ^^^ ------------
    public static void main(String[] args) throws FileNotFoundException, IOException, Exception {

        switch (choice) {

            case 1:

                long startTime = System.currentTimeMillis();

                Calendar cal = new GregorianCalendar(Integer.parseInt(yyyy), mm - 1, 1);
                int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);     // 29
                if (mm < 10) {
                    mm2Char = "0" + mm;
                } else {
                    mm2Char = String.valueOf(mm);
                }

                for (int i = dateBegin; i <= dateEnd; i++) {
                    dd = String.valueOf(i);

                    if (i < 10) {
                        dd2Char = "0" + dd;
                    } else {
                        dd2Char = String.valueOf(i);
                    }

                    clear();
                    import3(String.valueOf(i), monthInString);
                    createREVFile();
                    createTRFFile();
                }

                long stopTime = System.currentTimeMillis();
                long elapsedTime = stopTime - startTime;
                System.out.println(elapsedTime);

                break;

            case 2:
                if (uploadToFTPDMS()) {
                    System.out.println("อัพโหลดข้อมูลผ่านทาง FTP ไปยัง IP : 1.3.4.3 เสร็จสมบูรณ์");
                } else {
                    System.out.println("เกิดข้อผิดพลาด : ไม่สามารถอัพโหลดข้อมูลผ่านทาง FTP ไปยัง IP : 1.3.4.3 ได้");
                }

                break;

            default:
                System.out.println("กรุณาเลือกข้อใดข้อหนึ่ง (1,2)");

                break;
        }

    }

    public static String getStationEnt(String a, Integer i) throws Exception {
        String result = "";
        //ออก
        if (a.equals("405")) {
            if (i.equals(20)) {
                //เข้า
                result = "401";
            }
        } else if (a.equals("406")) {
            if (i.equals(20)) {
                result = "401";
            }
        } else if (a.equals("408")) {
            if (i.equals(20)) {
                result = "401";
            }
        } else if (a.equals("409")) {
            if (i.equals(20)) {
                result = "401";
            }
        } else if (a.equals("413")) {
            if (i.equals(20)) {
                result = "401";
            }
        } else if (a.equals("417")) {
            if (i.equals(20)) {
                result = "407";
            } else if (i.equals(20)) {
                result = "401";
            }
        } else if (a.equals("419")) {
            if (i.equals(30)) {
                result = "401";
            }
        } else if (a.equals("423")) {
            if (i.equals(40)) {
                result = "401";
            } else if (i.equals(35)) {
                result = "407";
            } else if (i.equals(25)) {
                result = "411";
            } else if (i.equals(20)) {
                result = "421";
            }
        } else if (a.equals("425")) {
            if (i.equals(45)) {
                result = "401";
            } else if (i.equals(40)) {
                result = "407";
            } else if (i.equals(35)) {
                result = "411";
            } else if (i.equals(20)) {
                result = "421";
            }
        } else if (a.equals("429")) {
            if (i.equals(60)) {
                result = "401";
            } else if (i.equals(50)) {
                result = "407";
            } else if (i.equals(45)) {
                result = "411";
            } else if (i.equals(20)) {
                result = "421";
            }
        } else if (a.equals("433")) {
            if (i.equals(70)) {
                result = "401";
            } else if (i.equals(60)) {
                result = "407";
            } else if (i.equals(55)) {
                result = "411";
            } else if (i.equals(35)) {
                result = "421";
            } else if (i.equals(20)) {
                result = "427";
            }
        } else if (a.equals("431")) {
            if (i.equals(20)) {
                result = "433";
            }
        } else if (a.equals("427")) {
            if (i.equals(20)) {
                result = "433";
            }
        } else if (a.equals("421")) {
            if (i.equals(35)) {
                result = "433";
            } else if (i.equals(20)) {
                result = "429";
            }
        } else if (a.equals("415")) {
            if (i.equals(55)) {
                result = "433";
            } else if (i.equals(45)) {
                result = "429";
            } else if (i.equals(30)) {
                result = "425";
            } else if (i.equals(25)) {
                result = "423";
            } else if (i.equals(20)) {
                result = "419";
            }
        } else if (a.equals("411")) {
            if (i.equals(55)) {
                result = "433";
            } else if (i.equals(45)) {
                result = "429";
            } else if (i.equals(35)) {
                result = "425";
            } else if (i.equals(25)) {
                result = "423";
            } else if (i.equals(20)) {
                result = "419";
            }
        } else if (a.equals("407")) {
            if (i.equals(60)) {
                result = "433";
            } else if (i.equals(50)) {
                result = "429";
            } else if (i.equals(40)) {
                result = "425";
            } else if (i.equals(35)) {
                result = "423";
            } else if (i.equals(20)) {
                result = "419";
            }
        } else if (a.equals("403")) {
            if (i.equals(70)) {
                result = "433";
            } else if (i.equals(60)) {
                result = "429";
            } else if (i.equals(45)) {
                result = "425";
            } else if (i.equals(40)) {
                result = "423";
            } else if (i.equals(30)) {
                result = "419";
            } else if (i.equals(20)) {
                result = "417";
            }
        } else if (a.equals("404")) {
            if (i.equals(110)) {
                result = "433";
            } else if (i.equals(100)) {
                result = "429";
            } else if (i.equals(85)) {
                result = "425";
            } else if (i.equals(80)) {
                result = "423";
            } else if (i.equals(70)) {
                result = "419";
            } else if (i.equals(60)) {
                result = "405";
            }
        }
        return result;
    }

    public static void import3(String f, String thaiMonthYear) throws FileNotFoundException, Exception {
        StringBuilder r = new StringBuilder();

        FileInputStream fstream = new FileInputStream(impDir + f + thaiMonthYear + ".csv");
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "Unicode"));
        String strLine;
        int count = 0;

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = con.prepareStatement("INSERT INTO TRF1 VALUES (?,?,?,?)");
        stmt.setMaxRows(50000);
        while ((strLine = br.readLine()) != null) {
            if (count != 0) {
                //String[] sa = strLine.split("\t");
                String[] sa = strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

                if (sa.length > 10) {
                    if (count == 10000) {
                        stmt.executeBatch();
                        stmt.clearBatch();
                        System.out.println("=========================== clear1 ============================");
                    } else if (count == 20000) {
                        stmt.executeBatch();
                        stmt.clearBatch();
                        System.out.println("=========================== clear2 ============================");
                    } else if (count == 30000) {
                        stmt.executeBatch();
                        stmt.clearBatch();
                        System.out.println("=========================== clear3 ============================");
                    } else if (count == 40000) {
                        stmt.executeBatch();
                        stmt.clearBatch();
                        System.out.println("=========================== clear4 ============================");
                    }
                    String extStt = (sa[23].substring(0, 3).equals("324")) ? "404" : sa[23].substring(0, 3);

                    int trf = Integer.parseInt(sa[39]);
                    System.out.println("Debug is " + sa[41] + " | " + sa.length);
                    //  System.out.println(sa[23]);
                    // int realNumTrf = (int) Math.floor(Double.valueOf(sa[41]) + 0.5d) / Integer.parseInt(sa[39]);
                    int rev = (sa[23].substring(0, 3).equals("324")) ? trf * 60 : (int) Math.floor(Double.valueOf(sa[41]) + 0.5d);

                    String entStt = getStationEnt(extStt, rev / trf);
                    System.out.println(count + " | " + sa[23].substring(0, 3) + " | " + sa[39] + " | " + sa[41] + " | " + Integer.parseInt(sa[39]) + " | " + (int) Math.floor(Double.valueOf(sa[41]) + 0.5d) + " |   " + (int) Math.floor(Double.valueOf(sa[41]) + 0.5d) / Integer.parseInt(sa[39]));

                    stmt.setString(1, entStt);
                    stmt.setString(2, extStt);
                    stmt.setString(3, String.valueOf(trf));
                    stmt.setString(4, String.valueOf(rev));
                    stmt.addBatch();
                }
            }
            count++;
        }
        stmt.executeBatch();
        con.commit();
        con.close();
        stmt.close();

        fstream.close();
        in.close();
        br.close();
    }

    public static void createTRFFile() throws Exception {
        Connection con = ConnectionFactory.getConnection();
        StringBuilder sql = new StringBuilder("select ext_stt as a,ent_stt as b,sum(num) as c from trf1 ");
        sql.append("where  ent_stt is not null and ext_stt is not null group by ext_stt,ent_stt order by ext_stt,ent_stt");
        StringBuilder text = new StringBuilder();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql.toString());

        while (rs.next()) {
            text.append(rs.getString("a")).append("\t").append(rs.getString("b")).append("\t").append(dd2Char + mm2Char + yyyy).append("\t1444444444\t1\t01\t01\t04\t6\t0\t").append(rs.getString("c")).append("\t0\t0\n");
        }
        Writer output = null;

        File file = new File(expTRFDir + "TL_04_ETC_CLS_TRF_" + yyyy + mm2Char + dd2Char + ".txt");
        output = new BufferedWriter(new FileWriter(file));
        output.write(text.toString());
        output.close();

        con.close();
        stmt.close();
        rs.close();
    }

    public static void createREVFile() throws Exception {
        Connection con = ConnectionFactory.getConnection();
        StringBuilder sql = new StringBuilder("select ext_stt as a,ent_stt as b,sum(rev) as c from trf1 ");
        sql.append("where  ent_stt is not null and ext_stt is not null group by ext_stt,ent_stt order by ext_stt,ent_stt");
        StringBuilder text = new StringBuilder();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql.toString());

        while (rs.next()) {
            text.append(rs.getString("a")).append("\t").append(rs.getString("b")).append("\t").append(dd2Char + mm2Char + yyyy).append("\t1444444444\t1\t01\t01\t04\t6\t0\t").append(rs.getString("c")).append("\t0\t0\n");
        }
        Writer output = null;

        File file = new File(expREVDir + "TL_04_ETC_CLS_REV_" + yyyy + mm2Char + dd2Char + ".txt");
        output = new BufferedWriter(new FileWriter(file));
        output.write(text.toString());
        output.close();

        con.close();
        stmt.close();
        rs.close();
    }

    public static void clear() throws Exception {
        Connection con = ConnectionFactory.getConnection();
        StringBuilder sql = new StringBuilder("delete trf1");
        Statement stmt = con.createStatement();
        stmt.execute(sql.toString());
        con.close();
        stmt.close();
    }

    public static boolean uploadToFTPDMS() throws Exception {
        try {
            File folder = new File(ftpREVTRFDir);
            File[] listOfFiles = folder.listFiles();

            if (listOfFiles.length <= 62) {

                String result = "";
                //String files;

                FTPClient ftpClient = new FTPClient();
                ftpClient.connect("1.3.4.3", 21);
                System.out.println("Reply from FTP : " + ftpClient.getReplyCode());
                if (!ftpClient.login("appint", "apPInt")) {
                    result += "เกิดข้อผิดพลาด : รหัสผ่านในการเข้าใช้ FTP ผิด ";
                    System.out.println(result);
                }
                //เข้าไปใน Dir ที่ต้องการ
                ftpClient.changeWorkingDirectory("import/DMS");


                //---------- เริ่มต้นเขียนลงบน FTP Server ----------

                //*** ถ้าไฟล์ที่อัพโหลด ไม่ใช่ไฟล์ที่ Stream เป็น Text ต้องเซ็ตประเภทของไฟล์เป็น BINARY_FILE_TYPE ด้วย
                //แต่ถ้าเซ็ตเอาไว้ ก็จะสามารถอัพโหลดไฟล์ที่เป็น Text ได้เหมือนกัน
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);

                for (int i = 0; i < listOfFiles.length; i++) {
                    if (listOfFiles[i].isFile()) {
                        FileInputStream fis = new FileInputStream(listOfFiles[i]);
                        ftpClient.storeFile(listOfFiles[i].getName(), fis);
                        fis.close();
                        //files = listOfFiles[i].getName();
                        //System.out.println(files);            
                    }
                }

                //Logout ออกจาก FTP Server
                ftpClient.logout();

                return true;

                //---------- สิ้นสุดเขียนลงบน FTP Server ----------

                //ต้อง Close Stream ก่อน ถึงจะสามารถลบได้ เพราะเหมือนกับว่าเราปิดการ Edit ไฟล์นั้น
                //fis.close();
            } else {
                System.out.println("เกิดข้อผิดพลาด : จำนวนแฟ้มข้อมูลมีมากเกินกว่าค่าปรกติ -> " + listOfFiles.length + " แฟ้ม");
                return false;
            }
        } catch (Exception ex) {
            System.out.println("เกิดข้อผิดพลาด (Exception) : " + ex.getMessage());
            return false;
        }
    }
}