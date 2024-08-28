package org.cherrygirl.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.sql.Timestamp;

/**
 * @author nannanness
 */
@TableName("cherrygirl_cookies")
public class CookiesDO {
    @TableId(value = "id", type = IdType.AUTO)
    private long id;
    @TableField(value = "isv")
    private String isv;
    @TableField(value = "cookies")
    private String cookies;
    @TableField(value = "login_time")
    private Timestamp loginTime;

    public CookiesDO() {
    }

    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIsv() {
        return isv;
    }

    public void setIsv(String isv) {
        this.isv = isv;
    }

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }
}
