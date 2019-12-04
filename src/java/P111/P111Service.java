/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P111;

import Connect.Connector;
import com.Helper.Helper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;

/**
 *
 * @author siridet_suk
 */
public class P111Service extends Helper {

    public P111Service() {
    }

    public StringBuilder importCyber(String fDate, String line) {
        Connector c = new Connect.Connector();
        StringBuilder txt = new StringBuilder();
        txt.append("<br>---import cyber start...");
        try {

            String dateArr[] = fDate.split("/");
            String dateFind = dateArr[2] + dateArr[0] + dateArr[1];
            String sys_type = "OPN";

            if (line.equals("4") || line.equals("6") || line.equals("7")) {
                sys_type = "CLS";
            }

            String f = "TL_0" + (line.equals("3") ? "1" : line) + "_ETC_" + sys_type + "_REV_" + dateFind + ".txt";

            // return retrieveFromFTP(a, b);
            InputStream reader = retrieveFromFTP("import/DMS", f);
            if (reader == null) {
                txt.append("<br>--ไม่มีไฟล์จาก cyber--");

            } else {
                BufferedReader br = new BufferedReader(new InputStreamReader(reader));
                String sCurrentLine;

                c.connect();
                c.addBatch("DELETE FROM CYBER_REV WHERE SUBSTR(STT_CODE,0,1)='" + line + "' AND TRX_DT=TO_DATE('" + dateArr[1] + "" + dateArr[0] + "" + dateArr[2] + "','ddmmyyyy')");
                txt.append("<br>--clear--"); 
                while ((sCurrentLine = br.readLine()) != null) {
                    String[] str = sCurrentLine.split("\t");
                    if (str[0].startsWith(line)) {
                        if (line.equals("4") || line.equals("6") || line.equals("7")) {
                         //   System.out.println("INSERT INTO CYBER_REV  VALUES('" + str[0] + "',TO_DATE('" + str[2] + "','ddmmyyyy'),'" + str[4] + "','" + str[5] + "','" + str[6] + "','" + str[7] + "','" + str[8] + "','" + str[9] + "','" + str[10] + "','" + str[11] + "','" + str[12] + "','" + str[1] + "')");
                            c.addBatch("INSERT INTO CYBER_REV  VALUES('" + str[0] + "',TO_DATE('" + str[2] + "','ddmmyyyy'),'" + str[4] + "','" + str[5] + "','" + str[6] + "','" + str[7] + "','" + str[8] + "','" + str[9] + "','" + str[10] + "','" + str[11] + "','" + str[12] + "','" + str[1] + "')");
                        } else {
                            c.addBatch("INSERT INTO CYBER_REV VALUES('" + str[0] + "',TO_DATE('" + str[1] + "','ddmmyyyy'),'" + str[3] + "','" + str[4] + "','" + str[5] + "','" + str[6] + "','" + str[7] + "','" + str[8] + "','" + str[9] + "','" + str[10] + "','" + str[11] + "','')");
                        }
                    }
                }
                br.close();
                c.executeBatch();
                //c.commit();
                c.clearBatch();

                txt.append("<br>--นำเข้า cyber เรียบร้อย--");

            }
            reader.close();
        } catch (Exception e) {
            txt.append("<br>--Error --<br>" + e.getMessage());
        } finally {
            c.close();
            return txt;
        }

    }

