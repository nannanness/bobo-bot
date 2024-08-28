package org.cherrygirl.command;

import cn.hutool.core.util.RandomUtil;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.ShortVideo;
import net.mamoe.mirai.utils.ExternalResource;
import org.apache.commons.lang3.StringUtils;
import org.cherrygirl.utils.FileUtil;
import org.cherrygirl.utils.TalkUtil;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 聊天
 * @author nannanness
 */
@Component
public class TalkCommand implements Command{
    private long beginTime;

    private void playing(long beginTime){
        this.beginTime = beginTime;
    }
    @Override
    public void run(GroupMessageEvent event, int id, String... params) throws IOException {
        if(beginTime == 0){
            playing(System.currentTimeMillis() + 1000 * 15);
            doin(event, id, params);
        }else if(beginTime < System.currentTimeMillis()){
            playing(System.currentTimeMillis() + 1000 * 15);
            doin(event, id, params);
        }
    }

    private void doin(GroupMessageEvent event, int id, String... params) throws IOException {
        if(params != null && params.length > 0){
            String text = params[0];
            if(StringUtils.isNotEmpty(text)){
                String msg = TalkUtil.talk("qq" + event.getSender().getId(), text);
                event.getGroup().sendMessage(msg);
            }
        }
    }

}
