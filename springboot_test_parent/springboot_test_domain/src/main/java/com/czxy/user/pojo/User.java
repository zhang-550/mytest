package com.czxy.user.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name ="t_user")
public class User {
    @Id
    private Integer uid;
    private String username;
    private String password;
    private Integer rid;

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", rid=" + rid +
                '}';
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public User() {
    }

    public User(Integer uid, String username, String password, Integer rid) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.rid = rid;
    }
}