    public StringBuilder importGateWayEXAT(String dt, String line) throws IOException {
        StringBuilder txt = new StringBuilder();
        txt.append("<br--import gateway exat start...");
        Connector c = new Connect.Connector();

        try {
            NtlmPasswordAuthentication authentication = new NtlmPasswordAuthentication("1.3.3.1", "administrator", "GWTW");
            boolean hasFileP16 = false;
            //  boolean hasFileP06 = false;
            boolean hasFileP02 = false;
            SmbFile file = new SmbFile("smb://1.3.3.1/beer/gateway/", authentication);
            SmbFile[] listFiles = file.listFiles();
            String dateArr[] = dt.split("/");
            String dd = dateArr[1];
            String mm = dateArr[0];
            String yyyy = dateArr[2];
            c.connect();

            if (line.equals("1") || line.equals("2") || line.equals("3") || line.equals("4") || line.equals("5") || line.equals("6") || line.equals("8")) {
                txt.append("<br>-- สายทาง exat --");
                c.execute("DELETE FROM GATEWAY_REV WHERE SUBSTR(PLAZA_ID,0,1)='" + line + "' AND TO_CHAR(TRX_DT,'ddmmyyyy')='" + dd + "" + mm + "" + yyyy + "'");
                txt.append("<br>--clear gw P16,P02 success--");
                for (SmbFile listFile : listFiles) {
                    if (listFile.getName().startsWith("P16_" + yyyy + "" + mm)) {
                        hasFileP16 = true;
                        BufferedReader br = new BufferedReader(new InputStreamReader(listFile.getInputStream()));
                        String dataInFile;

                        while ((dataInFile = br.readLine()) != null) {
                            String[] str = dataInFile.split("\\|");
                            if (str[1].startsWith(line) && str[2].equals(dd + "/" + mm + "/" + yyyy)) {
                                c.addBatch("INSERT INTO GATEWAY_REV (PLAZA_ID,TRX_DT,AMT,REP)"
                                        + " VALUES('" + str[1] + "',TO_DATE('" + str[2] + "','dd/mm/yyyy'),'" + str[8] + "','P16')");
                            }
                        }
                        br.close();
                        txt.append("<br>--read P16 --");
                    }
                    if (listFile.getName().startsWith("P02_" + yyyy + "" + mm + "" + dd)) {
                        hasFileP02 = true;
                        BufferedReader br = new BufferedReader(new InputStreamReader(listFile.getInputStream()));
                        String dataInFile;

                        while ((dataInFile = br.readLine()) != null) {
                            String[] str = dataInFile.split("\\|");
                            if (str[1].startsWith(line) && str[4].startsWith(dd + "/" + mm + "/" + yyyy)) {

                                c.addBatch("INSERT INTO GATEWAY_REV (PLAZA_ID,TRX_DT,OBU,AMT,TRX_DT2,REP)"
                                        + " VALUES('" + str[1] + "',TO_DATE('" + str[4] + "','dd/mm/yyyy hh24:mi:ss'),'" + str[3] + "','" + str[6] + "',TO_TIMESTAMP('" + str[4] + "','dd/mm/yyyy hh24:mi:ss'),'P02')");
                            }
                        }
                        br.close();
                        txt.append("<br>--read P02 เรียบร้อย--");
                    }
                }
            }
            txt.append("<br>----P02 : ").append(hasFileP02);
            txt.append("<br>----P16 : ").append(hasFileP16);
            c.executeBatch();
            c.clearBatch();
            txt.append("<br>--นำเข้า gw เรียบร้อย--");
        } catch (Exception e) {
            txt.append("<br>--Error --<br>").append(e.getMessage());
        } finally {
            c.close();
            return txt;
        }

    }

