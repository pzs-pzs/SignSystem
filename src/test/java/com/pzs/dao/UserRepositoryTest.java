package com.pzs.dao;

import com.pzs.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by puzhengsong on 2017/7/28.
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    public void findByNumAndPassword() throws Exception {
    }

    @Test
    public void save() throws Exception {
        User user = new User();
        user.setName("test");
        user.setEmail("123");
        user.setNum("33");
        user.setTel("123");
        user.setPassword("123");
        user.setOutdate(new Timestamp(System.currentTimeMillis()));
        user.setValidataCode("sdd");
        User user1 = userRepository.save(user);
        System.out.println(user1);
    }

    @Test
    public void findByNum() throws Exception {
    }

    @Test
    public void setFixedValidataCodeAndOutdateFor() {
        List<User> list = userRepository.findByNum("35");
        User user = list.get(0);
        userRepository.setFixPasswoedFor("qwe",62L);
        List<User> list1 = userRepository.findByNum("35");
        System.out.println(list1.get(0).getPassword());
    }
    @Test
    public void setFixedNameFor() {
        int i = userRepository.setFixedNameFor("pzs","35");
    }
}