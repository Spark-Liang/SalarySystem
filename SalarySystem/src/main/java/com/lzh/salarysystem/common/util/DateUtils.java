package com.lzh.salarysystem.common.util;

import java.util.Date;

public class DateUtils extends org.apache.commons.lang.time.DateUtils{

	public static Date add(Date a, Date b) {
		long augend = a != null ? a.getTime() : 0
			,addend = b != null ? b.getTime() : 0;
		return new Date(augend + addend);
	}

	public static Date sub(Date a, Date b) {
		long minuend = a != null ? a.getTime() : 0
			,subtrahend = b != null ? b.getTime() : 0;
		return new Date(minuend - subtrahend);
	}
}
