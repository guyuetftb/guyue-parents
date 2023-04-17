package com.gy.util;

import java.text.SimpleDateFormat;

public class DateUtils {
	// 时间

	public final static int SECOND = 1000;

	public final static int MINUTE = 60 * SECOND;

	public final static int HOUR = 60 * MINUTE;

	public final static int DAY = 24 * HOUR;

	public final static int WEEK = 7 * DAY;

	public final static int PAGE_SIZE = 12;

	public final static SimpleDateFormat yearMonthDayHourMinuteSecond = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public final static SimpleDateFormat yearMonthDay = new SimpleDateFormat("yyyy-MM-dd");

}
