package org.cherrygirl.core;


public class Soldier extends Chess
{
    @Override
    protected boolean isLegal(char dir, char where, Chess[][] map)
    {
        int cow = 0;
        int dis = 0;
        int dx = 0;
        int dy = 0;
        int x = this.pos.x;
        int y = this.pos.y;
        if(dir == '退')
        {
            return false;
        }
        else if (dir == '平')
        {
            cow = getCowByNum(where);
            if(cow < 0)
            {
                return false;
            }
            /*未过河小兵不能平*/
            if((this.team.equals(Team.red) && x > 4) || (this.team.equals(Team.black) && x < 5))
            {
                return false;
            }

            dy = cow - y;
            /*小兵只能移动一格*/
            if (dy != -1 && dy !=1)
            {
                return false;
            }
        }
        /*只剩下"进"的情况了*/
        else if(this.team.equals(Team.red))
        {
            dis = getDisByNum(where);
            /*只能进一格，到底了不能进*/
            if (dis != 1 || x < 1)
            {
                return false;
            }
            dx = -1;
        }
        else if(this.team.equals(Team.black))
        {
            dis = getDisByNum(where);
            /*只能进一格，到底了不能进*/
            if (dis != 1 || x > 8)
            {
                return false;
            }
            dx = 1;
        }

        this.info.new_x = x + dx;
        this.info.new_y = y + dy;
        return true;
    }

    public Soldier(Team team, int x, int y)
    {
        super(initName(team), team, new Vector2(x,y));
    }

    private static char initName(Team team)
    {
        if (team.equals(Team.red))
        {
            return  '兵';
        }
        else
        {
            return  '卒';
        }
    }

}


