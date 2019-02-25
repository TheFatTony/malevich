package io.malevich.server.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class DateUtils{

    public static Timestamp getDateWithoutTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    public static Timestamp addDay(Date date, int daysToAdd) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, daysToAdd);
        return new Timestamp(calendar.getTimeInMillis());
    }
}
