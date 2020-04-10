package utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

public class DateHelper {

	protected static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public String getCurrentDateFormatted(String format) {
		Date date = new Date();
		LOGGER.fine("generated raw date: "+date);
		DateFormat dateFormat = new SimpleDateFormat(format);
		LOGGER.fine("created date formatter");
		String formattedDate = dateFormat.format(date);
		LOGGER.fine("returning fomatted current date: "+formattedDate);
		return formattedDate;
	}

	public String addSubtractToDate(String date, String format, int increment) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		Calendar calander = Calendar.getInstance();
		LOGGER.fine("created instance of the calander");
		try {
			calander.setTime(simpleDateFormat.parse(date));
			LOGGER.fine("set time to the calander using the date "+date);
		} catch (ParseException e) {
			LOGGER.warning("could not set time to the calander instance");
			e.printStackTrace();
		}
		calander.add(Calendar.DATE, increment);
		LOGGER.fine("added the increment: "+increment+" to the calander");
		String finalDate = simpleDateFormat.format(calander.getTime());
		LOGGER.fine("returnnig the date after incrementing: "+finalDate);
		return finalDate;
	}
	
	public static void main(String[] args) {
		DateHelper dh = new DateHelper();
		String dt = dh.getCurrentDateFormatted("MMddyyyyhhmmss");
		System.out.println(dt);
	}
}
