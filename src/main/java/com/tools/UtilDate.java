package com.tools;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by simon on 5/24/2019.
 */
public class UtilDate {
    public Date createDateMexicoLocalZone (){

        ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("America/Mexico_City"));

        final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
        final DateFormat FORMATTER_YYYYMMDD_HH_MM_SS = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        String dateStr = zdt.format(DATETIME_FORMATTER);

        Date utcDate = null;

        try {
            utcDate = FORMATTER_YYYYMMDD_HH_MM_SS.parse(dateStr);
            return utcDate;
        }catch (ParseException ex){

            return new Date();
        }
    }
}
