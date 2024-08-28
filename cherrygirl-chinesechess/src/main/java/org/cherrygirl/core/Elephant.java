package org.cherrygirl.core;

/**
 * Elephant象
 */
public class Elephant extends Chess {
    @Override
    protected boolean isLegal(char dir, char where, Chess[][] map) {
        int cow = getCowByNum(where);
        int dy = cow - this.pos.y;
        int dx = 0;

        //象不可以平,也不可以越界
        if (dir == '平' || cow < 0) {
            return false;
        }
        //进
        if (dir == '进' && this.team.equals(Team.red)) {
            dx = -2;
            if (dy == 2) {
                //撇脚
                if (map[this.pos.x - 1][this.pos.y + 1] != null) {
                    return false;
                }

            } else if (dy == -2) {
                //撇脚
                if (map[this.pos.x - 1][this.pos.y - 1] != null) {
                    return false;
                }

            } else {
                return false;
            }
        } else if (dir == '退' && this.team.equals(Team.red)) {
            if (this.pos.x > 7) {
                return false;
            }

            dx = 2;
            if (dy == 2) {
                //撇脚
                if (map[this.pos.x + 1][this.pos.y + 1] != null) {
                    return false;
                }

            } else if (dy == -2) {
                //撇脚
                if (map[this.pos.x + 1][this.pos.y - 1] != null) {
                    return false;
                }
            } else {
                return false;
            }
        } else if (dir == '进' && this.team.equals(Team.black)) {
            dx = 2;
            if (dy == 2) {
                //撇脚
                if (map[this.pos.x + 1][this.pos.y + 1] != null) {
                    return false;
                }

            } else if (dy == -2) {
                //撇脚
                if (map[this.pos.x + 1][this.pos.y - 1] != null) {
                    return false;
                }

            } else {
                return false;
            }
        } else if (dir == '退' && this.team.equals(Team.black)) {
            if (this.pos.x < 2) {
                return false;
            }

            dx = -2;
            if (dy == 2) {
                //撇脚
                if (map[this.pos.x - 1][this.pos.y + 1] != null) {
                    return false;
                }

            } else if (dy == -2) {
                //撇脚
                if (map[this.pos.x - 1][this.pos.y - 1] != null) {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }

        this.info.new_x = this.pos.x + dx;
        this.info.new_y = this.pos.y + dy;
        /*命令OK,下面作是否出界的判断*/
        if (this.team.equals(Team.red)) {
            if (this.info.new_x < 5) {
                this.info.out_local = true;
            }
        } else {
            if (this.info.new_x > 4) {
                this.info.out_local = true;
            }
        }
        return true;
    }

    public Elephant(Team team, int x, int y) {
        super(initName(team), team, new Vector2(x, y));
    }

    private static char initName(Team team) {
        if (team.equals(Team.red)) {
            return '相';
        } else {
            return '象';
        }
    }
}
