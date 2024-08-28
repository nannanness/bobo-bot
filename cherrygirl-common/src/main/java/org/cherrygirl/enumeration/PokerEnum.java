package org.cherrygirl.enumeration;

/**
 * 扑克牌
 * @author nannanness
 */
public enum PokerEnum {

    /**
     * 黑桃
     */
    SPADE_1("1", "黑桃", 1), SPADE_2("2", "黑桃", 2), SPADE_3("3", "黑桃", 3),

    SPADE_4("4", "黑桃", 4), SPADE_5("5", "黑桃", 5), SPADE_6("6", "黑桃", 6),

    SPADE_7("7", "黑桃", 7), SPADE_8("8", "黑桃", 8), SPADE_9("9", "黑桃", 9),

    SPADE_10("10", "黑桃", 10), SPADE_11("J", "黑桃", 11), SPADE_12("Q", "黑桃", 12),

    SPADE_13("K", "黑桃", 13),

    /**
     * 红桃
     */
    HEART_1("1", "红桃", 1), HEART_2("2", "红桃", 2), HEART_3("3", "红桃", 3),

    HEART_4("4", "红桃", 4), HEART_5("5", "红桃", 5), HEART_6("6", "红桃", 6),

    HEART_7("7", "红桃", 7), HEART_8("8", "红桃", 8), HEART_9("9", "红桃", 9),

    HEART_10("10", "红桃", 10), HEART_11("J", "红桃", 11), HEART_12("Q", "红桃", 12),

    HEART_13("K", "红桃", 13),

    /**
     * 梅花
     */
    DIAMOND_1("1", "梅花", 1), DIAMOND_2("2", "梅花", 2), DIAMOND_3("3", "梅花", 3),

    DIAMOND_4("4", "梅花", 4), DIAMOND_5("5", "梅花", 5), DIAMOND_6("6", "梅花", 6),

    DIAMOND_7("7", "梅花", 7), DIAMOND_8("8", "梅花", 8), DIAMOND_9("9", "梅花", 9),

    DIAMOND_10("10", "梅花", 10), DIAMOND_11("J", "梅花", 11), DIAMOND_12("Q", "梅花", 12),

    DIAMOND_13("K", "梅花", 13),

    /**
     * 方片
     */
    CLUB_1("1", "方片", 1), CLUB_2("2", "方片", 2), CLUB_3("3", "方片", 3),

    CLUB_4("4", "方片", 4), CLUB_5("5", "方片", 5), CLUB_6("6", "方片", 6),

    CLUB_7("7", "方片", 7), CLUB_8("8", "方片", 8), CLUB_9("9", "方片", 9),

    CLUB_10("10", "方片", 10), CLUB_11("J", "方片", 11), CLUB_12("Q", "方片", 12),

    CLUB_13("K", "方片", 13),

    /**
     * 小鬼
     */
    SMALL_JOKER("B_JOKER", "小鬼", 14),

    /**
     * 大鬼
     */
    BIG_JOKER("R_JOKER", "大鬼", 15),

    /**
     * 背面
     */
    BACK("BACK", "背面", 0),

    ;

    private String label;

    private String color;

    private int value;

    PokerEnum(String label, String color, int value) {
        this.label = label;
        this.color = color;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public int getValue() {
        return value;
    }

    public String getColor() {
        return color;
    }
}
