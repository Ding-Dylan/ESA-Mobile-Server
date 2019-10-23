package com.dingyongfei.async;

/**
 * @Author: Ding Yongfei
 */
public enum EventType {

    LOGIN(0),

    MAIL(1);

    private int value;

    EventType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
