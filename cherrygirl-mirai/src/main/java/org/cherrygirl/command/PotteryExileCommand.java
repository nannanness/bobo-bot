package org.cherrygirl.command;

import cn.hutool.core.util.RandomUtil;
import net.mamoe.mirai.contact.MemberPermission;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import org.cherrygirl.command.handler.GroupCommandHandler;
import org.cherrygirl.manager.ImgManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 典中典
 * @author nannanness
 */
@Component
public class PotteryExileCommand implements Command {

    private boolean flag;
    private int bulletNumber;
    private long beginTime;
    private long beginGroup;

    private void playing(long groupId, long beginTime){
        this.flag = true;
        this.bulletNumber = 5;
        this.beginTime = beginTime;
        this.beginGroup = groupId;
    }

    private void finishing(int id){
        flag = false;
        this.bulletNumber = 5;
        this.beginTime = 0;
        this.beginGroup = 0;
        GroupCommandHandler.clearCache(id);
    }

    private String getBulletNumberWord() {
        return "剩余" + this.bulletNumber +"颗子弹";
    }

    private void updateTime() {
        this.beginTime = System.currentTimeMillis() + 1000 * 10;
    }

        @Autowired
    private ImgManager imgManager;

    @Override
    public void run(GroupMessageEvent event, int id, String... params) throws IOException {
        if(!flag){
            playing(event.getGroup().getId(), System.currentTimeMillis() + 1000 * 8);
            event.getGroup().sendMessage("陶片惩罚开始，" + getBulletNumberWord() + "，参与游戏输入开枪");
        } else if(beginTime < System.currentTimeMillis()){
            finishing(id);
        } else if(flag && beginGroup == event.getGroup().getId()){
            String content = event.getMessage().contentToString();
            if("开枪".equals(content)){
                if(bulletNumber > 0){
                    if(event.getSender().getPermission() == MemberPermission.MEMBER) {
                        updateTime();
                        int c = RandomUtil.randomInt(1, 101);
                        if(c % 5 == 0 || bulletNumber == 1) {
                            event.getGroup().sendMessage(new At(event.getSender().getId()).plus("你扣动了扳机，砰的一声。。。你倒下了，看来运气不佳呢。游戏结束！"));
                            event.getSender().mute(60 * 3);
                            finishing(id);
                        } else {
                            bulletNumber--;
                            event.getGroup().sendMessage(new At(event.getSender().getId()).plus("你扣动了扳机，砰的一声。。。很好，是空包弹。游戏继续！" + getBulletNumberWord() + "，继续游戏输入开枪"));
                        }
                    } else {
                        event.getGroup().sendMessage(new At(event.getSender().getId()).plus("你好，仅限普通群员参与！"));
                    }
                }
            }
        }
    }
}
