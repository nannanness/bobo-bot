package org.cherrygirl.command;

import cn.hutool.cache.impl.TimedCache;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import org.cherrygirl.domain.CoinDO;
import org.cherrygirl.manager.CoinManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 每日签到/聊天记录
 * @author nannanness
 */
@Slf4j
@Component
public class ViewBoCoinCommand implements Command {

    @Autowired
    private CoinManager coinManager;

    @Override
    public void run(GroupMessageEvent event, int id, String... params) throws IOException {

        long groupId = event.getGroup().getId();
        long qq = event.getSender().getId();
        String s = groupId + ":" + qq;
        CoinDO coinDO = coinManager.viewCoins(groupId, qq);
        if(coinDO != null || coinDO.getCoin() != null){
            event.getGroup().sendMessage(new At(event.getSender().getId()).plus("你的波币余额为：" + coinDO.getCoin()
                    + "\n获取波币的方法："
                    + "\n签到\n聊天（一定数量）\n上传图片 典中典"
                    + "\n（波币可用于消费特定游戏和指令，更多玩法开发中。。。）"));
        } else {
            event.getGroup().sendMessage(new At(event.getSender().getId()).plus("你暂无波币，可通过聊天获取波币哦"));
        }
    }
}
