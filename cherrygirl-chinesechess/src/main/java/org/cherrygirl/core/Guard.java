package org.cherrygirl.core;

/**
 * Guard士
 */
public class Guard extends Chess {
    @Override
    protected boolean isLegal(char dir, char where, Chess[][] map) {
        int cow = getCowByNum(where);
        int dy = cow - this.pos.y;
        int dx = 1;

        if (dy != 1 && dy != -1) {
            return false;
        }
        //仕不可以平,也不可以越界
        if (dir == '平' || cow < 0) {
            return false;
        }

        if (this.team.equals(Team.red)) {
            if (dir == '进') {
                if (this.pos.x < 1) {
                    return false;
                }
                dx = -1;
            } else {
                if (this.pos.x > 8) {
                    return false;
                }
            }
        } else {
            if (dir == '退') {
                if (this.pos.x < 1) {
                    return false;
                }
                dx = -1;
            } else {
                if (this.pos.x > 8) {
                    return false;
                }
            }
        }


        this.info.new_x = this.pos.x + dx;
        this.info.new_y = cow;
        /*命令ok，开始判断是否出界*/
        if (cow > 5 || cow < 3) {
            this.info.out_local = true;
        } else if (this.team.equals(Team.red) && this.info.new_x < 7) {
            this.info.out_local = true;
        } else if (this.team.equals(Team.black) && this.info.new_x > 2) {
            this.info.out_local = true;
        }
        return true;
    }

    public Guard(Team team, int x, int y) {
        super(initName(team), team, new Vector2(x, y));
    }

    private static char initName(Team team) {
        if (team.equals(Team.red)) {
            return '仕';
        } else {
            return '士';
        }
    }
}
