package LocalMonitoring;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeHandler {
	private static final String DATE_FORMAT_AGENT = "yyyy-MM-dd HH:mm:ss.SSSS";
	public static DateFormat dateNormalFormat = new SimpleDateFormat(
			DATE_FORMAT_AGENT, Locale.KOREA);

	/**
	 * Parameter로 주어진 String 시간을 기준으로 Date Object을 생성하여 Return 합니다.
	 * 
	 * @param p_time
	 * @return
	 */
	public static synchronized Date getDate(String p_time) {
		Date date = new Date();
		try {
			if (p_time != "") {
				date = dateNormalFormat.parse(p_time);// DateHandler.getDate(p_time,
														// DATE_FORMAT_AGENT) ;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 현재 시간을 기준으로 지정한 Format에 맞추어 시간 String을 리턴 합니다.
	 * 
	 * @return
	 */
	public static synchronized String getDateTime() {
		String retMessage = "NOT DEFINED";
		try {
			retMessage = dateNormalFormat.format(System.currentTimeMillis());
		} catch (Throwable ex) {

		}
		return retMessage;
	}

	public static synchronized String getDateTime(long p_timeMiliseconds) {
		String retMessage = "NOT DEFINED";
		try {
			retMessage = DateTimeHandler.dateNormalFormat
					.format(p_timeMiliseconds);
		} catch (Throwable ex) {

		}
		return retMessage;
	}

	// public static Date getDate(){
	// Date date = new Date();
	// try {
	// date = DateHandler.getDate(DATE_FORMAT_AGENT) ;
	// } catch (ParseException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return date;
	// }

}
