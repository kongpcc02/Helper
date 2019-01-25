/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package P101;

import com.Helper.Helper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.SocketException;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 *
 * @author siridet_suk
 */
public class P101Service {

    String programPathName = "D://pluginRVA//P101//";
    String host = "1.3.4.3";
    String user = "appint";
    String pwd = "apPInt";

    public void convertData(String fileName, String stt) throws SocketException, IOException, Exception {
        FTPClient ftp = null;
        ftp = new FTPClient();
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        int reply;
        ftp.connect(host);
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new Exception("Exception in connecting to FTP Server");
        }
        ftp.login(user, pwd);
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        ftp.enterLocalPassiveMode();
        ftp.changeWorkingDirectory("import/DMS");


        FileWriter fw = new FileWriter(programPathName + fileName.concat(".txt"));//สร้างไฟล์ที่จะเขียนลง
        BufferedWriter bw = new BufferedWriter(fw);

        InputStream input = ftp.retrieveFileStream(fileName.concat(".mnl"));
        BufferedReader br = new BufferedReader(new InputStreamReader(input));

        String thisLine = null;

        while ((thisLine = br.readLine()) != null) {
            String[] sa = thisLine.split("\t");
//                int carType6 = Integer.parseInt(sa[11]);
//                int carType9 = Integer.parseInt(sa[14]);

            //ตรวจสอบด่านที่ถูกส่งมา หรือไม่ส่งค่ามาหรือไม่
            if (stt.equals(sa[0]) || stt.equals("")) {
                System.out.println("======convert======");
                //แปลงข้อมูลจาก 9->6
                bw.write(sa[0] + "\t" + sa[1] + "\t" + sa[2] + "\t" + sa[3] + "\t" + sa[4] + "\t" + sa[5] + "\t" + sa[6] + "\t" + sa[7] + "\t" + sa[8] + "\t" + sa[9] + "\t" + sa[10] + "\t" + sa[14] + "\t" + sa[12] + "\t" + sa[13] + "\t" + 0 + "\t" + sa[15] + "\t" + sa[16]);
            } else {
                bw.write(sa[0] + "\t" + sa[1] + "\t" + sa[2] + "\t" + sa[3] + "\t" + sa[4] + "\t" + sa[5] + "\t" + sa[6] + "\t" + sa[7] + "\t" + sa[8] + "\t" + sa[9] + "\t" + sa[10] + "\t" + sa[11] + "\t" + sa[12] + "\t" + sa[13] + "\t" + sa[14] + "\t" + sa[15] + "\t" + sa[16]);

            }
            bw.newLine();
        }

        input.close();
        br.close();
        bw.close();

        if (ftp.isConnected()) {
            try {
                ftp.logout();
                ftp.disconnect();
            } catch (IOException f) {
                // do nothing as file is already downloaded from FTP server
            }
        }
        uploadToFTP(programPathName, fileName.concat(".txt"));
    }

    private void uploadToFTP(String pName, String fName) throws SocketException, IOException {

        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(host, 21);

        if (!ftpClient.login(user, pwd)) {
            System.out.println("เกิดข้อผิดพลาด : รหัสผ่านในการเข้าใช้ FTP ผิด ");
        }
        //เข้าไปใน Dir ที่ต้องการ
        ftpClient.changeWorkingDirectory("import/DMS");

        //*** ถ้าไฟล์ที่อัพโหลด ไม่ใช่ไฟล์ที่ Stream เป็น Text ต้องเซ็ตประเภทของไฟล์เป็น BINARY_FILE_TYPE ด้วย
        //แต่ถ้าเซ็ตเอาไว้ ก็จะสามารถอัพโหลดไฟล์ที่เป็น Text ได้เหมือนกัน
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);

        File file_in = new File(pName + fName);
        FileInputStream is = new FileInputStream(file_in);

        //อัพโหลดไฟล์โดยที่ใช้ชื่อแบบไม่ร่วมเลขที่สุ่ม 2 หลักแรก จึงต้อง subString ออกไป
        ftpClient.storeFile(fName, is);

        //Logout ออกจาก FTP Server
        ftpClient.logout();

        //ต้อง Close Stream ก่อน ถึงจะสามารถลบได้ เพราะเหมือนกับว่าเราปิดการ Edit ไฟล์นั้น
        is.close();


    }

    public void copyFile(String fileName) throws Exception {
        FTPClient ftp = null;
        ftp = new FTPClient();
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        int reply;
        ftp.connect(host);
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new Exception("Exception in connecting to FTP Server");
        }
        ftp.login(user, pwd);
        ftp.enterLocalPassiveMode();
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
        ftp.setFileTransferMode(FTP.BINARY_FILE_TYPE);
        ftp.changeWorkingDirectory("import/DMS");


        InputStream is = ftp.retrieveFileStream(fileName);

        //อัพโหลดไฟล์โดยที่ใช้ชื่อแบบไม่ร่วมเลขที่สุ่ม 2 หลักแรก จึงต้อง subString ออกไป
        ftp.storeFile("a", is);



        //อัพโหลดไฟล์โดยที่ใช้ชื่อแบบไม่ร่วมเลขที่สุ่ม 2 หลักแรก จึงต้อง subString ออกไป
        //   ftp.storeFile(fileName.replace(".mnl", ".txt"), ftp.retrieveFileStream(fileName));

        //Logout ออกจาก FTP Server
        ftp.logout();

        //ต้อง Close Stream ก่อน ถึงจะสามารถลบได้ เพราะเหมือนกับว่าเราปิดการ Edit ไฟล์นั้น
//        is.close();
        
        
//        
//        
//                try {
//            FTPClient ftpClient = new FTPClient();
//            ftpClient.setType(FTPClient.TYPE_BINARY);
//            ftpClient.connect("1.3.4.3", 21);
//            ftpClient.login("appint", "apPInt");
//            ftpClient.changeDirectory("import/DMS");
//            
//            File temp = new File("D:/abc2.txt");
//            
//            ftpClient.download("abc.mnl", temp);
//            ftpClient.upload(temp);
//            
//            //temp.delete();
//            
//            ftpClient.logout();
//        } catch (Exception ex) {
//            System.out.println("เกิดข้อผิดพลาด (Exception) : " + ex.getMessage());
//        }
    }

    public static void main(String[] args) throws Exception {
        P101Service c = new P101Service();
        c.copyFile("abc.mnl");
    }
}
