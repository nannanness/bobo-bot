package org.cherrygirl.core;
/**
 * ChessExcept
 */
public class ChessExcept extends Exception
{
    protected String msg;

    public ChessExcept(String msg)
    {
        this.msg = msg;
    }

    @Override
    public final String toString() {
        return this.msg;
    }

    @Override
    public final void printStackTrace()
    {
        System.out.println(this.msg);
    }
}


