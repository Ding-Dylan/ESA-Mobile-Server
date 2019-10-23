package com.dingyongfei;

import com.dingyongfei.controller.Firebase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ESAMobileApplication.class)
public class FirebaseTest {

    @Test
    public void testRead() throws IOException {
        Firebase firebase = new Firebase("esa-mobile-3c387");

    }
}
