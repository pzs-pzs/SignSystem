package com.pzs.service;

import com.pzs.entity.User;

import javax.mail.MessagingException;
import java.io.File;

/**
 * Created by puzhengsong on 2017/7/27.
 */
public interface MailService {
     public void sendEmail(String to,String emailContext) throws MessagingException;
     public File readFile(String path);


}
