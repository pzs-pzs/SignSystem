package com.pzs.serviceimpl;

import com.pzs.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 * Created by puzhengsong on 2017/7/27.
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class MailServiceImplTest {
    @Autowired
    MailServiceImpl mailService;

    @Test
    public void findPassword() {

    }
    @Test
    public void sendEmail() throws Exception {


    }

    @Test
    public void reaFile() {
        File f = mailService.readFile("classpath:email.txt");
        if(f!=null) {
            System.out.println(f.exists());
        }
    }


}