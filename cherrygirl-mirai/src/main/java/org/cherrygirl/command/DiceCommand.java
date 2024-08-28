package org.cherrygirl.command;

import cn.hutool.core.util.RandomUtil;
import net.mamoe.mirai.contact.MemberPermission;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Dice;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import org.apache.commons.lang3.StringUtils;
import org.cherrygirl.command.handler.GroupCommandHandler;
import org.cherrygirl.domain.CoinDO;
import org.cherrygirl.manager.CoinManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 骰子对决
 * @author nannanness
 */
@Component
public class DiceCommand implements Command {

    @Autowired
    private CoinManager coinManager;

    private boolean flag;
    private long beginTime;
    private long beginGroup;
    private long player1;
    private long player2;
    private long player1Val;
    private long player2Val;

    private void playing(long beginGroup, long player1, long player2, long beginTime){
        this.flag = true;
        this.beginTime = beginTime;
        this.beginGroup = beginGroup;
        this.player1 = player1;
        this.player2 = player2;
    }

    private void finishing(int id){
        this.flag = false;
        this.beginTime = 0;
        this.beginGroup = 0;
        this.player1 = 0;
        this.player2 = 0;
        this.player1Val = 0;
        this.player2Val = 0;
        GroupCommandHandler.clearCache(id);
    }

    @Override
    public void run(GroupMessageEvent event, int id, String... params) throws IOException {


        if(!flag && params != null && params.length == 2 && "对决".equals(params[0])){
            if(event.getSender().getPermission() != MemberPermission.MEMBER) {
                event.getGroup().sendMessage(new At(event.getSender().getId()).plus("你好，由于不能禁言管理，仅限普通群员参与！"));
                finishing(id);
                return;
            }
            String qq = null;
            if(params.length == 2) {
                qq = params[1];
            } else {
                event.getGroup().sendMessage(new At(event.getSender().getId()).plus("你好，此游戏无法单人参与，请@对方"));
                finishing(id);
                return;
            }
            if(StringUtils.isNotEmpty(qq)){
                playing(event.getGroup().getId(), event.getSender().getId(),  Long.parseLong(qq), System.currentTimeMillis() + 1000 * 30);
                event.getGroup().sendMessage(new At(event.getSender().getId()).plus("骰子对决开始，游戏规则：\n" +
                        "发起人和被指定人各掷一枚骰子（魔法表情），通过点数来对决，输者将接收惩罚，参与对决请在10s内掷骰子" +
                        "\n（本次游戏将花费20波币）"));

                CoinDO coinDO = coinManager.viewCoins(event.getGroup().getId(), event.getSender().getId());
                if(coinDO == null || coinDO.getCoin() < 20){
                    event.getGroup().sendMessage(new At(event.getSender().getId()).plus("波币不足，游戏退出"));
                    finishing(id);
                } else {
                    coinManager.sub20Coins(event.getGroup().getId(), event.getSender().getId());
                }
            }

        } else if(beginTime < System.currentTimeMillis()){
            finishing(id);
        } else if(flag && beginGroup == event.getGroup().getId()){
            MessageChain message = event.getMessage();
            Dice dice = (Dice) message.stream().filter(Dice.class::isInstance).findFirst().orElse(null);
            if(dice != null){
                if((player1 == event.getSender().getId())){
                    player1Val = dice.getValue();
                }else if(player2 == event.getSender().getId()){
                    player2Val = dice.getValue();
                }
            }
            if(player1Val != 0 && player2Val != 0){
                if(player1Val > player2Val){
                    event.getGroup().sendMessage(new At(player2).plus("接收审判吧。。。阿~门~。。"));
                    event.getGroup().get(player2).mute(60 * 1);
                } else if(player1Val < player2Val){
                    event.getGroup().sendMessage(new At(player1).plus("接收审判吧。。。阿~门~。。"));
                    event.getGroup().get(player1).mute(60 * 1);
                } else {
                    event.getGroup().sendMessage("看来是平局呢，不如握手言和吧！");
                }
                finishing(id);
            }
        }
    }
}
