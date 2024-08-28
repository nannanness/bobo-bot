package org.cherrygirl.core;

public class Commander extends Chess {
    @Override
    protected boolean isLegal(char dir, char where, Chess[][] map) {
        int cow = 0;
        int dis = 0;
        int dx = 0;
        int dy = 0;
        int x = this.pos.x;
        int y = this.pos.y;
        int idx = 0;
        boolean out = false;

        if (dir == '平') {
            cow = getCowByNum(where);
            if (cow < 0) {
                return false;
            }
            dy = cow - y;
            /*将帅只能移动一格*/
            if (dy != -1 && dy != 1) {
                return false;
            }
            /*有没有离开皇宫*/
            if (cow < 3 || cow > 5) {
                out = true;
            }
        }
        /*"进"的情况*/
        else if (dir == '进') {
            dis = getDisByNum(where);
            if (this.team.equals(Team.red)) {
                /*只能进一格，到底了不能进*/
                if (dis != 1) {
                    /*对脸笑*/
                    int row = x - dis;
                    if (row < 0 || map[row][y] == null || !(map[row][y].name == '将')) {
                        return false;
                    } else {
                        /*目标棋子是对面的将，进行路径检测*/
                        for (idx = x - 1; idx > row; idx--) {
                            if (map[idx][y] != null) {
                                return false;
                            }
                        }

                        this.info.new_x = row;
                        this.info.new_y = y;
                        return true;
                    }
                }
                /*到底了不能进*/
                if (x < 1) {
                    return false;
                }
                /*标记出界*/
                else if (x < 8) {
                    out = true;
                }
                dis = -1;
            } else {
                /*只能进一格，到底了不能进*/
                if (dis != 1) {
                    /*对脸笑*/
                    int row = x + dis;
                    if (row > 9 || map[row][y] == null || !(map[row][y].name == '帅')) {
                        return false;
                    } else {
                        /*目标棋子是对面的帅，进行路径检测*/
                        for (idx = x + 1; idx < row; idx++) {
                            if (map[idx][y] != null) {
                                return false;
                            }
                        }

                        this.info.new_x = row;
                        this.info.new_y = y;
                        return true;
                    }
                }
                if (x > 8) {
                    return false;
                } else if (x > 1) {
                    out = true;
                }
                dis = 1;
            }
            dx = dis;
        }
        /*退*/
        else {
            dis = getDisByNum(where);
            /*只能进一格，到底了不能进*/
            if (dis != 1) {
                return false;
            }
            if (this.team.equals(Team.red)) {
                if (x > 8) {
                    return false;
                } else if (x < 6) {
                    out = true;
                }
                dis = 1;
            } else {
                if (x < 1) {
                    return false;
                } else if (x > 3) {
                    out = true;
                }
                dis = -1;
            }
            dx = dis;
        }

        this.info.new_x = x + dx;
        this.info.new_y = y + dy;
        this.info.out_local = out;
        return true;
    }

    private static char initName(Team team) {
        if (team.equals(Team.red)) {
            return '帅';
        } else {
            return '将';
        }
    }

    public Commander(Team team, int x, int y) {
        super(initName(team), team, new Vector2(x, y));
    }
}
