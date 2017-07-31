package com.pzs.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class PhotoRepositoryTest {
    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    public void findPhotoById() throws Exception {







    }

}