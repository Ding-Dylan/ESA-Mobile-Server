package com.dingyongfei.controller;

import com.dingyongfei.model.HostHolder;
import com.dingyongfei.model.ViewObject;
import com.dingyongfei.service.MessageService;
import com.dingyongfei.service.UserService;
import com.dingyongfei.util.SeverityUtils;
import com.emc.pie.adapters.cyclone.event.PowerStoreEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Ding Yongfei
 */

@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    HostHolder hostHolder;

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    /*@Autowired
    Firebase firebase;*/


    @RequestMapping(path = {"/msg/detail"}, method = {RequestMethod.GET})
    public String conversationDetail(Model model,
                                     @RequestParam("conversationId") String eventId) {
        try {
            int localUserId = hostHolder.getUser().getId();
            //messageService.updateUnreadCount(localUserId, conversationId);
            //List<ViewObject> messages = new ArrayList<>();
            PowerStoreEvent conversation = messageService.getConversationDetail(eventId);
//            ViewObject vo = new ViewObject();
//            vo.set("message", conversation);
            //User user = userService.getUser(msg.getFromId());
//            if (user == null) {
//                continue;
//            }
            //vo.set("headUrl", user.getHeadUrl());
            //vo.set("userId", user.getId());
            //messages.add(vo);

//            User sendUser = userService.getUser(sendId);
//            model.addAttribute("sendUser", sendUser);
            String severityUrl = "";
            model.addAttribute("message", conversation);
            for (final SeverityUtils s : SeverityUtils.values()) {
                if (s.getKey().equals(conversation.getSeverity().value())) {
                    severityUrl = s.getSeverityUrl();
                }
            }
            model.addAttribute("severityUrl", severityUrl);
            //model.addAttribute("sendId", sendId);

        } catch (Exception e) {
            logger.error("Failed to get the station letter detail." + e.getMessage());
        }
        return "letterDetail";
    }

    /*@RequestMapping(path = {"/msg/list"}, method = {RequestMethod.GET})
    public String conversationList(Model model) {
        try {
            int localUserId = hostHolder.getUser().getId();
            List<ViewObject> conversations = new ArrayList<ViewObject>();
            List<Message> conversationList = messageService.getConversationList(localUserId, 0, 10);
            for (Message msg : conversationList) {
                ViewObject vo = new ViewObject();
                vo.set("conversation", msg);
                int targetId = msg.getFromId() == localUserId ? msg.getToId() : msg.getFromId();
                User user = userService.getUser(targetId);
                vo.set("unreadCount", messageService.getUnreadCount(localUserId, msg.getConversationId()));
                vo.set("user", user);
                vo.set("targetId", targetId);
                conversations.add(vo);
            }
            model.addAttribute("conversations", conversations);
        } catch (Exception e) {
            logger.error("Failed to get the station letter list." + e.getMessage());

        }
        return "letter";
    }*/

    @RequestMapping(path = {"/msg/list"}, method = {RequestMethod.GET})
    public String conversationList(Model model) {
        try {
            //firebase.init();
            int localUserId = hostHolder.getUser().getId();
            List<ViewObject> conversations = new ArrayList<ViewObject>();
            List<PowerStoreEvent> eventList = messageService.getConversationList();
            for (PowerStoreEvent event : eventList) {
                ViewObject vo = new ViewObject();
                vo.set("conversation", event);
                vo.set("severity", event.getSeverity().value());
                for (final SeverityUtils s : SeverityUtils.values()) {
                    if (s.getKey().equals(event.getSeverity().value())) {
                        vo.set("severityUrl", s.getSeverityUrl());
                    }
                }
                conversations.add(vo);
            }
            model.addAttribute("conversations", conversations);
        } catch (Exception e) {
            logger.error("Fail to get Alerts in PowerStore." + e.getMessage());

        }
        return "letter";
    }
}
