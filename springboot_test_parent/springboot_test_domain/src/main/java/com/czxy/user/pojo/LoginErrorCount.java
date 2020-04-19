package com.czxy.user.pojo;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "loginerrorount")
public class LoginErrorCount {
    @Id
    private Integer eid;
    private String username;
    private Integer counts;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date times;

    @Override
    public String toString() {
        return "LoginErrorCount{" +
                "eid=" + eid +
                ", username='" + username + '\'' +
                ", counts=" + counts +
                ", times=" + times +
                '}';
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getCounts() {
        return counts;
    }

    public void setCounts(Integer counts) {
        this.counts = counts;
    }

    public Date getTimes() {
        return times;
    }

    public void setTimes(Date times) {
        this.times = times;
    }

    public LoginErrorCount() {
    }

    public LoginErrorCount(Integer eid, String username, Integer counts, Date times) {
        this.eid = eid;
        this.username = username;
        this.counts = counts;
        this.times = times;
    }
}
