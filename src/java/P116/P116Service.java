/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P116;

import com.Helper.Helper;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author chonpisit_klo
 */
public class P116Service extends Helper {

    public void transferData(String reqDate) throws Exception, FileNotFoundException {
        String date = util.DateUtil.convertFormat(reqDate, "MM/dd/yyyy", "yyyyMMdd");
        String rmtFile = "ET_08_ETC_RMT_" + date + ".TXT";
        String trxFile = "ET_08_ETC_TRX_" + date + ".TXT";
        if (hasFile(rmtFile)) {
            uploadETCToFTP(rmtFile.replace("TXT","txt"));
        } else {
            throw new FileNotFoundException("ไม่พบไฟล์ " + rmtFile);
        }
        if (hasFile(trxFile)) {
            uploadETCToFTP(trxFile.replace("TXT","txt"));
        } else {
            throw new FileNotFoundException("ไม่พบไฟล์ " + trxFile);
        }
    }

    private void convertEmpCode(String fileName) throws Exception {
        DataInputStream dataInStr = new DataInputStream(getFileETC(fileName));
        BufferedReader bufferReade = new BufferedReader(new InputStreamReader(dataInStr));
        String strLine;
        while ((strLine = bufferReade.readLine()) != null) {
            String[] dataList = strLine.split("\t");
            String hrEmpCode = dataList[0] + "99999";
            dataList[2] = hrEmpCode;
            printList(dataList);
        }
    }
    
    private void printList(String [] dataList){
        for(String data : dataList){
            System.out.print(data+"\t");
        }
        System.out.println();
    }
    public static void main(String[] args) {
        try {
            new P116Service().convertEmpCode("ET_08_ETC_RMT_20190522.txt");
        } catch (Exception e) {

        }

    }
}
