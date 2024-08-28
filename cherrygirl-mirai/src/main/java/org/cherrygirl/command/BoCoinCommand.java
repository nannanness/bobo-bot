package org.cherrygirl.command;

import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.contact.MemberPermission;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import org.cherrygirl.command.handler.GroupCommandHandler;
import org.cherrygirl.config.TalkScheduleTask;
import org.cherrygirl.domain.TalkDO;
import org.cherrygirl.manager.CoinManager;
import org.cherrygirl.manager.ImgManager;
import org.cherrygirl.manager.TalkManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;

/**
 * 聊天记录
 * @author nannanness
 */
@Slf4j
@Component
public class BoCoinCommand implements Command {

    @Autowired
    private CoinManager coinManager;

    @Autowired
    private TalkManager talkManager;

    @Override
    public void run(GroupMessageEvent event, int id, String... params) throws IOException {

        long groupId = event.getGroup().getId();
        long qq = event.getSender().getId();
        String s = groupId + ":" + qq;
        try {
            talkManager.addOneTalk(groupId, qq);
            TalkDO talkDO = talkManager.viewTodayTalks(groupId, qq);
            Long talkCount = talkDO.getTalkCount();
            if(talkCount == 9){
//                coinManager.add50Coins(groupId, qq);
//                event.getGroup().sendMessage(new At(event.getSender().getId()).plus("聊天达到10条，波币+50"));
            } else if(talkCount == 49){
//                coinManager.add50Coins(groupId, qq);
//                event.getGroup().sendMessage(new At(event.getSender().getId()).plus("聊天达到50条，波币+100"));
            } else if(talkCount == 99){
                coinManager.add50Coins(groupId, qq);
                event.getGroup().sendMessage(new At(event.getSender().getId()).plus("聊天达到100条，波币+50"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
