package com.pzs.dao;

import com.pzs.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;


@org.springframework.stereotype.Repository
public interface UserRepository extends CrudRepository<User,Long> {
    List<User> findByNumAndPassword(String num, String password);

    @Override
    User save(User user);

    List<User> findByNum(String num);

    List<User> findById(Long id);

   /* @Modifying
    @Query("update User u set u.password = ?1,u.num = ?2 where u.id = ?3 ")
    int updatePasswordAndAndNumById(String pwd,String num,Long id);*/

    @Modifying
    @Query("update User u set u.name = ?1 where u.num = ?2")
    @Transactional
    int setFixedNameFor(String name,String num);

    @Modifying
    @Query("update User u set u.validataCode = ?1,u.outdate = ?2 where u.id = ?3")
    @Transactional
    int setFixedValidataCodeAndOutdateFor(String validatecode, Timestamp outdate,Long id);

    @Modifying
    @Query("update User u set u.password= ?1 where u.id = ?2")
    @Transactional
    int setFixPasswoedFor(String pwd,Long id);




}
