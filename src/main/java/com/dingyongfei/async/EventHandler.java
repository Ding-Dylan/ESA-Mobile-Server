package com.dingyongfei.async;

import java.util.List;

/**
 * @Author: Ding Yongfei
 */
public interface EventHandler {
    void doHandle(EventModel model);

    List<EventType> getSupportEventTypes();
}

