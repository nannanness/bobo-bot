package org.cherrygirl.pojo;

public class Vote {
    private int vote;
    private int number;

    public Vote(int vote, int number) {
        this.vote = vote;
        this.number = number;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
