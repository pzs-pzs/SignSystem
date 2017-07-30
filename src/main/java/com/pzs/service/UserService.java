package com.pzs.service;

import com.pzs.entity.User;

public interface UserService {
    public User login(User user);
    public User register(User user);
    public int changePassword(User user);
    public int findPassword(String num , String basePath);
    public int isExitsNum(String num);

}
