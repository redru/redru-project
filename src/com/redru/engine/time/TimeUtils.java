package com.redru.engine.time;


public class TimeUtils {
	public static final int NANOSECOND = 1000000000;
	public static final int MICROSECOND = 1000000;
	public static final int MILLISECOND = 1000;
	
	public static long timeStart = 0L;
	public static long timeEnd = 0L;
	
	public static int getDifferenceInSeconds() {
		return (int) ((timeEnd - timeStart) / NANOSECOND);
	}
	
	public static int getDifferenceInMilliseconds() {
		return (int) ((timeEnd - timeStart) / MICROSECOND);
	}
	
	public static int getDifferenceInMicroseconds() {
		int tmp = (int) ((timeEnd - timeStart) / MILLISECOND);
		return tmp;
	}
	
	public static void setStart() {
		timeStart = System.nanoTime();
	}
	
	public static void setEnd() {
		timeEnd = System.nanoTime();
	}

}
