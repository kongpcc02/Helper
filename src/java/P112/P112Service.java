package P112;

import Connect.Connector;
import com.Helper.Helper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;

public class P112Service extends Helper {

    public StringBuilder importCyber(String fn, String dt) {
        StringBuilder txt = new StringBuilder();
        txt.append("<br>---retrive file cyber start...");
        Connector c = new Connect.Connector();
        try {
            InputStream reader = retrieveFromFTP("import/DMS", fn);
            if (reader == null) {
                txt.append("<br>--ไม่มีไฟล์จาก (DMS)--");
                reader = retrieveFromFTP("import/DMS_FOR_TEST", fn);
            }

            if (reader == null) {
                txt.append("<br>--ไม่มีการส่งไฟล์--");
            } else {
                c.connect();
                c.executeUpdate("DELETE FROM P112_TMP ");
                txt.append("<br>--clear ^^--");

                BufferedReader br = new BufferedReader(new InputStreamReader(reader));
                String sCurrentLine;
                //BufferedWriter bw = null;
                //FileWriter fw = null;
                //fw = new FileWriter("D:\\pluginRVA\\P112\\Tmp\\" + fn.replace(".gw", ".mnl"));
                //bw = new BufferedWriter(fw);
                //  while ((sCurrentLine = br.readLine()) != null) {
                    /*   String[] str = sCurrentLine.split("\t");
                 String stt = str[0];
                 if (stt.equals("403")) {
                 if (str[9].indexOf("1") < 0 && str[9].indexOf("2") < 0 && str[9].indexOf("3") < 0) {//ด่าน 404 
                 stt = "404";
                 }
                 }
                 bw.write(stt + "\t");
                 for (int i = 1; i < str.length; i++) {

                 if (i == 8 && str[i].length() > 7) {
                 bw.write(str[i].substring(0, 7) + "\t");
                 continue;
                 }
                 bw.write(str[i] + "\t");
                 }
                 bw.newLine();*/
                while ((sCurrentLine = br.readLine()) != null) {
                    String[] str = sCurrentLine.split("\t");
                    String stt = str[0];
                    if (stt.equals("403")) {
                        if (str[9].indexOf("1") < 0 && str[9].indexOf("2") < 0 && str[9].indexOf("3") < 0) {//ด่าน 404 
                            stt = "404";
                        }
                    }
                    if (stt.equals("403") || stt.equals("404")) {
                        System.out.println(stt + " | " + str[9] + " | " + str[11]);
                    }
                    c.addBatch("INSERT INTO P112_TMP VALUES('" + stt + "','" + str[2] + "','" + str[5] + "','" + str[6] + "','" + str[7] + "','" + str[8] + "','" + str[9] + "','" + str[10] + "','" + str[11] + "','" + str[12] + "','" + str[13] + "','" + str[1] + "','" + str[4] + "','" + str[3] + "')");
                    //   System.out.println("INSERT INTO P112_TMP VALUES('" + str[0] + "','" + str[2] + "','" + str[5] + "','" + str[6] + "','" + str[7] + "','" + str[8] + "','" + str[9] + "','" + str[10] + "','" + str[11] + "','" + str[12] + "','" + str[13] + "','" + str[1] + "','" + str[4] + "','" + str[3] + "')");
                }
                c.executeBatch();
                br.close();
                txt.append("<br>--insert cyber into db success--");
                txt.append("<br>--start creaete mnl file--");
                // }
               /* br.close();
                 txt.append("<br>--นำเข้า cyber เรียบร้อย--");
                 bw.close();
                 fw.close();
                 uploadToFTP("D:\\pluginRVA\\P112\\Tmp\\", fn.replace(".gw", ".mnl"));
                 }
                 reader.close();
                 } catch (Exception e) {
                 txt.append("<br>--Error --<br>").append(e.getMessage());
                 } finally {
                 //c.close();
                 return txt;
                 }*/
                BufferedWriter bw = null;
                FileWriter fw = null;
                fw = new FileWriter("D:\\pluginRVA\\P112\\Tmp\\" + fn.replace(".gw", ".mnl"));
                bw = new BufferedWriter(fw);

                c.executeQuery("SELECT STT_CODE_EXT,STT_CODE_ENT,'" + dt + "',TOLL_DT,EMP,SHIF,PASS_TYPE,ISSUER,SERVICE_PROVINDER,1,0,SUM(PASS1),0,0 FROM P112_TMP "
                        //+ "WHERE SUBSTR (STT_CODE_EXT, 0, 1) = '" + line + "'  "
                        + " GROUP BY STT_CODE_EXT,TOLL_DT,STT_CODE_ENT,EMP,SHIF,PASS_TYPE,ISSUER,SERVICE_PROVINDER  ORDER BY STT_CODE_EXT");
                while (c.getResult().next()) {
                    bw.write(c.getResult().getString(1) + "\t" + c.getResult().getString(2) + "\t" + c.getResult().getString(3) + "\t" + c.getResult().getString(4) + "\t" + c.getResult().getString(5) + "\t" + c.getResult().getString(6) + "\t" + c.getResult().getString(7) + "\t" + c.getResult().getString(8) + "\t" + c.getResult().getString(9) + "\t" + c.getResult().getString(10) + "\t" + c.getResult().getString(11) + "\t" + c.getResult().getString(12) + "\t" + c.getResult().getString(13) + "\t0\n");
                }

                br.close();
                bw.close();
                fw.close();
                uploadToFTP("D:\\pluginRVA\\P112\\Tmp\\", fn.replace(".gw", ".mnl"));
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
        P112Service p = new P112Service();
        System.out.println(p.importCyber("TL_04_ETC_CLS_TRF_TOLL_20190222.gw", "22022019"));
        //System.out.println("5,6".indexOf("3"));
    }
}
