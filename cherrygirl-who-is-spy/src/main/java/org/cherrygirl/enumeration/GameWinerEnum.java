package org.cherrygirl.enumeration;

/**
 * @author nannanness
 */

public enum GameWinerEnum {

    /**
     * 玩家
     */
    pos("玩家"),
    /**
     * 卧底
     */
    neg("卧底");

    private String value;

    GameWinerEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static GameWinerEnum getEnumByValue(String value) {
        GameWinerEnum[] values = values();
        for(GameWinerEnum winer : values){
            if(winer.getValue().equals(value)){
                return winer;
            }
        }
        return null;
    }
}
