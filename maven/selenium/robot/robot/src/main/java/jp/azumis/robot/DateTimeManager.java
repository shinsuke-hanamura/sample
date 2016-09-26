package jp.azumis.robot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 時刻管理クラス
 * @author S.Hanamura
 *
 */
public class DateTimeManager  {

	public static final String RFC3339_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX";
	public static final String TO_MINUTE_DATE_FORMAT = "yyyy/MM/dd H:mm";
	
	private Calendar dateTime;
	
	public DateTimeManager() {
		this.dateTime = Calendar.getInstance();
	}
	
	public Calendar getDateTime() {return dateTime;}
	public void setDateTime(Calendar dateTime) {this.dateTime = dateTime;}

	public void setDateTimeWithFormat(String dateTime, String dateFormat) {
        //logger.debug("parse from String:" + dateTime);
        DateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date;
		try {
			date = sdf.parse(dateTime);
			this.dateTime.setTimeZone(TimeZone.getTimeZone("JST"));
	        this.dateTime.setTime(date);
		} catch (ParseException e) {
		}
	}

	public void setDateTimeWithRFC3339(String dateTime) {
		setDateTimeWithFormat(dateTime, RFC3339_DATE_FORMAT);
	}

	public String toString(String dateFormat) {
		if (this.dateTime == null) {
			return null;
		}
		//フォーマットパターンを指定して表示する
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String retString = sdf.format(dateTime.getTime());
        //logger.debug(retString);
        return retString;
	}
	
	public String toRFC3339String() {
        return toString(RFC3339_DATE_FORMAT);
	}

	/**
	 * 時刻文字列をCalender型に変換するstaticメソッド
	 * @param dateTime 時刻文字列
	 * @param dateFormat 時刻書式
	 * @return
	 */
	public static Calendar parseString(String dateTime, String dateFormat) {
		DateFormat sdf = new SimpleDateFormat(dateFormat);
		Calendar retCalendar = Calendar.getInstance();
		Date date;
		try {
			date = sdf.parse(dateTime);
			retCalendar.setTimeZone(TimeZone.getTimeZone("JST"));
			retCalendar.setTime(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retCalendar;
	}

	/**
	 * RFC3339形式の時刻文字列をCalender型に変換するstaticメソッド
	 * @param dateTime RFC3339形式の時刻文字列
	 * @return
	 */
	public static Calendar parseRFC3339String(String dateTime) {
		return parseString(dateTime, RFC3339_DATE_FORMAT);
	}

	/**
	 * Calender型の時刻をRFC3339形式の時刻文字列をに変換するstaticメソッド
	 * @param cal
	 * @return
	 */
	public static String toRFC3339String(Calendar cal) {
		SimpleDateFormat sdf = new SimpleDateFormat(RFC3339_DATE_FORMAT);
		String retString = sdf.format(cal.getTime());
		return retString;
	}
	
	/**
	 * 現在時刻をRFC3339形式の時刻文字列をに変換するstaticメソッド
	 * @return RFC3339_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX"
	 */
	public static String getNowWithRFC3339String() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(RFC3339_DATE_FORMAT);
		String retString = sdf.format(cal.getTime());
		return retString;
	}

	/**
	 * 本日日付をRFC3339形式の時刻文字列をに変換するstaticメソッド
	 * 時刻部分はカットする
	 * @return 本日日付 "yyyy-MM-dd" or Null(エラー時)
	 */
	public static String getTodayWithRFC3339String() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(RFC3339_DATE_FORMAT);
		String retString = sdf.format(cal.getTime());
		if (retString.length() < 10) {
			return null;
		}
		return retString.substring(0, 9);
	}

}
