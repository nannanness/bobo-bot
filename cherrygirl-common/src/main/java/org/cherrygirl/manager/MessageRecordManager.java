package org.cherrygirl.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.cherrygirl.domain.CoinDO;
import org.cherrygirl.domain.MessageRecordDO;
import org.cherrygirl.service.CoinService;
import org.cherrygirl.service.MessageRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
public class MessageRecordManager {

    @Autowired
    private MessageRecordService messageRecordService;

    public boolean save(MessageRecordDO messageRecordDO) {
        if(messageRecordDO.getFromId() != null && StringUtils.isNotEmpty(messageRecordDO.getIds())){
            return messageRecordService.save(messageRecordDO);
        }
        return false;
    }


    public List<String> queryImg(String ids, long groupId, long fromId) {
        QueryWrapper<MessageRecordDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MessageRecordDO::getFromId, fromId);
        queryWrapper.lambda().eq(MessageRecordDO::getIds, ids);
        queryWrapper.lambda().eq(MessageRecordDO::getGroupId, groupId);
        queryWrapper.lambda().eq(MessageRecordDO::getKind, "img");
        String sqlSelect = queryWrapper.getSqlSelect();
        System.out.println(sqlSelect);
        MessageRecordDO messageRecordDO = messageRecordService.getBaseMapper().selectOne(queryWrapper);
        if (messageRecordDO != null){
            String message = messageRecordDO.getMessage();
            List<String> urlList = JSONArray.parseArray(message, String.class);
            return urlList;
        }
        return null;
    }
    public List<String> queryImg2(String ids, long fromId) {
        QueryWrapper<MessageRecordDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MessageRecordDO::getFromId, fromId);
        queryWrapper.lambda().eq(MessageRecordDO::getIds, ids);
        queryWrapper.lambda().eq(MessageRecordDO::getKind, "img");
        String sqlSelect = queryWrapper.getSqlSelect();
        System.out.println(sqlSelect);
        MessageRecordDO messageRecordDO = messageRecordService.getBaseMapper().selectOne(queryWrapper);
        if (messageRecordDO != null){
            String message = messageRecordDO.getMessage();
            List<String> urlList = JSONArray.parseArray(message, String.class);
            return urlList;
        }
        return null;
    }

    public String queryText(String ids, long groupId, long fromId) {
        QueryWrapper<MessageRecordDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MessageRecordDO::getFromId, fromId);
        queryWrapper.lambda().eq(MessageRecordDO::getIds, ids);
        queryWrapper.lambda().eq(MessageRecordDO::getGroupId, groupId);
        queryWrapper.lambda().eq(MessageRecordDO::getKind, "text");
        String sqlSelect = queryWrapper.getSqlSelect();
        System.out.println(sqlSelect);
        MessageRecordDO messageRecordDO = messageRecordService.getBaseMapper().selectOne(queryWrapper);
        if (messageRecordDO != null){
            byte[] text = messageRecordDO.getText();
            return new String(text);
        }
        return null;
    }
}
