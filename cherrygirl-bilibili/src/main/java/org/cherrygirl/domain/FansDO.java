package org.cherrygirl.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * @author nannanness
 */
@TableName("cherrygirl_fans")
public class FansDO {
    @TableId(value = "id", type = IdType.AUTO)
    private long id;
    @TableField(value = "uid")
    private long uid;
    @TableField(value = "fans")
    private long fans;
    @TableField(value = "nums")
    private long nums;
    @TableField(value = "stat_time")
    private String statTime;

    public FansDO() {
    }

    public FansDO(long uid, long fans, long nums) {
        this.uid = uid;
        this.fans = fans;
        this.nums = nums;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getFans() {
        return fans;
    }

    public void setFans(long fans) {
        this.fans = fans;
    }

    public long getNums() {
        return nums;
    }

    public void setNums(long nums) {
        this.nums = nums;
    }

    public String getStatTime() {
        return statTime;
    }

    public void setStatTime(String statTime) {
        this.statTime = statTime;
    }
}
