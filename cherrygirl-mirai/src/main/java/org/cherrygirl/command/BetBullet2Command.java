package org.cherrygirl.command;

import cn.hutool.core.util.RandomUtil;
import net.mamoe.mirai.contact.MemberPermission;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChain;
import org.apache.commons.lang3.StringUtils;
import org.cherrygirl.command.handler.GroupCommandHandler;
import org.cherrygirl.domain.CoinDO;
import org.cherrygirl.manager.CoinManager;
import org.cherrygirl.manager.ImgManager;
import org.cherrygirl.utils.TextStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * 下注子弹 击穿不死
 * @author nannanness
 */
@Component
public class BetBullet2Command implements Command {

    @Autowired
    private CoinManager coinManager;

    private boolean flag;
    private int bulletNumber;
    private long beginTime;
    private long beginGroup;
    private long player;
    private boolean ready;

    private void playing(long player, long groupId, long beginTime){
        this.flag = true;
        this.bulletNumber = 1;
        this.beginTime = beginTime;
        this.beginGroup = groupId;
        this.player = player;
        this.ready = false;
    }

    private void finishing(int id){
        this.flag = false;
        this.bulletNumber = 1;
        this.beginTime = 0;
        this.beginGroup = 0;
        this.player = 0;
        this.ready = false;
        GroupCommandHandler.clearCache(id);
    }

    private String getBulletNumberWord() {
        return "试炼剩" + this.bulletNumber +"颗子弹";
    }

    private void updateTime() {
        this.beginTime = System.currentTimeMillis() + 1000 * 15;
    }

    @Override
    public void run(GroupMessageEvent event, int id, String... params) throws IOException {
        String content = event.getMessage().contentToString();
        if(!flag && "子弹试炼".equals(content)){
            if(event.getSender().getPermission() != MemberPermission.MEMBER) {
                event.getGroup().sendMessage(new At(event.getSender().getId()).plus("你好，由于不能禁言管理，仅限普通群员参与！"));
                finishing(id);
                return;
            }
            playing(event.getSender().getId(), event.getGroup().getId(), System.currentTimeMillis() + 1000 * 25);
            event.getGroup().sendMessage(new At(event.getSender().getId()).plus("子弹试炼游戏开始，游戏规则：\n" +
                    "本游戏为单人模式，你有一定数量的子弹用于试炼，在试炼中死亡将游戏结束。如存活到最后，你将获得一发子弹，可用于攻击" +
                    "\n" + getBulletNumberWord() + "，请在15s内开枪" +
                    "\n（本次游戏将花费20波币）"));

            CoinDO coinDO = coinManager.viewCoins(event.getGroup().getId(), event.getSender().getId());
            if(coinDO == null || coinDO.getCoin() < 20){
                event.getGroup().sendMessage(new At(event.getSender().getId()).plus("波币不足，游戏退出"));
                finishing(id);
            } else {
                coinManager.sub20Coins(event.getGroup().getId(), event.getSender().getId());
            }
        } else if(beginTime < System.currentTimeMillis()){
            finishing(id);
        } else if(flag && beginGroup == event.getGroup().getId() && player == event.getSender().getId()){
            if("开枪".equals(content)){
                if(bulletNumber > 0){
                    if(event.getSender().getPermission() == MemberPermission.MEMBER) {

                        int c = RandomUtil.randomInt(1, 101);
                        if(c > 55) {
                            event.getGroup().sendMessage(new At(event.getSender().getId()).plus("试炼失败。。。不甘心么。。。啊。。。游戏结束"));
                            event.getSender().mute(60 * 1);
                            finishing(id);
                        } else {
                            bulletNumber--;
                            if(bulletNumber > 0){
                                event.getGroup().sendMessage(new At(event.getSender().getId()).plus("无事发生，" + getBulletNumberWord() + "，勇者请继续"));
                            } else {
                                ready = true;
                                updateTime();
                                event.getGroup().sendMessage(new At(event.getSender().getId()).plus("少年，恭喜你通过试炼，你将获得一颗子弹，" +
                                        "你可对任意目标（非管理）开枪\n请输入“开枪@对方”\n（15s内不开枪将失去机会）"));
                            }
                        }
                    } else {
                        event.getGroup().sendMessage(new At(event.getSender().getId()).plus("你好，由于不能禁言管理，仅限普通群员参与，你可用小号参与游戏哦。"));
                    }
                }
            } else if (ready){
                String matchCommand = null;
                String matchQQStr = null;
                if(content.contains("@")){
                    matchCommand = content.trim().split("@")[0];
                    matchQQStr = content.trim().split("@")[1];
                }else if(content.contains("CQ:at")) {
                    List<String> matchHanZi = TextStringUtil.matchHanZi(content);
                    List<String> matchQQ = TextStringUtil.matchQQ(content);
                    matchCommand = !matchHanZi.isEmpty() && matchHanZi.contains("开枪") ? "开枪" : null;
                    matchQQStr = !matchQQ.isEmpty()  ? matchQQ.get(0) : null;
                }
                if(StringUtils.isNotEmpty(matchQQStr) && StringUtils.isNotEmpty(matchCommand) && "开枪".equals(matchCommand)){
                    System.out.println("扣动扳机 s: " + matchQQStr);
                    System.out.println("扣动扳机 content: " + content);
                    Long qq = Long.valueOf(matchQQStr);
                    if(event.getGroup().contains(qq)){
                        if(event.getGroup().get(qq).getPermission() != MemberPermission.MEMBER) {
                            event.getGroup().sendMessage(new At(event.getSender().getId()).plus("你好，无法对管理开枪，子弹失效，游戏结束"));
                        } else {
                            event.getGroup().sendMessage(new At(qq).plus("对不起。。。你被偷袭了。。。啊。。。"));
                            event.getGroup().get(qq).mute(60 * 1);;
                            finishing(id);
                        }
                    }
                }
            }
        }
    }
}
