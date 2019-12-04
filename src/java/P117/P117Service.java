/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P117;

import Connect.Connector;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import util.DateUtil;
import com.Helper.Helper;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.net.ftp.FTPFile;

/**
 *
 * @author chonpisit_klo
 */
public class P117Service extends Helper {

//    private static final String DATABASE_SERVER_IP = "172.20.1.9";
//    private static final String DATABASE_SERVER_PORT = "1521";
//    private static final String DATABASE_SID = "etanetdb";
//    private static final String DATABASE_USER = "rvauser";
//    private static final String DATABASE_PASS = "userrva_";
    public boolean validateDate(Date startDate, Date endDate) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        if (startDate.equals(endDate)) {
            return true;
        }
        return startDate.before(endDate);
    }

    public void checkPath(String pathName) {
        File file = new File(pathName);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public String createFile(String pathName, String date, String version) throws Exception {
        String strDate = !"0".equals(version) ? date + "_" + version : date;
        String fileNameDoh = "SAP_06_AR_TL_DOH_" + strDate + ".txt";
        String fileNameExat = "SAP_06_AR_TL_EXAT_" + strDate + ".txt";
        String fileName = "SAP_06_AR_TL_" + strDate + ".txt";
        HashMap<String, String> fileNames = new HashMap<String, String>();
        fileNames.put("fileNameDoh", fileNameDoh);
        fileNames.put("fileNameExat", fileNameExat);
        fileNames.put("pathName", pathName);
        InputStream fileInStr = Helper.getFileSapRva(fileName);
        if (fileInStr == null) {
            return "<p><font color='red'>FILE " + fileName + " NOT FOUND </font></p>";
        }
        StringBuilder strBuilder = new StringBuilder();
        File fileDoh = new File(pathName + "/" + fileNameDoh);
        fileDoh.createNewFile();
        File fileExat = new File(pathName + "/" + fileNameExat);
        fileExat.createNewFile();
        writeData(fileInStr, date, fileNames);
        uploadSapRva(fileNames);
        strBuilder.append("<br>Create and Upload files " + fileNameDoh + " | " + fileNameExat + " to SAP server complete.");
        return strBuilder.toString();
    }

    private void writeData(InputStream fileInStr, String date, HashMap fileName) throws Exception {
        Connector conn = new Connector();
        try {
            conn.connectEta();
            String line;
            String[] data;
            String sql = "";
            String pathName = fileName.get("pathName").toString();
            String fileNameDoh = fileName.get("fileNameDoh").toString();
            String fileNameExat = fileName.get("fileNameExat").toString();
            StringBuilder strDoh = new StringBuilder();
            StringBuilder strExat = new StringBuilder();
            InputStreamReader inStr = new InputStreamReader(fileInStr);
            BufferedReader br = new BufferedReader(inStr);
            double dohRev, exatRev = 0.00;
            String temp;
            boolean isSum = false, isTl = true;
            while ((line = br.readLine()) != null) {
                data = line.split("\t");
                double sumRev = Double.parseDouble(data[6]);
                //DOH
                if (data[2].startsWith("TL") && !data[10].startsWith("OTH")) {
                    isTl = true;
                    if ("RP06000001".equals(data[5])) {
                        sql = "select  sum(COM_REV_TYPE1)+ sum(COM_REV_TYPE2) + sum(COM_REV_TYPE3) as doh_rev\n"
                                + "from RVA_MST_SCT_STT STT \n"
                                + "LEFT JOIN RVA_TRX_TOLL TOLL on STT.STATION_CODE = TOLL.STATION_CODE\n"
                                + "LEFT JOIN RVA_TRX_TOLL_DTL DTL on DTL.toll_id = TOLL.TOLL_ID\n"
                                + "where TRX_DATE = to_date('" + date + "' , 'yyyyMMdd')\n"
                                + "and PAY_TYPE = 'A'\n"
                                + "and COM_CODE = 'DOH'";
                        isSum = true;
                    } else {
                        sql = "select  sum(COM_REV_TYPE1)+ sum(COM_REV_TYPE2) + sum(COM_REV_TYPE3) as doh_rev\n"
                                + "from RVA_MST_SCT_STT STT \n"
                                + "LEFT JOIN RVA_TRX_TOLL TOLL on STT.STATION_CODE = TOLL.STATION_CODE\n"
                                + "LEFT JOIN RVA_TRX_TOLL_DTL DTL on DTL.toll_id = TOLL.TOLL_ID\n"
                                + "where TRX_DATE = to_date('" + date + "' , 'yyyyMMdd')\n"
                                + "and STT.SAP_STT_CODE = '" + data[10] + "'\n"
                                + "and PAY_TYPE = 'A'\n"
                                + "and COM_CODE = 'DOH'";
                        isSum = false;
                    }
                    ResultSet resultDoh = conn.executeQuery(sql);
                    while (resultDoh.next()) {
                        dohRev = resultDoh.getDouble("doh_rev");
                        exatRev = sumRev - dohRev;
                        if (dohRev != 0.00) {
                            temp = "";
                            for (int index = 0; index < data.length; index++) {
                                if (index == 2 || index == 11) {
                                    temp += "MTCDOH" + util.DateUtil.convertFormat(date, "yyyyMMdd", "ddMMyyyy") + "60\t";
                                } else if (index == 5) {
                                    temp += isSum ? "RP06000001\t" : "2131300\t";
                                } else if (index == 6) {
                                    temp += dohRev + "\t";
                                } else if (index == 7) {
                                    temp += isSum ? "\t" : "OX\t";
                                } else if ((index + 1) == data.length) {
                                    temp += data[index];
                                } else {
                                    temp += data[index] + "\t";
                                }
                            }
                            temp += "\n";
                            strDoh.append(temp);
                        }
                    }
                } else {
                    isTl = false;
                }
                //EXAT
                temp = "";
                for (int index = 0; index < data.length; index++) {
                    if (index == 6) {
                        temp += isTl ? exatRev + "\t" : sumRev + "\t";
                    } else {
                        temp += data[index] + "\t";
                    }
                }
                temp += "\n";
                strExat.append(temp);
            }
            inStr.close();
            File fileDoh = new File(pathName + "\\" + fileNameDoh);
            FileOutputStream fileOut = new FileOutputStream(fileDoh);
            Writer writer = new OutputStreamWriter(fileOut, "UTF-8");
            writer.write(strDoh.toString());
            writer.close();
            File fileExat = new File(pathName + "\\" + fileNameExat);
            fileOut = new FileOutputStream(fileExat);
            writer = new OutputStreamWriter(fileOut, "UTF-8");
            writer.write(strExat.toString());
            writer.close();
            fileOut.close();
        } finally {
            conn.close();
        }
    }

//    private void uploadFile(HashMap fileName){
//    
//    }
    public static void main(String[] args) {
    }
}
