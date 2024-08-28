package org.cherrygirl.command;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.*;
import org.apache.commons.lang3.StringUtils;
import org.cherrygirl.command.handler.GroupCommandHandler;
import org.cherrygirl.manager.CoinManager;
import org.cherrygirl.manager.ImgManager;
import org.cherrygirl.manager.MessageRecordManager;
import org.cherrygirl.manager.TextManager;
import org.cherrygirl.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 上传小作文
 * @author nannanness
 */
@Component
public class UploadTextCommand  implements Command{

    @Autowired
    private TextManager textManager;

    @Autowired
    private CoinManager coinManager;

    @Autowired
    private MessageRecordManager messageRecordManager;

    private boolean flag;
    private boolean send;
    private long uploader;
    private long uploadTime;
    private long uploadGroup;

    public void upLoading(long groupId, long uploader, long uploadTime){
        flag = true;
        this.uploader = uploader;
        this.uploadTime = uploadTime;
        this.uploadGroup = groupId;
    }

    public void upLoadFinish(int id){
        send = false;
        flag = false;
        this.uploader = 0;
        this.uploadTime = 0;
        this.uploadGroup = 0;
        GroupCommandHandler.clearCache(id);
    }


    private void uploadTexting(GroupMessageEvent event,  int id){
        String text = event.getMessage().contentToString();
        if(StringUtils.isNotEmpty(text)){
            long groupId = event.getGroup().getId();
            long qq = event.getSender().getId();
            boolean result = textManager.uploadText(text);
            if(result){
                coinManager.add50Coins(groupId, qq);
                event.getGroup().sendMessage(new At(event.getSender().getId()).plus("感谢您的上传！波币+50"));
                upLoadFinish(id);
            }
        }
    }

    private void uploadTexting(GroupMessageEvent event, String text, int id, long fromId){
        if(StringUtils.isNotEmpty(text)){
            long groupId = event.getGroup().getId();
            long qq = event.getSender().getId();
            boolean result = textManager.uploadText(text);
            if(result){
                coinManager.add50Coins(groupId, qq);
                event.getGroup().sendMessage(new At(qq).plus("感谢您的上传！波币+50"));

                if(qq != fromId){
                    coinManager.add50Coins(groupId, fromId);
                    event.getGroup().sendMessage(new At(fromId).plus("感谢您的上传！波币+50"));

                }

            }
        }
        upLoadFinish(id);
    }

    @Override
    public void run(GroupMessageEvent event, int id, String... params) throws IOException {
        if(!flag){
            // 检测回复消息
            QuoteReply quoteReply = (QuoteReply) event.getMessage().stream().filter(QuoteReply.class::isInstance).findFirst().orElse(null);
            if (quoteReply != null) {
                String ids = Arrays.toString(quoteReply.getSource().getIds());
                long formId = quoteReply.getSource().getFromId();
                long groupId = event.getGroup().getId();
                int timestamp = quoteReply.getSource().getTime();
                String queryText = messageRecordManager.queryText(ids, groupId, formId);
                uploadTexting(event, queryText, id, formId);
            } else {
                upLoading(event.getGroup().getId(), event.getSender().getId(), System.currentTimeMillis() + 1000 * 18);
                if(!send){
                    event.getGroup().sendMessage("请发送您要上传的小作文！");
                    send = true;
                }
            }
        } else if(uploadTime < System.currentTimeMillis()){
            upLoadFinish(id);
        } else if(flag && uploadGroup == event.getGroup().getId() && uploader == event.getSender().getId()) {
            uploadTexting(event, id);
        }
    }

}
