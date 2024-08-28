package org.cherrygirl.command;

import cn.hutool.core.util.RandomUtil;
import net.mamoe.mirai.contact.MemberPermission;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.utils.ExternalResource;
import org.apache.commons.lang3.StringUtils;
import org.cherrygirl.command.handler.GroupCommandHandler;
import org.cherrygirl.domain.ImgDO;
import org.cherrygirl.manager.ImgManager;
import org.cherrygirl.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * 下注子弹
 * @author nannanness
 */
@Component
public class BetBulletCommand implements Command {

    @Autowired
    private ImgManager imgManager;

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
        return "剩" + this.bulletNumber +"颗子弹";
    }

    private void updateTime() {
        this.beginTime = System.currentTimeMillis() + 1000 * 60;
    }

    @Override
    public void run(GroupMessageEvent event, int id, String... params) throws IOException {
        if(!flag){
            playing(event.getGroup().getId(), System.currentTimeMillis() + 1000 * 8);
            event.getGroup().sendMessage("下注子弹游戏开始，" + getBulletNumberWord() + "，参与游戏输入开枪");
        } else if(beginTime < System.currentTimeMillis()){
            finishing(id);
        } else if(flag && beginGroup == event.getGroup().getId()){
            String content = event.getMessage().contentToString();
            if("开枪".equals(content)){
                if(bulletNumber > 0){
                    if(event.getSender().getPermission() == MemberPermission.MEMBER) {
                        updateTime();
                        int c = RandomUtil.randomInt(1, 101);
                        if(c % 3 == 0 || bulletNumber == 1) {
                            event.getGroup().sendMessage(new At(event.getSender().getId()).plus("砰的一声。。。你倒下了，看来运气不佳呢"));
                            event.getSender().mute(60 * 5);
                            finishing(id);
                        } else {
                            bulletNumber--;
                            event.getGroup().sendMessage(new At(event.getSender().getId()).plus("无事发生，" + getBulletNumberWord() + "，勇者请继续"));
                        }
                    } else {
                        event.getGroup().sendMessage(new At(event.getSender().getId()).plus("你好，由于不能禁言管理，仅限普通群员参与！"));
                    }
                }
            }
        }
    }
}
