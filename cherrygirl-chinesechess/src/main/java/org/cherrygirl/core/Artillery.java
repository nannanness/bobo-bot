package org.cherrygirl.core;

import static org.cherrygirl.core.Tool.log;

/**
 * Artillery炮
 */
public class Artillery extends Chess {
    @Override
    protected boolean isLegal(char dir, char where, Chess[][] map) {
        String fun_name = "isLegal(炮)";
        int cow = 0;
        int dis = 0;
        int idx = 0;
        int dx = 0;
        int dy = 0;
        int x = this.pos.x;
        int y = this.pos.y;
        int chess_count = 0;

        if (dir == '平') {
            cow = getCowByNum(where);
            if (cow < 0) {
                return false;
            }
            /*查找路径上有几颗棋子*/
            if (cow > y) {
                for (idx = y + 1; idx < cow; idx++) {
                    if (map[x][idx] != null) {
                        chess_count++;
                    }
                }
            } else if (cow < y) {
                for (idx = y - 1; idx > cow; idx--) {
                    if (map[x][idx] != null) {
                        chess_count++;
                    }
                }
            } else {
                return false;
            }
            log(Log.debug, fun_name, "炮翻山为" + chess_count);
            /*判断路径上的棋子数量是否合法*/
            /*为0则终点不能有棋子*/
            if (chess_count == 0) {
                if (map[x][cow] != null) {
                    log(Log.debug, fun_name, "非法：翻山为0终点有棋子");
                    return false;
                }
            }
            /*为1则终点有棋子*/
            else if (chess_count == 1) {
                if (map[x][cow] == null) {
                    log(Log.debug, fun_name, "非法：翻山为1终点没有棋子");
                    return false;
                }
            }
            /*超过1则不合法*/
            else {
                log(Log.debug, fun_name, "非法：翻山超过1");
                return false;
            }
            /*平只改变y坐标*/
            dy = cow - y;
        } else {
            dis = getDisByNum(where);
            if (dis < 0) {
                return false;
            }
            /*进退只改变x坐标*/
            if ((this.team.equals(Team.red) && dir == '进') || (this.team.equals(Team.black) && dir == '退')) {
                dx = -dis;
            } else {
                dx = dis;
            }
            /*是否移出棋盘*/
            if (x + dx < 0 || x + dx > 9) {
                log(Log.debug, fun_name, "非法：炮的目标点移出了棋盘");
                return false;
            }

            /*查找路径上有几颗棋子*/
            if (dx > 0) {
                for (idx = x + 1; idx < x + dx; idx++) {
                    if (map[idx][y] != null) {
                        log(Log.debug, fun_name, "炮翻过了" + map[idx][y].name);
                        chess_count++;
                    }
                }
            } else {
                for (idx = x - 1; idx > x + dx; idx--) {
                    if (map[idx][y] != null) {
                        log(Log.debug, fun_name, "炮翻过了" + map[idx][y].name);
                        chess_count++;
                    }
                }
            }

            log(Log.debug, fun_name, "炮翻山为" + chess_count);
            /*判断路径上的棋子数量是否合法*/
            /*为0则终点不能有棋子*/
            if (chess_count == 0) {
                if (map[x + dx][y] != null) {
                    log(Log.debug, fun_name, "非法：翻山为0终点有棋子");
                    return false;
                }
            }
            /*为1则终点有棋子*/
            else if (chess_count == 1) {
                if (map[x + dx][y] == null) {
                    log(Log.debug, fun_name, "非法：翻山为1终点没有棋子");
                    return false;
                }
            }
            /*超过1则不合法*/
            else {
                log(Log.debug, fun_name, "非法：翻山超过了1");
                return false;
            }
        }

        this.info.new_x = x + dx;
        this.info.new_y = y + dy;
        return true;
    }

    public Artillery(Team team, int x, int y) {
        super('炮', team, new Vector2(x, y));
    }
}
