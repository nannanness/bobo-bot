package org.cherrygirl.enumeration;

/**
 * @author nannanness
 */

public enum GameStatusEnum {

    /**
     * 已开始
     */
    begin("已开始"),
    /**
     * 准备中
     */
    ready("准备中"),
    /**
     * 未开始
     */
    nonStart("未开始"),
    /**
     * 投票阶段
     */
    vote("投票阶段");

    private String value;

    GameStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static GameStatusEnum getEnumByValue(String value) {
        GameStatusEnum[] values = values();
        for(GameStatusEnum status : values){
            if(status.getValue().equals(value)){
                return status;
            }
        }
        return null;
    }


}
