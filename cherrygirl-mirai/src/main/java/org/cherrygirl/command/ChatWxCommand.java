package org.cherrygirl.command;

import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import org.apache.commons.lang3.StringUtils;
import org.cherrygirl.command.handler.GroupCommandHandler;
import org.cherrygirl.domain.CoinDO;
import org.cherrygirl.manager.CoinManager;
import org.cherrygirl.pojo.WxResult;
import org.cherrygirl.utils.TalkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 文心千帆模型
 */
@Component
public class ChatWxCommand implements Command, FriendCommand{

    @Autowired
    private CoinManager coinManager;
    private boolean flag;
    private boolean off;
    private long beginTime;
    private long beginGroup;
    private long user;

    private void start(long user, long groupId, long beginTime){
        this.flag = true;
        this.beginTime = beginTime;
        this.beginGroup = groupId;
        this.user = user;
    }
    private void pushTime(long beginTime){
        this.beginTime = beginTime;
    }



    private void finishing(int id){
        this.flag = false;
        this.beginTime = 0;
        this.beginGroup = 0;
        this.user = 0;
        GroupCommandHandler.clearCache(id);
    }

    @Override
    public void run(GroupMessageEvent event, int id, String... params) throws IOException {
        long qq = event.getSender().getId();
        String content = event.getMessage().contentToString();
        if(!flag && "AI模式".equals(content)){
            start(qq, event.getGroup().getId(), System.currentTimeMillis() + 1000 * 60);
            event.getGroup().sendMessage(new At(user).plus("AI模式开启：\n" +
                    "你可以向我提问题来生成连续对话（如：帮我解释一下什么是芯片，帮我写一篇小红书风格的关于AI的文案），我会尽力帮你解答。" +
                    "当你想结束对话时输入：“结束”。ps：一分钟内不提问将自动结束" + "\n"
                    + "\n" + "本技术由文心一言3.5模型提供。" +
                    "\n" + "（本次花费波币由对话tokens数量决定，消耗波币=tokens）"));
        }else if(beginTime < System.currentTimeMillis()){
            int count = TalkUtil.clear(user);
            event.getGroup().sendMessage(new At(user).plus("对话结束，本次对话共消耗tokens：" + count + "，你花费波币：" + count));
            if(count != 0){
                coinManager.subCoins(event.getGroup().getId(), user, -count);
            }
            finishing(id);
        } else if(flag && beginGroup == event.getGroup().getId() && user == event.getSender().getId()){
            if(params != null && params.length > 0){
                String text = params[0];
                if(StringUtils.isNotEmpty(text) && "结束".equals(text)){
                    int count = TalkUtil.clear(user);
                    event.getGroup().sendMessage(new At(user).plus("对话结束，本次对话共消耗tokens：" + count + "，你花费波币：" + count));
                    if(count != 0){
                        coinManager.subCoins(event.getGroup().getId(), user, -count);
                    }
                    finishing(id);
                } else {
                    if(!"AI模式".equals(text)){
                        WxResult wxResult = TalkUtil.chatWx(user, text);
                        event.getGroup().sendMessage(new At(user).plus(wxResult.getResult()
                                + "\n（消耗tokens : " + wxResult.getCount() +"）"
                        ));
                        pushTime(System.currentTimeMillis() + 1000 * 60);
                    }
                }
            }
        }
    }

    @Override
    public void runFriend(FriendMessageEvent event, long qq, String message) throws IOException {
        if("提问".equals(message)){
            event.getFriend().sendMessage("AI模式开启");
            off = true;
        } else if("结束".equals(message)){
            int count = TalkUtil.clear(qq);
            event.getFriend().sendMessage("对话结束，本次对话共消耗tokens：" + count);
            off = false;
        } else {
            if(off){
                WxResult wxResult = TalkUtil.chatWx(qq, message);
                event.getFriend().sendMessage(wxResult.getResult() + "\n（消耗tokens : " + wxResult.getCount() +"）");
            }
        }
    }
}
