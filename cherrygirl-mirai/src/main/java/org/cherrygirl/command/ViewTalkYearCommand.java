package org.cherrygirl.command;

import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import org.cherrygirl.domain.TalkDO;
import org.cherrygirl.manager.TalkManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * 查看聊天年度总结
 * @author nannanness
 */
@Slf4j
@Component
public class ViewTalkYearCommand implements Command {

    @Autowired
    private TalkManager talkManager;

    @Override
    public void run(GroupMessageEvent event, int id, String... params) throws IOException {

        long groupId = event.getGroup().getId();
        long qq = event.getSender().getId();
        try {
            List<TalkDO> talkDOS = talkManager.viewGroupQqYearTalks(groupId, qq, "2022");
            List<TalkDO> topTalkDOS = talkManager.viewGroupYearTalks(groupId, qq, "2022");
            if(talkDOS.isEmpty()){
                event.getGroup().sendMessage(new At(event.getSender().getId()).plus("暂无你2022年聊天记录"));
            } else {
                StringBuilder builder = new StringBuilder();
                builder.append("\n你的2022年度群聊报告如下：\n");
                long talkCount = talkDOS.stream().mapToLong(TalkDO::getTalkCount).sum();
                int signInToday = talkDOS.stream().mapToInt(TalkDO::getSignInToday).sum();
                builder.append("\n你在本群一共发出了").append(talkCount).append("条消息。\n");
                builder.append("\n在过去的一年中，你在").append(talkDOS.size()).append("天中均有发言。\n");
                builder.append("\n在过去的一年中，你一共签到了").append(signInToday).append("次。请继续加油吧！\n");
                long talkCountCnt = talkCount / talkDOS.size();
                builder.append("\n在你有发言的日子里，平均每天发出").append(talkCountCnt).append("条消息。");
                if(talkCountCnt > 100){
                    builder.append("你相当热爱交流，常常能在群聊中舌战群儒颇有风采。\n");
                } else if(talkCountCnt > 55){
                    builder.append("你在群聊中张弛有度，善于用精炼的语言传递精彩言论。\n");
                } else {
                    builder.append("你从不轻易发言，但总是在关键时刻给予有力的语句。\n");
                }
                builder.append("\n在过去的一年中，你拿到了").append(topTalkDOS.size()).append("次聊天王。");
                if(topTalkDOS.size() >= 50){
                    builder.append("你是本群当之无愧的年度聊天王，本群的灵魂人物，恭喜你！\n");
                } else if(topTalkDOS.size() >= 20){
                    builder.append("你是本群的气氛组成员，群内的快乐由你们供给。\n");
                } else if(topTalkDOS.size() >= 10){
                    builder.append("你是本群的不可或缺的人物，新的一年继续加油。\n");
                } else if(topTalkDOS.size() >= 1){
                    builder.append("你是本群的中坚力量，请继续为大家带来欢乐吧。\n");
                } else {
                    builder.append("你是本群的快乐群众，享受交流吧。\n\n\n");
                }
                builder.append("\n统计时间：\n（2022-08-20 ~ 2022-12-31）");
                event.getGroup().sendMessage(new At(event.getSender().getId()).plus(builder.toString()));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
