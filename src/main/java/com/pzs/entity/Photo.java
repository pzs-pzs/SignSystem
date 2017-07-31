package com.pzs.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;

@Entity
@Table(name = "tb_photo")
public class Photo implements Serializable {

    @Id
    @GeneratedValue
    private Long id ;

    @Column(name = "photo")
    private Blob blob;

    public void setText(String text) {
        this.text = text;
    }

    @Column(name ="text")
    private String text;

    public String getText() {
        return text;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBlob(Blob blob) {
        this.blob = blob;
    }

    public Long getId() {
        return id;
    }

    public Blob getBlob() {
        return blob;
    }


}
