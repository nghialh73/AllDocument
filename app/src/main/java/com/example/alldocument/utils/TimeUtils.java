package com.example.alldocument.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {
    public static String convertLongToTime(Long time) {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        return format.format(date);
    }
}
