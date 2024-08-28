package org.cherrygirl.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author nannanness
 */
@TableName("cherrygirl_combine")
public class CombineDO {
    @TableId(value = "id", type = IdType.AUTO)
    private long id;
    @TableField(value = "success")
    private int success;
    @TableField(value = "gtt")
    private String gtt;
    @TableField(value = "challenge")
    private String challenge;
    @TableField(value = "keykey")
    private String keykey;
    @TableField(value = "validate")
    private String validate;
    @TableField(value = "seccode")
    private String seccode;

    public CombineDO() {
    }

    public CombineDO(int success, String gtt, String challenge, String keykey) {
        this.success = success;
        this.gtt = gtt;
        this.challenge = challenge;
        this.keykey = keykey;
    }

    public String getValidate() {
        return validate;
    }

    public void setValidate(String validate) {
        this.validate = validate;
    }

    public String getSeccode() {
        return seccode;
    }

    public void setSeccode(String seccode) {
        this.seccode = seccode;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getGtt() {
        return gtt;
    }

    public void setGtt(String gtt) {
        this.gtt = gtt;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKeykey() {
        return keykey;
    }

    public void setKeykey(String keykey) {
        this.keykey = keykey;
    }

    @Override
    public String toString() {
        return "CombineDo{" +
                "success=" + success +
                ", gt='" + gtt + '\'' +
                ", challenge='" + challenge + '\'' +
                ", key='" + keykey + '\'' +
                ", validate='" + validate + '\'' +
                ", seccode='" + seccode + '\'' +
                '}';
    }
}
