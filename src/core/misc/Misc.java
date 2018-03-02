package core.misc;

import java.util.Calendar;
import java.util.GregorianCalendar;

public abstract class Misc {
	
	public static String getTime() {
		GregorianCalendar calendar = new GregorianCalendar();
    	
    	int hour = calendar.get(Calendar.HOUR_OF_DAY);
    	int minute = calendar.get(Calendar.MINUTE);
    	
    	String m = "";
    	
    	String s_minute = "";
    	
    	if (hour > 12) {
    		hour -= 12;
    		m = "pm";
    	} else {
    		m = "am";
    	}
    	
    	if (minute < 10) {
    		s_minute = "0" + minute;
    	} else {
    		s_minute = "" + minute;
    	}
    	
    	String time = hour + ":" + s_minute + m;
    	
    	return time;
	}
	
}
