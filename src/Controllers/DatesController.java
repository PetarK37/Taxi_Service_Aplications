package Controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class DatesController {


    public static GregorianCalendar convertDateAndTimeString(String dateAndTime){
        if (dateAndTime.equals("-")){
            return null;
        }else{
            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy 'at' HH:mm:ss");
            GregorianCalendar calendar = new GregorianCalendar();
            try {
                calendar.setTime(format.parse(dateAndTime));
            }catch (ParseException e){
                e.printStackTrace();
            }
            return calendar;
        }
    }

    public static GregorianCalendar convertDateString(String date){
            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
            GregorianCalendar calendar = new GregorianCalendar();
            try {
                calendar.setTime(format.parse(date));
            }catch (ParseException e){
                e.printStackTrace();
            }
            return calendar;
        }

    public static String dateAndTimeToStr(GregorianCalendar dateAndTime){
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy \'at\' HH:mm:ss");
        if (dateAndTime != null){
            return format.format(dateAndTime.getTime());
        }else {
            return "-";
        }
    }

    public static String dateToStr(GregorianCalendar dateAndTime){
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        if (dateAndTime != null){
            return format.format(dateAndTime.getTime());
        }else {
            return "-";
        }
    }

}
