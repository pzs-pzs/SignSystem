package com.pzs.dao;

import com.pzs.entity.TbUserEntity;
import org.junit.Test;

public class UserDaoTest {
    @Test
    public void save() throws Exception {
        TbUserEntity tbUserEntity = new TbUserEntity();
        tbUserEntity.setTel("165009296247");
        tbUserEntity.setPassword("123");
        tbUserEntity.setName("pzs");
        tbUserEntity.setNum("35");
        tbUserEntity.setEmail("1391663722@qq.com");

    }

}