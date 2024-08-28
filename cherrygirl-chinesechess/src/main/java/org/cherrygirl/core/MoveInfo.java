package org.cherrygirl.core;

/**
 * 记录移动前后的点，和移动后位置原有的棋子
 */
public class MoveInfo {
    int x;
    int y;
    int new_x;
    int new_y;
    Chess target;
    /*是否移出了被限制的区域，比如士象帅*/
    boolean out_local;
}
