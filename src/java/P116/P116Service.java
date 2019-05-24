/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P116;

import com.Helper.Helper;
import java.io.FileNotFoundException;

/**
 *
 * @author chonpisit_klo
 */
public class P116Service extends Helper {

    public void transferData(String reqDate) throws Exception, FileNotFoundException {
        String date = util.DateUtil.convertFormat(reqDate, "MM/dd/yyyy", "yyyyMMdd");
        String rmtFile = "ET_08_ETC_RMT_" + date + ".txt";
        String trxFile = "ET_08_ETC_TRX_" + date + ".txt";
        if (hasFile(rmtFile)) {
            uploadETCToFTP(rmtFile);
        } else {
            throw new FileNotFoundException("ไม่พบไฟล์ " + rmtFile);
        }
        if (hasFile(trxFile)) {
            uploadETCToFTP(trxFile);
        } else {
            throw new FileNotFoundException("ไม่พบไฟล์ " + trxFile);
        }
    }
}
