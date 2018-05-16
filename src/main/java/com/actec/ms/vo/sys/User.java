package com.actec.ms.vo.sys;

import com.actec.ms.vo.BaseEntity;

import javax.persistence.Table;

@Table(name = "user")
public class User extends BaseEntity {

    private String password;
    private String sex;
    private String phone;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static boolean isAdmin(int userId) {
        return userId == 1;
    }

}
