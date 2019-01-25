/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.exat.helper.System;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author Administrator
 */
public class Utill {

    public String convertToIntegerFormatt(String str) {
        str = str.replace('$', ' ');
        str = str.replaceAll(",", "");
        //str = str.replace(".", "");
        str = str.trim();

        if(str.equals("-") || str.equals(""))
            str = "0";
        return str;
    }

    public String setFormatDateTime(String s) throws ParseException {
        String sdate = s;
        SimpleDateFormat xxf = new SimpleDateFormat("dd/MM/yyyy kk:mm", Locale.US);
        Date xxxdate = xxf.parse((sdate));

        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy kk:mm", Locale.US);
        Date new_date = xxxdate;
        String str_date = sf.format(new_date);

        return str_date;
    }

    public String isDigit(String s) {
        String right = s;
        char c = 0;

        for (int index = 0; index < s.length(); index++) {

            c = s.charAt(index);

            if (c < '0' || c > '9') {

                right = "0";

            }
        }
        return right;
    }
}
