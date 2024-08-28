package org.cherrygirl.pojo;

/**
 * @author nannanness
 */
public class Player<T> {
    private T p;
    private String word;
    private int number;
    private int vote;
    private int outCount;
    private boolean alVote;
    private boolean alOut;
    private boolean alTalk;
    private boolean spy;
    private String name;

    public Player(T p, String name) {
        this.p = p;
        this.name = name;
    }


    public boolean isSpy() {
        return spy;
    }

    public void setSpy(boolean spy) {
        this.spy = spy;
    }

    public boolean isAlTalk() {
        return alTalk;
    }

    public void setAlTalk(boolean alTalk) {
        this.alTalk = alTalk;
    }

    public boolean isAlVote() {
        return alVote;
    }

    public boolean isAlOut() {
        return alOut;
    }

    public void setAlOut(boolean alOut) {
        this.alOut = alOut;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getAlVote() {
        return alVote;
    }

    public void setAlVote(boolean alVote) {
        this.alVote = alVote;
    }

    public int getOutCount() {
        return outCount;
    }

    public void setOutCount(int outCount) {
        this.outCount = outCount;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public T getP() {
        return p;
    }

    public void setP(T p) {
        this.p = p;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
