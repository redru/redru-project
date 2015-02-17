package com.redru.engine.utils;


public class TimeManager {
	public static final int NANOSECONDS = 1000000000;
	public static final int MICROSECONDS = 1000000;
	public static final int MILLISECONDS = 1000;
	
	public static long timeStart = 0L;
	public static long timeEnd = 0L;
	
	public static int getDifferenceInSeconds() {
		return (int) ((timeEnd - timeStart) / NANOSECONDS);
	}
	
	public static int getDifferenceInMilliseconds() {
		return (int) ((timeEnd - timeStart) / MICROSECONDS);
	}
	
	public static int getDifferenceInMicroseconds() {
		int tmp = (int) ((timeEnd - timeStart) / MILLISECONDS);
		return tmp;
	}
	
	public static void setStart() {
		timeStart = System.nanoTime();
	}
	
	public static void setEnd() {
		timeEnd = System.nanoTime();
	}

}
