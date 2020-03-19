/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P119;

import com.Helper.Helper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author chonpisit_klo
 */
public class P119Service extends Helper {

    public StringBuilder importCyber(String fn) {
        StringBuilder txt = new StringBuilder();
        txt.append("<br><br>---import cyber start...");
        try {
            InputStream reader = retrieveFromFTP("import/DMS", fn);
            if (reader == null) {
                txt.append("<br>--ไม่มีไฟล์ " + fn + " จาก (DMS)--");
//                reader = retrieveFromFTP("import/DMS_FOR_TEST", fn);
            } else {
                BufferedReader br = new BufferedReader(new InputStreamReader(reader));
                String sCurrentLine;
                BufferedWriter bw = null;
                FileWriter fw = null;
                fw = new FileWriter("D:\\pluginRVA\\P119\\tmp\\" + fn.replace(".pro", ".txt"));
                bw = new BufferedWriter(fw);
                while ((sCurrentLine = br.readLine()) != null) {
                    String[] str = sCurrentLine.split("\t");
                    String stt = str[0];
                    if (stt.equals("129-1")) {
                        stt = "129-2";
                    }
                    bw.write(stt + "\t");
                    for (int i = 1; i < str.length; i++) {
                        if (i == 8 && str[i].length() > 10) {
                            bw.write(str[i].substring(0, 9) + "\t");
                            continue;
                        }
                        bw.write(str[i] + "\t");
                    }
                    bw.newLine();
                }
                br.close();
                txt.append("<br>--นำไฟล์ " + fn.replace(".pro", ".txt") + " เข้า cyber เรียบร้อย--");
                bw.close();
                fw.close();
                uploadToFTP("D:\\pluginRVA\\P119\\tmp\\", fn.replace(".pro", ".txt"));
            }
            reader.close();
        } catch (Exception e) {
            txt.append("<br>--Error --<br>").append(e.getMessage());
        } finally {
            //c.close();
            return txt;
        }

    }
}
