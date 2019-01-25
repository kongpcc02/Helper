
import Connect.Connector;
import P114.P114Service;
import com.Helper.Helper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class ConvertFileAndGroupETC extends Helper {

    public StringBuilder convertData(String file) {
        StringBuilder txt = new StringBuilder();
        txt.append("<br>---retrive file cyber start...");
        Connector c = new Connect.Connector();
        try {
            InputStream reader = retrieveFromFTP("import/DMS", file);
            if (reader == null) {
                txt.append("<br>--ไม่มีไฟล์จาก (DMS)--");
                reader = retrieveFromFTP("import/DMS_FOR_TEST", file);
            }

            if (reader == null) {
                txt.append("<br>--ไม่มีการส่งไฟล์--");
            } else {
                c.connect();
                c.executeUpdate("DELETE FROM P114_TMP");
                txt.append("<br>--clear ^^--");
                BufferedReader br = new BufferedReader(new InputStreamReader(reader));
                String sCurrentLine;

                while ((sCurrentLine = br.readLine()) != null) {
                    String[] str = sCurrentLine.split("\t");
                    if (file.contains("OPN")) {
                        //c.addBatch("INSERT INTO P114_TMP VALUES('" + str[0] + "','" + str[1] + "','" + str[2] + "','" + str[3] + "','" + str[4] + "','" + str[5] + "','" + ((str[6].equals("000")) ? "00" : str[6]) + "','" + str[7] + "','" + str[8] + "','" + str[9] + "','" + str[10] + "')");
                        c.addBatch("INSERT INTO P114_TMP VALUES('" + str[0] + "','" + str[1] + "','" + str[3] + "','" + str[4] + "','" + str[5] + "','" + ((str[6].equals("000")) ? "00" : str[6]) + "','" + str[7] + "','" + str[8] + "','" + str[9] + "','" + str[10] + "','" + str[11] + "',NULL,'" + str[2] + "')");
                    } else {
                        if (str[0].equals("403")) {
                            if (str[8].indexOf("1") < 0 && str[8].indexOf("2") < 0 && str[8].indexOf("3") < 0) {//ด่าน 404 
                                str[0] = "404";
                            }
                        }
                        c.addBatch("INSERT INTO P114_TMP VALUES('" + str[0] + "','" + str[2] + "','" + str[4] + "','" + str[5] + "','" + str[6] + "','" + ((str[7].equals("000")) ? "00" : str[7]) + "','" + str[8] + "','" + str[9] + "','" + str[10] + "','" + str[11] + "','" + str[12] + "','" + str[1] + "','" + str[3] + "')");
                    }
                }
                c.executeBatch();
                br.close();
                txt.append("<br>--insert cyber into db success--");
                txt.append("<br>--start creaete mnl file--");

                BufferedWriter bw = null;
                FileWriter fw = null;
                fw = new FileWriter("D:\\pluginRVA\\P114\\Tmp\\" + file.replace(".gw", ".beer"));
                bw = new BufferedWriter(fw);

                if (file.contains("TL_01")) {
                    c.executeQuery("SELECT STT_CODE_EXT, TRX_DT ,EMP,SHIF,PASS_TYPE,ISSUER,SERVICE_PROVINDER,substr(LISTAGG(lane, ', ') WITHIN GROUP (ORDER BY lane),0,5) \"lane\",0,SUM(PASS1),0,0 FROM P114_TMP where substr(STT_CODE_EXT,0,1)='1'"
                            + " GROUP BY STT_CODE_EXT, TRX_DT ,EMP,SHIF,PASS_TYPE,ISSUER,SERVICE_PROVINDER ORDER BY STT_CODE_EXT");
                    while (c.getResult().next()) {
                        bw.write(c.getResult().getString(1) + "\t" + c.getResult().getString(2) + "\t" + c.getResult().getString(3) + "\t" + c.getResult().getString(4) + "\t" + c.getResult().getString(5) + "\t" + c.getResult().getString(6) + "\t" + c.getResult().getString(7) + "\t" + c.getResult().getString(8) + "\t" + c.getResult().getString(9) + "\t" + c.getResult().getString(10) + "\t" + c.getResult().getString(11) + "\t" + c.getResult().getString(11) + "\n");
                    }
                    c.executeQuery("SELECT STT_CODE_EXT, TRX_DT ,EMP,SHIF,PASS_TYPE,ISSUER,SERVICE_PROVINDER,lane,0,SUM(PASS1),0,0 FROM P114_TMP where substr(STT_CODE_EXT,0,1)='3'"
                            + " GROUP BY STT_CODE_EXT, TRX_DT ,EMP,SHIF,PASS_TYPE,ISSUER,SERVICE_PROVINDER,lane ORDER BY STT_CODE_EXT");
                    while (c.getResult().next()) {
                        bw.write(c.getResult().getString(1) + "\t" + c.getResult().getString(2) + "\t" + c.getResult().getString(3) + "\t" + c.getResult().getString(4) + "\t" + c.getResult().getString(5) + "\t" + c.getResult().getString(6) + "\t" + c.getResult().getString(7) + "\t" + c.getResult().getString(8) + "\t" + c.getResult().getString(9) + "\t" + c.getResult().getString(10) + "\t" + c.getResult().getString(11) + "\t" + c.getResult().getString(11) + "\n");
                    }
                } else if (file.contains("OPN") && !file.contains("TL_01")) {
                    c.executeQuery("SELECT STT_CODE_EXT, TRX_DT ,EMP,SHIF,PASS_TYPE,ISSUER,SERVICE_PROVINDER,substr(LISTAGG(lane, ', ') WITHIN GROUP (ORDER BY lane),0,7) \"lane\",0,SUM(PASS1),0,0 FROM P114_TMP "
                            + " GROUP BY STT_CODE_EXT, TRX_DT ,EMP,SHIF,PASS_TYPE,ISSUER,SERVICE_PROVINDER ORDER BY STT_CODE_EXT");
                    while (c.getResult().next()) {
                        bw.write(c.getResult().getString(1) + "\t" + c.getResult().getString(2) + "\t" + c.getResult().getString(3) + "\t" + c.getResult().getString(4) + "\t" + c.getResult().getString(5) + "\t" + c.getResult().getString(6) + "\t" + c.getResult().getString(7) + "\t" + c.getResult().getString(8) + "\t" + c.getResult().getString(9) + "\t" + c.getResult().getString(10) + "\t" + c.getResult().getString(11) + "\t" + c.getResult().getString(11) + "\n");
                    }
                } else {
                    c.executeQuery("SELECT STT_CODE_EXT,STT_CODE_ENT,TRX_DT,EMP,SHIF,PASS_TYPE,ISSUER,SERVICE_PROVINDER,substr(LISTAGG(lane, ', ') WITHIN GROUP (ORDER BY lane),0,7) \"lane\",0,SUM(PASS1),0,0 FROM P114_TMP "
                            + "  GROUP BY STT_CODE_EXT,STT_CODE_ENT, TRX_DT ,EMP,SHIF,PASS_TYPE,ISSUER,SERVICE_PROVINDER ORDER BY STT_CODE_EXT");
                    while (c.getResult().next()) {
                        bw.write(c.getResult().getString(1) + "\t" + c.getResult().getString(2) + "\t" + c.getResult().getString(3) + "\t" + c.getResult().getString(4) + "\t" + c.getResult().getString(5) + "\t" + c.getResult().getString(6) + "\t" + c.getResult().getString(7) + "\t" + c.getResult().getString(8) + "\t" + c.getResult().getString(9) + "\t" + c.getResult().getString(10) + "\t" + c.getResult().getString(11) + "\t" + c.getResult().getString(12) + "\t" + c.getResult().getString(13) + "\n");
                    }
                }
                br.close();
                bw.close();
                fw.close();
                //uploadToFTP("D:\\pluginRVA\\P114\\Tmp\\", file.replace(".gw", ".mnl"));
                txt.append("<br>--นำเข้า cyber เรียบร้อย--\t " + file);
            }
            reader.close();
        } catch (Exception e) {
            txt.append("<br>--Error --<br>").append(e.getMessage());
        } finally {
            c.close();
            return txt;
        }

    }

    public static void main(String[] args) {
        ConvertFileAndGroupETC s = new ConvertFileAndGroupETC();
        String[] type = {"TRF", "REV"};
        for (int i = 9; i <= 15; i++) {
            for (String t : type) {
                System.out.println(s.convertData("TL_07_ETC_CLS_" + t + "_201809" + String.format("%02d", i) + ".gw"));
            }

        }

    }
}
