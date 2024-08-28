package org.cherrygirl.events;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.UserOrBot;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.NudgeEvent;
import net.mamoe.mirai.message.data.*;
import net.mamoe.mirai.utils.ExternalResource;
import org.apache.commons.lang3.StringUtils;
import org.cherrygirl.command.*;
import org.cherrygirl.command.handler.GroupCommandHandler;
import org.cherrygirl.config.ResourcesConfig;
import org.cherrygirl.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author nannaness
 */
@Component
public class GroupEvent extends SimpleListenerHost {


    @Autowired
    GameSpyEvent gameSpyEvent;

    @Autowired
    GroupCommandHandler groupCommandHandler;

    @Autowired
    MessageRecordsEvent messageRecordsEvent;

    @Autowired
    ChatWxCommand chatWxCommand;

    @Autowired
    ChatImgWxCommand chatImgWxCommand;

    @Autowired
    BilibiliWorksDownloadCommand bilibiliWorksDownloadCommand;
    @Autowired
    BilibiliVideoDownloadCommand bilibiliVideoDownloadCommand;

    @Autowired
    CallVideoCommand callVideoCommand;
    @Autowired
    ResourcesConfig resourcesConfig;


    private void handlerSpy(GroupMessageEvent event) throws IOException {
        gameSpyEvent.handlerSpy(event);
    }


    @EventHandler
    public ListeningStatus onGroupMessage(GroupMessageEvent event) {
        try {
//            if (event.getGroup().getId() != 723474785) {
//                return ListeningStatus.LISTENING;
//            }

            MessageChain messageChain = event.getMessage();
            String message = messageChain.contentToString();
//            SingleMessage singleMessage = messageChain.stream().findFirst().orElse(null);
//            Face face = (Face) messageChain.stream().filter(Face.class::isInstance).findFirst().orElse(null);
            System.out.println("message:" + message);


            messageRecordsEvent.handlerMessageRecord(event);
            groupCommandHandler.handler(message, event);
//            this.handlerSpy(event);
        }catch (IOException|RuntimeException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            //保持监听
            return ListeningStatus.LISTENING;
        }
    }

    @EventHandler
    public ListeningStatus onNudgeMessage(NudgeEvent event) throws IOException {
        Contact subject = event.getSubject();
        UserOrBot userOrBot = event.getTarget();
        if(subject instanceof Group){
            if(userOrBot.getId() == 1){
                callVideoCommand.run((Group) subject);
            }
        }

        //保持监听
        return ListeningStatus.LISTENING;
    }

    @EventHandler
    public ListeningStatus onPrivateMessage(FriendMessageEvent event) throws IOException {
        try {
            this.handler(event);
        }catch (IOException|RuntimeException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            //保持监听
            return ListeningStatus.LISTENING;
        }
    }


    private void handler(FriendMessageEvent event) throws IOException {
        long qq = event.getSender().getId();
        String message = event.getMessage().contentToString();
        Face face = new Face(394);
        event.getFriend().sendMessage(face);
        if(qq == 1){
            if(message.startsWith("入库")) {
                String mid = StringUtils.replace(message, "入库", "").trim();
                bilibiliWorksDownloadCommand.runFriend(event, qq, mid);
            } else if(message.startsWith("下载")) {
                String mid = StringUtils.replace(message, "下载", "").trim();
                bilibiliVideoDownloadCommand.runFriend(event, qq, mid);
            }else {
                chatWxCommand.runFriend(event, qq, message);
            }
        }
    }

}
