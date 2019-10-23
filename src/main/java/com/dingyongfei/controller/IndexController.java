package com.dingyongfei.controller;

import com.dingyongfei.model.User;
import com.dingyongfei.service.ESAMobileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @Author: Ding Yongfei
 */
//@Controller
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private ESAMobileService esaMobileService;

    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String index(HttpSession session) {
        logger.info("Visit Index");
        return "Hello ESA," + session.getAttribute("msg")
                + "<br> Say:" + esaMobileService.say();
    }

    @RequestMapping("/redirect/{code}")
    public String redirect(@PathVariable("code") int code,
                           HttpSession session) {
        /*
        RedirectView red = new RedirectView("/", true);
        if (code == 301) {
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return red;*/
        session.setAttribute("msg", "Jump from redirect.");
        return "redirect:/";
    }

    @RequestMapping("/admin")
    @ResponseBody
    public String admin(@RequestParam(value = "key", required = false) String key) {
        if ("admin".equals(key)) {
            return "hello admin";
        }
        throw new IllegalArgumentException("Key error");
    }

    @ExceptionHandler()
    @ResponseBody
    public String error(Exception e) {
        return "error:" + e.getMessage();
    }
}
