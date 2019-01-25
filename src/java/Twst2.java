
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Siridet
 */
public class Twst2 {

    public static void main(String[] args) throws Exception {
        System.out.println(getShift(14, 0, 2));
    }

    public static int getShift(int h, int m, int s) {
        int shift = 0;

        Date shift1 = new Date();
        shift1.setHours(6);
        shift1.setMinutes(0);
        shift1.setSeconds(0);

        Date shift2 = new Date();
        shift2.setHours(14);
        shift2.setMinutes(0);
        shift2.setSeconds(0);

        Date shift3 = new Date();
        shift3.setHours(22);
        shift3.setMinutes(0);
        shift3.setSeconds(0);

        Date d = new Date();
        d.setHours(h);
        d.setMinutes(m);
        d.setSeconds(s);

        if (d.after(shift1) && d.before(shift2) || d.equals(shift1)) {
            shift = 1;
        } else if (d.after(shift2) && d.before(shift3) || d.equals(shift2)) {
            shift = 2;
        } else if (d.after(shift3) || d.before(shift1)) {
            shift = 3;
        }

        return shift;
    }
}
