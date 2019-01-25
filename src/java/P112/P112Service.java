package P112;

import com.Helper.Helper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;

public class P112Service extends Helper {

    public StringBuilder importCyber(String fn) {
        StringBuilder txt = new StringBuilder();
        txt.append("<br>---import cyber start...");
        try {
            InputStream reader = retrieveFromFTP("import/DMS", fn);
            if (reader == null) {
                txt.append("<br>--ไม่มีไฟล์จาก (DMS)--");
                reader = retrieveFromFTP("import/DMS_FOR_TEST", fn);
            }

            if (reader == null) {
                txt.append("<br>--ไม่มีการส่งไฟล์--");
            } else {
                BufferedReader br = new BufferedReader(new InputStreamReader(reader));
                String sCurrentLine;
                BufferedWriter bw = null;
                FileWriter fw = null;
                fw = new FileWriter("D:\\pluginRVA\\P112\\Tmp\\" + fn.replace(".gw", ".mnl"));
                bw = new BufferedWriter(fw);
                while ((sCurrentLine = br.readLine()) != null) {
                    String[] str = sCurrentLine.split("\t");
                    String stt = str[0];
                    if (stt.equals("403")) {
                        if (str[8].indexOf("1") < 0 && str[8].indexOf("2") < 0 && str[8].indexOf("3") < 0) {//ด่าน 404 
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
                    bw.newLine();
                }
                br.close();
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
        }

    }

    public static void main(String[] args) {
        P112Service p = new P112Service();
        System.out.println(p.importCyber("TL_04_ETC_CLS_TRF_20170115.txt"));
        //System.out.println("5,6".indexOf("3"));
    }
}