    public StringBuilder importGateWayDOH(String dt, String line) throws IOException {
        StringBuilder txt = new StringBuilder();
        txt.append("<br>--import gateway doh start...");
        Connector c = new Connect.Connector();

        try {
            NtlmPasswordAuthentication authentication = new NtlmPasswordAuthentication("1.3.3.1", "administrator", "GWTW");
            //  boolean hasFileP16 = false;
            boolean hasFileP06 = false;
            // boolean hasFileP02 = false;
            SmbFile file = new SmbFile("smb://1.3.3.1/beer/gateway/", authentication);
            SmbFile[] listFiles = file.listFiles();
            String dateArr[] = dt.split("/");
            String dd = dateArr[1];
            String mm = dateArr[0];
            String yyyy = dateArr[2];
            c.connect();

            if (line.equals("7") || line.equals("9")) {
                Date dt1 = DateUtil.calDay(DateUtil.string2Date(yyyy + "-" + mm + "-" + dd + " 22:00:01", "yyyy-MM-dd HH:mm:ss"), -1);
                Date dt2 = DateUtil.string2Date(yyyy + "-" + mm + "-" + dd + " 22:00:00", "yyyy-MM-dd HH:mm:ss");
                txt.append("<br>-- สายทาง doh --");

                c.execute("DELETE FROM GATEWAY_REV WHERE SUBSTR(PLAZA_ID,0,1)='" + line + "' "
                        + "AND TRX_DT >= TO_DATE ('" + DateUtil.date2String(dt1, "ddMMyyyy HHmmss") + "',  'ddmmyyyy hh24miss')"
                        + "AND TRX_DT <= TO_DATE ('" + DateUtil.date2String(dt2, "ddMMyyyy HHmmss") + "',  'ddmmyyyy hh24miss')");

                txt.append("<br>--clear gw P06 success--");//ลบ 22.00-22.00
                for (SmbFile listFile : listFiles) {
                    if ((listFile.getName().startsWith("P06_" + yyyy + "" + mm + "" + dd))
                            || (listFile.getName().startsWith("P06_" + DateUtil.date2String(DateUtil.calDay(DateUtil.string2Date(dd + "" + mm + "" + yyyy, "ddMMyyyy"), -1), "yyyyMMdd")))) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(listFile.getInputStream()));
                        String dataInFile;
                        hasFileP06 = true;
                        while ((dataInFile = br.readLine()) != null) {
                            String[] str = dataInFile.split("\\|");
                            if ((str[1].startsWith(line))
                                    && (DateUtil.string2Date(str[5], "dd/MM/yyyy HH:mm:ss").getTime() >= dt1.getTime() && (DateUtil.string2Date(str[5], "dd/MM/yyyy HH:mm:ss").getTime() <= dt2.getTime()))) {
                                c.addBatch("INSERT INTO GATEWAY_REV (PLAZA_ID,TRX_DT,OBU,AMT,TRX_DT2,REP)"
                                        + " VALUES('" + str[1] + "',TO_DATE('" + str[5] + "','dd/mm/yyyy hh24:mi:ss'),'" + str[3] + "','" + str[6] + "',TO_TIMESTAMP('" + str[5] + "','dd/mm/yyyy hh24:mi:ss'),'P06')");
                            }
                        }
                        br.close();
                        txt.append("<br>--insert P06 --");
                    }
                }
            }
            txt.append("<br> P06 : ").append(hasFileP06);
            c.executeBatch();
            c.clearBatch();
            txt.append("<br>--นำเข้า gw เรียบร้อย--");
        } catch (Exception e) {
            e.printStackTrace();
            txt.append("<br>--Error --<br>").append(e.getMessage());
        } finally {
            c.close();
            return txt;
        }

    }

    public List<TRXModel> retriveDataExat(String dt, String line) throws IOException {
        List<TRXModel> trxs = new ArrayList<TRXModel>();
        Connector c = new Connect.Connector();
        String dateArr[] = dt.split("/");
        String dd = dateArr[1];
        String mm = dateArr[0];
        String yyyy = dateArr[2];
        try {
            c.connect();

            if (line.equals("1") || line.equals("2") || line.equals("3") || line.equals("4") || line.equals("5") || line.equals("6") || line.equals("8")) {
                c.executeQuery("SELECT CY.STT AS STT , SUM (CY.REV) AS REV_CYBER, SUM (GW.REV) AS REV_GW FROM "
                        + "(SELECT STT_CODE AS STT,SUM (PASS1) AS REV FROM CYBER_REV WHERE TRX_DT = \"TO_DATE\" ('" + dd + "" + mm + "" + yyyy + "', 'ddmmyyyy') AND \"SUBSTR\"(STT_CODE,0,1)='" + line + "'	GROUP BY STT_CODE) CY "
                        + "LEFT JOIN  (	SELECT PLAZA_ID AS STT,	SUM (AMT) AS REV	FROM GATEWAY_REV th	WHERE	\"TO_CHAR\" (th.TRX_DT, 'ddmmyyyy') = '" + dd + "" + mm + "" + yyyy + "' AND \"SUBSTR\"(PLAZA_ID,0,1)='" + line + "' GROUP BY PLAZA_ID) GW  "
                        + "ON CY.STT = GW.STT GROUP BY 	CY.STT,	GW.STT ORDER BY	CY.STT");

                while (c.getResult().next()) {
                    trxs.add(new TRXModel(c.getResult().getString("STT"), c.getResult().getString("REV_CYBER"), c.getResult().getString("REV_GW")));
                }
            }

        } catch (Exception e) {

        } finally {
            c.close();
        }
        return trxs;

    }

    public List<TRXModel> retriveDataDoh(String dt, String line) throws IOException {
        List<TRXModel> trxs = new ArrayList<TRXModel>();
        Connector c = new Connect.Connector();
        String dateArr[] = dt.split("/");
        String dd = dateArr[1];
        String mm = dateArr[0];
        String yyyy = dateArr[2];
        try {
            c.connect();

            if (line.equals("7") || line.equals("9")) {

                c.executeQuery("SELECT CY.STT AS STT , SUM (CY.REV) AS REV_CYBER, SUM (GW.REV) AS REV_GW FROM "
                        + "(SELECT STT_CODE AS STT,SUM (PASS1) AS REV FROM CYBER_REV WHERE TRX_DT = \"TO_DATE\" ('" + dd + "" + mm + "" + yyyy + "', 'ddmmyyyy') AND \"SUBSTR\"(STT_CODE,0,1)='" + line + "'	GROUP BY STT_CODE) CY "
                        + "LEFT JOIN  (	SELECT PLAZA_ID AS STT,	SUM (AMT) AS REV	FROM GATEWAY_REV th	"
                        //+ "WHERE TO_CHAR (th.TRX_DT, 'ddmmyyyy') = '" + dd + "" + mm + "" + yyyy + "' AND \"SUBSTR\"(PLAZA_ID,0,1)='" + line + "' GROUP BY PLAZA_ID) GW  "
                        + "WHERE 	TRX_DT >=  TO_DATE('" + DateUtil.date2String(DateUtil.calDay(DateUtil.string2Date(dd + "" + mm + "" + yyyy, "ddMMyyyy"), -1), "ddMMyyyy") + " 220001','ddmmyyyy hh24miss') AND TRX_DT <= TO_DATE('" + dd + "" + mm + "" + yyyy + " 220000','ddmmyyyy hh24miss') GROUP BY PLAZA_ID) GW "
                        + "ON CY.STT = GW.STT GROUP BY 	CY.STT,	GW.STT ORDER BY	CY.STT");

                while (c.getResult().next()) {
                    trxs.add(new TRXModel(c.getResult().getString("STT"), c.getResult().getString("REV_CYBER"), c.getResult().getString("REV_GW")));
                }
            }

        } catch (Exception e) {

        } finally {
            c.close();
        }
        return trxs;

    }

//    public static void main(String[] args) throws IOException {
//        P111Service p = new P111Service();
//        //  System.out.println(p.importGateWay("01/01/2017", "1"));
//        List<TRXModel> d = p.retriveDataDoh("01/01/2017", "7");
//
//    }
}
