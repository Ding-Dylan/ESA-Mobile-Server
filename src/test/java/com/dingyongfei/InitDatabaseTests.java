package com.dingyongfei;

import com.dingyongfei.dao.LoginTicketDAO;
import com.dingyongfei.dao.UserDAO;
import com.dingyongfei.model.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.Date;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ESAMobileApplication.class)
@Sql("/init-schema.sql")
public class InitDatabaseTests {
    @Autowired
    UserDAO userDAO;

    @Autowired
    LoginTicketDAO loginTicketDAO;


    @Test
    public void initData() {
        Random random = new Random();
        for (int i = 0; i < 11; ++i) {
            User user = new User();
            user.setHeadUrl(String.format("", random.nextInt(1000)));
            user.setName(String.format("user%d", i));
            user.setPassword("");
            user.setSalt("");
            user.setTimes(0);
            userDAO.addUser(user);


            Date date = new Date();
            date.setTime(date.getTime() + 1000 * 3600 * 5 * i);
            user.setPassword("111111");
            userDAO.updatePassword(user);

            LoginTicket ticket = new LoginTicket();
            ticket.setStatus(0);
            ticket.setUserId(i + 1);
            ticket.setExpired(date);
            ticket.setTicket(String.format("TICKET%d", i + 1));
            loginTicketDAO.addTicket(ticket);

            loginTicketDAO.updateStatus(ticket.getTicket(), 2);

        }

        Assert.assertEquals("111111", userDAO.selectById(1).getPassword());
        userDAO.deleteById(1);
        Assert.assertNull(userDAO.selectById(1));

        Assert.assertEquals(1, loginTicketDAO.selectByTicket("TICKET1").getUserId());
        Assert.assertEquals(2, loginTicketDAO.selectByTicket("TICKET1").getStatus());

    }

}
