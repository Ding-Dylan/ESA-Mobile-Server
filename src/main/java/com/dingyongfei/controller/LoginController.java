package com.dingyongfei.controller;

import com.dingyongfei.async.EventModel;
import com.dingyongfei.async.EventProducer;
import com.dingyongfei.async.EventType;
import com.dingyongfei.service.UserService;
import com.dingyongfei.util.ESAMobileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author: Ding Yongfei
 */
@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    @Autowired
    EventProducer eventProducer;

    @RequestMapping(path = {"/reg/"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String reg(Model model, @RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam(value = "remember", defaultValue = "0") int rememberme,
                      HttpServletResponse response) {
        try {
            Map<String, Object> map = userService.register(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                if (rememberme > 0) {
                    cookie.setMaxAge(3600 * 24 * 5);
                }
                response.addCookie(cookie);
                return ESAMobileUtil.getJSONString(0, "Successful registration.");
            } else {
                return ESAMobileUtil.getJSONString(1, map);
            }

        } catch (Exception e) {
            logger.error("Abnormal registration." + e.getMessage());
            return ESAMobileUtil.getJSONString(1, "Abnormal registration.");
        }
    }

    @RequestMapping(path = {"/login/"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String login(Model model, @RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam(value = "remember", defaultValue = "0") int rememberme,
                        HttpServletResponse response) {
        try {
            Map<String, Object> map = userService.login(username, password);
            if (userService.getUserByName(username).getTimes() > 2) {
                eventProducer.fireEvent(new
                        EventModel(EventType.LOGIN).setActorId(userService.getUserByName(username).getId())
                        .setExt("username", "Dylan").setExt("email", "***@qq.com"));
                userService.updateTimes(userService.getUserByName(username).getId(), 0);
            }
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                if (rememberme > 0) {
                    cookie.setMaxAge(3600 * 24 * 5);
                }
                response.addCookie(cookie);

                return ESAMobileUtil.getJSONString(0, "Successful registration.");
            } else {
                return ESAMobileUtil.getJSONString(1, map);
            }

        } catch (Exception e) {
            logger.error("Abnormal registration." + e.getMessage());
            return ESAMobileUtil.getJSONString(1, "Abnormal registration.");
        }
    }

    @RequestMapping(path = {"/logout/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        return "redirect:/index";
    }

}
