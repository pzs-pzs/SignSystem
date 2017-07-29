package com.pzs.controller;

import com.pzs.entity.Sign;
import com.pzs.entity.User;
import com.pzs.service.MailService;
import com.pzs.serviceimpl.SignServiceImpl;
import com.pzs.serviceimpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.Date;

@Controller
public class DemoController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private SignServiceImpl signService;

    private final String SUCCESS = "{\"success\":\"1\"}";
    private final String FAILURE = "{\"success\":\"0\"}";


    @RequestMapping("/registerHandler")
    public String register(@ModelAttribute User user, Model model,HttpServletResponse response) {
        User returnUser = userService.register(user);
        if(returnUser!=null){

            model.addAttribute("name",returnUser.getName());
            model.addAttribute("num",returnUser.getNum());
            model.addAttribute("email",returnUser.getEmail());
            model.addAttribute("tel",returnUser.getTel());
            addCookie(response,returnUser);
            return "user";
        }
        return "index";
    }

    @RequestMapping("/loginHandler")
    public String login(@ModelAttribute User user, Model model,HttpServletRequest request,HttpServletResponse response) {
        System.out.println(user.getNum());
        System.out.println(user.getPassword());
        System.out.println(request);
        User returnUser = userService.login(user);
        if(returnUser!=null) {
            showAndaddCookies(request,response,returnUser);
            model.addAttribute("name",returnUser.getName());
            model.addAttribute("num",returnUser.getNum());
            model.addAttribute("tel",returnUser.getTel());
            model.addAttribute("email",returnUser.getEmail());
            return "index";
        }
        return "login";
    }

    /**
     * 签到
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/signCount")
    @ResponseBody
    public String singCountHandler(HttpServletRequest request,HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();//这样便可以获取一个cookie数组
        boolean flag = false;
        if (null==cookies) {
            System.out.println("=====没有cookie=========");
        } else {
            for(Cookie cookie : cookies){
                if(cookie.getName().equalsIgnoreCase("user")){
                     String user_inf = cookie.getValue();
                     flag = signCount(user_inf);
                     break;
                }
            }
        }
        if(flag) {
            return SUCCESS;
        }
        return FAILURE;
    }
    /*
     name+"-"+num+"-"+tel+"-"+email
     */
    public boolean signCount(String user_inf) {
        String[] a = user_inf.split("-");
        for(String s : a) {
            System.out.println(s);
        }
        Sign sign = new Sign();
        sign.setName(a[0]!=null?a[0]:"pzs");
        sign.setNum(a[1]!=null?a[1]:"35");
        sign.setTime(new Timestamp(new Date().getTime()));
        /*
           判断今日是否签到，已签到返回false
         */
        if(signService.checkIsSignedToday(sign)){
            return false;
        }
        Sign returnSign = signService.save(sign);
        if(returnSign == null) {
            return false;
        }
        return true;
    }


    /**
     * name+"-"+num+"-"+tel+"-"+email
     * 显示签到信息
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/signHandler")
    public String sign(Model model,HttpServletRequest request,HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();//这样便可以获取一个cookie数组
        String[] user_inf = null ;
        Integer num = 0;
        if (null==cookies) {
            System.out.println("没有cookie=========");
        } else {
            for(Cookie cookie : cookies){
                if(cookie.getName().equalsIgnoreCase("user")) {
                    user_inf = cookie.getValue().split("-");

                    num = getSignNum(user_inf);
                    break;
                }
            }
        }
        model.addAttribute("name",user_inf[0]);
        model.addAttribute("num",user_inf[1]);
        model.addAttribute("shoudBeNumOfDays",num);
        model.addAttribute("numOfDays",num);

        return "sign_inf";
    }
    public int getSignNum(String[] user_inf) {
        Sign sign = new Sign();
        sign.setName(user_inf[0]!=null?user_inf[0]:"pzs");
        sign.setNum(user_inf[1]!=null?user_inf[1]:"35");
        int num = signService.signCount(sign);

        return num;
    }
    public void showAndaddCookies(HttpServletRequest request,HttpServletResponse response ,User user){

        Cookie[] cookies = request.getCookies();//这样便可以获取一个cookie数组
        if (null==cookies) {
            System.out.println("没有cookie=========");
            addCookie(response,user);
        } else {
            for(Cookie cookie : cookies){
                System.out.println("name:"+cookie.getName()+",value:"+ cookie.getValue());
                addCookie(response,user);
            }
        }

    }

    public void addCookie(HttpServletResponse response,User user){
        Cookie cookie = new Cookie("user",user.toString());//创建新cookie
        cookie.setMaxAge(-1);// 设置存在时间为5分钟
        cookie.setPath("/");//设置作用域

        response.addCookie(cookie);//将cookie添加到response的cookie数组中返回给客户端
    }

    public void delCookie(HttpServletRequest request,HttpServletResponse response,String name){
        Cookie[] cookies = request.getCookies();
        if (null==cookies) {
            System.out.println("没有cookie==============");
        } else {
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(name)){
                    cookie.setValue(null);
                    cookie.setMaxAge(0);// 立即销毁cookie
                    cookie.setPath("/");
                    System.out.println("被删除的cookie名字为:"+cookie.getName());
                    response.addCookie(cookie);
                    break;
                }
            }
        }
    }
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        delCookie(request,response,"user");
        return "index";
    }

    @RequestMapping("/toIndex")
    public String toIndex() {
        return "index";
    }

    /**
     * name+"-"+num+"-"+tel+"-"+email
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/toUser")
    public String toUser(HttpServletRequest request,HttpServletResponse response,Model model) {
        Cookie[] cookies = request.getCookies();//这样便可以获取一个cookie数组
        String[] user_inf = null ;
        if (null==cookies) {
            System.out.println("没有cookie=========");
        } else {
            for(Cookie cookie : cookies){
                if(cookie.getName().equalsIgnoreCase("user")) {
                    user_inf = cookie.getValue().split("-");
                    break;
                }
            }
        }
        if(user_inf!=null) {
            model.addAttribute("name", user_inf[0]);
            model.addAttribute("num", user_inf[1]);
            model.addAttribute("tel", user_inf[2]);
            model.addAttribute("email", user_inf[3]);
        }
        return "user";
    }
    @RequestMapping("/demo")
    public String demo(Model model) {
        model.addAttribute("name","pzs");
        return "demo";
    }
    @Autowired
    MailService mailService;

    @PostMapping("/findPassword")
    public String findPassword (@RequestParam("num") String num,HttpServletRequest request,HttpServletResponse response) {
        String path=request.getContextPath();
        String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
        userService.setNum(num);
        userService.setBasePath(basePath);
        Thread thread = new Thread(userService);
        thread.start();
        return "index";
    }

    @RequestMapping("/check")
    public String check(@RequestParam("sid") String sid,@RequestParam("id") String id,Model model) {
        if(StringUtils.isEmpty(sid)||StringUtils.isEmpty(id)) {
            return "demo";
        }
        Long lid = Long.parseLong(id);
        if(userService.check(sid,lid)==0){
            return "demo";
        }
        model.addAttribute("id",id);
        return "changepwd";
    }
    @PostMapping("/changePwd")
    public String changePwd(@ModelAttribute User user) {
        userService.changePassword(user);
        return "login";
    }

}
