package com.dingyongfei.util;

/**
 * @Author: Ding Yongfei
 */
public class RedisKeyUtil {
    private static String SPLIT = ":";
    private static String BIZ_EVENT = "EVENT";
    private static String RATING = "RATING";

    public static String getRatingKey(int entityId, int entityType) {
        return RATING + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

    public static String getEVENTQUEUEKey() {
        return BIZ_EVENT;
    }

}
