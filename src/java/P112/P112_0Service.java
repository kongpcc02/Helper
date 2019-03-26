/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P112;

import Connect.Connector;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.Helper.Helper;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author chonpisit_klo
 */
public class P112_0Service extends Helper {

    public StringBuilder convertDataLane0(String reqDate) {

        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("<br>---retrive file cyber start...");
        Connector connector = new Connect.Connector();
        try {
            String date = util.DateUtil.convertFormat(reqDate, "MM/dd/yyyy", "yyyyMMdd");
            String fileName = "TL_06_ETC_CLS_TRF_L0_" + date + ".txt";
            System.out.println("File Name : " + fileName);
            InputStream reader = retrieveFromFTP("import/DMS/", fileName);
//            System.out.println (reqDate + "   1  "+ reader );
            if (reader == null) {
                strBuilder.append("<br>--ไม่มีไฟล์จาก (DMS)--");
                reader = retrieveFromFTP("import/DMS_FOR_TEST/", fileName);
//                System.out.println (reqDate + "   2  "+ reader );
            }
            if (reader == null) {
                strBuilder.append("<br>--ไม่มีการส่งไฟล์--");
                return strBuilder;
            } else {
                
                connector.connect();
                connector.executeUpdate("DELETE FROM P112_TMP ");
                strBuilder.append("<br>--clear--");
                BufferedReader br = new BufferedReader(new InputStreamReader(reader));
                String sCurrentLine;
                while ((sCurrentLine = br.readLine()) != null) {
                    String[] str = sCurrentLine.split("\t");
                    String sql = "INSERT INTO P112_TMP VALUES('" + str[0] + "', '" + str[2] + "', '" + str[4] + "', '" + str[5] + "', '" + str[6] + "', '" + str[7] + "', '" + str[8] + "', '" + str[9] + "', '" + str[10] + "', '" + str[11] + "', '" + str[12] + "', '" + str[1] + "', '1666666660', null)";
                    System.out.println(sql);
                    connector.addBatch(sql);
                }
                connector.executeBatch();
                br.close();
                strBuilder.append("<br>-- insert cyber into db success --");
                strBuilder.append("<br>-- start creaete trf file for lane 0 --");

                BufferedWriter bw = null;
                FileWriter fw = null;
//                String file = fileName.replaceAll(date, "20150201");
                fw = new FileWriter("D:\\pluginRVA\\P112\\Tmp\\" + fileName.replace(".txt", ".mnl"));
                bw = new BufferedWriter(fw);
                StringBuilder data = new StringBuilder();
                ResultSet result = connector.executeQuery("SELECT STT_CODE_EXT, STT_CODE_ENT,  TRX_DT, '1666666660', '3', '01', '01', '06', '0', '0', SUM(PASS1) AS PASS1, '0', '0' \n"
                        + "FROM P112_TMP \n"
                        + "GROUP BY STT_CODE_EXT, STT_CODE_ENT, TRX_DT\n"
                        + "ORDER BY STT_CODE_EXT ASC");
                System.out.println("SELECT STT_CODE_EXT, STT_CODE_ENT,  TRX_DT, '1666666660', '3', '01', '01', '06', '0', '0', SUM(PASS1) AS PASS1, '0', '0' "
                        + "FROM P112_TMP \n"
                        + "GROUP BY STT_CODE_EXT, STT_CODE_ENT, TRX_DT\n"
                        + "ORDER BY STT_CODE_EXT ASC");
                while (result.next()) {
                    int size = 13;
                    for (int index = 1; index <= size; index++) {
//                        System.out.println(index);
//                        System.out.println(result.getString(index));C
                        data.append(result.getString(index)).append("\t");
                    }
                    data.append("\n");
                }
                bw.write(data.toString());
                br.close();
                bw.close();
                fw.close();
                uploadToFTP("D:\\pluginRVA\\P112\\Tmp\\", fileName.replace(".txt", ".mnl"));
                strBuilder.append("<br>--นำเข้า cyber เรียบร้อย--");
            }
            
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            strBuilder.append("<br>--Error --<br>").append(e.getMessage());
        } finally {
            connector.close();
            return strBuilder;
        }

//        return strBuilder;
    }

    private void a() {
        try {
            String startDate = "01/01/2019";
            String endDate = "02/01/2019";
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            Date date = sdf.parse(startDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            while (true) {
                if(!compareBeforeDate(sdf.format(cal.getTime()),endDate)){
                    break;
                }
                convertDataLane0(sdf.format(cal.getTime()));
                
                cal.add(Calendar.DAY_OF_YEAR, 1);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void main(String[] arg) {
        P112_0Service pp = new P112_0Service();
//        pp.a();
    }

    private boolean compareBeforeDate(String lastDate, String yesterday) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date lastShiftDate = sdf.parse(lastDate);
        Date yesterday1 = sdf.parse(yesterday);
        if (lastShiftDate.before(yesterday1)) {
            return true;
        } else {
            return false;
        }
    }
}
