package com.demo.bugtrack.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;

/**
 * @author gaurav_t
 * @since 25-06-2021
 * 
 *        Date time Util
 *
 */
public class DateUtil {

	public static LocalDate getStartAndEndDateOfWeek(int numberOfWeek, int year) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.WEEK_OF_YEAR, numberOfWeek);
		cal.set(Calendar.YEAR, year);
		return cal.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

	}

	public static LocalDate getEndDateOfWeek(LocalDate startDate) {
		return startDate.plusDays(6);
	}

	public static int getYearlyWeekCount(int year) {
		boolean is53weekYear = LocalDate.of(year, 1, 1).getDayOfWeek() == DayOfWeek.THURSDAY
				|| LocalDate.of(year, 12, 31).getDayOfWeek() == DayOfWeek.THURSDAY;
		return is53weekYear ? 53 : 52;
	}

	public static String getFormatMonth(int month) {
		return String.valueOf(month).length() == 2 ? String.valueOf(month) : "0" + month;
	}

}
