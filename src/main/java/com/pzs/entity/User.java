package com.pzs.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "tb_user")
public class User implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false,name = "name")
    private String name;

    @Column(nullable = false,name = "email")
    private String email;

    @Column(nullable = false,name = "num")
    private String num;

    @Column(nullable = false,name = "tel")
    private String tel;

    @Column(nullable = false,name = "password")
    private String password;

    @Column(name = "outdate")
    private Timestamp outdate;

    @Column(name = "validatacode")
    private String validataCode;

    public void setValidataCode(String validataCode) {
        this.validataCode = validataCode;
    }

    public void setOutdate(Timestamp outdate) {
        this.outdate = outdate;
    }

    public String getValidataCode() {
        return validataCode;
    }

    public Timestamp getOutdate() {
        return outdate;
    }





    public User() {

    }

    public User(String name, String email, String num, String tel, String password, String validataCode, Timestamp outdate) {
        this.name = name;
        this.email = email;
        this.num = num;
        this.tel = tel;
        this.password = password;
        this.outdate = outdate;
        this.validataCode =validataCode;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getNum() {
        return num;
    }

    public String getTel() {
        return tel;
    }

    public String getPassword() {
        return password;
    }



    public void setId(Long id) {

        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return name+"-"+num+"-"+tel+"-"+email;
    }
}
