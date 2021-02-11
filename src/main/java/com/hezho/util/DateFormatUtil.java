package com.hezho.util;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil {
    public static SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String format(Date date){
        return format.format(date);
    }

    public static long toTime(String formatString){
        try {
            return format.parse(formatString).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
