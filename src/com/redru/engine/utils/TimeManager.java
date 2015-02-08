package com.redru.engine.utils;

import java.util.ArrayList;

public class TimeManager {
	public static final int NANOSECONDS = 1000000000;
	public static final int MICROSECONDS = 1000000;
	public static final int MILLISECONDS = 1000;
	
	private static ArrayList<Integer> times = new ArrayList<Integer>(400);
	
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
		times.add(tmp);
		return tmp;
	}
	
	public static void setStart() {
		timeStart = System.nanoTime();
	}
	
	public static void setEnd() {
		timeEnd = System.nanoTime();
	}
	
	public static int getAverage() {
		int tmp = 0;
		
		for (int w = 0; w < times.size(); w++) {
			tmp += times.get(w);
		}
		
		return tmp / times.size();
	}
	
	public static void resetAverage() {
		times.clear();
	}

}
