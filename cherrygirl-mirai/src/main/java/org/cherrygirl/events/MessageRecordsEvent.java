package org.cherrygirl.events;

import com.alibaba.fastjson.JSON;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.*;
import org.cherrygirl.command.handler.GroupCommandHandler;
import org.cherrygirl.domain.MessageRecordDO;
import org.cherrygirl.manager.MessageRecordManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MessageRecordsEvent {

    @Autowired
    MessageRecordManager messageRecordManager;

    public void handlerMessageRecord(GroupMessageEvent event) throws IOException {
        MessageChain messageChain = event.getMessage();
        String message = event.getMessage().contentToString();

        OnlineMessageSource.Incoming.FromGroup source = event.getSource();
        String ids = Arrays.toString(source.getIds());
        long formId = source.getFromId();
        long groupId = source.getTargetId();
        int timestamp = source.getTime();
        List<SingleMessage> singleMessages = messageChain.stream().toList();
        List<String> urlList = messageChain.stream().filter(Image.class::isInstance).map(chain -> Image.queryUrl((Image) chain)).toList();
        if(!urlList.isEmpty()){
            String msg = JSON.toJSONString(urlList);
            MessageRecordDO messageRecordDO = new MessageRecordDO();
            messageRecordDO.setMessage(msg);
            messageRecordDO.setKind("img");
            messageRecordDO.setIds(ids);
            messageRecordDO.setTimestamp(new Timestamp(Long.parseLong(timestamp + "000000")));
            messageRecordDO.setFromId(formId);
            messageRecordDO.setGroupId(groupId);
            messageRecordManager.save(messageRecordDO);
        }

        if(singleMessages.size() == 2){
            Optional<SingleMessage> first = singleMessages.stream().filter(PlainText.class::isInstance).findFirst();
            if(first.isPresent()){
                String encodeStr = new String(Base64.getEncoder().encode(message.getBytes()));
                byte[] decode = Base64.getDecoder().decode(encodeStr);
                MessageRecordDO messageRecordDO = new MessageRecordDO();
                messageRecordDO.setKind("text");
                messageRecordDO.setIds(ids);
                messageRecordDO.setTimestamp(new Timestamp(Long.parseLong(timestamp + "000000")));
                messageRecordDO.setFromId(formId);
                messageRecordDO.setGroupId(groupId);
                messageRecordDO.setText(decode);
                messageRecordManager.save(messageRecordDO);
            }
        }
    }
}
