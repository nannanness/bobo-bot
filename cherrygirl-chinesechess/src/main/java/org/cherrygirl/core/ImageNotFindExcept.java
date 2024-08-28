package org.cherrygirl.core;

public class ImageNotFindExcept extends ChessExcept {
    public ImageNotFindExcept(String notice) {
        super("[error : ImageNotFind] 图片没有找到:" + notice);
    }
}
