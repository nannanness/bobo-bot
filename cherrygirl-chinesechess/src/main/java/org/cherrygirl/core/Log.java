package org.cherrygirl.core;

enum Log {
    error(1, "[error]"),
    warn(2, "[warn]"),
    info(3, "[info]"),
    debug(4, "[debug]");

    private final int rank;
    private final String msg;

    Log(int rank, String msg) {
        this.rank = rank;
        this.msg = msg;
    }

    public int getRank() {
        return this.rank;
    }

    public String getMsg() {
        return this.msg;
    }
}
