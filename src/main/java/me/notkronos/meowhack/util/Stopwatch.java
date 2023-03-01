package me.notkronos.meowhack.util;

public class Stopwatch implements Passable
{
    private volatile long time;

    public boolean passed(double ms)
    {
        return System.currentTimeMillis() - time >= ms;
    }

    @Override
    public boolean passed(long ms)
    {
        return System.currentTimeMillis() - time >= ms;
    }

    public Stopwatch reset()
    {
        time = System.currentTimeMillis();
        return this;
    }

    public long getTime()
    {
        return System.currentTimeMillis() - time;
    }

    public void setTime(long ns)
    {
        time = ns;
    }

}