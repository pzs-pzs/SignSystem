package com.pzs.serviceimpl;

import com.pzs.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceImplTest {
    @Autowired
    UserServiceImpl userService;
    @Test
    public void login() throws Exception {


    }

}