package it.spot.android.timespot.support;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author a.rinaldi
 */
public class Utils {

    public static class Date {

        public static String formatDateInServerFormat(Calendar date) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            return format.format(date.getTime());
        }

        public static Calendar parseDateInServerFormat(String date) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            Calendar calendar = Calendar.getInstance();

            try {
                calendar.setTimeInMillis(format.parse(date).getTime());
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

            return calendar;
        }
    }
}
