package com.redru.application;

import com.redru.engine.utils.ResourceUtils;

public class Constants {
	public static final int TARGET_SLEEP_TIME = Integer.parseInt(ResourceUtils.getApplicationProperty("target_sleep_time"));
	public static final int TARGET_FPS = 1000 / TARGET_SLEEP_TIME;
}
