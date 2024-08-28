package org.cherrygirl.enumeration;

/**
 * @author nannanness
 */
public enum CloudMusicModeEnum {
    /**
     * 歌名
     */
    NAME("name"),
    /**
     * 歌手
     */
    SINGER("singer");
    private String mode;
    CloudMusicModeEnum(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }
}
