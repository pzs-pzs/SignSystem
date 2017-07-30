package com.pzs.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "tb_sign")
public class Sign implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "num",nullable = false)
    private String num;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "time",nullable = false)
    private Timestamp time;

    public void setId(Long id) {
        this.id = id;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getNum() {
        return num;
    }

    public String getName() {
        return name;
    }

    public Timestamp getTime() {
        return time;
    }

    public Sign() {

    }
}
