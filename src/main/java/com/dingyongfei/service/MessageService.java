package com.dingyongfei.service;

import com.emc.pie.adapters.cyclone.event.PowerStoreEvent;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.emc.pie.adapters.cyclone.rest.CycloneAdapter;

/**
 * @Author: Ding Yongfei
 */

@Service
public class MessageService {

    public PowerStoreEvent getConversationDetail(String eventId) {
        final CycloneAdapter adapter = (CycloneAdapter) new MockPowerStoreAdapterImpl();
        PowerStoreEvent event = adapter.getAlertService().getPowerStoreEvent(eventId);
//        return messageDAO.getConversationDetail(conversationId, offset, limit);
        return event;
    }

    public List<PowerStoreEvent> getConversationList() throws IOException, InterruptedException {
        final CycloneAdapter adapter = new MockPowerStoreAdapterImpl();
        List<PowerStoreEvent> list = adapter.getAlertService().getAllPowerStoreEvents();
        List<PowerStoreEvent> paginatedList = new ArrayList<>();
        for (int i = 100; i < 120; i++) {
            paginatedList.add(list.get(i));
        }

//        return messageDAO.getConversationList(userId, offset, limit);
        return paginatedList;
    }

}
