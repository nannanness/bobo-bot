package org.cherrygirl.chinesechess;

import org.cherrygirl.core.Team;

public final class Player {

    public Player(String id, String name, Team team, int idx) {
        this.id = id;
        this.name = name;
        this.team = team;
        this.number = Math.max(1, idx);
    }

    public Player(Team team, int number) {
        this.id = null;
        this.team = team;
        this.name = null;
        this.number = number;
    }

    public String id;
    public String name;
    public final Team team;

    public final int number;

    public long use_time;

    @Override
    public String toString() {
        return this.team.toString() + this.number;
    }

    public String getMsgWithNum() {
        return this.team.toString() + this.number + ":" + this.name;
    }

    public String getMsg() {
        return this.team.toString() + ":" + this.name;
    }

    public void clear() {
        this.id = null;
        this.name = null;
        this.use_time = 0;
    }
}
