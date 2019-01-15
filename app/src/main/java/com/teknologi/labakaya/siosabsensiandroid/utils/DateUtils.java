package com.teknologi.labakaya.siosabsensiandroid.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by januar on 1/11/18.
 */

public class DateUtils {
    public static final String DEFAULT_OUT_FORMAT = "dd/MM/yyyy";
    public static final String DEFAULT_IN_FORMAT = "yyyy-MM-dd";

    public static String format(String format, Date date){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.FRANCE);
            return  sdf.format(date);
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    public static String format(String format, String date){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date _date = parse(date);
        return  sdf.format(_date);
    }

    public static String format(Date date){
        return format(DEFAULT_OUT_FORMAT, date);
    }

    public static String format(String date){
        return format(DEFAULT_OUT_FORMAT, date);
    }

    public static Date parse(String date){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_IN_FORMAT);
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  null;
    }

    public static Date parse(String date, String format){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  null;
    }

    public static String getWeekDay(Date date) {
        switch (date.getDay()) {
            case 0:
                return "miggu";
            case 1:
                return "senin";
            case 2:
                return "selasa";
            case 3:
                return "rabu";
            case 4:
                return "kamis";
            case 5:
                return "jumat";
            case 6:
                return "sabtu";
            default:
                return "miggu";
        }
    }
}
