package com.oauth.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;

public class TimeUtil {

private static ZoneOffset ZONEOFFSET = ZoneOffset.of("+8");
	
	public static DateTimeFormatter FULLY_DIGITAL = new DateTimeFormatterBuilder().appendPattern("yyyyMMddHHmmss").toFormatter();
	
	
	/**
	 * 当前时间
	 */
	public static long currentMilli() {
		return LocalDateTime.now().toInstant(ZONEOFFSET).toEpochMilli();
	}
	
	public static Date currentDate() {
		return Date.from(LocalDateTime.now().toInstant(ZONEOFFSET));
	}
	
	public static long unixTimeStamp() {
		return currentMilli()/1000;
	}
	
	public static String currentYMDHMS() {
		return LocalDateTime.now().format(FULLY_DIGITAL);
	}
	
	
	/**
	 * 当日最后时间点
	 */
	public static Date dateLastTime(Date date) {
		LocalDate ld = date.toInstant().atZone(ZONEOFFSET).toLocalDate();
		return Date.from(LocalDateTime.of(ld.getYear(), ld.getMonth(), ld.getDayOfMonth(), 23, 59, 59).toInstant(ZONEOFFSET));
	}
	
	
	/**
	 * 时间格式化
	 */
	public static String milliFormat(long time, DateTimeFormatter formatter) {
		return milliToLdt(time).format(formatter);
	}
	
	public static String dateFormat(Date date, DateTimeFormatter formatter) {
		return dateToLdt(date).format(formatter);
	}
	
	
	/**
	 * 时间类型转换
	 */
	public static LocalDateTime dateToLdt(Date date) {
		return LocalDateTime.ofInstant(date.toInstant(), ZONEOFFSET);
	}
	
	public static Date ldtToDate(LocalDateTime ldt) {
		return Date.from(ldt.toInstant(ZONEOFFSET));
	}
	
	public static LocalDateTime milliToLdt(long time) {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZONEOFFSET);
	}
	
	public static long ldtToMilli(LocalDateTime ldt) {
		return ldt.toInstant(ZONEOFFSET).toEpochMilli();
	}
}
