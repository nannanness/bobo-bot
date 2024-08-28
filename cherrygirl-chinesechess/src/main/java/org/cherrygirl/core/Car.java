package org.cherrygirl.core;

/**
 * Car车
 */
public class Car extends Chess {
    public Car(Team team, int x, int y) {
        super('车', team, new Vector2(x, y));
    }


    @Override
    public boolean isLegal(char dir, char where, Chess[][] map) {
        int x = this.pos.x;
        int y = this.pos.y;
        int cow = 0;
        int idx = 0;
        int dx = 0;

        if (dir == '平') {
            cow = getCowByNum(where);
            if (cow < 0) {
                return false;
            }

            if (cow > y) {
                //路径上没有棋子
                for (idx = y + 1; idx < cow; idx++) {
                    if (map[x][idx] != null) {
                        return false;
                    }
                }
            } else if (cow == y) {
                return false;
            } else {
                //路径上没有棋子
                for (idx = y - 1; idx > cow; idx--) {
                    if (map[x][idx] != null) {
                        return false;
                    }
                }
            }

            this.info.new_x = x;
            this.info.new_y = cow;
            return true;
        }
        dx = getDisByNum(where);
        if (dx < 0) {
            return false;
        }
        if ((dir == '进' && this.team.equals(Team.red)) || (dir == '退' && this.team.equals(Team.black))) {
            if (x - dx < 0) {
                return false;
            }
            for (idx = x - 1; idx > x - dx; idx--) {
                if (map[idx][y] != null) {
                    return false;
                }
            }
            this.info.new_x = x - dx;
        } else {
            if (x + dx > 9) {
                return false;
            }
            for (idx = x + 1; idx < x + dx; idx++) {
                if (map[idx][y] != null) {
                    return false;
                }
            }
            this.info.new_x = x + dx;
        }
        this.info.new_y = y;

        return true;
    }


}
