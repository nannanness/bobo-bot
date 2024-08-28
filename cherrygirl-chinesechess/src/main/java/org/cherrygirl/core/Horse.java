package org.cherrygirl.core;

/**
 * Horse马
 */
public class Horse extends Chess {
    @Override
    protected boolean isLegal(char dir, char where, Chess[][] map) {
        int cow = getCowByNum(where);
        int dy = cow - this.pos.y;
        int dx = 0;

        //马不可以平,也不可以越界
        if (dir == '平' || cow < 0) {
            return false;
        }
        //进
        else if ((dir == '进' && this.team.equals(Team.red)) || (dir == '退' && this.team.equals(Team.black))) {
            if (this.pos.x < 1) {
                return false;
            }
            //横着走
            if (dy == 2) {
                //撇脚
                if (map[this.pos.x][this.pos.y + 1] != null) {
                    return false;
                } else {
                    dx = -1;

                }
            } else if (dy == -2) {
                //撇脚
                if (map[this.pos.x][this.pos.y - 1] != null) {
                    return false;
                } else {
                    dx = -1;
                }
            } else if (dy == 1 || dy == -1) {
                if (this.pos.x < 2) {
                    return false;
                }
                //撇脚
                if (map[this.pos.x - 1][this.pos.y] != null) {
                    return false;
                } else {
                    dx = -2;
                }
            } else {
                return false;
            }
        } else if ((dir == '退' && this.team.equals(Team.red)) || (dir == '进' && this.team.equals(Team.black))) {
            if (this.pos.x > 8) {
                return false;
            }
            //横着走
            if (dy == 2) {
                //撇脚
                if (map[this.pos.x][this.pos.y + 1] != null) {
                    return false;
                } else {
                    dx = 1;

                }
            } else if (dy == -2) {
                //撇脚
                if (map[this.pos.x][this.pos.y - 1] != null) {
                    return false;
                } else {
                    dx = 1;
                }
            } else if (dy == 1 || dy == -1) {
                if (this.pos.x > 7) {
                    return false;
                }
                //撇脚
                if (map[this.pos.x + 1][this.pos.y] != null) {
                    return false;
                } else {
                    dx = 2;
                }
            } else {
                return false;
            }
        }

        this.info.new_x = this.pos.x + dx;
        this.info.new_y = this.pos.y + dy;
        return true;
    }


    public Horse(Team team, int x, int y) {
        super('马', team, new Vector2(x, y));
    }

}
