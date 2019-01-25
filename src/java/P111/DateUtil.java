/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package P111;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil {

    public static Date customDate(int c) {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date); // convert your date to Calendar object
        cal.add(Calendar.DATE, c);
        date = cal.getTime();
        return date;
    }

    public static String getMonthThaiStr(int m) {
        String[] str = {"มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน", "พฤษภาคม", "มิถุนายน", "กรกฏาคม", "สิงหาคม", "กันยายน", "ตุลาคม", "พฤศจิกายน", "ธันวาคม"};
        return str[m - 1];
    }

    public static String getMonthThaiShortNameStr(int m) {
        String[] str = {"ม.ค", "ก.พ", "มี.ค", "เม.ย", "พ.ค", "มิ.ย", "ก.ค", "ส.ค", "ก.ย", "ต.ค", "พ.ย", "ธ.ค"};
        return str[m - 1];
    }

    public static int getDayQtyOfMonth(int month, int year) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        return calendar.getActualMaximum(Calendar.DATE);
    }

    public static String getLastDayOfMonth(String d) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", new Locale("th", "TH"));
        Date da = sdf.parse(d);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(da);

        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);

        Date lastDayOfMonth = calendar.getTime();

        return String.valueOf(sdf.format(lastDayOfMonth));
    }

    public static String getNow(String fmt) throws ParseException {
        Date date = new Date();
        SimpleDateFormat dt1 = new SimpleDateFormat(fmt);
        return dt1.format(date);
    }

    public static String getNow() throws ParseException {
        Date date = new Date();
        SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd : HH:mm", Locale.US);
        return dt1.format(date);
    }

    public static Date string2Date(String dt) throws java.text.ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        Date date = formatter.parse(dt);
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        return date;

    }

    public static Date string2Date(String dt, String fm) throws java.text.ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(fm, Locale.US);

        Date date = formatter.parse(dt);
        // date.setHours(0);
        //date.setMinutes(0);
        //date.setSeconds(0);
        return date;

    }

    public static String date2String(Date dt, String fmt) throws ParseException {

        SimpleDateFormat dt1 = new SimpleDateFormat(fmt, Locale.US);
        return dt1.format(dt);
    }

    public static boolean isSabbathDay(int emptype, Date d) throws ParseException {
        Date date = d;
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day_of_week = c.get(Calendar.DAY_OF_WEEK);

        if (emptype == 1) {
            return day_of_week == 7 || day_of_week == 1;
        } else {
            return day_of_week == 1;
        }
    }

    public static String getDayThai(Date date) {
        String[] str = {"จันทร์", "อังคาร", "พุธ", "พฤหัส", "ศุกร์", "เสาร์", "อาทิตย์"};

        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day_of_week = c.get(Calendar.DAY_OF_WEEK);
        return str[day_of_week - 1];
    }

    public static Date string2Time(String dt) throws java.text.ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.US);

        Date date = formatter.parse(dt);
        return date;
    }

    public static boolean isDatesOverLapped(Date startDate1, Date endDate1, Date startDate2, Date endDate2) throws NullPointerException {
        if (endDate1.before(startDate2) || startDate1.after(endDate2)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /* public static boolean isWorkingTime(Date startTime, Date endTime) throws ParseException {
     if (startTime.after(DateUtil.string2Time("08:29")) && endTime.before(DateUtil.string2Time("16:31"))) {
     return Boolean.TRUE;
     }
     return Boolean.FALSE;
     }*/
    public static boolean validateTimePattern24Hour(String t) {
        //00:00-23:59
        return Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]").matcher(t).matches();
    }

    public static double convTime100MinuteToDisplay60Minute(double n) {
        DecimalFormat f = new DecimalFormat("##.##");

        return Double.parseDouble(f.format(Math.floor(n) + (Double.parseDouble(f.format(n - Math.floor(n))) * 60 / 100)));
    }

    public static double convTime60MinuteTo100Minute(double n) {
        DecimalFormat f = new DecimalFormat("##.##");

        return Double.parseDouble(f.format(Math.floor(n) + (Double.parseDouble(f.format(n - Math.floor(n))) * 100 / 60)));
    }

    public static Date calDay(Date dt,int d) {
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, d);
        return c.getTime();
    }
    public static void main(String[] args) throws ParseException {
        Date d = DateUtil.string2Date("28022017 221222", "ddMMyyyy HHmmss");
        
    }
}
