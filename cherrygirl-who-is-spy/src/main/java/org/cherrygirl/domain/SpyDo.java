package org.cherrygirl.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("cherrygirl_spy")
public class SpyDo {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField(value = "pos")
    private String pos;
    @TableField(value = "neg")
    private String neg;
    @TableField(value = "type")
    private String type;
    @TableField(value = "parent")
    private String parent;
    @TableField(value = "children")
    private String children;
    @TableField(value = "weight")
    private String weight;

    public SpyDo() {
    }

    public SpyDo(String pos, String neg) {
        this.pos = pos;
        this.neg = neg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getNeg() {
        return neg;
    }

    public void setNeg(String neg) {
        this.neg = neg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
