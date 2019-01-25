/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package P110;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author siridet_suk
 */
public class P110Controller extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        //------------- เอกสารเอาไว้อ่านการใช้ JExcel ------------
        //http://www.quicklyjava.com/write-to-excel-in-java/
        //http://www.andykhan.com/jexcelapi/
        //http://r4r.co.in/java/apis/jexcel/

        //PrintWriter out = response.getWriter();

        //---------- หน้านี้เอาไว้ดึงไฟล์ HQ COMPUTER SYSTEM ปริมาณจราจร และ รายได้ รายวัน เท่านั้น ----------

        try {

            //initial เป็นค่าที่บอกว่านี่คือการรันครั้งแรก เอาไว้เช็คตอนเกิดไฟล์ซ้ำให้มันลบเฉพาะไฟล์ที่ถูกเขียนก่อนหน้า
            boolean initial = true;
            //found เป็นค่าที่เอาไว้เช็คว่ามีการเจอ 129 หรือ 324 แล้วหรือยัง
            boolean found = false;
            //found129 เป็นตัวที่เอาไว้เช็คว่าเจอ 129 หรือยัง
            boolean found129 = true;
            //found324 เป็นตัวที่เอาไว้เช็คว่าเจอ 324 หรือยัง
            boolean found324 = true;
            //Path ที่ใช้เก็บไฟล์ที่ได้จากการประมวลผลบนเว็บเซิร์ฟเวอร์แล้ว
            String serverProcessPath = "D:/pluginRVA/P110/";

            // Path ของ TRF และ REV บน Server
            String TRFFilePath = "";
            String REVFilePath = "";
            String TRF129 = "0";
            String result = "";

            //ตัวแปรรอการบันทึกวันที่ที่ได้จากฟอร์ม
            String dateInFormTemp = "";//"14/06/2013";//

            //ตัวแปรเอาไว้เก็บวันที่ช่วงเวลาปฏิบัติงานของทั้ง 2 ไฟล์ XLS ที่นำเข้ามา
            String oprDate1 = "";
            String oprDate3 = "";
            String randomNo;

            //ประกาศ List แบบ InputStream เอาไว้รอรับไฟล์จากหน้าฟอร์ม
            List<InputStream> listFileContent = new ArrayList();

            //---------- เริ่มต้น Step 1 รับไฟล์จากหน้าฟอร์ม ----------

            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            for (FileItem item : items) {
                if (item.isFormField()) {
                    //String fieldname = item.getFieldName();
                    //String fieldvalue = item.getString();
                    // เอาค่าที่ได้จาก Form มาใช้ (input type="text|radio|checkbox|etc", select, etc).
                    if (item.getFieldName().toString().equals("d1")) {
                        dateInFormTemp = item.getString();
                    }

                } else {
                    // ถ้า else จะเป็น(input type="file").
                    if (item.getInputStream() != null) {
                        //String fieldname = item.getFieldName();
                        //String filename = FilenameUtils.getName(item.getName());
                        //InputStream filecontent = item.getInputStream();
                        listFileContent.add(item.getInputStream());
                    }
                }
            }

            //---------- สิ้นสุด Step 1 รับไฟล์จากหน้าฟอร์ม ----------

            //### วิธีดู แถวและคอลัมน์ใน Excel ใช้วิธีง่ายๆคือนับเอาว่าจะเอาคอลัมน์ไหน
            //สมมติว่า อยู่แถว C10 : C ก็เท่ากับ 2 (C <ABC> C อยู่อันดับที่ 3 ต้องเอาไปลบ 1 เสมอ ก็เลยเหลือ > 2 <)
            //ส่วนแถวที่ 10 ก็เอาไปลบ 1 มันก็จะเหลือ > 9 <

            //เวลาเรา getCell ก็จะต้องบอกว่า .getCell(2, 9)
            //sheet.getCell(column - 1, row - 1).getContents();

            //Workbook 1 อันก็เหมือน Excel ไฟล์นึง
            List<Workbook> listwb = new ArrayList();
            //Workbook wb;
            Sheet sheet;

            for (int i = 0; i < listFileContent.size(); i++) {
                listwb.add(Workbook.getWorkbook(listFileContent.get(i)));
                //ใน Excel ไฟล์นึงอาจจะมีหลายบชีท ในที้นี่เรามีแค่ชีทเดียว ก็เลยกลายเป็นชีทที่ 0
                sheet = listwb.get(i).getSheet(0);

                found = false;

                //บันทึกช่วงวันที่ปฏิบัติงานเอาไว้ เพื่อรอการเปรียบเทียบว่าทั้งสองไฟล์นี้ มีวันที่ปฏิบัติงานอยู่ในช่วงเดียวกันหรือไม่
                if (oprDate1.equals("")) {
                    oprDate1 = sheet.getCell(3, 2).getContents();
                } else if (oprDate3.equals("")) {
                    oprDate3 = sheet.getCell(3, 2).getContents();
                }

                //ที่ต้องวนถอยหลังเพราะว่า ด่าน 129 กับ 324 มักจะอยู่ข้างล่าง ดังนั้น ถ้าวนแถวจากข้างล่างจะทำให้เร็วกว่า
                for (int j = sheet.getRows() - 1; j >= 0 && found == false; j--) {

                    //ถ้า เซลล์ในคอลลัมน์แรกมี ขีด (-) อยู่ด้วย แสดงว่าเซ็ลล์นั้นเป็นเซลล์ที่บอกด่าน
                    if (sheet.getCell(0, j).getContents().indexOf("-") == 4) {
                        //รหัสด่านเก็บเงิน
                        String ExtStt = sheet.getCell(0, j).getContents().substring(0, 3);
                        //System.out.println(ExtStt);
                        if (ExtStt.equals("129")) {
                            //เก็บประมาณรถไว้ในตัวแปร TRF129 เพื่อรอคำนวณ รายได้ใน 324
                            TRF129 = sheet.getCell(13, j).getContents();
                            TRF129 = TRF129.replace(",", "");

                            //System.out.println(sheet.getCell(3, 2).getContents());

                            //เซ็ตให้ found129 ว่าเจอแล้ว จะได้เอาไปเช็คตอนหลังว่าถ้าไม่เจอเลย จะได้ออกจากโปรแกรม
                            found129 = true;
                            //เซ็ตให้ found เพราะถ้าพบแล้ว จะได้ดีดออกจาก Loop นี้เลย ไม่ต้องทำต่อให้เสียเวลา
                            found = true;
                        } else if (ExtStt.equals("324")) {
                            //System.out.println(sheet.getCell(3, 2).getContents());

                            found324 = true;
                            found = true;
                        }
                    }
                }
            }

            //---------- เริ่มต้น Step 2 เขียนไฟล์ที่อ่านมาได้ ลงใน Path ที่กำหนดไว้ ----------


            /*File xlsFile = new File("D:/HQ/" + filename);
             OutputStream os = new FileOutputStream(xlsFile);
             byte[] buffer = new byte[4096];
             int bytesRead;
             while ((bytesRead = filecontent.read(buffer)) != -1) {
             os.write(buffer, 0, bytesRead);
             }
             filecontent.close();
             os.close();*/

            //---------- สิ้นสุด Step 2 เขียนไฟล์ที่อ่านมาได้ ลงใน Path ที่กำหนดไว้ ----------


            //---------- เริ่มต้น Step 3 ประมวลผลไฟล์ Excel ที่อ่านมา พร้อมทั้งเขียนเป็นไฟล์ใหม่ สองไฟล์มี TRF/REV ----------

            Random r = new Random();
            randomNo = String.valueOf(r.nextInt(9)) + String.valueOf(r.nextInt(9));

            //ถ้าเจอทั้ง 129 และ 324 แล้วก็ oprDate1 และ oprDate3 คือวันที่ปฏิบัติการวันเดียวกันก็ให้เริ่มทำงานได้เลย

            if ((found129 == true && found324 == true) && (oprDate1.equals(oprDate3))) {

                //ต้อง Split วันที่จากฟอร์มก่อน เพื่อเอาไว้รอ Gen ไฟล์
                String dateinForm[] = dateInFormTemp.split("/");
                String dateinFormYMD = dateinForm[2].substring(0, 4) + dateinForm[1] + dateinForm[0];

                //----- 3.1 เริ่มต้นส่วนการเตรียมเขียนไฟล์ -----

                //การตั้ง Path และชื่อไฟล์จะประกอบไปด้วย 5 ส่วนคือ
                // 1. Path ที่อยู่บน Web Server (ไม่ใช่บน FTP Server)
                // 2. เลข Random 0-100 เพื่อป้องการ User สร้างไฟล์วันที่เดียวกัน และในเวลาเดียวกัน
                // 3. ชื่อตาม Format : TL_01_ETC_OPN_(TRF/REV)_
                // 4. วันที่ที่ผู้ใช้ได้เลือกที่ปฏิทินในวันก่อนหน้า
                // 5. นามสกุลของไฟล์ (ในที่นี้ใช้ .txt)
                TRFFilePath = serverProcessPath + randomNo + "TL_01_ETC_OPN_TRF_" + dateinFormYMD + ".txt";
                File TRFFile = new File(TRFFilePath);
                //ถ้า Gen ชื่อแล้ว มีไฟล์ซ้ำกันในเซิร์ฟเวอร์ ก็ต้องลบก่อน
                if (TRFFile.exists() && initial == true) {
                    TRFFile.delete();
                }
                FileWriter TRFStream = new FileWriter(TRFFile, true); //true = เขียนต่อ / false = เขียนทับ
                BufferedWriter TRFWriter = new BufferedWriter(TRFStream);

                REVFilePath = serverProcessPath + randomNo + "TL_01_ETC_OPN_REV_" + dateinFormYMD + ".txt";
                File REVFile = new File(REVFilePath);
                //ถ้า Gen ชื่อแล้ว มีไฟล์ซ้ำกันในเซิร์ฟเวอร์ ก็ต้องลบก่อน
                if (REVFile.exists() && initial == true) {
                    REVFile.delete();
                }
                FileWriter REVStream = new FileWriter(REVFile, true); //true = เขียนต่อ / false = เขียนทับ
                BufferedWriter REVWriter = new BufferedWriter(REVStream);

                //----- 3.1 สิ้นสุดส่วนการเตรียมเขียนไฟล์ -----

                //วนหาว่าใน List ของ InputStream มีกี่ไฟล์บ้าง (ซึ่งตามปรกติ ก็จะเป็น 2 ไฟล์)
                for (int j = 0; j < listFileContent.size(); j++) {

                    //------- Lib นี้ทำได้แค่ ไฟล์ .XLS เท่านั้น ไฟล์ .XLSX ยังมีปัญหา --------

                    //ใน Excel ไฟล์นึงอาจจะมีหลายบชีท ในที้นี่เรามีแค่ชีทเดียว ก็เลยกลายเป็นชีทที่ 0
                    sheet = listwb.get(j).getSheet(0);

                    //ลูบวนลงมา สังเกตุว่าจะเป็นการ getRows(หาจำนวน Row ทั้งหมดที่อยู่ใน Sheet นี้ ถ้าเป็นการเลื่อนไปทางซ้าย จะเป็น getColumns
                    //ถ้าจะเอา column ทั้งหมดก็ .getColumns()
                    for (int i = 0; i < sheet.getRows(); i++) {

                        //ถ้า เซลล์ในคอลลัมน์แรกมี ขีด (-) อยู่ด้วย แสดงว่าเซ็ลล์นั้นเป็นเซลล์ที่บอกด่าน
                        if (sheet.getCell(0, i).getContents().indexOf("-") == 4) {
                            //รหัสด่านเก็บตัง
                            String ExtStt = sheet.getCell(0, i).getContents().substring(0, 3);
                            //วันที่
                            String DATETEMP[] = sheet.getCell(3, 2).getContents().split("/");
                            String DMYDATE = DATETEMP[0] + DATETEMP[1] + DATETEMP[2].substring(0, 4);
                            String YMDDATE = DATETEMP[2].substring(0, 4) + DATETEMP[1] + DATETEMP[0];
                            //ปริมาณรถ
                            String TRF = sheet.getCell(13, i).getContents();
                            TRF = TRF.replace(",", "");
                            //รายได้
                            String REV = sheet.getCell(13, i + 1).getContents();

                            //System.out.println(ExtStt + "\t" + DMYDATE + "\t" + "1111111111" + "\t" + "1" + "\t" + "01" + "\t" + "02" + "\t" + "02" + "\t" + "1" + "\t" + "0" + "\t" + TRF + "\t" + "0" + "\t" + "0");
                            //System.out.println(ExtStt + "\t" + DMYDATE + "\t" + "1111111111" + "\t" + "1" + "\t" + "01" + "\t" + "02" + "\t" + "02" + "\t" + "1" + "\t" + "0" + "\t" + REV + "\t" + "0" + "\t" + "0");

                            //System.out.println(ExtStt + "\t" + DMYDATE + "\t" + "1111111111" + "\t" + "1" + "\t" + "01" + "\t" + "02" + "\t" + "02" + "\t" + "1" + "\t" + "0" + "\t" + TRF + "\t" + "0" + "\t" + "0");

                            //System.out.println("REV : " + REV + " TRF : " + TRF + " TRF129 : " + TRF129);

                            //ไรท์ลงไฟล์ TRF จริงๆ
                            TRFWriter.write(ExtStt + "\t" + DMYDATE + "\t" + "1111111111" + "\t" + "1" + "\t" + "01" + "\t" + "02" + "\t" + "02" + "\t" + "1" + "\t" + "0" + "\t" + TRF + "\t" + "0" + "\t" + "0");
                            TRFWriter.newLine();

                            //ถ้าบรรทัดนั้นเป็นด่าน 129 จะต้องมีบรรทัด 129 หนึ่งบรรทัด และ 129-1 หนึ่งบรรทัดด้วย
                            if (ExtStt.equals("129")) {
                                TRFWriter.write(ExtStt + "-1\t" + DMYDATE + "\t" + "1111111111" + "\t" + "1" + "\t" + "01" + "\t" + "02" + "\t" + "02" + "\t" + "1" + "\t" + "0" + "\t" + TRF + "\t" + "0" + "\t" + "0");
                                TRFWriter.newLine();
                            }

                            //ไรท์ลงไฟล์ REV จริงๆ
                            //ถ้าเจอด่าน 129 มันจะต้องเขียน 2 บรรทัด คือ 129 ธรรมดา กับ 129-1 / 129 ธรรมดา ประมาณรถ * 5 / 129-1 ประมาณรถ * 40
                            if (ExtStt.equals("129")) {
                                REVWriter.write(ExtStt + "\t" + DMYDATE + "\t" + "1111111111" + "\t" + "1" + "\t" + "01" + "\t" + "02" + "\t" + "02" + "\t" + "1" + "\t" + "0" + "\t" + Integer.parseInt(TRF) * 5 + "\t" + "0" + "\t" + "0");
                                REVWriter.newLine();
                                REVWriter.write(ExtStt + "-1\t" + DMYDATE + "\t" + "1111111111" + "\t" + "1" + "\t" + "01" + "\t" + "02" + "\t" + "02" + "\t" + "1" + "\t" + "0" + "\t" + Integer.parseInt(TRF) * 40 + "\t" + "0" + "\t" + "0");
                                REVWriter.newLine();
                            } else if (ExtStt.equals("324")) {
                                REVWriter.write(ExtStt + "\t" + DMYDATE + "\t" + "1111111111" + "\t" + "1" + "\t" + "01" + "\t" + "02" + "\t" + "02" + "\t" + "1" + "\t" + "0" + "\t" + (Integer.parseInt(TRF) - Integer.parseInt(TRF129)) * 40 + "\t" + "0" + "\t" + "0");
                                REVWriter.newLine();
                            } else {
                                REVWriter.write(ExtStt + "\t" + DMYDATE + "\t" + "1111111111" + "\t" + "1" + "\t" + "01" + "\t" + "02" + "\t" + "02" + "\t" + "1" + "\t" + "0" + "\t" + REV + "\t" + "0" + "\t" + "0");
                                REVWriter.newLine();
                            }

                            /*System.out.println("[" + 0 + "]" + "[" + i + "] ด่าน : " + sheet.getCell(0, i).getContents());
                             System.out.println("[" + 0 + "]" + "[" + i + "] รหัสด่าน : " + sheet.getCell(0, i).getContents().substring(0, 3));
                             System.out.println("[" + 13 + "]" + "[" + i + "] ปริมาณรถ : " + sheet.getCell(13, i).getContents());
                             System.out.println("[" + 13 + "]" + "[" + i + "] รายได้ : " + sheet.getCell(13, i+1).getContents());
                    
                             System.out.println("");*/

                        }

                        Cell cell1 = sheet.getCell(3, 2);
                        //System.out.println(cell1.getContents());

                        //initial = false;
                    }

                    //พอขั้นตอนการเขียนเสร็จเรียบร้อย ก็จะต้อง close Stream file เพื่อป้องกันไฟล์มีปัญหา

                    //---------- สิ้นสุด Step 3 ประมวลผลไฟล์ Excel ที่อ่านมา พร้อมทั้งเขียนเป็นไฟล์ใหม่ สองไฟล์มี TRF/REV ----------
                }

                //ปิด BufferedWriter
                TRFWriter.close();
                REVWriter.close();

                //ปิด FileWriter
                TRFStream.close();
                REVStream.close();

                //---------- เริ่มต้น Step 4 เขียนลงบน FTP Server ----------

                FTPClient ftpClient = new FTPClient();
                ftpClient.connect("1.3.4.3", 21);
                System.out.println("Reply from FTP : " + ftpClient.getReplyCode());
                if (!ftpClient.login("appint", "apPInt")) {
                    result += "เกิดข้อผิดพลาด : รหัสผ่านในการเข้าใช้ FTP ผิด ";
                    System.out.println(result);
                }
                //เข้าไปใน Dir ที่ต้องการ
                ftpClient.changeWorkingDirectory("import/DMS");

                //ถ้าอยากรู้ว่าทันทีที่เข้าไปใน FTP แล้วเราไปอยู่ใน Dir ไหน ก็ต้องใช้ .printWorkingDirectory()
                //System.out.println(ftpClient.printWorkingDirectory());

                /*
                 FTPClient ftpClient = new FTPClient();

                 //ถ้าต่อไม่ได้ภายใน 10 วิ แสดงว่ามีปัญหาอะไรซักอย่างกับเซิร์ฟเวอร์ หรือเน็ตเวิร์ค
                 ftpClient.setConnectTimeout(10000);

                 ftpClient.connect("192.168.56.102", 21);
                 if (!ftpClient.login("root", "jolojie")) {
                 result += "- รหัสผ่านในการเข้าใช้ FTP ผิดพลาด";
                 System.out.println(result);
                 }
                 */

                //*** ถ้าไฟล์ที่อัพโหลด ไม่ใช่ไฟล์ที่ Stream เป็น Text ต้องเซ็ตประเภทของไฟล์เป็น BINARY_FILE_TYPE ด้วย
                //แต่ถ้าเซ็ตเอาไว้ ก็จะสามารถอัพโหลดไฟล์ที่เป็น Text ได้เหมือนกัน
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);

                //File filePDF = new File(serverProcessPath + "WUVPNSetup.pdf");
                //FileInputStream Testfis = new FileInputStream(filePDF);
                //File fileTRF = new File(TRFFilePath);
                FileInputStream TRFfis = new FileInputStream(TRFFile);
                //File fileREV = new File(REVFilePath);
                FileInputStream REVfis = new FileInputStream(REVFile);

                //อัพโหลดไฟล์โดยที่ใช้ชื่อแบบไม่ร่วมเลขที่สุ่ม 2 หลักแรก จึงต้อง subString ออกไป
                ftpClient.storeFile(TRFFile.getName().substring(2, TRFFile.getName().length()), TRFfis);
                ftpClient.storeFile(REVFile.getName().substring(2, REVFile.getName().length()), REVfis);

                //System.out.println(ftpClient.storeFile(TRFS.getName(), TRFfis));
                //System.out.println(ftpClient.storeFile(REVS.getName(), REVfis));
                //System.out.println(ftpClient.storeFile("1" + PDFS.getName(), Testfis));

                //Logout ออกจาก FTP Server
                ftpClient.logout();

                //ต้อง Close Stream ก่อน ถึงจะสามารถลบได้ เพราะเหมือนกับว่าเราปิดการ Edit ไฟล์นั้น
                TRFfis.close();
                REVfis.close();

                //---------- สิ้นสุด Step 4 เขียนลงบน FTP Server ----------


                //ลบไฟล์ชั่วคราวออกจากเว็บเซิร์ฟเวอร์ (ไม่ใช่ FTP เซิร์ฟเวอร์)
                //TRFFile.delete();
                //REVFile.delete();

                /*
                 File PDFS = new File(serverProcessPath + "WUVPNSetup.pdf");
                 FileInputStream test = new FileInputStream(PDFS);
                 File TRFS = new File(TRFFilePath);
                 FileInputStream st1 = new FileInputStream(TRFFilePath);
                 File REVS = new File(REVFilePath);
                 FileInputStream st2 = new FileInputStream(REVFilePath);
                
                 FtpClient ftpClient2 = new FtpClient();
                 ftpClient2.openServer("192.168.56.102");
                 ftpClient2.login("root", "jolojie");
                 //ftpClient.cd("import/DMS/");
                 ftpClient2.binary();
                
                 TelnetOutputStream os = ftpClient2.put(PDFS.getName());
                 File file_in = new File(PDFS.getPath() + PDFS.getName());
                
                 FileInputStream is = new FileInputStream(file_in);
                 byte[] bytes = new byte[1024];
                 int c;
                
                 while ((c = is.read(bytes)) != -1) {
                 os.write(bytes, 0, c);
                 }
                 is.close();
                 os.close();
                 ftpClient2.closeServer();
                 */

                //System.out.println(ftpClient.printWorkingDirectory());

                result += "success";

            } else {
                if (found129 == false && found324 == false) {
                    result += "เกิดข้อผิดพลาด : ไม่พบด่าน 129 และ 324 ในแฟ้มข้อมูล ";
                } else if (found129 == false) {
                    result += "เกิดข้อผิดพลาด : ไม่พบด่าน 129 ในแฟ้มข้อมูล ";
                } else if (found324 == false) {
                    result += "เกิดข้อผิดพลาด : ไม่พบด่าน 324 ในแฟ้มข้อมูล ";
                } else {
                    result += "เกิดข้อผิดพลาด : ช่วงวันที่ปฏิบัติงานของทั้งสองไฟล์ไม่ตรงกัน (ไฟล์สายทางที่ 1 : " + oprDate1 + " กับ ไฟล์สายทางที่ 3 : " + oprDate3 + ") กรุณาตรวจสอบแฟ้มข้อมูล .xls อีกครั้ง ";
                }
            }

            request.setAttribute("result", result);

            RequestDispatcher rd;

            rd = request.getRequestDispatcher("/content/Program/P110.jsp");
            rd.forward(request, response);

        } catch (Exception w) {
            System.out.println("เกิดข้อผิดพลาด (Exception) : " + w.getMessage());
            String result = "เกิดข้อผิดพลาด (Exception) : " + w.getMessage();
            request.setAttribute("result", result);

            RequestDispatcher rd;

            rd = request.getRequestDispatcher("/content/Program/P110.jsp");
            rd.forward(request, response);
        }
    }
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
