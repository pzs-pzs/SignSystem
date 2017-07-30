package com.pzs.serviceimpl;

import com.pzs.entity.Sign;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Date;


@SpringBootTest
@RunWith(SpringRunner.class)
public class SignServiceImplTest {

    @Autowired
    SignServiceImpl signServiceImpl;

    @Test
    public void signCount() throws Exception {
    }

    @Test
    public void save() throws Exception {
        Sign sign = new Sign();
        sign.setName("pzs");
        sign.setNum("35");
        sign.setTime(new Timestamp(new Date().getTime()));
        if(!signServiceImpl.checkIsSignedToday(sign)) {
            Sign s = signServiceImpl.save(sign);
            System.out.println(s);

        }else{
            System.out.println("今日已签到");
        }

    }

}