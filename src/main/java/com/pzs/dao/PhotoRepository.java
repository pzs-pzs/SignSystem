package com.pzs.dao;

import com.pzs.entity.Photo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface PhotoRepository extends CrudRepository<Photo,Long> {
    public List<Photo> findPhotoById(Long id);

    
}
