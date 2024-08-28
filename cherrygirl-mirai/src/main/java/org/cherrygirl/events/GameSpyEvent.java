package org.cherrygirl.events;

import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;
import org.cherrygirl.pojo.Nick;
import org.cherrygirl.domain.SpyDo;
import org.cherrygirl.command.handler.GameHandler;
import org.cherrygirl.enumeration.GameStatusEnum;
import org.cherrygirl.enumeration.GameWinerEnum;
import org.cherrygirl.pojo.Player;
import org.cherrygirl.service.SpyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nannanness
 */
@Component
public class GameSpyEvent {

    private long groupId = -1;
    private long nextPlayerId = -1;
    private boolean trigger = false;
    private int id = -1;

    @Autowired
    private GameHandler gameHandler;
    @Autowired
    private SpyService spyService;

    public void setId(int id){
        this.id = id;
    }

    public void triggerStart(){
        trigger = true;
    }

    public boolean isPlayer(Long id) {
        List<Player> players = gameHandler.getPlayers();
        for(Player player : players){
            Nick nick = (Nick) player.getP();
            if(nick.getId() == id){
                return true;
            }
        }
        return false;
    }

    public boolean isNextPlayer(Long id) {
        return id == nextPlayerId;
    }

    public Player getPlayer(Long id) {
        List<Player> players = gameHandler.getPlayers();
        for(Player player : players){
            Nick nick = (Nick) player.getP();
            if(nick.getId() == id){
                return player;
            }
        }
        return null;
    }

    public boolean allowVoteScope(String msg) {
        List<Player> players = gameHandler.getPlayers();
        List<String> list = new ArrayList<>();
        for(Player player : players){
            if(!player.isAlOut()){
                list.add(String.valueOf(player.getNumber()));
            }
        }
        return list.contains(msg);
    }

    public void dealToSingle(GroupMessageEvent event, Player player) throws IOException {
        Nick nick = (Nick) player.getP();
        NormalMember normalMember = event.getGroup().getMembers().get(nick.getId());
        normalMember.sendMessage(player.getNumber() + "号玩家你好哟，你的词是：" + player.getWord() + "\n" +
                "发完言请以\"结束\"结尾");
    }

    public void dealToAll(GroupMessageEvent event) throws IOException {
        List<Player> players = gameHandler.getPlayers();
        for(Player player : players){
            if(!player.isAlOut()){
                dealToSingle(event, player);
            }
        }
    }

    public void start(GroupMessageEvent event) throws IOException {
        if(trigger){
            groupId = event.getGroup().getId();
            List<SpyDo> list = spyService.list();
            gameHandler.init(list);
            Nick nick = Nick.structNick(event.getSender());
            gameHandler.ready();
            gameHandler.join(new Player(nick, nick.getNick()));
            event.getGroup().sendMessage("谁是卧底5人局，游戏启动，" + nick.getNick() + "加入游戏\n"
                    + "加入游戏回复1\n"
                    + "已加入" + gameHandler.count() + "人"
            );
            trigger = false;
        }

    }

    public void join(GroupMessageEvent event) throws IOException {
        String message = event.getMessage().contentToString();
        if("1".equals(message)){
            if(!isPlayer(event.getSender().getId())){
                Nick nick = Nick.structNick(event.getSender());
                boolean res = gameHandler.join(new Player(nick, nick.getNick()));
                if(res){
                    int size = GameHandler.SIZE - gameHandler.count();
                    MessageChainBuilder msg = new MessageChainBuilder();
                    msg.append(nick.getNick());
                    msg.append("加入游戏\n");
                    if(size > 0){
                        msg.append("加入游戏回复1\n");
                        msg.append("已加入");
                        msg.append(String.valueOf(gameHandler.count()));
                        msg.append("人\n");
                        msg.append("还可加入");
                        msg.append(String.valueOf(size));
                        msg.append("人");
                    }else{
                        gameHandler.deal();
                        dealToAll(event);
                        Player player = gameHandler.nextSpeaker();
                        Nick nick1 = (Nick) player.getP();
                        nextPlayerId = nick1.getId();
                        msg.append("人数已满，游戏开始\n");
                        msg.append("第一回合，按以下顺序发言：\n");
                        msg.append(gameHandler.printPlayers());
                        msg.append("第一位玩家：");
                        msg.append(new At(nick1.getId()));
                        msg.append("\n请描述你的拿到的词");
                        msg.append("\n发言后请以“结束”结尾\n卡住时玩家可输入“快速结束”来结束本局");
                        gameHandler.begin();
                    }
                    event.getGroup().sendMessage(msg.build());
                }
            }
        }else if("开始".equals(message)){
            if(isPlayer(event.getSender().getId()) && gameHandler.minBegin()){
                MessageChainBuilder msg = new MessageChainBuilder();
                gameHandler.deal();
                dealToAll(event);
                Player player = gameHandler.nextSpeaker();
                Nick nick1 = (Nick) player.getP();
                nextPlayerId = nick1.getId();
                msg.append("人数已满，游戏开始\n");
                msg.append("第一回合，按以下顺序发言：\n");
                msg.append(gameHandler.printPlayers());
                msg.append("第一位玩家：");
                msg.append(new At(nick1.getId()));
                msg.append("\n请描述你的拿到的词");
                msg.append("\n发言后请以“结束”结尾\n卡住时玩家可输入“快速结束”来结束本局");
                gameHandler.begin();
                event.getGroup().sendMessage(msg.build());
            }
        }
    }

