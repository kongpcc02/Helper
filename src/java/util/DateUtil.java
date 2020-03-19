/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author Siridet
 */
public class DateUtil {

    public static String convertFormat(String d, String formatt) throws ParseException {
        SimpleDateFormat sdfSource = new SimpleDateFormat("dd/MM/yyyy");
        Date date = sdfSource.parse(d);
        SimpleDateFormat sdfDestination = new SimpleDateFormat(formatt);

        d = sdfDestination.format(date);

        return d;
    }

    public static String convertFormat(String d, String from, String to) throws ParseException {
        SimpleDateFormat sdfSource = new SimpleDateFormat(from);
        Date date = sdfSource.parse(d);
        SimpleDateFormat sdfDestination = new SimpleDateFormat(to);

        d = sdfDestination.format(date);

        return d;
    }

    public static String convertFormatYear(String d, String formatt) throws ParseException {
        SimpleDateFormat sdfSource = new SimpleDateFormat("dd/MM/yyyy");
        Date date = sdfSource.parse(d);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, 543);
        SimpleDateFormat sdfDestination = new SimpleDateFormat(formatt);
        d = sdfDestination.format(calendar.getTime());

        return d;
    }

    public static String now(String f) {
        Locale.setDefault(new Locale("en", "US"));  //Import java.util.Locale;
        Calendar cal = Calendar.getInstance(Locale.US);
        SimpleDateFormat sdf = new SimpleDateFormat(f);
        return sdf.format(cal.getTime());
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(convertFormat("2012-01-05", "yyyy-MM-dd", "dd/MM/yyyy"));
    }

    public static String getDateTimeExportReport() {
        SimpleDateFormat sdf = new SimpleDateFormat("ออกรายงานเมื่อวันที่ dd MMMM yyyy เวลา HH:mm:ss น.", new Locale("th", "TH"));
        return sdf.format(new Date());
    }

    public static String getMonthTh(int m) {
        if (m == 1) {
            return "มกราคม";
        } else if (m == 2) {
            return "กุมภาพันธ์";
        } else if (m == 3) {
            return "มีนาคม";
        } else if (m == 4) {
            return "เมษายน";
        } else if (m == 5) {
            return "พฤษภาคม";
        } else if (m == 6) {
            return "มิถุนายน";
        } else if (m == 7) {
            return "กรกฎาคม";
        } else if (m == 8) {
            return "สิงหาคม";
        } else if (m == 9) {
            return "กันยายน";
        } else if (m == 10) {
            return "ตุลาคม";
        } else if (m == 11) {
            return "พฤศจิกายน";
        } else if (m == 12) {
            return "ธันวาคม";
        } else {
            return "ไม่มีชื่อเดือนนี้";
        }

    }
}
