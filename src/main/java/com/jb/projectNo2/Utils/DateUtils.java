package com.jb.projectNo2.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
    Date date = new Date();
    Calendar calendar = new GregorianCalendar();
    public static String getLocalDateTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        //System.out.println(simpleDateFormat.format(date));
        return String.valueOf(simpleDateFormat.format(date));
    }


}
