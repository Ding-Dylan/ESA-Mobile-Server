package com.dingyongfei.async.handler;

import com.dingyongfei.async.EventHandler;
import com.dingyongfei.async.EventModel;
import com.dingyongfei.async.EventType;
import com.dingyongfei.service.MessageService;
import com.dingyongfei.util.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;

/**
 * @Author: Ding Yongfei
 */
@Component
public class LoginExceptionHandler implements EventHandler {

    @Autowired
    MessageService messageService;

    @Autowired
    MailSender mailSender;

    @Override
    public void doHandle(EventModel model) {

        //Mail
        Map<String, Object> map = new HashMap<>();
        map.put("username", model.getExt("username"));
        mailSender.sendWithHTMLTemplate(model.getExt("email"), "Alerts from PowerStore.",
                "mails/alertsMail.html", map);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LOGIN);
    }
}
