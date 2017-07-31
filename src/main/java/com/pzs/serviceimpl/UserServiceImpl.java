package com.pzs.serviceimpl;

import com.pzs.dao.UserRepository;
import com.pzs.entity.User;
import com.pzs.service.MailService;
import com.pzs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Encoder;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService ,Runnable{
    private String num;
    private String basePath;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    MailService mailService;



    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     *
     * @param user
     * @return 1 登陆成功 0 登陆失败
     */
    @Override
    public User login(User user) {
        List<User> list = userRepository.findByNumAndPassword(user.getNum(),user.getPassword());
        if(userRepository.findByNumAndPassword(user.getNum(),user.getPassword()).size()==0){
            return null;
        }
        return list.get(0);
    }

    /**
     *
     * @param user
     * @return 1注册成功 0注册失败
     */
    @Override
    public User register(User user) {
        if(isExitsNum(user.getNum())==1){
            return null;
        }
        User returnUser = userRepository.save(user);
        if(returnUser!=null) {
            return returnUser;
        }
        return null;
    }

    @Override
    public int changePassword(User user) {
        int i =userRepository.setFixPasswoedFor(user.getPassword(),user.getId());
        userRepository.setFixedValidataCodeAndOutdateFor(null,new Timestamp(System.currentTimeMillis()),user.getId());
        return i;
    }

    @Override
    public int findPassword(String num , String basePath) {
        List<User> list = userRepository.findByNum(num);
        if(list == null){
            return -1;
        }
        User user = list.get(0);
        String mailContext = createLink(user,basePath);
        try {
            mailService.sendEmail(user.getEmail(),mailContext);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int isExitsNum(String num) {
        List<User> list = userRepository.findByNum(num);
        if(list.size()==0){
            return 0;
        }
        return 1;
    }


    public String createLink(User user,String basePath) {
        //生成秘钥
        String secretKey= UUID.randomUUID().toString();
        //设置过期时间
        Date outDate = new Date(System.currentTimeMillis() + 30 * 60 * 1000);// 30分钟后过期

//        long date = outDate.getTime() / 1000 * 1000;// 忽略毫秒数  mySql 取出时间是忽略毫秒数的

        userRepository.setFixedValidataCodeAndOutdateFor(secretKey,new Timestamp(outDate.getTime()),user.getId());

        //将用户名、密钥生成链接密钥
        String key =user.getNum() + "$" + "$" + secretKey;

        String digitalSignature = getMD5(key);
        String temp = null;
        try {
            temp = URLEncoder.encode(digitalSignature,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        System.out.println("?--------"+temp+"------");

        if(digitalSignature == null) {
            return null;
        }


        String resetPassHref = basePath + "/check?sid="+ temp +"&id="+user.getId();
        String emailContent = "请勿回复本邮件.点击下面的链接,重设密码,本邮件超过30分钟," +
                "链接将会失效，需要重新申请找回密码." +"<a href=\""+resetPassHref+"\">"+
                resetPassHref+"</a>";
        return emailContent;
    }

    public String getMD5(String message) {

        MessageDigest messageDigest = null;
        String newstr = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            newstr = base64en.encode(messageDigest.digest(message.getBytes("utf-8")));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        return newstr;
    }

    public int check(String sid,Long id) {

        List<User> list = userRepository.findById(id);
        User user = list.get(0);

        //判断请求是否过期
        /*if(user.getOutdate().getTime()<System.currentTimeMillis()){
            return 0;
        }*/
        String key = user.getNum()+"$"+"$"+user.getValidataCode();//数字签名
        System.out.println(user.getValidataCode());
        String digitalSignature = getMD5(key);

        String temp = null;
        try {
            temp = URLDecoder.decode(sid,"utf-8");
            System.out.println(temp);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(!temp.equals(digitalSignature)){
            return 0;
        }
        return 1;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }


    @Override
    public void run() {
        findPassword(num,basePath);
    }
}
