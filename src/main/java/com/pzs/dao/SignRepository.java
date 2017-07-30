package com.pzs.dao;

import com.pzs.entity.Sign;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SignRepository extends CrudRepository<Sign,Long> {
    List<Sign> findByNumAndName(String num,String name);

    @Override
    Sign save(Sign s);
}
