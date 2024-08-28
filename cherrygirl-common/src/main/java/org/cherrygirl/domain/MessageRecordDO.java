package org.cherrygirl.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;
@Data
@TableName("cherrygirl_message_record")
public class MessageRecordDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField(value = "ids")
    private String ids;
    @TableField(value = "fromId")
    private Long fromId;
    @TableField(value = "toId")
    private Long toId;
    @TableField(value = "group_id")
    private Long groupId;
    @TableField(value = "kind")
    private String kind;
    @TableField(value = "message")
    private String message;
    @TableField(value = "time_stamp")
    private Timestamp timestamp;
    @TableField(value = "text")
    private byte[] text;
}