    public void speak(GroupMessageEvent event){
        String message = event.getMessage().contentToString();
        if(message.endsWith("结束") && isNextPlayer(event.getSender().getId())){
            // todo
            Player player = getPlayer(event.getSender().getId());
            if(player != null){
                gameHandler.talkOver(player.getNumber());
            }
            if(gameHandler.isAllTalkOver()){
                event.getGroup().sendMessage("所有人回合结束，请开始投票: \n" +
                        gameHandler.printPlayers() +
                        "输入你认为是卧底的玩家的序号，仅输入数字");
                gameHandler.inVote();
                nextPlayerId = -1;
            }else{
                Nick n = (Nick) player.getP();
                Player player2 = gameHandler.nextSpeaker();
                Nick n2 = (Nick) player2.getP();
                nextPlayerId = n2.getId();
                event.getGroup().sendMessage(new PlainText(n.getNick() + "发言结束，现在是").plus(new At(n2.getId()).plus("的回合\n发言后请以“结束”结尾\n卡住时玩家可输入“快速结束”来结束本局")));
            }
        }
    }

    public void vote(GroupMessageEvent event){
        String message = event.getMessage().contentToString();
        if(isPlayer(event.getSender().getId())){
            if(allowVoteScope(message)){
                Player player = getPlayer(event.getSender().getId());
                player.setVote(Integer.parseInt(message));
                gameHandler.vote(player.getVote(), player.getNumber());
            }else if(message.trim().matches("^[0-9]*$")){
                event.getGroup().sendMessage(new At(event.getSender().getId()).plus("请输入正确的序号"));
            }
        }
        if(gameHandler.isAllVote()){
            Player player = gameHandler.voteCol();
            String win = gameHandler.getWin();
            String text = "";
            boolean continuing = true;
            if(GameWinerEnum.neg.getValue().equals(win)){
                Nick nick = (Nick)player.getP();
                text = "，游戏结束，卧底胜利！" + "卧底是：" + nick.getNick();
                continuing = false;
            }else if(GameWinerEnum.pos.getValue().equals(win)){
                Nick nick = (Nick)player.getP();
                text = "，游戏结束，玩家胜利！" + "卧底是：" + nick.getNick();
                continuing = false;
            }else{
                text = "，游戏继续。\n";
                continuing = true;
            }
            if(player != null){
                Nick nick = (Nick)player.getP();
                Player p = gameHandler.nextSpeaker();
                Nick nick1 = (Nick) p.getP();
                nextPlayerId = nick1.getId();
                String msg = nick.getNick() + "出局" + text;
                if(continuing){
                    event.getGroup().sendMessage(new PlainText(msg
                            + "按以下顺序发言：\n"
                            + gameHandler.printPlayers()
                            + "第一位玩家：")
                            .plus(new At(nick1.getId()))
                            .plus(new PlainText("\n请描述你拿到的词"))
                            .plus(new PlainText("\n发言后请以“结束”结尾\n卡住时玩家可输入“快速结束”来结束本局"))
                    );
                    gameHandler.begin();
                }else{
                    event.getGroup().sendMessage(msg);
                    groupId = -1;
                    gameHandler.over(id);
                }

            }else{
                Player p = gameHandler.nextSpeaker();
                Nick nick1 = (Nick) p.getP();
                nextPlayerId = nick1.getId();
                event.getGroup().sendMessage(new PlainText(
                        "无人出局，游戏继续\n"
                        + "按以下顺序发言：\n"
                        + gameHandler.printPlayers()
                        + "第一位玩家：")
                        .plus(new At(nick1.getId()))
                        .plus(new PlainText("\n请描述你拿到的词"))
                        .plus(new PlainText("\n发言后请以“结束”结尾\n卡住时玩家可输入“快速结束”来结束本局"))
                );
                gameHandler.begin();
            }
        }
    }

    public void handlerSpy(GroupMessageEvent event) throws IOException {
        if(groupId != -1 && event.getGroup().getId() != groupId){
            return;
        }
        if(isPlayer(event.getSender().getId())){
            String message = event.getMessage().contentToString();
            if("快速结束".equals(message)){
                event.getGroup().sendMessage("游戏已结束");
                groupId = -1;
                gameHandler.over(id);
            }
        }
        GameStatusEnum enumValue = GameStatusEnum.getEnumByValue(gameHandler.status());
        switch (enumValue){
            case nonStart:
                start(event);
                break;
            case ready:
                join(event);
                break;
            case begin:
                speak(event);
                break;
            case vote:
                vote(event);
                break;
            default:
                break;
        }
    }
}
