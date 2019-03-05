/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P114;

import Connect.Connector;
import com.Helper.Helper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author siridet_suk
 */
public class P114Service extends Helper {

    public StringBuilder convertData(String file, String line, String dt) {
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
                c.executeUpdate("DELETE FROM P114_TMP WHERE SUBSTR(STT_CODE_EXT,0,1)='" + line + "' ");
                txt.append("<br>--clear ^^--");
                BufferedReader br = new BufferedReader(new InputStreamReader(reader));
                String sCurrentLine;

                while ((sCurrentLine = br.readLine()) != null) {
                    String[] str = sCurrentLine.split("\t");
                    if (line.equals("9")) {
                        c.addBatch("INSERT INTO P114_TMP  VALUES('" + str[0] + "','" + str[1] + "','" + str[4] + "','" + str[5] + "','" + str[6] + "','" + str[7] + "','" + str[8] + "','" + str[9] + "','" + str[10] + "','" + str[11] + "','" + str[12] + "',NULL,'" + str[3] + "','" + str[2] + "')");
                    } else {
                        c.addBatch("INSERT INTO P114_TMP VALUES('" + str[0] + "','" + str[2] + "','" + str[5] + "','" + str[6] + "','" + str[7] + "','" + str[8] + "','" + str[9] + "','" + str[10] + "','" + str[11] + "','" + str[12] + "','" + str[13] + "','" + str[1] + "','" + str[4] + "','" + str[3] + "')");
                    }
                }
                c.executeBatch();
                br.close();
                txt.append("<br>--insert cyber into db success--");
                txt.append("<br>--start creaete mnl file--");

                BufferedWriter bw = null;
                FileWriter fw = null;
                fw = new FileWriter("D:\\pluginRVA\\P114\\Tmp\\" + file.replace(".gw", ".mnl"));
                bw = new BufferedWriter(fw);

                if (line.equals("9")) {
                    c.executeQuery("SELECT STT_CODE_EXT,'" + dt + "',TOLL_DT,EMP,SHIF,PASS_TYPE,ISSUER,SERVICE_PROVINDER,1,0,SUM(PASS1),0,0 FROM P114_TMP "
                            + "WHERE SUBSTR (STT_CODE_EXT, 0, 1) = '" + line + "' "
                            + " GROUP BY STT_CODE_EXT,TOLL_DT,EMP,SHIF,PASS_TYPE,ISSUER,SERVICE_PROVINDER ORDER BY STT_CODE_EXT");
                    while (c.getResult().next()) {
                        bw.write(c.getResult().getString(1) + "\t" + c.getResult().getString(2) + "\t" + c.getResult().getString(3) + "\t" + c.getResult().getString(4) + "\t" + c.getResult().getString(5) + "\t" + c.getResult().getString(6) + "\t" + c.getResult().getString(7) + "\t" + c.getResult().getString(8) + "\t" + c.getResult().getString(9) + "\t" + c.getResult().getString(10) + "\t" + c.getResult().getString(11) + "\t0\t0\n");
                    }
                } else {
                    c.executeQuery("SELECT STT_CODE_EXT,STT_CODE_ENT,'" + dt + "',TOLL_DT,EMP,SHIF,PASS_TYPE,ISSUER,SERVICE_PROVINDER,1,0,SUM(PASS1),0,0 FROM P114_TMP "
                            + "WHERE SUBSTR (STT_CODE_EXT, 0, 1) = '" + line + "'  "
                            + " GROUP BY STT_CODE_EXT,TOLL_DT,STT_CODE_ENT,EMP,SHIF,PASS_TYPE,ISSUER,SERVICE_PROVINDER  ORDER BY STT_CODE_EXT");
                    while (c.getResult().next()) {
                        bw.write(c.getResult().getString(1) + "\t" + c.getResult().getString(2) + "\t" + c.getResult().getString(3) + "\t" + c.getResult().getString(4) + "\t" + c.getResult().getString(5) + "\t" + c.getResult().getString(6) + "\t" + c.getResult().getString(7) + "\t" + c.getResult().getString(8) + "\t" + c.getResult().getString(9) + "\t" + c.getResult().getString(10) + "\t" + c.getResult().getString(11) + "\t" + c.getResult().getString(12) + "\t" + c.getResult().getString(13) + "\t0\n");
                    }
                }
                br.close();
                bw.close();
                fw.close();
                uploadToFTP("D:\\pluginRVA\\P114\\Tmp\\", file.replace(".gw", ".mnl"));
                txt.append("<br>--นำเข้า cyber เรียบร้อย--");
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
        P114Service s = new P114Service();
        System.out.println(s.convertData("TL_07_ETC_CLS_REV_20170204.txt", "7", "04022017"));

    }
}
