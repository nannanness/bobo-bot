package org.cherrygirl.core;

public enum ChessStyle {
    defalt("默认");


    private String name;

    ChessStyle(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
