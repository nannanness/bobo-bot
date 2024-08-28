package org.cherrygirl.chinesechess;

import org.cherrygirl.core.ChessControl;
import org.cherrygirl.core.Team;

import java.util.Random;

public class EveryChess {
    public EveryChess(int player_count, String seed) {
        control = new ChessControl(seed);
        this.initPlayers(player_count);
        this.random = new Random();
    }

    public Player[] players;

    public ChessControl control;

    public int player_count;

    public int has_add_count;

    public Random random;

    public String getPlayerMsg() {
        int idx = 0;
        StringBuilder result = new StringBuilder();

        for (idx = 0; idx < this.player_count; idx++) {
            result.append(this.players[idx].getMsgWithNum()).append("\n");
        }

        return result.toString();
    }

    public void initPlayers(int count) {
        int idx = 0;
        this.player_count = count;
        this.has_add_count = 0;
        this.players = new Player[count];

        for (idx = 0; idx < this.player_count; idx++) {
            if (idx % 2 == 0) {
                this.players[idx] = new Player(Team.red, idx / 2 + 1);
            } else {
                this.players[idx] = new Player(Team.black, idx / 2 + 1);
            }
        }
    }

    /**
     * @param id     id
     * @param name   name
     * @param team   team
     * @param number number
     * @description: 加入成功则返回null，加入失败则返回失败原因
     * @return: java.lang.String
     * @author: BigCherryBall
     * @time: 2023/11/18 10:20
     */
    public String addByNum(String id, String name, Team team, int number) {
        if (number < 1 || number > this.player_count / 2) {
            return "加入失败，选择的位置编号不正确";
        }

        if (team == null) {
            return "加入失败：内部参数错误3，请帮忙踢作者一脚: ChessAdaptor.EveryChess.add()";
        }

        int select;
        if (Team.red == team) {
            select = number * 2 - 2;
        } else {
            select = number * 2 - 1;
        }

        if (this.players[select].id != null) {
            return "加入失败，该位置已经有人了";
        }

        this.addByIdx(id, name, select);
        return null;
    }

    public void addByIdx(String id, String name, int idx) {
        Player select = this.players[idx];
        select.id = id;
        select.name = name;
        this.has_add_count++;
    }
}
