package org.cherrygirl.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author nannanness
 */
@TableName("cherrygirl_coin")
public class CoinDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField(value = "qq")
    private Long qq;
    @TableField(value = "group_id")
    private Long groupId;
    @TableField(value = "coin")
    private Long coin;

    public CoinDO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQq() {
        return qq;
    }

    public void setQq(Long qq) {
        this.qq = qq;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getCoin() {
        return coin;
    }

    public void setCoin(Long coin) {
        this.coin = coin;
    }
}
