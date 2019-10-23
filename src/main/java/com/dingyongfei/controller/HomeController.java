package com.dingyongfei.controller;

import com.dingyongfei.model.HostHolder;
import com.dingyongfei.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Ding Yongfei
 */
@Controller
public class HomeController {

    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;


    @RequestMapping(path = {"/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model, @RequestParam(value = "pop", defaultValue = "0") int pop) {

        model.addAttribute("vos", null);
        if (hostHolder.getUser() != null) {
            pop = 0;
        }
        model.addAttribute("pop", pop);
        return "home";
    }

    @RequestMapping(path = {"/user/{userId}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String userIndex(Model model, @PathVariable("userId") int userId) {
        model.addAttribute("vos", null);
        model.addAttribute("extra", null);
        return "home";
    }

}