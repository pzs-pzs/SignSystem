package com.pzs.serviceimpl;

import com.pzs.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileNotFoundException;


/**
 * Created by puzhengsong on 2017/7/27.
 */
@Service
@Transactional
@PropertySource("classpath:test.properties")
public class MailServiceImpl implements MailService{

    @Autowired
    JavaMailSenderImpl sender;

    @Value("${from}")
    String from ;

    @Value("${email}")
    String s;

    @Override
    public void sendEmail(String to,String emailContext) throws MessagingException {

        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,false,"utf-8");
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject("找回密码");
        mimeMessageHelper.setText(emailContext,true);
        sender.send(mimeMessage);


    }

    @Override
    public File readFile(String path) {
        File file = null;
        try {
             file = ResourceUtils.getFile(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return file;
    }



}
