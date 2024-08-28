package org.cherrygirl.core;

public class Vector2 {
    public int x = 0;
    public int y = 0;

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2() {

    }
}
