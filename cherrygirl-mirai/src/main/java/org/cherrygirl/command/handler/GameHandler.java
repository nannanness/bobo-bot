package org.cherrygirl.command.handler;

import com.alibaba.fastjson.JSON;
import org.cherrygirl.domain.SpyDo;
import org.cherrygirl.enumeration.GameStatusEnum;
import org.cherrygirl.enumeration.GameWinerEnum;
import org.cherrygirl.pojo.Player;
import org.cherrygirl.pojo.Vote;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author nannanness
 */
@Component
public class GameHandler {
    private final List<Player> players = Collections.synchronizedList(new ArrayList<>());
    private final List<Vote> voteCounter = new ArrayList<>();
    private final List<SpyDo> spyDos = new ArrayList<>();
    private String status = GameStatusEnum.nonStart.getValue();;
    public static final int SIZE = 5;
    public String win;

    public Player nextSpeaker() {
        return nextSpeaker(players);
    }

    public String printPlayers() {
        return printPlayers(players);
    }

    public void inVote() {
        status = GameStatusEnum.vote.getValue();
        for(Player player : players){
            player.setAlTalk(false);
        }
    }

    public boolean isAllTalkOver() {
        for(Player player : players){
            if(!player.isAlOut() && !player.isAlTalk()){
                return false;
            }
        }
        return true;
    }
    public boolean isAllVote() {
        for(Player player : players){
            if(!player.isAlOut() && !player.isAlVote()){
                return false;
            }
        }
        return true;
    }

    public void talkOver(int number) {
        for(Player p : players){
            if(number == p.getNumber()){
                p.setAlTalk(true);
            }
        }
    }

    public void init(List<SpyDo> spyDos) {
        System.out.println("spyDos: " + JSON.toJSONString(spyDos));
        this.spyDos.addAll(spyDos);
    }

    public void ready() {
        status = GameStatusEnum.ready.getValue();
    }

    public String status() {
        return status;
    }

    public int count() {
        return players.size();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public boolean join(Player player) {
        if(players.size() < SIZE){
            players.add(player);
            return true;
        }
        return false;
    }

    public boolean vote(int vote, int number) {
        for(Player p : players){
            if(number == p.getNumber()){
                if (p.isAlVote()){
                    return false;
                }
                voteCounter.add(new Vote(vote, number));
                p.setAlVote(true);
                return true;
            }
        }
        return false;
    }

    public void begin() {
        status = GameStatusEnum.begin.getValue();
    }

    public boolean minBegin() {
        return players.size() >= 3;
    }

    public void deal() {
        SpyDo spy = spyDos.get(new Random().nextInt(spyDos.size()));
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setNumber(i + 1);
            players.get(i).setOutCount(0);
            players.get(i).setWord(spy.getPos());
        }
        Player player = players.get(new Random().nextInt(players.size()));
        player.setWord(spy.getNeg());
        player.setSpy(true);
    }


    public String getWin() {
        return win;
    }

    public void resetVote() {
        for(Player p : players){
            p.setOutCount(0);
            p.setAlVote(false);
        }
    }

    public Player voteCol() {
        for (Player player : players) {
            if(player.isAlOut()){
                continue;
            }
            for (Vote vote : voteCounter) {
                if (player.getNumber() == vote.getVote()) {
                    player.setOutCount(player.getOutCount() + 1);
                }
            }
        }
        voteCounter.clear();
        List<Player> playersCopy = players.stream().filter(p -> !p.isAlOut()).collect(Collectors.toList());
        playersCopy.sort(Comparator.comparingInt(Player::getOutCount));
        Collections.reverse(playersCopy);
        Player player1 = playersCopy.get(0);
        Player player2 = playersCopy.get(1);
        if (player1.getOutCount() > player2.getOutCount()) {
            Optional<Player> first = players.stream().filter(p -> p.getNumber() == player1.getNumber()).findFirst();
            first.get().setAlOut(true);
            if(player1.isSpy()){
                win = GameWinerEnum.pos.getValue();
            }else{
                boolean alive = playersCopy.stream().anyMatch(Player::isSpy);
                if(playersCopy.size() <= 2 && alive){
                    win = GameWinerEnum.neg.getValue();
                }
            }
            resetVote();
            return player1;
        }
        resetVote();
        return null;
    }

    public void over(int id) {
        players.clear();
        voteCounter.clear();
        status = GameStatusEnum.nonStart.getValue();
        GroupCommandHandler.clearCache(id);
    }

    public String printPlayers(List<Player> players){
        StringBuilder text = new StringBuilder();
        for(int i = 0; i < players.size(); i++){
            Player player = players.get(i);
            if(player.isAlOut()){
                continue;
            }
            text.append(player.getNumber());
            text.append("号 ");
            text.append(player.getName());
            text.append("\n");
        }
        return text.toString();
    }

    public Player nextSpeaker(List<Player> players){
        StringBuilder text = new StringBuilder();
        for(int i = 0; i < players.size(); i++){
            Player player = players.get(i);
            if(player.isAlOut()){
                continue;
            }
            if(player.isAlTalk()){
                continue;
            }
            return player;
        }
        return null;
    }
}
