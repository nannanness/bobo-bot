package org.cherrygirl.command;

import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChain;
import org.cherrygirl.domain.CoinDO;
import org.cherrygirl.domain.TalkDO;
import org.cherrygirl.manager.CoinManager;
import org.cherrygirl.manager.TalkManager;
import org.cherrygirl.utils.CalenderUtil;
import org.cherrygirl.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * 查看聊天排行
 * @author nannanness
 */
@Slf4j
@Component
public class ViewTalkTopCoinCommand implements Command {

    @Autowired
    private TalkManager talkManager;

    @Override
    public void run(GroupMessageEvent event, int id, String... params) throws IOException {

        long groupId = event.getGroup().getId();
        try {
            List<TalkDO> talkDOS = talkManager.viewGroupTopTalks(groupId);
            if(talkDOS.isEmpty()){
                event.getGroup().sendMessage(new At(event.getSender().getId()).plus("今日还没有人聊天哦"));
            } else {
                StringBuilder builder = new StringBuilder();
                builder.append("今日聊天排行：\n");
                for (int i = 0; i < talkDOS.size(); i++) {
                    TalkDO talkDO = talkDOS.get(i);
                    NormalMember normalMember = event.getGroup().get(talkDO.getQq());
                    String nick = normalMember.getNick();
                    builder.append(i + 1).append(".").append(nick).append(" ").append(talkDO.getTalkCount()).append("条\n");
                }
                builder.append("（统计于0点~当前）");
                event.getGroup().sendMessage(builder.toString());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
