package com.dingyongfei.service;

import com.dingyongfei.dao.LoginTicketDAO;
import com.dingyongfei.dao.UserDAO;
import com.dingyongfei.model.LoginTicket;
import com.dingyongfei.model.User;
import com.dingyongfei.util.ESAMobileUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * @Author: Ding Yongfei
 */
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private LoginTicketDAO loginTicketDAO;

    public Map<String, Object> register(String username, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(username)) {
            map.put("msgname", "Username cannot be null.");
            return map;
        }

        if (password.length() > 18) {
            map.put("msgpwd", "Password length is too long.");
            return map;
        }

        if (username.contains("@") || username.contains("*")) {
            map.put("msgname", "Username is illegal.");
            return map;
        }

        if (StringUtils.isBlank(password)) {
            map.put("msgpwd", "Password cannot be null.");
            return map;
        }

        User user = userDAO.selectByName(username);

        if (user != null) {
            map.put("msgname", "Username has already been registered.");
            return map;
        }

        // password strength
        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        String head = String.format("", new Random().nextInt(1000));
        user.setHeadUrl(head);
        user.setPassword(ESAMobileUtil.MD5(password + user.getSalt()));
        user.setTimes(0);
        userDAO.addUser(user);

        // login
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        return map;
    }

    public Map<String, Object> login(String username, String password) {

        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(username)) {
            map.put("msgname", "Username cannot be null.");
            return map;
        }

        if (StringUtils.isBlank(password)) {
            map.put("msgpwd", "Password cannot be null.");
            if (!map.containsKey(""))
                return map;
        }

        User user = userDAO.selectByName(username);

        if (user == null) {
            map.put("msgname", "Username not exist.");
            return map;
        }

        if (!ESAMobileUtil.MD5(password + user.getSalt()).equals(user.getPassword())) {
            map.put("msgpwd", "Incorrect password.");
            userDAO.updateTimes(user.getId(), user.getTimes() + 1);
            return map;
        }

        if (!username.equals("admin"))
        {
            map.put("msgname", "Non-admin user has no access to ESA.");
            return map;
        }

        if (!password.equals("Vrops123!"))
        {
            map.put("msgpwd", "Incorrect password for admin.");
            return map;
        }

        map.put("userId", user.getId());

        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        return map;
    }

    private String addLoginTicket(int userId) {
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 1000 * 3600 * 24);
        ticket.setExpired(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        loginTicketDAO.addTicket(ticket);
        return ticket.getTicket();
    }

    public User getUser(int id) {
        return userDAO.selectById(id);
    }

    public User getUserByName(String name) {
        return userDAO.selectByName(name);
    }

    public void updateUserInfo(User user) {
        userDAO.updateUserInfo(user);
    }

    public void updateTimes(int id, int times) {
        userDAO.updateTimes(id, times);
    }

    public void logout(String ticket) {
        loginTicketDAO.updateStatus(ticket, 1);
    }
}
