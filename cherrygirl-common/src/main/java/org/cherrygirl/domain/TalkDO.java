package org.cherrygirl.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.sql.Date;

/**
 * @author nannanness
 */
@TableName("cherrygirl_talk")
public class TalkDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField(value = "load_date")
    private Date loadDate;
    @TableField(value = "group_id")
    private Long groupId;
    @TableField(value = "qq")
    private Long qq;
    @TableField(value = "talk_count")
    private Long talkCount;
    @TableField(value = "sign_in_today")
    private int signInToday;

    public TalkDO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getLoadDate() {
        return loadDate;
    }

    public void setLoadDate(Date loadDate) {
        this.loadDate = loadDate;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getQq() {
        return qq;
    }

    public void setQq(Long qq) {
        this.qq = qq;
    }

    public Long getTalkCount() {
        return talkCount;
    }

    public void setTalkCount(Long talkCount) {
        this.talkCount = talkCount;
    }

    public int getSignInToday() {
        return signInToday;
    }

    public void setSignInToday(int signInToday) {
        this.signInToday = signInToday;
    }
}
